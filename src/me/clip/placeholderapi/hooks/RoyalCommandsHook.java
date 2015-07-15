package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.RoyalCommands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class RoyalCommandsHook {

	private PlaceholderAPIPlugin plugin;
	
	public RoyalCommandsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("RoyalCommands")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("RoyalCommands", new PlaceholderHook() {

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

					}, true);

			if (hooked) {
				plugin.log.info("Hooked into RoyalCommands for placeholders!");
			}
		}
	}
}
