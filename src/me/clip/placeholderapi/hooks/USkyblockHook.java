package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import us.talabrek.ultimateskyblock.uSkyBlock;

public class USkyblockHook extends IPlaceholderHook {
	
	private uSkyBlock skyblock;
	
	public USkyblockHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		skyblock = (uSkyBlock) Bukkit.getPluginManager().getPlugin(getPlugin());
			
		if (skyblock != null) {
				
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

		switch (identifier) {
		
		case "island_level":
			return String.valueOf(skyblock.getIslandLevel(p));
		case "island_rank":
			return skyblock.getIslandRank(p) != null ? String.valueOf(skyblock.getIslandRank(p).getRank()) : "0";
		}
		return null;
	}
}
