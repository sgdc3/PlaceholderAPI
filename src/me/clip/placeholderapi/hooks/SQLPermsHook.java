package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.john01dav.sqlperms.SQLPerms;
import src.john01dav.sqlperms.database.DataStore;

public class SQLPermsHook extends IPlaceholderHook {
	
	private SQLPerms sqlperms;

	public SQLPermsHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		sqlperms = (SQLPerms) Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (sqlperms != null) {
				
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.contains("rank_")) {
			
			String channel = identifier.split("rank_")[1];

			return getPrefix(p, channel);
			
		} else if (identifier.contains("prefix_")) {
			
			String channel = identifier.split("prefix_")[1];
			
			return getPrefix(p, getGroup(p, channel));
		}
		return null;
	}

	
	private String getPrefix(Player p, String rank) {
		
		if (rank == null || rank.isEmpty()) {
			return rank;
		}
		
		String prefix = sqlperms.getConfigManager().getPermissionsConfigurationManager().getPrefix(rank);
		
		if (prefix == null || prefix.isEmpty()) {
			return "";
		}
		
		return prefix;
	}

	private String getGroup(Player p, String s) {
		
		DataStore store = sqlperms.getDataStore();
		
		if (store == null) {
			return "";
		}
		 
		String rank = store.getRank(p.getUniqueId(), s);
		 
		if (rank != null) {
			return rank;
		}
		
		return "";
	}

}
