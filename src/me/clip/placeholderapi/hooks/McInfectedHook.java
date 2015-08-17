package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.bimmr.mcinfected.IPlayers.IStats;
import com.bimmr.mcinfected.IPlayers.IStats.Stat;

public class McInfectedHook extends IPlaceholderHook {

	public McInfectedHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
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