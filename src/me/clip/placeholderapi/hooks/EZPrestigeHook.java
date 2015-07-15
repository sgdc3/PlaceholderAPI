package me.clip.placeholderapi.hooks;

import me.clip.ezprestige.EZPrestige;
import me.clip.ezprestige.PrestigeManager;
import me.clip.ezprestige.objects.Prestige;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EZPrestigeHook {

	private PlaceholderAPIPlugin plugin;
	
	public EZPrestigeHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("EZPrestige")) {

			EZPrestige ezprestige = (EZPrestige) Bukkit.getPluginManager().getPlugin("EZPrestige");

			if (ezprestige != null) {

				boolean hooked = PlaceholderAPI.registerPlaceholderHook(
						ezprestige, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								
								if (p == null) {
									return "";
								}
								
								switch (identifier) {
								
								case "prestige":
									return getCurrentPrestige(p);
								case "prestigetag":
									return getCurrentPrestigeTag(p);
								case "nextprestige":
									return getNextPrestige(p);
								case "nextprestigetag":
									return getNextPrestigeTag(p);
								}
								
								return null;
							}
						}, true);

				if (hooked) {
					plugin.log.info("Hooked into EZPrestige for placeholders!");
				}
			}
		}
	}
	
	private String getCurrentPrestigeTag(Player p) {
		

		Prestige pr = PrestigeManager.getCurrentPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return pr.getDisplayTag();
	}

	private String getCurrentPrestige(Player p) {
		
		Prestige pr = PrestigeManager.getCurrentPrestige(p);
		
		if (pr == null) {
			return "0";
		}
		
		return String.valueOf(pr.getPrestige());
	}
	
	private String getNextPrestigeTag(Player p) {
		
		Prestige pr = PrestigeManager.getNextPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return pr.getDisplayTag();
	}

	private String getNextPrestige(Player p) {
		
		Prestige pr = PrestigeManager.getNextPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return String.valueOf(pr.getPrestige());
	}

}
