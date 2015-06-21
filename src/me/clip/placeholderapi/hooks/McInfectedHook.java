package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bimmr.mcinfected.IPlayers.IStats;
import com.bimmr.mcinfected.IPlayers.IStats.Stat;

public class McInfectedHook {

	private PlaceholderAPIPlugin plugin;

	public McInfectedHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("McInfected")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("McInfected", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {
					switch (identifier) {

					case "score":
						return getScore(p);
					case "kills":
						return getKills(p);
					case "deaths":
						return getDeaths(p);
					case "wins":
						return getWins(p);
					case "losses":
						return getLosses(p);
					case "killstreak":
						return getKillstreak(p);
					case "time":
						return getTime(p);

					}
					return null;
				}
			}, true);

			if (hooked) {
				plugin.log.info("Hooked into McInfected for placeholders!");
			}
		}
	}

	private String getScore(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Score));
	}

	private String getKills(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Kills));
	}

	private String getDeaths(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Deaths));
	}

	private String getWins(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Wins));
	}

	private String getLosses(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Losses));
	}

	private String getKillstreak(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.KillStreak));
	}

	private String getTime(Player p) {
		IStats stat = IStats.getStats(p.getName());
		if (stat == null) {
			return "";
		}
		return String.valueOf(stat.getStat(Stat.Time));
	}
}