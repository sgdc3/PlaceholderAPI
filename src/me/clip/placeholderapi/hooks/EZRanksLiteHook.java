package me.clip.placeholderapi.hooks;

import me.clip.ezrankslite.EZRanksLite;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EZRanksLiteHook {

	private PlaceholderAPIPlugin plugin;

	public EZRanksLiteHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("EZRanksLite")) {
			
			EZRanksLite ezr = (EZRanksLite) Bukkit.getPluginManager().getPlugin("EZRanksLite");
			
			if (ezr != null) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(ezr, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						switch (identifier) {
						
						case "balance":
							return getBalance(p);
						case "rank":
							return getRank(p);
						case "rankprefix":
							return getRankPrefix(p);
						case "nextrank":
							return getNextRank(p);
						case "nextrankprefix":
							return getNextRankPrefix(p);
						case "rankupcost":
							return getNextRankCost(p);							
						case "progress":
							return getProgress(p);
						case "progressbar":
							return getProgressBar(p);
						}
						
						return null;
					}
				});
			
				if (hooked) {
					plugin.log.info("Hooked into EZRanksLite for placeholders!");
				}
			}
		}
	}
	
	private String getRankPrefix(Player p) {
		if (EZRanksLite.getAPI().getRankData(p) == null) {
			return "";
		}
		return String.valueOf(EZRanksLite.getAPI().getRankData(p).getPrefix());
	}
	
	private String getRank(Player p) {
		if (EZRanksLite.getAPI().getCurrentRank(p) == null) {
			return "";
		}
		return String.valueOf(EZRanksLite.getAPI().getCurrentRank(p));
	}
	
	private String getNextRank(Player p) {
		if (EZRanksLite.getAPI().getCurrentRank(p) == null) {
			return "";
		}
		
		if (EZRanksLite.getInstance().getRankHandler().hasRankData(getRank(p))
				&& EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).hasRankups()) {
			
			String r = EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).getRankups().iterator().next().getRank();
			
			if (r == null) {
				return "";
			}
			return r;
		}
		
		return "";
	}
	
	private String getNextRankCost(Player p) {
		if (EZRanksLite.getAPI().getCurrentRank(p) == null) {
			return "0";
		}
		
		if (EZRanksLite.getInstance().getRankHandler().hasRankData(getRank(p))
				&& EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).hasRankups()) {
			
			
			String cost = EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).getRankups().iterator().next().getCost();
			
			if (cost == null) {
				return "0";
			}
			return cost;
		}
		
		return "0";
	}
	
	private String getNextRankPrefix(Player p) {
		if (EZRanksLite.getAPI().getCurrentRank(p) == null) {
			return "";
		}
		
		if (EZRanksLite.getInstance().getRankHandler().hasRankData(getRank(p))
				&& EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).hasRankups()) {
			
			
			String pre = EZRanksLite.getInstance().getRankHandler().getRankData(getRank(p)).getRankups().iterator().next().getPrefix();
			
			if (pre == null) {
				return "";
			}
			return pre;
		}
		
		return "";
	}
	
	private String getBalance(Player p) {
		if (EZRanksLite.getAPI().getFormattedBalance(p) == null) {
			return "0";
		}
		return String.valueOf(EZRanksLite.getAPI().getFormattedBalance(p));
	}
	
	private String getProgress(Player p) {	
		return String.valueOf(EZRanksLite.getAPI().getRankupProgress(p));
	}
	
	private String getProgressBar(Player p) {
		return EZRanksLite.getAPI().getRankupProgressBar(p);
	}
}
