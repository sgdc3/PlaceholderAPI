package me.clip.placeholderapi.hooks;

import org.bukkit.entity.Player;
import org.royaldev.royalcommands.RoyalCommands;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class RoyalCommandsHook extends IPlaceholderHook {

	public RoyalCommandsHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		switch (identifier) {
		
		case "nickname":
			return RoyalCommands.instance.getAPI().getPlayerAPI().getDisplayName(p);
		}

		return null;
	}
}
