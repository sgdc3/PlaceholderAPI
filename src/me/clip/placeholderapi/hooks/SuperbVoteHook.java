package me.clip.placeholderapi.hooks;

import io.minimum.minecraft.superbvote.SuperbVote;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class SuperbVoteHook extends IPlaceholderHook {

	public SuperbVoteHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("votes")) {
			return String.valueOf(SuperbVote.getPlugin().getVoteStorage().getVotes(p.getUniqueId()));
		}
		
		return null;
	
	}
}