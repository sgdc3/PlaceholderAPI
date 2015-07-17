package me.clip.placeholderapi.hooks;

import me.clip.minecrates.MineCrates;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MineCratesHook {

	private PlaceholderAPIPlugin plugin;
	
	public MineCratesHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("MineCrates")) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("MineCrates", new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								
								if (identifier.equalsIgnoreCase("counter")) {
									return String.valueOf(MineCrates.getBreakCounter());
								} else if (identifier.equalsIgnoreCase("total_needed")) {
									return String.valueOf(MineCrates.getMaxBreaks());
								}
								return null;
							}

						}, true);

				if (hooked) {
					plugin.log.info("Hooked into MineCrates for placeholders!");
				}
			}
	}
}