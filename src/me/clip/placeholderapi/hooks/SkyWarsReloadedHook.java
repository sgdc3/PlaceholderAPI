package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.walrusone.skywars.SkyWarsReloaded;
import com.walrusone.skywars.game.GamePlayer;

public class SkyWarsReloadedHook extends IPlaceholderHook {

	public SkyWarsReloadedHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		GamePlayer pl = SkyWarsReloaded.getPC().getPlayer(p.getUniqueId());
		
		if (pl == null) {
			return "";
		}
		
		switch(identifier) {
		
		case "blocks":
			return String.valueOf(pl.getBlocks());
		case "kills":
			return String.valueOf(pl.getKills());
		case "deaths":
			return String.valueOf(pl.getDeaths());
		case "games":
		case "games_played":
			return String.valueOf(pl.getGamesPlayed());
		case "opvote":
		case "op_vote":
			return String.valueOf(pl.getOpVote());
		case "score":
			return String.valueOf(pl.getScore());
		case "timevote":
		case "time_vote":
			return String.valueOf(pl.getTimeVote());
		case "wins":
			return String.valueOf(pl.getWins());
		case "selected_kit":
			return pl.getSelectedKit() != null ? pl.getSelectedKit().getKitName() : "";
		case "map_name":
			return pl.getGame() != null ? pl.getGame().getMapName() : "";
		}

		return null;
	}
}
