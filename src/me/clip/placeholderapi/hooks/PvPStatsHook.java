package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import net.slipcor.pvpstats.PVPData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PvPStatsHook {

	private PlaceholderAPIPlugin plugin;

	public PvPStatsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("pvpstats")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("pvpstats", new PlaceholderHook() {

						@Override
						public String onPlaceholderRequest(Player p, String identifier) {
							
							if (p == null) {
								return "";
							}

							switch (identifier) {

							case "kills":
								return getKills(p);
							case "deaths":
								return getDeaths(p);
							case "elo":
								return getElo(p);
							case "killstreak":
								return getStreak(p);
							case "maxstreak":
								return getMaxStreak(p);
							}
							return null;
						}
					}, true);

			if (hooked) {
				plugin.log.info("Hooked into pvpstats for placeholders!");
			}
		}
	}

	private String getKills(Player p) {
		return String.valueOf(PVPData.getKills(p.getName()));
	}

	private String getDeaths(Player p) {
		return String.valueOf(PVPData.getDeaths(p.getName()));
	}

	private String getElo(Player p) {
		return String.valueOf(PVPData.getEloScore(p.getName()));
	}

	private String getStreak(Player p) {
		return String.valueOf(PVPData.getStreak(p.getName()));
	}

	private String getMaxStreak(Player p) {
		return String.valueOf(PVPData.getMaxStreak(p.getName()));
	}

} 
	

