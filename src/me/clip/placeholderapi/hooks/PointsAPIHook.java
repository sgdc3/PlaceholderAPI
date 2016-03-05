package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.BukkitPVP.PointsAPI.PointsAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class PointsAPIHook extends IPlaceholderHook {

	private PointsAPI plugin;
	
	public PointsAPIHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {
		
		plugin = (PointsAPI) Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (plugin == null) {
			return false;
		}
		
		return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		return String.valueOf(plugin.getPoints(p));
	}

}
