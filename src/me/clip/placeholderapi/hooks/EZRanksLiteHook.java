package me.clip.placeholderapi.hooks;

import me.clip.ezrankslite.EZRanksLite;
import me.clip.ezrankslite.rankdata.Rankup;
import me.clip.ezrankslite.util.EcoUtil;
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
				
				if (ezr.getDescription().getVersion().startsWith("2.")) {

					boolean hooked = PlaceholderAPI.registerPlaceholderHook(
							ezr, new PlaceholderHook() {

								@Override
								public String onPlaceholderRequest(Player p,
										String identifier) {

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
									}

									return null;
								}
							}, true);

					if (hooked) {
						plugin.log.info("Hooked into EZRanksLite for placeholders!");
					}
				} else {
					plugin.log.info("This version of PlaceholderAPI is only compatible with EZRanksLite 2.0 or higher!");
				}
			}
		}
	}
	
	private String getRankPrefix(Player p) {
		if (Rankup.getRankup(p)== null) {
			
			if (Rankup.isLastRank(p)) {
				return Rankup.getLastRank().getPrefix();
			}
			
			return "";
		}
		return Rankup.getRankup(p).getPrefix();
	}
	
	private String getRank(Player p) {
		if (Rankup.getRankup(p) == null) {
			if (Rankup.isLastRank(p)) {
				return Rankup.getLastRank().getRank();
			}
			return "";
		}
		return Rankup.getRankup(p).getRank();
	}
	
	private String getNextRank(Player p) {
		if (Rankup.getRankup(p) == null) {
			return "";
		}
		
		return Rankup.getRankup(p).getRankup();
	}
	
	private String getNextRankCost(Player p) {
		if (Rankup.getRankup(p) == null) {
			return "";
		}
		
		return EcoUtil.fixMoney(Rankup.getRankup(p).getCost());
	}
	
	private String getNextRankPrefix(Player p) {
		if (Rankup.getRankup(p) == null) {
			return "";
		}
		
		String rank = Rankup.getRankup(p).getRankup();
		
		if (Rankup.getRankup(rank) == null) {
			return "";
		}
		
		return Rankup.getRankup(rank).getPrefix();
	}
	
	private String getBalance(Player p) {
		return EcoUtil.fixMoney(EZRanksLite.get().getEconomy().getBalance(p));
	}
}
