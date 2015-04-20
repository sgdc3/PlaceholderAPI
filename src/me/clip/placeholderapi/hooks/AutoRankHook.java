package me.clip.placeholderapi.hooks;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.api.API;
import me.armar.plugins.autorank.util.AutorankTools;
import me.armar.plugins.autorank.util.AutorankTools.Time;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AutoRankHook {
	
	private PlaceholderAPIPlugin plugin;
	
	private API autorank;

	public AutoRankHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("Autorank")) {
			
			Autorank ar = (Autorank) Bukkit.getPluginManager().getPlugin("Autorank");
			
			if (ar != null) {
				
				autorank = ar.getAPI();
				
				if (autorank == null) {
					return;
				}
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(ar, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						switch(identifier) {
						
						case "current_rank":
							return autorank.getPrimaryGroup(p) != null ? autorank.getPrimaryGroup(p) : "";
						case "next_rank":
							return autorank.getNextRankupGroup(p) != null ? autorank.getNextRankupGroup(p) : "";
						case "time_of_player":
							return String.valueOf(autorank.getTimeOfPlayer(p));
						case "local_time":
							String localTime = AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()), Time.MINUTES);
							return localTime != null ? localTime : ""; 
						case "global_time":
							String globalTime = AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()), Time.MINUTES);
							return globalTime != null ? globalTime : ""; 
						
						}
					
						return null;
					}
					
				});
				
				if (hooked) {
					plugin.log.info("Hooked into Autorank for placeholders!");
				}
			}
		}
	}
}
