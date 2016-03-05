package me.clip.placeholderapi.hooks;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import me.RobiRami.leaderheads.Tools;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class LeaderheadsHook extends IPlaceholderHook {

	public LeaderheadsHook(InternalHook hook) {
		super(hook);
	}
	
	private final List<String> TYPES = Arrays.asList(new String[] { "sg-kills", "sg-deaths", "sg-wins", "sg-coins", "sg-played", "tr-wins", "tr-looses", 
			"tr-played", "tp-kills", "tp-elo", "tp-deaths", "treasure", "traded", "swim", "played", "pig", "mobkills", 
			"minecart", "fish", "horse", "fly", "joined", "mine", "kills", "enchanted", "drop", "dive", "deaths", "damage-give", 
			"damage-receive", "cake", "climb", "boat", "bred", "walk", "refer", "vr-total", "vr-monthly", "vr-streak", "vr-previous", 
			"swcc-wins", "swcc-kills", "swcc-deaths", "swcc-played", "buycraft", "ptr-time", "bwr-deaths", "bwr-beds", "bwr-score", "bwr-loses", 
			"bwr-wins", "bwr-kd", "bwr-games", "bwr-kills", "ps-kills", "ps-elo", "ps-deaths", "ps-streak", "battlestreak", "battlekills", 
			"battledeaths", "town-land", "town-residents", "nation-land", "nation-residents", "mcmmo-power", "sc-deaths", "sc-members", "sc-ratio", 
			"quests-points", "ez-blocks", "sql-swr-blocks", "flat-swr-blocks", "sql-swr-score", "sql-swr-wins", "sql-swr-played", "sql-swr-deaths", 
			"sql-swr-kills", "flat-swr-wins", "flat-swr-played", "flat-swr-deaths", "flat-swr-kills", "flat-swr-score", "sbs-deaths", "uf-chunks", 
			"uf-members", "uf-balance", "uf-power", "cr-wins", "f-balance", "asb-level", "f-chunks", "f-members", "f-power", "ai-level", "sql-ws-score", 
			"sql-ws-wins", "sql-ws-played", "sql-ws-deaths", "sql-ws-kills", "flat-ws-wins", "flat-ws-played", "flat-ws-deaths", "flat-ws-kills", 
			"flat-ws-score", "sql-sw-score", "sql-sw-wins", "sql-sw-played", "sql-sw-deaths", "sql-sw-kills", "rv-votes", "flat-sw-wins", "flat-sw-played", 
			"flat-sw-deaths", "flat-sw-kills", "sbs-kills", "flat-sw-score", "usb-level", "sbs-killstreak", "sbs-mobkills", "balance", "gal-votes", 
			"iwlevel", "battlelevel", "battlescore", "playerpoints", "ks-kills", "ks-deaths", "ks-streak", "ks-ratio", "uv-total", "uv-monthly" });

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		//%leaderheads_player_<type>_#%
		//%leaderheads_score_<type>_#%
		
		String[] parts = identifier.split("_");
		
		if (parts.length != 3) {
			return null;
		}
		
		String returnType = parts[0];
		
		String lType = parts[1];
		
		int e = 1;
		
		try {
			e = Integer.parseInt(parts[2]);
		} catch(Exception ex) {
			return null;
		}
		
		e = e-1;
		
		if (!TYPES.contains(lType)) {
			return "incorrect leaderboard name!";
		}
		
		List<Entry<String, String>> board = Tools.getTop(lType);
		
		if (board == null) {
			return null;
		}
		
		if (e >= board.size()) {
			return "";
		}
		
		if (returnType.equalsIgnoreCase("player")) {
			return board.get(e).getKey();
		} else if (returnType.equalsIgnoreCase("score")){
			return board.get(e).getValue();
		}
		
		return null;
	}
}
