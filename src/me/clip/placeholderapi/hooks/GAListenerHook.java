package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.swifteh.GAL.GAL;
import com.swifteh.GAL.VoteAPI;

public class GAListenerHook {

	private PlaceholderAPIPlugin plugin;
	
	public GAListenerHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("GAListener")) {
				
			GAL ga = (GAL) Bukkit.getPluginManager().getPlugin("GAListener");
			
			if (ga != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(ga, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						if (identifier.equals("votes")) {
							return getVotes(p);
						}
						return null;
					}
				});
				
				if (hooked) {
					plugin.log.info("Hooked into GAListener for placeholders!");
				}
			}
			
		}
	}
	
	private String getVotes(Player p) {
		return String.valueOf(VoteAPI.getVoteTotal(p));
	}
}
