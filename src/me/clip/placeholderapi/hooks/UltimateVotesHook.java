package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import teozfrank.ultimatevotes.util.UltimateVotesAPI;

public class UltimateVotesHook extends IPlaceholderHook {
	
	public UltimateVotesHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

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
