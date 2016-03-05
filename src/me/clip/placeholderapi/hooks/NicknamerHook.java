package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import de.inventivegames.nickname.Nicks;

public class NicknamerHook extends IPlaceholderHook {

	public NicknamerHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("has_nickname")) {
			
			return Nicks.isNicked(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
			
		} else if (identifier.equalsIgnoreCase("has_skin")) {
			
			return Nicks.hasSkin(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
			
		} else if (identifier.equalsIgnoreCase("nickname")) {
			
			if (Nicks.isNicked(p.getUniqueId())) {
				String nick = Nicks.getNick(p.getUniqueId());
				
				return nick != null ? nick : p.getName();
			} 
			return  p.getName(); 
		}
		
		return null;
	}
}