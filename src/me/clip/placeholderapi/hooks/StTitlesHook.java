package me.clip.placeholderapi.hooks;

import org.bukkit.entity.Player;

import com.shepherdjerred.sttitles.objects.Title;
import com.shepherdjerred.sttitles.objects.TitlePlayer;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class StTitlesHook extends IPlaceholderHook {

	public StTitlesHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		TitlePlayer pl = TitlePlayer.getTitlePlayer(p);
		
		if (pl == null) {
			return "";
		}
		
		Title t = pl.getTitle();
		
		if (t == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("name")) {
			return t.getName() != null ? t.getName() : "";
		}
		
		if (identifier.equalsIgnoreCase("id")) {
			return String.valueOf(t.getId());
		}
		
		if (identifier.equalsIgnoreCase("display")) {
			return t.getDisplay() != null ? t.getDisplay() : "";
		}
		
		if (identifier.equalsIgnoreCase("content")) {
			return t.getContent() != null ? t.getContent() : "";
		}
		
		return null;
	}

}
