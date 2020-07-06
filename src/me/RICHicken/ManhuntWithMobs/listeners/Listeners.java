package me.RICHicken.ManhuntWithMobs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.RICHicken.ManhuntWithMobs.Main;
import me.RICHicken.ManhuntWithMobs.utils.Drops;
import me.RICHicken.ManhuntWithMobs.utils.Helpers;
import me.RICHicken.ManhuntWithMobs.utils.Items;
import me.RICHicken.ManhuntWithMobs.utils.Utils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import net.md_5.bungee.api.ChatColor;

import java.security.DigestException;

public class Listeners implements Listener {

	private static Main plugin;

	public Listeners(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		Player hunter = event.getPlayer();
		if (Helpers.hasHunterTag(hunter) && !DisguiseAPI.isDisguised(hunter)) {
			if (!Utils.transform(event.getRightClicked(), hunter)) {
				hunter.sendMessage(ChatColor.AQUA + "You can't transform into that mob!");
			}

		} else if (DisguiseAPI.isDisguised(hunter)) {
			if (DisguiseAPI.getDisguise(hunter).getType() == DisguiseType.WITHER_SKELETON && hunter.getInventory().getItemInMainHand().getType() == Material.WITHER_SKELETON_SKULL) {
				if (event.getRightClicked() instanceof LivingEntity) {
					((LivingEntity) event.getRightClicked())
							.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0));
					Utils.putItemOnCooldown(hunter, plugin, Items.witherSkeleInflictWitherItem(), 200);
				} else {
					hunter.sendMessage("You can't wither this!");
				}
			} else if (DisguiseAPI.getDisguise(hunter).getType() == DisguiseType.CAVE_SPIDER && hunter.getInventory().getItemInMainHand().getType() == Material.SPIDER_EYE) {
				if (event.getRightClicked() instanceof LivingEntity) {
					Utils.caveSpiderPoison((LivingEntity) event.getRightClicked());
					Utils.putItemOnCooldown(hunter, plugin, Items.caveSpiderPoison(), 300);
				} else {
					hunter.sendMessage("You can't poison this!");
				}
			}
		}
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent event) {
		if (DisguiseAPI.isDisguised(event.getEntity())) {
			Player hunter = (Player) event.getEntity();

			hunter.getInventory().remove(new ItemStack(Material.ARROW));
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					Utils.giveArrow(hunter);
				}
			}, 40L);

		}
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event){
		Player player = event.getPlayer();

		if(DisguiseAPI.isDisguised(player) && player.getInventory().getItemInMainHand().getType().equals(Material.POTATO)){
			player.setFoodLevel(16);
		}
		//if the player has the glowing effect, give it back after they drink milk.
		else if(player.hasPotionEffect(PotionEffectType.GLOWING) && player.getInventory().getItemInMainHand().getType().equals(Material.MILK_BUCKET)){
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 0));
					}
				}, 3L);
		}

		//use this to give doppleganger weakness if they drink milk.
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		//prevents mob from dropping items
		if(DisguiseAPI.isDisguised(player)){
			player.sendMessage(ChatColor.DARK_PURPLE + "You can't drop items as a mob!");
			event.setCancelled(true);
		}


	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (Helpers.hasHunterTag(player) && DisguiseAPI.isDisguised(player)) {
			// save location of player death
			Location playerLocation = player.getLocation();

			// clear player's inventory

			event.getDrops().clear();
			event.setDroppedExp(5);
			// drop mob loot from loot table
			EntityType playerEntityDisguise = DisguiseAPI.getDisguise(player).getType().getEntityType();
			switch (playerEntityDisguise){
				case ENDERMAN:
//					for(ItemStack i : player.getInventory().getContents()){
//						if(i.getType() != Material.AIR && i.getType().isBlock() && (i.getType() != Material.BARRIER)){
//							player.getWorld().dropItemNaturally(playerLocation, i);
//						}
//					}
					break;
				default:
					break;
			}

			Drops.dropItems(player.getWorld(), playerLocation, playerEntityDisguise);
			player.sendMessage("you made it");

			// make player's gamemode into spectator
			player.setFlySpeed(.1f);
			player.setAllowFlight(false);
			player.setGameMode(GameMode.SPECTATOR);
			// have a special death message when player dies

			event.setDeathMessage(ChatColor.RED + "Player Mob Killed!");

		} else if (player.hasPotionEffect(PotionEffectType.GLOWING)) {
			Bukkit.broadcastMessage(ChatColor.AQUA + player.getDisplayName() + " lost!");
		}
	}

	@EventHandler
	public void hunterRespawn(PlayerRespawnEvent event) {
		Player hunter = event.getPlayer();
		Location deathLocation = hunter.getLocation();

		//teleports player back to where they died
		if (Helpers.hasHunterTag(hunter)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					hunter.teleport(deathLocation);
				}
			}, 3L);
		}

		if (DisguiseAPI.isDisguised(hunter)) {
			DisguiseAPI.undisguiseToAll(hunter);
		}

	}

	@EventHandler
	public void stopAggroOnHunter(EntityTargetEvent event) {
		if (DisguiseAPI.isDisguised(event.getTarget())) {
			event.setTarget(null);
		}
	}

	@EventHandler
	public void OnRightClick(PlayerInteractEvent event) {
		if (DisguiseAPI.isDisguised(event.getPlayer())) {
			Action action = event.getAction();
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				Player hunter = event.getPlayer();
				if (hunter.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
					hunter.damage(100000);
				} else if (DisguiseAPI.isDisguised(event.getPlayer()) && action == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().isInteractable()) {
					//stop mobs from interacting with objects (chests, furnaces, levers, doors, etc.)

					event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Mobs can't interact with objects!");
					event.setCancelled(true);

				} else {

					switch (DisguiseAPI.getDisguise(hunter).getType().getEntityType()) {
						case BLAZE:
							switch (hunter.getInventory().getItemInMainHand().getType()) {
								case BLAZE_ROD:

									Utils.shootBlazeFireballVolley(hunter, plugin);
									Utils.putItemOnCooldown(hunter, plugin, Items.blazeFireBallItem(), 160);

									break;
								case FEATHER:
									hunter.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 55, 2));
									Utils.putItemOnCooldown(hunter, plugin, Items.blazeJump(), 100);

									break;
								default:
									break;
							}
							break;

						case CREEPER:
							if (hunter.getInventory().getItemInMainHand().getType() == Material.GUNPOWDER) {
								Utils.creeperExplode(hunter);
							}
							break;

						case ENDERMAN:
							if (hunter.getInventory().getItemInMainHand().getType() == Material.WOODEN_PICKAXE) {
								if (action == Action.RIGHT_CLICK_BLOCK) {
									Block block = event.getClickedBlock();
									Location loc = block.getLocation();
									if (Utils.endermanGiveBlock(hunter, block.getType(), loc)) {
										hunter.getInventory().removeItem(Items.endermanPickUpBlockItem());
									} else {
										hunter.sendMessage(ChatColor.LIGHT_PURPLE + "You can not pick up this block!");
									}
								}

							} else if (hunter.getInventory().getItemInMainHand().getType().isBlock() && hunter.getInventory().getItemInMainHand().getType() != Material.AIR) {
								ItemStack selectedBlock = hunter.getInventory().getItemInMainHand();
								hunter.getLocation().getBlock().setType(selectedBlock.getType());
								hunter.getInventory().removeItem(selectedBlock);

								Utils.giveItemBackAfter(hunter, plugin, Items.endermanPickUpBlockItem(), 40);
							} else if (hunter.getInventory().getItemInMainHand().getType() == Material.POPPED_CHORUS_FRUIT) {
								Utils.endermanTeleport(hunter);
								hunter.getInventory().removeItem(Items.endermanTeleportItem());

							}
							break;

						case GHAST:
							if (hunter.getInventory().getItemInMainHand().getType() == Material.GHAST_TEAR) {

								hunter.launchProjectile(Fireball.class);
								Utils.putItemOnCooldown(hunter, plugin, Items.ghastFireBallItem(), 45);

							}
							break;

						case PHANTOM:
							if (hunter.getInventory().getItemInMainHand().getType() == Material.PHANTOM_MEMBRANE) {
								hunter.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 12));
								Utils.putItemOnCooldown(hunter, plugin, Items.phantomJumpItem(), 160);
							}
							break;

						case SPIDER:
						case CAVE_SPIDER:

							if (hunter.getInventory().getItemInMainHand().getType() == Material.LADDER &&
									(hunter.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR ||
											hunter.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR ||
											hunter.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR ||
											hunter.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR)) {

								Utils.spiderClimb(hunter, plugin);
							}
							break;

						default:
							break;
					}
				}
/*
			 if (hunter.getInventory().getItemInMainHand().getType() == Material.GUNPOWDER) {
				Utils.creeperExplode(hunter);

			} else if (DisguiseAPI.getDisguise(hunter).getType().equals(DisguiseType.ENDERMAN)) {
				if (hunter.getInventory().getItemInMainHand().getType() == Material.WOODEN_PICKAXE) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						Block block = event.getClickedBlock();
						Location loc = block.getLocation();
						if (Utils.endermanGiveBlock(hunter, block.getType(), loc)) {
							hunter.getInventory().removeItem(Items.endermanPickUpBlockItem());
						} else {
							hunter.sendMessage(ChatColor.LIGHT_PURPLE + "You can not pick up this block!");
						}
					}

				} else if (hunter.getInventory().getItemInMainHand().getType().isBlock() && hunter.getInventory().getItemInMainHand().getType() != Material.AIR) {
					ItemStack selectedBlock = hunter.getInventory().getItemInMainHand();
					hunter.getLocation().getBlock().setType(selectedBlock.getType());
					hunter.getInventory().removeItem(selectedBlock);

					Utils.giveItemBackAfter(hunter, plugin, Items.endermanPickUpBlockItem(), 80);
				} else if (hunter.getInventory().getItemInMainHand().getType() == Material.POPPED_CHORUS_FRUIT) {
					Utils.endermanTeleport(hunter);
					hunter.getInventory().removeItem(Items.endermanTeleportItem());

				}
			} else if (DisguiseAPI.getDisguise(hunter).getType().equals(DisguiseType.BLAZE)) {

				switch (hunter.getInventory().getItemInMainHand().getType()) {
					case BLAZE_ROD:

						Utils.shootBlazeFireballVolley(hunter, plugin);
						Utils.putItemOnCooldown(hunter, plugin, Items.blazeFireBallItem(), 160);

						break;
					case FEATHER:
						hunter.setVelocity(new Vector(0, 1.5, 0));
						Utils.putItemOnCooldown(hunter, plugin, Items.blazeJump(), 100);

						break;
				}

			} else if (DisguiseAPI.getDisguise(hunter).getType().equals(DisguiseType.GHAST)
					&& hunter.getInventory().getItemInMainHand().getType() == Material.GHAST_TEAR) {

				hunter.launchProjectile(Fireball.class);
				Utils.putItemOnCooldown(hunter, plugin, Items.ghastFireBallItem(), 45);

			} else if (hunter.getInventory().getItemInMainHand().getType() == Material.PHANTOM_MEMBRANE
					&& DisguiseAPI.getDisguise(hunter).getType().equals(DisguiseType.PHANTOM)) {
				hunter.setVelocity(new Vector(0, 2.5, 0));
				Utils.putItemOnCooldown(hunter, plugin, Items.phantomJumpItem(), 160);
			}
			*/
			}
		}
	}
}
