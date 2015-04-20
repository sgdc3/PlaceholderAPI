package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.germanelectronix.simplecoins.SimpleCoinsAPI;

public class SimpleCoinsAPIHook {

	private PlaceholderAPIPlugin plugin;
	
	public SimpleCoinsAPIHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("SimpleCoinsAPI")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("SimpleCoinsAPI", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {

					if (identifier.equals("coins")) {
						return getCoins(p);
					}
					return null;
				}
				
			});
			
			if (hooked) {
				plugin.log.info("Hooked into QuickSell for placeholders!");
			}
		}
	}
	
	private String getCoins(Player p) {
		return String.valueOf(SimpleCoinsAPI.getCoins(p));
	}
}
