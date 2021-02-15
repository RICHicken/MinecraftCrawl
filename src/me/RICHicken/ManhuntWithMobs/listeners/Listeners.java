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
import org.bukkit.inventory.PlayerInventory;
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
import java.util.Iterator;

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
			if (DisguiseAPI.getDisguise(hunter).getType() == DisguiseType.WITHER_SKELETON
					&& hunter.getInventory().getItemInMainHand().getType() == Material.WITHER_SKELETON_SKULL) {
				if (event.getRightClicked() instanceof LivingEntity) {
					((LivingEntity) event.getRightClicked())
							.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0));
					Utils.putItemOnCooldown(hunter, plugin, Items.witherSkeleInflictWitherItem(), 200);
				} else {
					hunter.sendMessage("You can't wither this!");
				}
			} else if (DisguiseAPI.getDisguise(hunter).getType() == DisguiseType.CAVE_SPIDER
					&& hunter.getInventory().getItemInMainHand().getType() == Material.SPIDER_EYE) {
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

			switch (DisguiseAPI.getDisguise(hunter).getType()) {
			case PILLAGER:
				Utils.giveItemBackAfter(hunter, plugin, new ItemStack(Material.ARROW), 100);
				break;
			case SKELETON:
				Utils.putItemOnCooldown(hunter, plugin, new ItemStack(Material.ARROW), 40);
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();

		// if the player has the glowing effect, give it back after they drink milk.
		if (player.hasPotionEffect(PotionEffectType.GLOWING)
				&& player.getInventory().getItemInMainHand().getType().equals(Material.MILK_BUCKET)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 0));
				}
			}, 3L);
		}

		// use this to give doppleganger weakness if they drink milk.
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		// prevents mob from dropping items
		if (DisguiseAPI.isDisguised(player)) {
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

			// clear hunter's inventory

			event.getDrops().clear();
			event.setDroppedExp(5);
			// drop mob loot from loot table
			EntityType playerEntityDisguise = DisguiseAPI.getDisguise(player).getType().getEntityType();
			switch (playerEntityDisguise) {
			case ENDERMAN:
				if(player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
					Drops.dropEndermanEndItems(player.getWorld(), playerLocation);
				}
				break;
			default:
				break;
			}

			Drops.dropItems(player.getWorld(), playerLocation, playerEntityDisguise);

			// make hunter's gamemode into spectator
			player.setFlySpeed(.1f);
			player.setAllowFlight(false);
			player.setGameMode(GameMode.SPECTATOR);

			// have a special death message when hunter dies
			event.setDeathMessage(ChatColor.RED + "Player Mob Killed!");

			// A player has died
		} else if (player.hasPotionEffect(PotionEffectType.GLOWING)) {
			// Bodyswap time
			if (plugin.getConfig().getString("CrawlMode") == "true") {
				Player hunter = player;
				// Find a suitable ghost to take the player's place
				if (player.getKiller() != null) {
					hunter = player.getKiller();
				}
				if (hunter == player) {

					Iterator<Player> playerIterator = player.getWorld().getPlayers().iterator();
					Player closestPlayer = player;
					double lowestDistance = -1;

					while (playerIterator.hasNext()) {
						Player p = playerIterator.next();
						if (!p.isDead() && Helpers.hasHunterTag(p)) {
							closestPlayer = p;
							lowestDistance = p.getLocation().distance(player.getLocation());
						}
					}

					if (lowestDistance != -1) {
						Bukkit.broadcastMessage("Closest ghost is " + closestPlayer.getDisplayName() + ", who was "
								+ (int)lowestDistance + "m away");
					}
					hunter = closestPlayer;
				}

				// Bodyswap variables
				Location playerLocation = player.getLocation();
				PlayerInventory playerInventory = player.getInventory();

				// If nobody is swapping, our job is simple
				if (hunter.equals(player)) {

					// player.teleport(playerLocation);
					event.getDrops().clear();
					player.setHealth(20);
					player.setFoodLevel(20);
					event.setKeepInventory(true);
					event.setDeathMessage(ChatColor.GREEN + player.getDisplayName() + 
							" has died, but nobody was around to claim the kill!");

					// Otherwise it ain't so simple
				} else {
					// Hunter to Player

                    hunter.getInventory().setContents(playerInventory.getContents());

                    if(playerLocation.getY() >= 0){
						hunter.teleport(playerLocation);
					}else{
                    	hunter.teleport(new Location(hunter.getWorld(),0, 90, 0));
					}
					hunter.setFoodLevel(20);
					// Undisguise if disguised
					if (DisguiseAPI.isDisguised(hunter)) {
						DisguiseAPI.undisguiseToAll(hunter);
					}
					Helpers.removeHunterTag(hunter);
					for (PotionEffect i : hunter.getActivePotionEffects()) {
						hunter.removePotionEffect(i.getType());
					}
					hunter.setFlySpeed(0.1f);
					hunter.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 0));
					hunter.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 200));
					hunter.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0));
					hunter.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100, 200));
					hunter.setGameMode(GameMode.SURVIVAL);
					event.setDeathMessage((ChatColor.GREEN + player.getDisplayName()) + " has died and swapped with "
							+ (ChatColor.GREEN + hunter.getDisplayName()) + "!");

					// Player to Hunter
					event.getDrops().clear();
					Helpers.giveHunterTag(player, hunter);
				}
			} else {
				event.setDeathMessage(ChatColor.AQUA + player.getDisplayName() + " died!");
			}
		}
	}

	@EventHandler
	public void playerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Location deathLocation = player.getLocation();

		// Teleport player back to where they died
		if (Helpers.hasHunterTag(player) || plugin.getConfig().getBoolean("CrawlMode")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.teleport(deathLocation), 3L);
		}

		// Undisguise if disguised
		if (DisguiseAPI.isDisguised(player)) {
			DisguiseAPI.undisguiseToAll(player);
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
				} else if (DisguiseAPI.isDisguised(event.getPlayer()) && action == Action.RIGHT_CLICK_BLOCK
						&& event.getClickedBlock().getType().isInteractable()) {
					// stop mobs from interacting with objects (chests, furnaces, levers, doors,
					// etc.)

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

						} else if (hunter.getInventory().getItemInMainHand().getType().isBlock()
								&& hunter.getInventory().getItemInMainHand().getType() != Material.AIR) {
							ItemStack selectedBlock = hunter.getInventory().getItemInMainHand();
							hunter.getLocation().getBlock().setType(selectedBlock.getType());
							hunter.getInventory().removeItem(selectedBlock);

							Utils.giveItemBackAfter(hunter, plugin, Items.endermanPickUpBlockItem(), 40);
						} else if (hunter.getInventory().getItemInMainHand()
								.getType() == Material.POPPED_CHORUS_FRUIT) {
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

						if (hunter.getInventory().getItemInMainHand().getType() == Material.LADDER && (hunter
								.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR
								|| hunter.getLocation().getBlock().getRelative(BlockFace.SOUTH)
										.getType() != Material.AIR
								|| hunter.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR
								|| hunter.getLocation().getBlock().getRelative(BlockFace.WEST)
										.getType() != Material.AIR)) {

							Utils.spiderClimb(hunter, plugin);
						}
						break;
					case WITCH:
						if (hunter.getInventory().getItemInMainHand().getType() == Material.SPLASH_POTION) {
							long cooldown = 40;

							if (hunter.getInventory().getItemInMainHand() == Items.witchPotionHarming()) {
								cooldown = 80;
							}

							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									hunter.getInventory().remove(Material.SPLASH_POTION);
								}
							}, 1);

							Utils.giveItemBackAfter(hunter, plugin, Items.witchPotionHarming(), cooldown);
							Utils.giveItemBackAfter(hunter, plugin, Items.witchPotionHealing(), cooldown);
							Utils.giveItemBackAfter(hunter, plugin, Items.witchPotionPoison(), cooldown);
							Utils.giveItemBackAfter(hunter, plugin, Items.witchPotionSlowness(), cooldown);
							Utils.giveItemBackAfter(hunter, plugin, Items.witchPotionWeakness(), cooldown);
						}
					default:
						break;
					}
				}
			}
		}
	}

	public void useBed(PlayerBedEnterEvent event) {
		boolean setDay = true;
		Player player = event.getPlayer();
		if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK
				&& player.hasPotionEffect(PotionEffectType.GLOWING)) {
			for (Player i : player.getWorld().getPlayers()) {
				if (i.hasPotionEffect(PotionEffectType.GLOWING) && !i.isSleeping()) {
					setDay = false;
				}
			}
		}

		if (setDay) {
			player.getWorld().setTime(0);
		}
	}
}
