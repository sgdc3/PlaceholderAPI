package me.clip.placeholderapi.placeholders;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.VersionSpecific;
import me.clip.placeholderapi.internal.NMSVersion;

public class NMSPlayer_1_9_R1_Placeholders extends IPlaceholderHook implements VersionSpecific {

	public NMSPlayer_1_9_R1_Placeholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("ping")) {
			return String.valueOf(((CraftPlayer)p).getHandle().ping);
		}
		return null;
	}

	@Override
	public NMSVersion getVersion() {
		return NMSVersion.SPIGOT_1_9_R1;
	}

}
