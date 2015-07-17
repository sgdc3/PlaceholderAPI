package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.voteparty.VoteParty;
import me.clip.voteparty.VotePartyAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VotePartyHook {

	private PlaceholderAPIPlugin plugin;
	
	public VotePartyHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("VoteParty")) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("VoteParty", new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								
								
								
								if (identifier.equalsIgnoreCase("counter")) {
									return String.valueOf(VotePartyAPI.getCurrentVoteCounter());
								} else if (identifier.equalsIgnoreCase("votes")) {
									return String.valueOf(VotePartyAPI.getVotes());
								} else if (identifier.equalsIgnoreCase("total_votes_needed")) {
									return String.valueOf(VotePartyAPI.getTotalVotesNeeded());
								} else if (identifier.equalsIgnoreCase("is_ignoring")) {
									if (p == null) {
										return "";
									}
									return VoteParty.getInstance().ignoringParty(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
								}
								return null;
							}

						}, true);

				if (hooked) {
					plugin.log.info("Hooked into VoteParty for placeholders!");
				}
			
		}
	}
}
