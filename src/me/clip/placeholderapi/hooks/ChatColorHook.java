package me.clip.placeholderapi.hooks;

import main.ColorAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class ChatColorHook extends IPlaceholderHook {

	public ChatColorHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("chatcolor")) {
			return ColorAPI.getChatColor(p) != null ? ColorAPI.getChatColor(p) : "";
		}
		
		if (identifier.equalsIgnoreCase("namecolor")) {
			return ColorAPI.getNameColor(p) != null ? ColorAPI.getNameColor(p) : "";
		}
		
		return null;
	
	}
}