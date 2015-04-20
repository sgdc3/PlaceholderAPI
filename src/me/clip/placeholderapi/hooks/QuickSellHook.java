package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.mrCookieSlime.QuickSell.boosters.Booster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QuickSellHook {

	private PlaceholderAPIPlugin plugin;

	public QuickSellHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("QuickSell")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("QuickSell", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {

					if (identifier.equals("booster")) {
						return getBooster(p);
					}
					if (identifier.equals("booster_time")) {
						return getBoosterTimeLeft(p);
					}
					return null;
				}
				
			});
			
			if (hooked) {
				plugin.log.info("Hooked into QuickSell for placeholders!");
			}
		}
	}


	public String getBooster(Player p) {
		if (Booster.getBoosters(p.getName()) != null && !Booster.getBoosters(p.getName()).isEmpty()) {
			double m = 0;
			for (Booster b : Booster.getBoosters(p.getName())) {
				m = m+b.getMultiplier();
			}
			return String.valueOf(m);
		}
		return "0";
	}
	
	public String getBoosterTimeLeft(Player p) {
		if (Booster.getBoosters(p.getName()) != null && !Booster.getBoosters(p.getName()).isEmpty()) {
			for (Booster b : Booster.getBoosters(p.getName())) {
				return String.valueOf(b.formatTime());
			}
		}
		return "";
	}
}
