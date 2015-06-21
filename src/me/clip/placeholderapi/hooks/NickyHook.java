package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.nonit.nicky.Nicky;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NickyHook {

	private PlaceholderAPIPlugin plugin;

	public NickyHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("Nicky")) {
			
			Nicky nicky = (Nicky) Bukkit.getPluginManager().getPlugin("Nicky");
			
			if (nicky != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(nicky, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						if (identifier.equals("nickname")) {
							return getNickname(p);
						}
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into Nicky for nicknames!");
				}
			}
		}
	}

	private String getNickname(Player p) {

		if (Nicky.getNickDatabase() != null
				&& Nicky.getNickDatabase().downloadNick(p.getUniqueId().toString()) != null) {
			
			return Nicky.getNickDatabase().downloadNick(p.getUniqueId().toString());
		}
		return p.getName();
	}
}
