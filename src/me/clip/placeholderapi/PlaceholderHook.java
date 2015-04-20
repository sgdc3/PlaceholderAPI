package me.clip.placeholderapi;

import org.bukkit.entity.Player;

public abstract class PlaceholderHook {

	public abstract String onPlaceholderRequest(Player p, String identifier);
}
