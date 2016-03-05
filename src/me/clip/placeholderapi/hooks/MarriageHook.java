package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.lenis0012.bukkit.marriage2.Gender;
import com.lenis0012.bukkit.marriage2.MPlayer;
import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class MarriageHook extends IPlaceholderHook {

	public MarriageHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (p == null) {
			return "";
		}
		
		MPlayer pl = MarriagePlugin.getInstance().getMPlayer(p.getUniqueId());
		
		if (pl == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("is_married")) {
			return pl.isMarried() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (identifier.equalsIgnoreCase("is_priest")) {
			return pl.isPriest() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (identifier.equalsIgnoreCase("gender")) {
			return pl.getGender() == Gender.MALE ? "male" : "female";
		}
		
		if (identifier.equalsIgnoreCase("gender_chat_prefix")) {
			String pre = pl.getGender().getChatPrefix();
			return pre != null ? pre : "";
		}
		
		if (identifier.equalsIgnoreCase("last_name")) {
			return pl.getLastName() != null ? pl.getLastName() : "";
		}
		
		if (identifier.equalsIgnoreCase("has_pvp_enabled")) {
			if (pl.isMarried()) {
				return pl.getMarriage().isPVPEnabled() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
			} else {
				return PlaceholderAPIPlugin.booleanFalse();
			}
		}
		
		if (identifier.equalsIgnoreCase("has_home_set")) {
			if (pl.isMarried()) {
				return pl.getMarriage().isHomeSet() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
			} else {
				return PlaceholderAPIPlugin.booleanFalse();
			}
		}
		
		if (identifier.equalsIgnoreCase("home_x")) {
			if (pl.isMarried() && pl.getMarriage().isHomeSet()) {
				return String.valueOf(pl.getMarriage().getHome().getBlockX());
			}
			return "";
		}
		
		if (identifier.equalsIgnoreCase("home_y")) {
			if (pl.isMarried() && pl.getMarriage().isHomeSet()) {
				return String.valueOf(pl.getMarriage().getHome().getBlockY());
			}
			return "";
		}
		
		if (identifier.equalsIgnoreCase("home_z")) {
			if (pl.isMarried() && pl.getMarriage().isHomeSet()) {
				return String.valueOf(pl.getMarriage().getHome().getBlockZ());
			}
			return "";
		}
		
		if (identifier.equalsIgnoreCase("partner")) {
			if (pl.isMarried()) {
				Player partner = Bukkit.getPlayer(pl.getPartner().getUniqueId());
				if (partner != null) {
					return partner.getName();
				}
				OfflinePlayer t = Bukkit.getOfflinePlayer(pl.getPartner().getUniqueId());
				if (t != null) {
					return t.getName();
				}
				return "";
			}
		}
		
		return null;
	}

}
