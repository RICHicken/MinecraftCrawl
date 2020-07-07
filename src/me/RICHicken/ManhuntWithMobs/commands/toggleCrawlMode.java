package me.RICHicken.ManhuntWithMobs.commands;

import me.RICHicken.ManhuntWithMobs.Main;
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
                plugin.getConfig().set("CrawlMode", "false");
            }else if(plugin.getConfig().getString("CrawlMode") == "false"){
                plugin.getConfig().set("CrawlMode", "true");
            }
        }
        return true;
    }
}
