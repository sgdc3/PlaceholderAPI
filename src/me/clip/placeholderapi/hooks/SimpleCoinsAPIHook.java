package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import de.germanelectronix.simplecoins.SimpleCoinsAPI;

public class SimpleCoinsAPIHook extends IPlaceholderHook {

	public SimpleCoinsAPIHook(InternalHook hook) {
		super(hook);
	}
	

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equals("coins")) {
			return getCoins(p);
		}
		return null;
	}
	
	private String getCoins(Player p) {
		return String.valueOf(SimpleCoinsAPI.getCoins(p));
	}
}
