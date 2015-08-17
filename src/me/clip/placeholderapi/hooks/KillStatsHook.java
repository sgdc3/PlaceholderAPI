package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.gmail.dejayyy.killStats.ksMain;

public class KillStatsHook extends IPlaceholderHook {

	public KillStatsHook(InternalHook hook) {
		super(hook);
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

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
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
}
