package me.RICHicken.ManhuntWithMobs;

import me.RICHicken.ManhuntWithMobs.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import me.RICHicken.ManhuntWithMobs.listeners.Listeners;


public class Main extends JavaPlugin{
	@Override
	public void onEnable() {

		getConfig().addDefault("CrawlMode", "true");
    	getConfig().options().copyDefaults();
		saveDefaultConfig();

		this.getCommand("harassplayer").setExecutor(new harassplayer(this));
		this.getCommand("gettags").setExecutor(new getTags(this));
		this.getCommand("stopharass").setExecutor(new stopharass(this));
		this.getCommand("disguisecheck").setExecutor(new disguisecheck(this));
		this.getCommand("togglecrawlmode").setExecutor(new toggleCrawlMode(this));
		new Listeners(this);
	}
}
