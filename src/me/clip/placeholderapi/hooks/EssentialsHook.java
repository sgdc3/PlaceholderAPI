package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;

public class EssentialsHook {
	
	private PlaceholderAPIPlugin plugin;
	
	private Essentials essentials = null;

	public EssentialsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
			
			essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
			
			if (essentials != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(essentials, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						switch (identifier) {
						
						case "nickname":
							return essentials.getUser(p).getNickname() != null ? essentials.getUser(p).getNickname() : p.getName();
						case "godmode":
							return essentials.getUser(p).isGodModeEnabled() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "jailed":
							return String.valueOf(essentials.getUser(p).isJailed());
						}
						
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into Essentials for placeholders!");
				}	
			}
		}
	}
	
}
