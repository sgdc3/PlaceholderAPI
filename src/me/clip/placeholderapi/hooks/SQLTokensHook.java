package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.john01dav.sqltokens.SQLTokens;
import src.john01dav.sqltokens.database.DataStore;

public class SQLTokensHook extends IPlaceholderHook {
	
	private SQLTokens sqlTokens;

	public SQLTokensHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		sqlTokens = (SQLTokens) Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (sqlTokens != null) {
			
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);			
		}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("tokens")) {
			
			return getTokens(p);
		} 
		return null;
	}

	private String getTokens(Player p) {
		
		DataStore store = sqlTokens.getDataStore();
		
		if (store == null) {
			return "";
		}
		 
		return String.valueOf(store.getBalance(p));
	}
}
