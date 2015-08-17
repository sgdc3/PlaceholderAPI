package me.clip.placeholderapi.hooks;

import me.bimmr.mcinfectedranks.McInfectedRanks;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class McInfectedRanksHook extends IPlaceholderHook {

	public McInfectedRanksHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		switch (identifier) {

		case "rank":
			return getRank(p);
		case "prefix":
			return getPrefix(p);

		}
		return null;
	}

	private String getRank(Player p) {
		
		String rank = McInfectedRanks.getRankManager().getPlayersRank(p).getName();
		
		return rank != null ? rank : "";
	}

	private String getPrefix(Player p) {
		
		String pre = McInfectedRanks.getRankManager().getPlayersRank(p).getPrefix();
		return pre != null ? pre : "";
	}
}