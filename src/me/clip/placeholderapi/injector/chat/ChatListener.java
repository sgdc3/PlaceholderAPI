package me.clip.placeholderapi.injector.chat;

import java.util.regex.Matcher;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

/**
@author extended_clip, GGhost
*/
public class ChatListener implements Listener {
	
	public ChatListener(PlaceholderAPIPlugin instance) {
		Bukkit.getPluginManager().registerEvents(this, instance);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		String message = e.getMessage();
		
		Matcher matcher = PlaceholderAPI.getBracketPlaceholderPattern().matcher(message);
		
		if (p.hasPermission("placeholderapi.injector.chat.bypass")) {
			
			if (matcher.find()) {
				
				message = PlaceholderAPI.setBracketPlaceholders(p, message);
			}

		} else {
			
			while (matcher.find()) {
				message = matcher.replaceAll("");
			}
		}
		
		if (message.isEmpty()) {
			e.setCancelled(true);
			return;
		}
		
		e.setMessage(message);
		
		String format = e.getFormat();
		
		matcher = PlaceholderAPI.getBracketPlaceholderPattern().matcher(format);
		
		if (matcher.find()) {
			format = PlaceholderAPI.setBracketPlaceholders(p, format);
			e.setFormat(format);
		}			
	}
}
