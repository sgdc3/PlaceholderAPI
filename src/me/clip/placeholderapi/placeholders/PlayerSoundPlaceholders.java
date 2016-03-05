package me.clip.placeholderapi.placeholders;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class PlayerSoundPlaceholders extends IPlaceholderHook implements Cacheable {

	private final Map<String, PlayerSound> sounds = new ConcurrentHashMap<String, PlayerSound>();
	
	public PlayerSoundPlaceholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public void clear() {
		sounds.clear();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (sounds.containsKey(identifier)) {
			sounds.get(identifier).play(p);
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
			PlayerSound gs = new PlayerSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play(p);
			return "";
		}
		
		try {
			volume = Integer.parseInt(volPitch.substring(0, second));
		} catch (Exception ex) {
			PlayerSound gs = new PlayerSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play(p);
			return "";
		}
		
		try {
			pitch = Integer.parseInt(volPitch.substring(second+1));
		} catch (Exception ex) {
			PlayerSound gs = new PlayerSound(s, volume, pitch);
			sounds.put(identifier, gs);
			gs.play(p);
			return "";
		}
		
		PlayerSound gs = new PlayerSound(s, volume, pitch);
		sounds.put(identifier, gs);
		gs.play(p);
		return "";
	}

	private class PlayerSound {

		@Getter
		private Sound s;
		@Getter
		private int volume;
		@Getter
		private int pitch;
		
		public PlayerSound(Sound s, int volume, int pitch) {
			this.s = s;
			this.volume = volume;
			this.pitch = pitch;
		}
		
		public void play(Player p) {
			p.playSound(p.getLocation(), s, volume, pitch);
		}
	}
	
}
