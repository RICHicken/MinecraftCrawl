package me.RICHicken.ManhuntWithMobs.utils;

import java.util.Iterator;

import org.bukkit.entity.Player;

public class Helpers {
	public static String getHunterTarget(Player hunter) {
		Iterator<String> tags = hunter.getScoreboardTags().iterator();

		String tag;

		while (tags.hasNext()) {
			tag = tags.next();

			if (tag.toString().substring(0,10).equals("harassing:")) {

				return tag.substring(11);
			}
		}

		return "No target found";
	}
	
	public static boolean hasHunterTag(Player hunter) {
		Iterator<String> tags = hunter.getScoreboardTags().iterator();

		String tag;

		while (tags.hasNext()) {
			tag = tags.next();
			if (tag.toString().substring(0,10).equals("harassing:")) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean removeHunterTag(Player hunter) {
		Iterator<String> tags = hunter.getScoreboardTags().iterator();
		
		String tag;
		boolean found = false;
		while(tags.hasNext()) {
			tag = tags.next();
			if (tag.toString().substring(0,10).equals("harassing:")) {
				hunter.removeScoreboardTag(tag.toString());
				found = true;
			}
		}
		return found;
	}
}
