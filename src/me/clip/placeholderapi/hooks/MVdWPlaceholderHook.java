package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import be.maximvdw.placeholderapi.PlaceholderAPI;

public class MVdWPlaceholderHook extends IPlaceholderHook {
	
	public MVdWPlaceholderHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		identifier = "{" + identifier + "}";

		String value = PlaceholderAPI.replacePlaceholders(p, identifier);

		if (value.equals(identifier)) {
			return null;
		}

		return value;
	}
}
