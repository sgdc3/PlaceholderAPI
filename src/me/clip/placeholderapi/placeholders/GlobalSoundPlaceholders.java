package me.clip.placeholderapi.placeholders;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class GlobalSoundPlaceholders extends IPlaceholderHook implements Cacheable {

	private final Map<String, GlobalSound> sounds = new ConcurrentHashMap<String, GlobalSound>();
	
	public GlobalSoundPlaceholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public void clear() {
		sounds.clear();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (sounds.containsKey(identifier)) {
			sounds.get(identifier).play();
			return "";
		}
		
		int first = identifier.indexOf("-");
		
		Sound s = null;
		
		if (first == -1) {
			try {
				String sound = identifier.toUpperCase();
				s = Sound.valueOf(sound);
			} catch (Exception ex) {
				return null;
			}
			return null;
		} else {
			try {
				String sound = identifier.substring(0, first).toUpperCase();
				s = Sound.valueOf(sound);
			} catch (Exception ex) {
				return null;
			}
		}

		int volume = 10;
		
		int pitch = 1;
		
		String volPitch = identifier.substring(first+1);
		
		int second = volPitch.indexOf("-");
		
		if (second == -1) {
			GlobalSound gs = new GlobalSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play();
			return "";
		}
		
		try {
			volume = Integer.parseInt(volPitch.substring(0, second));
		} catch (Exception ex) {
			GlobalSound gs = new GlobalSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play();
			return "";
		}
		
		try {
			pitch = Integer.parseInt(volPitch.substring(second+1));
		} catch (Exception ex) {
			GlobalSound gs = new GlobalSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play();
			return "";
		}
		
		GlobalSound gs = new GlobalSound(s, volume, pitch);
		sounds.put(identifier, gs);
		gs.play();
		return "";
	}

	private class GlobalSound {

		@Getter
		private Sound s;
		@Getter
		private int volume;
		@Getter
		private int pitch;
		
		public GlobalSound(Sound s, int volume, int pitch) {
			this.s = s;
			this.volume = volume;
			this.pitch = pitch;
		}
		
		public void play() {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.playSound(p.getLocation(), s, volume, pitch);
			}
		}
	}
	
}
