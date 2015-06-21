package me.clip.placeholderapi.hooks;

import me.clip.chatreaction.ChatReaction;
import me.clip.chatreaction.ReactionAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatReactionHook {
	
	private PlaceholderAPIPlugin plugin;

	public ChatReactionHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("ChatReaction")) {		
			
			ChatReaction cr = (ChatReaction) Bukkit.getPluginManager().getPlugin("ChatReaction");
			
			if (cr != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(cr, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (identifier.equals("wins")) {
							return String.valueOf(ReactionAPI.getWins(p));
						}
						
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into ChatReaction for placeholders!");
				}
			}
		}
	}
}
