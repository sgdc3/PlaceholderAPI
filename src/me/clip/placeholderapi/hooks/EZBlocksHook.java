package me.clip.placeholderapi.hooks;

import me.clip.ezblocks.EZBlocks;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class EZBlocksHook extends IPlaceholderHook {

	public EZBlocksHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		switch(identifier) {
		
		case "broken":
		case "blocks":
		case "blocks broken":
			return String.valueOf(EZBlocks.getEZBlocks().getBlocksBroken(p));
		}
		return null;
	}
}
