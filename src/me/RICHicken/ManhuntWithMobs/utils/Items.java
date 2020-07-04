package me.RICHicken.ManhuntWithMobs.utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.Item;

import org.bukkit.inventory.meta.Damageable;

public class Items {
	public static ItemStack bow() {
		ItemStack bow = new ItemStack(Material.BOW);

		bow.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemMeta meta = bow.getItemMeta();
		meta.setDisplayName("Skeleton Bow");
		bow.setItemMeta(meta);

		return bow;
	}

	public static ItemStack zombieAxe() {
		ItemStack sword = new ItemStack(Material.WOODEN_AXE);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemMeta meta = sword.getItemMeta();
		meta.setDisplayName("Zombie Axe");
		sword.setItemMeta(meta);

		return sword;

	}

	public static ItemStack droppedSkeleBow() {
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta meta = bow.getItemMeta();
		Random r = new Random();

		((Damageable) meta).setDamage(r.nextInt(381));

		bow.setItemMeta(meta);

		return bow;
	}
	
	public static ItemStack droppedPigmanSword() {
		ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = sword.getItemMeta();
		
		((Damageable) meta).setDamage(new Random().nextInt(32));
		
		sword.setItemMeta(meta);
		return sword;
	}

	public static ItemStack rareZombieDrops() {
		Material[] items = new Material[] { Material.IRON_INGOT, Material.CARROT, Material.POTATO };
		
		return new ItemStack(items[new Random().nextInt(3)]);
	}
	
	public static ItemStack escapeBody() {
		ItemStack barrier = new ItemStack(Material.BARRIER);
		
		ItemMeta meta = barrier.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Leave Body");		
		
		barrier.setItemMeta(meta);
		
		return barrier;
	}
	///////BLAZE
	
	public static ItemStack blazeFireBallItem() {
		ItemStack rod = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = rod.getItemMeta();
		
		meta.setDisplayName(ChatColor.YELLOW + "Fireball Thrower");
		
		rod.setItemMeta(meta);
		
		return rod;
		
	}
	
	public static ItemStack blazeJump() {
		ItemStack feather = new ItemStack(Material.FEATHER);
		ItemMeta meta = feather.getItemMeta();
		
		meta.setDisplayName(ChatColor.YELLOW + "Blaze Launch");
		
		feather.setItemMeta(meta);
		return feather;
	}
	
	///////////////////GHAST
	
	public static ItemStack ghastFireBallItem() {
		ItemStack item = new ItemStack(Material.GHAST_TEAR);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Fireball Thrower");
		item.setItemMeta(meta);
		
		return item;
	}
	
	///////////////ENDERMAN
	public static ItemStack endermanTeleportItem() {
		ItemStack teleportItem = new ItemStack(Material.POPPED_CHORUS_FRUIT);
		ItemMeta meta = teleportItem.getItemMeta();
		
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport");
		teleportItem.setItemMeta(meta);
		
		return teleportItem;
	
	}
	
	public static ItemStack endermanPickUpBlockItem() {
		ItemStack pick = new ItemStack(Material.WOODEN_PICKAXE);
		ItemMeta meta = pick.getItemMeta();
		
		meta.setDisplayName("Pick up block");
		pick.setItemMeta(meta);
		
		return pick;
		
	}

	/////////////////////////// SPIDER

	public static ItemStack spiderClimb() {
		ItemStack ladder = new ItemStack(Material.LADDER);
		ItemMeta meta = ladder.getItemMeta();

		meta.setDisplayName("Climb");
		ladder.setItemMeta(meta);

		return ladder;

	}

	/////////////////////////// CAVE SPIDER
	
	public static ItemStack caveSpiderPoison() {
		ItemStack spiderEye = new ItemStack(Material.SPIDER_EYE);
		ItemMeta meta = spiderEye.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + "Inflict Poison");
		spiderEye.setItemMeta(meta);
		
		return spiderEye;
		
	}
	
	/////////////////////CREEPER
	
	public static ItemStack creeperExplodeItem() {
		ItemStack gunpowder = new ItemStack(Material.GUNPOWDER);
		ItemMeta meta = gunpowder.getItemMeta();
		
		meta.setDisplayName(ChatColor.RED + "EXPLODE");
		gunpowder.setItemMeta(meta);
		
		return gunpowder;
	}
	
	
	//////////////////DROWNED
	
	public static ItemStack depthStriderBoots() {
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.addEnchantment(Enchantment.DEPTH_STRIDER, 2);
		
		return boots;
	}
	
	/////////////////PHANTOM
	
	public static ItemStack phantomJumpItem() {
		ItemStack jump = new ItemStack(Material.PHANTOM_MEMBRANE);
		ItemMeta meta = jump.getItemMeta();
		
		meta.setDisplayName(ChatColor.AQUA + "Phantom Jump");
		jump.setItemMeta(meta);
		
		return jump;
	}

	public static ItemStack phantomWings(){
		ItemStack wings = new ItemStack(Material.ELYTRA);
		ItemMeta meta = wings.getItemMeta();

		meta.setUnbreakable(true);
		wings.setItemMeta(meta);

		return wings;
	}
	///////////////WITHER SKELETON
	
	public static ItemStack witherSkeleInflictWitherItem() {
		ItemStack inflict = new ItemStack(Material.WITHER_SKELETON_SKULL);
		ItemMeta meta = inflict.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GRAY + "Inflict Wither");
		inflict.setItemMeta(meta);
		
		return inflict;
	}
}
