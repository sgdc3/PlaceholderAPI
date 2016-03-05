package me.clip.placeholderapi.placeholders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class DatePlaceholders extends IPlaceholderHook implements Cacheable {

	private final Map<String, SimpleDateFormat> formats = new HashMap<String, SimpleDateFormat>();
	
	public DatePlaceholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public void clear() {
		formats.clear();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (formats.containsKey(identifier)) {
			return formats.get(identifier).format(new Date());
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat(identifier);
			
			formats.put(identifier, format);
			
			return format.format(new Date());
		} catch (NullPointerException | IllegalArgumentException ex) {
			return null;
		}
	}
}
