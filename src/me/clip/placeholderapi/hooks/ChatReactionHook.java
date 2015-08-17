package me.clip.placeholderapi.hooks;

import me.clip.chatreaction.ReactionAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class ChatReactionHook extends IPlaceholderHook {

	public ChatReactionHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equals("wins")) {
			return String.valueOf(ReactionAPI.getWins(p));
		}
		
		return null;
	}
}
