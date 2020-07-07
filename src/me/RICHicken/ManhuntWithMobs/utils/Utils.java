package me.RICHicken.ManhuntWithMobs.utils;

import java.util.Random;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.RICHicken.ManhuntWithMobs.Main;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.Vector;

public class Utils {
	
	public static boolean transform(Entity e, Player hunter) {
		EntityType mob = e.getType();
		/*
		EntityType[] mobs = new EntityType[] {EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, 
				EntityType.ENDERMAN, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.SILVERFISH,
				EntityType.SKELETON, EntityType.SPIDER, EntityType.ZOMBIE, EntityType.PIG_ZOMBIE,
				EntityType.WITHER_SKELETON, EntityType.PHANTOM, EntityType.DROWNED};
		boolean success = false;
		for(EntityType i : mobs) {
			
			if(i.equals(mob)) {
				success = true;
			}
		}
		
		if(!success) {
			return false;
		}*/
		
		MobDisguise m;
		hunter.getInventory().clear();
		hunter.setGameMode(GameMode.ADVENTURE);

		switch (mob) {
			case BLAZE:
				m = blaze(hunter);
				break;
			case CAVE_SPIDER:
				m = caveSpider(hunter);
				break;
			case CREEPER:
				m = creeper(hunter);
				break;
			case ENDERMAN:
				m = enderman(hunter);
				break;
			case GHAST:
				m = ghast(hunter);
				break;
			case MAGMA_CUBE:
				m = magmaCube(hunter);
				break;
			case SILVERFISH:
				m = silverfish(hunter);
				break;
			case SKELETON:
				m = skeleton(hunter);
				break;
			case SPIDER:
				m = spider(hunter);
				break;
			case ZOMBIE:
			case ZOMBIE_VILLAGER:
				m = zombie(hunter);
				break;
			case DROWNED:
				m = drowned(hunter);
				break;
			case PHANTOM:
				m = phantom(hunter);
				break;
			case WITHER_SKELETON:
				m = witherSkele(hunter);
				break;
			case PIG_ZOMBIE:
				m = zombiePigman(hunter);
				break;
			case WITCH:
				m = witch(hunter);
				break;
			default:

				hunter.setGameMode(GameMode.SPECTATOR);
				return false;
		}
		
		e.remove();

		hunter.sendMessage(ChatColor.RED + "You are now a " + mob.toString() + "!");
		m.setSelfDisguiseVisible(false);
		m.setEntity(hunter);
		m.startDisguise();
	

		hunter.setFoodLevel(16);
		hunter.getInventory().addItem(new ItemStack(Material.POTATO, 64));
		hunter.getInventory().addItem(Items.escapeBody());
		
		return true;
	}
	
	private static MobDisguise witherSkele(Player hunter) {
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 99999, 0));
		hunter.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		hunter.getInventory().addItem(Items.witherSkeleInflictWitherItem());
		
		return new MobDisguise(DisguiseType.WITHER_SKELETON);
		
	}
	private static MobDisguise drowned(Player hunter) {
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
		hunter.getInventory().setBoots(Items.depthStriderBoots());
		zombie(hunter);
		return new MobDisguise(DisguiseType.DROWNED);
	}
	
	private static MobDisguise phantom(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.STONE_AXE));
		hunter.getInventory().setChestplate(Items.phantomWings());
		hunter.getInventory().addItem(Items.phantomJumpItem());
		
		return new MobDisguise(DisguiseType.PHANTOM);
	}
	
	private static MobDisguise blaze(Player hunter) {
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 5));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
		
		hunter.getInventory().addItem(Items.blazeFireBallItem());
		hunter.getInventory().addItem(Items.blazeJump());
		
		return new MobDisguise(DisguiseType.BLAZE);		
	}
	
	private static MobDisguise caveSpider(Player hunter) {
		spider(hunter);
		hunter.getInventory().addItem(Items.caveSpiderPoison());
		hunter.getInventory().setBoots(Items.healthModBoots(12));
		//hunter.damage(8);

		return new MobDisguise(DisguiseType.CAVE_SPIDER);	
	}
	
	private static MobDisguise creeper(Player hunter) {
		hunter.getInventory().addItem(Items.creeperExplodeItem());
		return new MobDisguise(DisguiseType.CREEPER);	
	}
	
	private static MobDisguise enderman(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		hunter.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
		hunter.getInventory().addItem(Items.endermanPickUpBlockItem());
		hunter.getInventory().setBoots(Items.healthModBoots(40));

		//hunter.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999, 4));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 4));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 99999, 0));
		
		return new MobDisguise(DisguiseType.ENDERMAN);	
	}
	
	private static MobDisguise ghast(Player hunter) {
		hunter.setAllowFlight(true);
		hunter.setFlySpeed(.025f);
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
		hunter.getInventory().addItem(Items.ghastFireBallItem());
		hunter.getInventory().setBoots(Items.healthModBoots(2));
		//hunter.damage(18);

		return new MobDisguise(DisguiseType.GHAST);	
	}
	
	private static MobDisguise magmaCube(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 150));
		hunter.getInventory().setBoots(Items.healthModBoots(16));
		//hunter.damage(4);

		return new MobDisguise(DisguiseType.MAGMA_CUBE);	
	}
	
	private static MobDisguise silverfish(Player hunter) {
		hunter.getInventory().setBoots(Items.healthModBoots(8));
		//hunter.damage(12);
		return new MobDisguise(DisguiseType.SILVERFISH);	
	}
	
	private static MobDisguise skeleton(Player hunter) {
		hunter.getInventory().addItem(Items.bow());
		giveArrow(hunter);

		return new MobDisguise(DisguiseType.SKELETON);	
	}
	
	private static MobDisguise spider(Player hunter) {
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 99999, 0));
		hunter.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		hunter.getInventory().addItem(Items.spiderClimb());
		hunter.getInventory().setBoots(Items.healthModBoots(16));
		//hunter.damage(4);

		return new MobDisguise(DisguiseType.SPIDER);	
	}
	
	private static MobDisguise zombie(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		hunter.getInventory().addItem(Items.zombieAxe());
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0));
		
		return new MobDisguise(DisguiseType.ZOMBIE);
	}
	
	private static MobDisguise zombiePigman(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.GOLDEN_SWORD));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0));
		hunter.getInventory().setBoots(Items.healthModBoots(10));
		//hunter.damage(10);

		return new MobDisguise(DisguiseType.PIG_ZOMBIE);
	}

	private static MobDisguise witch(Player hunter){
		hunter.getInventory().addItem(Items.witchPotionFireWater());
		hunter.getInventory().addItem(Items.witchPotionHarming());
		hunter.getInventory().addItem(Items.witchPotionHealing());
		hunter.getInventory().addItem(Items.witchPotionPoison());
		hunter.getInventory().addItem(Items.witchPotionSlowness());
		hunter.getInventory().addItem(Items.witchPotionWeakness());
		hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 1));

		return new MobDisguise(DisguiseType.WITCH);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	public static void giveArrow(Player hunter) {
		hunter.getInventory().addItem(new ItemStack(Material.ARROW));
	}
	
	public static void putItemOnCooldown(Player player, Main plugin, ItemStack item, long cooldownTime) {
		player.getInventory().removeItem(item);
		
		giveItemBackAfter(player, plugin, item, cooldownTime);

	}
	
	
	public static void giveItemBackAfter(Player player, Main plugin, ItemStack item, long cooldownTime) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				player.getInventory().addItem(item);
			}
		}, cooldownTime);
	}
	
	public static void shootBlazeFireballVolley(Player hunter, Main plugin) {

		Runnable activity = new Runnable() {
			public void run() {
				hunter.launchProjectile(SmallFireball.class);
			}
		};
		
		hunter.launchProjectile(SmallFireball.class);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, activity, 6L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, activity, 6L);
	}
	
	public static void endermanTeleport(Player hunter) {
		Location start = hunter.getLocation();
		Location loc;
		Random r = new Random();
		
		for(int i = 1; i <= 16; i++) {
		loc = start.add(new Location(hunter.getWorld(), r.nextInt(65)-32 , r.nextInt(65)-32, r.nextInt(65)-32));
			if(!loc.getBlock().getType().isSolid()) {
				while(!loc.getBlock().getType().isSolid()) {
					loc.setY(loc.getY()-1);
				}
				loc.setY(loc.getY()+1);
				hunter.teleport(loc);
				return;
			}
		}
		
		hunter.sendMessage(ChatColor.LIGHT_PURPLE + "Your teleport failed! Unlucky!");		
	}
	
	public static void caveSpiderPoison(LivingEntity target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0));
	}
	
	public static void creeperExplode(Player hunter) {
		Creeper creeper = (Creeper) hunter.getWorld().spawnEntity(hunter.getLocation(), EntityType.CREEPER);
		creeper.ignite();
		hunter.damage(100000);
	}
	
	private static Material[] notPickupable() {
		Material[] notPickupable = new Material[] {Material.BEDROCK, Material.END_PORTAL_FRAME, 
				Material.END_PORTAL, Material.OBSIDIAN, Material.SPAWNER, Material.NETHER_PORTAL};
		
		return notPickupable;
	
	}
	
	public static boolean endermanGiveBlock(Player enderman, Material block, Location blockLocation) {
		
/*
		for(Material m : Utils.notPickupable()) {
			if(block == m) return false;
		}
*/
		switch(block){
			case BEDROCK:
			case END_PORTAL_FRAME:
			case END_PORTAL:
			case OBSIDIAN:
			case SPAWNER:
			case NETHER_PORTAL:
			case CHEST:
			case FURNACE:
			case SMOKER:
			case BLAST_FURNACE:

				return false;

			default:
				break;

		}

		enderman.getInventory().addItem(new ItemStack(block));
		
		blockLocation.getBlock().setType(Material.AIR);
		
		return true;
	}

	public static void spiderClimb(Player player, Main plugin){
		if(player.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR ||
				player.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR ||
				player.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR ||
				player.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR)
		{
			player.setVelocity(new Vector(player.getVelocity().getX(), 0.3, player.getVelocity().getZ()));
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					spiderClimb(player, plugin);
				}
			}, 5l);
		}

		/*
		(Math.abs(Math.round(Math.abs(player.getLocation().getX() - Math.abs(player.getLocation().getBlockX())) * 10)) == 3 ||
				Math.abs(Math.round(Math.abs(player.getLocation().getX() - Math.abs(player.getLocation().getBlockX())) * 10)) == 7 ||
				Math.abs(Math.round(Math.abs(player.getLocation().getZ()) - Math.abs(player.getLocation().getBlockZ()) * 10)) == 3 ||
				Math.abs(Math.round(Math.abs(player.getLocation().getZ()) - Math.abs(player.getLocation().getBlockZ()) * 10)) == 7)
		 */
	}

	public static void dropWitchItems(int i1, int i2, int i3, World w, Location l){
		Random r = new Random();
		int count = r.nextInt(3);
		Material[] drops = new Material[]{Material.GLASS_BOTTLE, Material.GLOWSTONE_DUST, Material.GUNPOWDER,
				Material.REDSTONE, Material.SPIDER_EYE, Material.SUGAR, Material.STICK};

		if (count != 0){
			w.dropItemNaturally(l, new ItemStack(drops[i1], count));
		}

		if (count != 0){
			w.dropItemNaturally(l, new ItemStack(drops[i2], count));
		}

		if (count != 0){
			w.dropItemNaturally(l, new ItemStack(drops[i3], count));
		}

	}
}
