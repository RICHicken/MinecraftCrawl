package me.RICHicken.ManhuntWithMobs.utils;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class Drops {

	public static void dropItems(World world, Location loc, EntityType mob) {
		LivingEntity entity = (LivingEntity) world.spawnEntity(loc, mob);
		entity.damage(100);
	}

	public static void dropEndermanEndItems(World w, Location l){
		Material[] drops = new Material[]{Material.OAK_LOG, Material.BREAD, Material.ARROW};

		w.dropItemNaturally(l, new ItemStack(drops[new Random().nextInt(3)], 3));

		if(Math.random() <= .2){
			w.dropItemNaturally(l, new ItemStack(Material.IRON_INGOT));
		}

	}
}

