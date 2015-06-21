package me.clip.placeholderapi.hooks;

import java.util.List;

import me.clip.deluxetags.DeluxeTag;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeluxeTagsHook {
	
	private PlaceholderAPIPlugin plugin;

	public DeluxeTagsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("DeluxeTags")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook(
					"DeluxeTags", new PlaceholderHook() {
						@Override
						public String onPlaceholderRequest(Player p, String s) {

							switch (s) {

							case "tag":
								return DeluxeTag.getPlayerDisplayTag(p);

							case "identifier":
								String tagId = DeluxeTag.getPlayerTagIdentifier(p);
								return tagId != null ? tagId : "";

							case "description":
								return DeluxeTag.getPlayerTagDescription(p);

							case "amount":
								List<String> tmp = DeluxeTag.getAvailableTagIdentifiers(p);
								return tmp != null ? String.valueOf(tmp.size())
										: "0";
							}

							return "&cIncorrect placeholder!";
						}
					}, true);

			if (hooked) {
				plugin.log.info("Hooked into DeluxeTags for placeholders!");
			}
		}
	}

}

