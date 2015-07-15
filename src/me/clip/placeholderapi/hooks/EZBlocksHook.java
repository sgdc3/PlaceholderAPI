package me.clip.placeholderapi.hooks;

import me.clip.ezblocks.EZBlocks;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EZBlocksHook {

	private PlaceholderAPIPlugin plugin;

	public EZBlocksHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("EZBlocks")) {
			
			EZBlocks ezblocks = (EZBlocks) Bukkit.getPluginManager().getPlugin("EZBlocks");
			
			if (ezblocks != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(ezblocks, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}
						
						switch(identifier) {
						
						
						case "broken":
						case "blocks":
						case "blocks broken":
							return String.valueOf(EZBlocks.getEZBlocks().getBlocksBroken(p));
						}
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into EZBlocks for placeholders!");
				}
			}
		}
	}
}
