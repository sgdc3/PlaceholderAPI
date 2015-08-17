package me.clip.placeholderapi.hooks;

import io.loyloy.nicky.Nicky;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class NickyHook extends IPlaceholderHook {

	public NickyHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("nickname")) {
			return getNickname(p);
		}
		return null;
	}

	private String getNickname(Player p) {

		if (Nicky.getNickDatabase() != null
				&& Nicky.getNickDatabase().downloadNick(p.getUniqueId().toString()) != null) {
			
			return Nicky.getNickDatabase().downloadNick(p.getUniqueId().toString());
		}
		return p.getName();
	}
}
