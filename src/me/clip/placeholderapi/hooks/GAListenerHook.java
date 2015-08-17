package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.swifteh.GAL.VoteAPI;

public class GAListenerHook extends IPlaceholderHook {

	public GAListenerHook(InternalHook hook) {
		super(hook);
	}
	
	private String getVotes(Player p) {
		return String.valueOf(VoteAPI.getVoteTotal(p));
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equals("votes")) {
			return getVotes(p);
		}
		return null;
	
	}
}
