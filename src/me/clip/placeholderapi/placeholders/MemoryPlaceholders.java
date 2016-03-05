package me.clip.placeholderapi.placeholders;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class MemoryPlaceholders extends IPlaceholderHook {

	private final int MB = 1024*1024;
	
	public MemoryPlaceholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		 
	        Runtime runtime = Runtime.getRuntime();
	         
	        if (identifier.equals("used")) {
	        	return String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / MB);
	        }
	        
	        if (identifier.equals("free")) {
	        	return String.valueOf(runtime.freeMemory() / MB);
	        }
	        
	        if (identifier.equals("total")) {
	        	return String.valueOf(runtime.totalMemory() / MB);
	        }
	        
	        if (identifier.equals("max")) {
	        	return String.valueOf(runtime.maxMemory() / MB);
	        }
	        
	        return null;
	    }
}
