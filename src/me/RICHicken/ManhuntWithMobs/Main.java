package me.RICHicken.ManhuntWithMobs;

import org.bukkit.plugin.java.JavaPlugin;

import me.RICHicken.ManhuntWithMobs.commands.disguisecheck;
import me.RICHicken.ManhuntWithMobs.commands.getTags;
import me.RICHicken.ManhuntWithMobs.commands.harassplayer;
import me.RICHicken.ManhuntWithMobs.commands.stopharass;
import me.RICHicken.ManhuntWithMobs.listeners.Listeners;


//Manhunt but with mobs!
public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		this.getCommand("harassplayer").setExecutor(new harassplayer(this));
		this.getCommand("gettags").setExecutor(new getTags(this));
		this.getCommand("stopharass").setExecutor(new stopharass(this));
		this.getCommand("disguisecheck").setExecutor(new disguisecheck(this));
		new Listeners(this);
	}
}
