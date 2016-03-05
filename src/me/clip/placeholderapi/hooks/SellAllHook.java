package me.clip.placeholderapi.hooks;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.simgar98.sellall.multiply;

public class SellAllHook extends IPlaceholderHook {

	public SellAllHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (identifier.equals("multiplier")) {
			return String.valueOf(multiply.getTier(p));
		}
		if (identifier.equals("multitime")) {
			return String.valueOf(multiply.getDuration(p));
		}
		return null;
	}

}
