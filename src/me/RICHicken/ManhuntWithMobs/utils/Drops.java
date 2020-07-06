package me.RICHicken.ManhuntWithMobs.utils;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class Drops {

	public static void dropItems(World world, Location loc, EntityType mob) {
		switch (mob) {
			case BLAZE:
				blaze(world, loc);
				break;
			case CREEPER:
				creeper(world, loc);
				break;
			case ENDERMAN:
				enderman(world, loc);
				break;
			case GHAST:
				ghast(world, loc);
				break;
			case MAGMA_CUBE:
				magmaCube(world, loc);
				break;
			case SKELETON:
				skeleton(world, loc);
				break;
			case SPIDER:
			case CAVE_SPIDER:
				spider(world, loc);
				break;
			case ZOMBIE:
				zombie(world, loc);
				break;
			case PIG_ZOMBIE:
				zombiePigman(world, loc);
				break;
			case PHANTOM:
				phantom(world, loc);
				break;
			case WITHER_SKELETON:
				witherSkele(world, loc);
				break;
			case DROWNED:
				drowned(world, loc);
				break;
			case WITCH:
				witch(world, loc);
			default:
				break;
		}
	}
	
	private static void phantom(World w, Location l) {
		int count = new Random().nextInt(3);
		
		if (count != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.PHANTOM_MEMBRANE, count));
		}
	}
	
	private static void witherSkele(World w, Location l) {
		Random r = new Random();
		int count = r.nextInt(3);
		
		if(count != 0) w.dropItemNaturally(l, new ItemStack(Material.BONE, count));
		
		if(r.nextInt(3) == 0) w.dropItemNaturally(l, new ItemStack(Material.COAL));
		
		if(Math.random() < .025) w.dropItemNaturally(l, new ItemStack(Material.WITHER_SKELETON_SKULL));
		
		if(Math.random() < .085) w.dropItem(l, new ItemStack(Material.STONE_SWORD));
	}
	
	private static void drowned(World w, Location l) {
		int count = new Random().nextInt(3);
		
		if (count != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.ROTTEN_FLESH, count));
		}
		
		if(Math.random() <= .05) w.dropItemNaturally(l, new ItemStack(Material.GOLD_INGOT));
	}
	
	private static void blaze(World w, Location l) {
		if(new Random().nextBoolean()) {
			w.dropItemNaturally(l,  new ItemStack(Material.BLAZE_ROD));
		}
	}
	
	private static void creeper(World w, Location l){
		int current = new Random().nextInt(3);
		
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.GUNPOWDER, current));
		}
	}
	
	private static void enderman(World w, Location l){
		if(new Random().nextBoolean()) {
			w.dropItemNaturally(l, new ItemStack(Material.ENDER_PEARL));
		}
	}

	private static void ghast(World w, Location l){
		Random r = new Random();
		int current = r.nextInt(3);
		
		if(r.nextBoolean()) {
			w.dropItemNaturally(l, new ItemStack(Material.GHAST_TEAR));
		}
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.GUNPOWDER, current));
		}
	}
	
	private static void magmaCube(World w, Location l) {
		if(new Random().nextBoolean()) {
			w.dropItemNaturally(l,new ItemStack(Material.MAGMA_CREAM));
		}
	}
	
	private static void spider(World w, Location l) {
		Random r = new Random();
		int current = r.nextInt(3);
		
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.STRING, current));
		}
		
		if(r.nextInt(3) == 0) {
			w.dropItemNaturally(l, new ItemStack(Material.SPIDER_EYE));
		}
	}
	
	private static void zombiePigman(World w, Location l) {
		Random r = new Random();
		
		if (r.nextBoolean()) {
			w.dropItemNaturally(l, new ItemStack(Material.ROTTEN_FLESH));
		}
		
		if(r.nextBoolean()) {
			w.dropItemNaturally(l, new ItemStack(Material.GOLD_NUGGET));
		}
		
		if(Math.random() <= .025) {
			w.dropItemNaturally(l, new ItemStack(Material.GOLD_INGOT));
		}
		
		if(Math.random() <= .085) {
			w.dropItemNaturally(l, Items.droppedPigmanSword());
		}
		
	}

	private static void skeleton(World w, Location l) {

		Random r = new Random();
		int current = r.nextInt(3);
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.BONE, current));
		}
		current = r.nextInt(3);
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.ARROW, current));
		}

		if (Math.random() <= .1) {
			ItemStack bow = Items.droppedSkeleBow();
			w.dropItemNaturally(l, bow);
		}
	}
	
	private static void zombie(World w, Location l) {
		
		Random r = new Random();
		int current = r.nextInt(3);
		
		if(current != 0) {
			w.dropItemNaturally(l, new ItemStack(Material.ROTTEN_FLESH, current));
		}
		
		if(Math.random() <= 0.25) {
			w.dropItemNaturally(l, Items.rareZombieDrops());
		}
	}

	private static void witch(World w, Location l) {
		Random r = new Random();
		for(int i=0; i<6; i++) {
			if(Math.random() <= 0.125) {

			}
		}
	}
}
