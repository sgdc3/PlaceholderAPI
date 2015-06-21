package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.dejayyy.killStats.ksMain;

public class KillStatsHook {

	private PlaceholderAPIPlugin plugin;
	
	public KillStatsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("killStats")) {
			
			ksMain pl = (ksMain) Bukkit.getPluginManager().getPlugin("killStats");
			
			if (pl != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(pl, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						switch (identifier) {
						
						case "kills":
							return getKills(p);
						case "killrank":
							return getKillsRank(p);
						case "deaths":
							return getDeaths(p);
						case "deathrank":
							return getDeathsRank(p);
						case "streak":
							return getStreak(p);
						case "streakrank":
							return getStreakRank(p);
						case "kdr":
							return getKDR(p);
						case "kdrrank":
							return getKDRRank(p);
						}
						return null;
					}
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into killStats for placeholders!");
				}
			}
		}
	}
	
	private String getKills(Player p) {
		return String.valueOf(ksMain.api.getKills(p));
	}
	
	private String getKillsRank(Player p) {
		return String.valueOf(ksMain.api.getKillsRank(p));
	}
	
	private String getDeaths(Player p) {
		return String.valueOf(ksMain.api.getDeaths(p));
	}
	
	private String getDeathsRank(Player p) {
		return String.valueOf(ksMain.api.getDeathsRank(p));
	}
	
	private String getKDR(Player p) {
		return String.valueOf(ksMain.api.getRatio(p));
	}
	
	private String getKDRRank(Player p) {
		return String.valueOf(ksMain.api.getRatioRank(p));
	}
	
	private String getStreak(Player p) {
		return String.valueOf(ksMain.api.getStreak(p));
	}
	
	private String getStreakRank(Player p) {
		return String.valueOf(ksMain.api.getStreakRank(p));
	}
	
	
}
