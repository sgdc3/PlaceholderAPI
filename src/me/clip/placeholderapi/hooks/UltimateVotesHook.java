package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import teozfrank.ultimatevotes.util.UltimateVotesAPI;

public class UltimateVotesHook {

	private PlaceholderAPIPlugin plugin;
	
	public UltimateVotesHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("UltimateVotes")) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("UltimateVotes", new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {

								switch (identifier) {
								
								case "monthlyvotes":
									return getMonthly(p);
								case "totalvotes":
									return getTotal(p);
								case "hasvoted":
									return hasVoted(p);
								
								}
								return null;
							}
						}, true);

				if (hooked) {
					plugin.log.info("Hooked into UltimateVotes for placeholders!");
				}
			}
	}

	
	private String getMonthly(Player p) {
		return String.valueOf(UltimateVotesAPI.getPlayerMonthlyVotes(p.getUniqueId()));
	}
	private String getTotal(Player p) {
		return String.valueOf(UltimateVotesAPI.getPlayerTotalVotes(p.getUniqueId()));
	}
	private String hasVoted(Player p) {
		return UltimateVotesAPI.hasVotedToday(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}

}
