package me.clip.placeholderapi.hooks;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class SimplePrefixHook extends IPlaceholderHook {

	public SimplePrefixHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("prefix")) {
			return getPrefixSuffix(p, "prefix");
		} else if (identifier.equals("suffix")) {
			return getPrefixSuffix(p, "suffix");
		}
		return null;
	}
	
	public String getPrefixSuffix(Player player, String type){    
	    if(player.hasMetadata(type)){
	        return player.getMetadata(type).get(0).asString();
	    }
	    return "";
	}

}
