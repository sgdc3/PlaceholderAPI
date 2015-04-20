package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPointsHook {
	
	private PlaceholderAPIPlugin plugin;
	
	public PlayerPointsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	private PlayerPoints playerPoints;


	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
			
			PlayerPoints points = (PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints");
		    
			if (points != null) {
				
				playerPoints = points;
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(points, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {

						if (identifier.equals("points")) {
							return getPoints(p);
						}
						
						return null;
					}
				});
				
				if (hooked) {
					plugin.log.info("Hooked into PlayerPoints for placeholders!");
				}
			}
		}
	}
	
	public boolean enabled() {
		return playerPoints != null;
	}
	
	private String getPoints(Player p) {
		if (!enabled()) {
			return "0";
		}
		return String.valueOf(playerPoints.getAPI().look(p.getUniqueId()));
	}
}
