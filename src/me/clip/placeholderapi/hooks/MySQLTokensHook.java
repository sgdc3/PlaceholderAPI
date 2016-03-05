package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.masterderpydoge.tokens.TokenCore;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class MySQLTokensHook extends IPlaceholderHook {

	private TokenCore plugin;
	
	public MySQLTokensHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {
		
		plugin = (TokenCore) Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (plugin == null) {
			return false;
		}
		
		return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (p == null) {
			return null;
		}
		if (identifier.equals("tokens")) {
			return String.valueOf(plugin.getTokens(p));
		}
		return null;
	}
}
