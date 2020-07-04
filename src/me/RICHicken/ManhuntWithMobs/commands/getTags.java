package me.RICHicken.ManhuntWithMobs.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.RICHicken.ManhuntWithMobs.Main;
import net.md_5.bungee.api.ChatColor;

public class getTags implements CommandExecutor{
	
	Main plugin;
	
	public getTags(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(ChatColor.RED + "Player not specified.");
				player.sendMessage(ChatColor.RED + "/gettags <player>");
			}
			else {
				Player person = Bukkit.getPlayerExact(args[0]);
				player.sendMessage(person.getDisplayName() +"'s tags: " + person.getScoreboardTags().toString());
			}
			
		} else {
			
			if(args.length == 0) {
				System.out.println("Player not specified.");
			}else {
			Player person = Bukkit.getPlayerExact(args[0]);
			System.out.println(person.getDisplayName() +"'s tags: " + person.getScoreboardTags().toString());
			}
			
		}
		return false;
	}
}
