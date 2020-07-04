package me.RICHicken.ManhuntWithMobs.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.RICHicken.ManhuntWithMobs.Main;
import me.RICHicken.ManhuntWithMobs.utils.Helpers;

public class stopharass implements CommandExecutor{
	Main plugin;
	
	public stopharass(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player hunter = (Player) sender;
			String target = Helpers.getHunterTarget(hunter);

			
			boolean foundtags = Helpers.removeHunterTag(hunter);
			
			if(foundtags) {
				hunter.sendMessage(ChatColor.GREEN + "You are no longer harassing anyone.");
				hunter.setCanPickupItems(true);
			}
			else {
				hunter.sendMessage(ChatColor.LIGHT_PURPLE + "You haven't been harassing anyone");
			}
		}else {
			System.out.println("Sir, you are a console.");
		}
	return false;
	}
	
}
