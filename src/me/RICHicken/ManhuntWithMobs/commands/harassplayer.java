package me.RICHicken.ManhuntWithMobs.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.RICHicken.ManhuntWithMobs.Main;
import me.RICHicken.ManhuntWithMobs.utils.Helpers;
import net.md_5.bungee.api.ChatColor;

public class harassplayer implements CommandExecutor{
	Main plugin;
	
	public harassplayer(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player hunter = (Player) sender;
			
			if (args.length < 1) {
				hunter.sendMessage(ChatColor.RED + "You need to give the player name.");
				hunter.sendMessage(ChatColor.RED + "/harassplayer <player> \nOR\nharrass <player>");
			} else if (args.length > 1) {
				hunter.sendMessage(ChatColor.RED + "Too many arguments.");
				hunter.sendMessage(ChatColor.RED + "/harassplayer <player> \nOR\nharrass <player>");
			}else {
				Player target = Bukkit.getPlayerExact(args[0]);
				
				if(target instanceof Player) {
					Helpers.removeHunterTag(hunter);
					hunter.sendMessage(ChatColor.RED + "Time to harass " + target.getDisplayName());
					hunter.setGameMode(GameMode.SPECTATOR);
					hunter.addScoreboardTag("harassing: " + target.getDisplayName());
					hunter.setCanPickupItems(false);
					
					target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 0));
					target.sendMessage(ChatColor.RED + "You are now getting harassed by " + hunter.getDisplayName() + "!");
					
				}else {
					hunter.sendMessage(args[0] + " is not a player in the server.");
				}
			}			
			
		} else {
			System.out.println("You need to be a player to execute this command.");
		}
		
		return false;
	}
}
