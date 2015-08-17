package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPointsHook extends IPlaceholderHook {
	
	public PlayerPointsHook(InternalHook hook) {
		super(hook);
	}
	
	private PlayerPoints playerPoints;


	@Override
	public boolean hook() {
		
		boolean hooked = false;
		
		PlayerPoints points = (PlayerPoints) Bukkit.getPluginManager().getPlugin(getPlugin());
		    
		if (points != null) {
				
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
				
			if (hooked) {
				playerPoints = points;
			}
		}
		return hooked;
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}

		if (identifier.equals("points")) {
			return getPoints(p);
		}

		return null;
	}

	
	private String getPoints(Player p) {
		return String.valueOf(playerPoints.getAPI().look(p.getUniqueId()));
	}
}
