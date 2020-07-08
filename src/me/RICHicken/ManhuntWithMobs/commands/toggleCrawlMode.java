package me.RICHicken.ManhuntWithMobs.commands;

import me.RICHicken.ManhuntWithMobs.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class toggleCrawlMode implements CommandExecutor {


    Main plugin;

    public toggleCrawlMode(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;

            if(plugin.getConfig().getString("CrawlMode") == "true"){
                player.sendMessage(ChatColor.LIGHT_PURPLE + ("Crawl Mode is now " ) + ChatColor.RED + "off!");
                plugin.getConfig().set("CrawlMode", "false");
            }else if(plugin.getConfig().getString("CrawlMode") == "false") {
                player.sendMessage(ChatColor.LIGHT_PURPLE + ("Crawl Mode is now ") + ChatColor.GREEN + "on!");
                plugin.getConfig().set("CrawlMode", "true");
            }
            plugin.saveDefaultConfig();
        }
        return true;
    }
}
