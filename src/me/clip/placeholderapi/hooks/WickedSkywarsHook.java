package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.mooglemods.wickedskywars.controllers.PlayerController;
import com.mooglemods.wickedskywars.player.GamePlayer;

public class WickedSkywarsHook extends IPlaceholderHook {
	
	public WickedSkywarsHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

		switch(identifier) {
		
		case "score":
			return getScore(p);
		case "kills":
			return getKills(p);
		case "deaths":
			return getDeaths(p);
		case "gamesplayed":
		case "played":
			return getGamesPlayed(p);
		case "gameswon":
		case "won":
			return getGamesWon(p);
		
		}
		return null;
	}
	
	private String getScore(Player p) { 
			GamePlayer gamePlayer = PlayerController.get().get(p);
			
			if (gamePlayer == null) {
				return "0";
			}
			return String.valueOf(gamePlayer.getScore());
	}
	
	private String getKills(Player p) {
			GamePlayer gamePlayer = PlayerController.get().get(p);
			
			if (gamePlayer == null) {
				return "0";
			}
			return String.valueOf(gamePlayer.getKills());
	}
	
	private String getDeaths(Player p) {
			GamePlayer gamePlayer = PlayerController.get().get(p);
			
			if (gamePlayer == null) {
				return "0";
			}
			return String.valueOf(gamePlayer.getDeaths());
	}
	
	private String getGamesPlayed(Player p) {
			GamePlayer gamePlayer = PlayerController.get().get(p);
			
			if (gamePlayer == null) {
				return "0";
			}
			return String.valueOf(gamePlayer.getGamesPlayed());
	}
	
	private String getGamesWon(Player p) { 
			GamePlayer gamePlayer = PlayerController.get().get(p);
			
			if (gamePlayer == null) {
				return "0";
			}
			return String.valueOf(gamePlayer.getGamesWon());
	}
}
