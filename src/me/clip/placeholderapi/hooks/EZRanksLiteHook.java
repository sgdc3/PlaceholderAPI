package me.clip.placeholderapi.hooks;

import me.clip.ezrankslite.EZRanksLite;
import me.clip.ezrankslite.multipliers.CostHandler;
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
								public String onPlaceholderRequest(Player p, String identifier) {
									
									if (p == null) {
										return "";
									}
									
									Rankup r = Rankup.getRankup(p);
									
									String replacement = null;
									
									double cost = 0.0;
									
									switch (identifier) {

									case "player":
										replacement = p.getName();
										break;
									case "displayname":
										replacement = p.getDisplayName();
										break;
									case "world":
										replacement = p.getWorld().getName();
										break;
									case "rank":
									case "rankfrom":
									case "currentrank":
										replacement = r != null && r.getRank() != null ? r.getRank()
												: Rankup.isLastRank(p) && Rankup.getLastRank() != null
														&& Rankup.getLastRank().getRank() != null ? Rankup
														.getLastRank().getRank() : EZRanksLite.get().getPerms()
														.getPrimaryGroup(p) != null ? EZRanksLite.get().getPerms()
														.getPrimaryGroup(p) : "unknown";
										break;
									case "nextrank":
									case "rankto":
									case "rankup":
										replacement = r != null && r.getRankup() != null ? r
												.getRankup() : "none";
										break;
									case "cost":

										

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										long send = (long) cost;

										replacement = String.valueOf(send);
										break;
									case "cost_formatted":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = EcoUtil.fixMoney(cost);
										break;
									case "difference":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = String.valueOf(EcoUtil.getDifference(EZRanksLite.get().getEconomy().getBalance(p), cost));
										break;
									case "difference_formatted":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = EcoUtil.fixMoney(EcoUtil.getDifference(EZRanksLite.get().getEconomy().getBalance(p), cost));
										break;
									case "progress":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = String.valueOf(EcoUtil.getProgress(EZRanksLite.get().getEconomy().getBalance(p), cost));
										break;
									case "progressbar":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = EcoUtil.getProgressBar(EcoUtil.getProgressInt(EZRanksLite.get().getEconomy().getBalance(p), cost));
										break;
									case "progressexact":

										if (r != null) {

											cost = r.getCost();

											cost = CostHandler.getMultiplier(p, cost);

											cost = CostHandler.getDiscount(p, cost);
										}

										replacement = EcoUtil.getProgressExact(EZRanksLite.get().getEconomy().getBalance(p), cost);
										break;
									case "balance":
										replacement = String.valueOf(EZRanksLite.get().getEconomy().getBalance(p));
										break;
									case "balance_formatted":
										replacement = EcoUtil.fixMoney(EZRanksLite.get().getEconomy().getBalance(p));
										break;
									case "rankprefix":
									case "rank_prefix":
										replacement = r != null && r.getPrefix() != null ? r
												.getPrefix() : Rankup.isLastRank(p)
												&& Rankup.getLastRank() != null
												&& Rankup.getLastRank().getPrefix() != null ? Rankup
												.getLastRank().getPrefix() : "";
										break;
									case "lastrank":
									case "last_rank":
										replacement = Rankup.getLastRank() != null
												&& Rankup.getLastRank().getRank() != null ? Rankup
												.getLastRank().getRank() : "";
										break;
									case "lastrank_prefix":
									case "lastrankprefix":
										replacement = Rankup.getLastRank() != null
												&& Rankup.getLastRank().getPrefix() != null ? Rankup
												.getLastRank().getPrefix() : "";
										break;
									case "rankupprefix":
									case "rankup_prefix":
										if (r == null) {
											replacement = "";
											break;
										}
										if (Rankup.getRankup(r.getRankup()) == null) {
											replacement = Rankup.getLastRank() != null
													&& Rankup.getLastRank().getPrefix() != null ? Rankup
													.getLastRank().getPrefix() : "";
											break;
										}

										replacement = Rankup.getRankup(r.getRankup()).getPrefix() != null ? Rankup
												.getRankup(r.getRankup()).getPrefix() : "";
										break;
									}
									
									return replacement;
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
}
