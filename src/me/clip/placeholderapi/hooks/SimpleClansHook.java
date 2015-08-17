package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SimpleClansHook extends IPlaceholderHook {

	private ClanManager clanManager;

	public SimpleClansHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		SimpleClans clans = (SimpleClans) Bukkit.getPluginManager().getPlugin(getPlugin());

		if (clans != null) {

			clanManager = clans.getClanManager();

			if (clanManager != null) {

				hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);

			}
		}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		ClanPlayer player = clanManager.getClanPlayer(p);
		
		if (player == null) {
			return "";
		}
		
		switch (identifier) {

		case "in_clan":
			return  player.getClan() != null ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "join_date":
			return player.getJoinDateString();
		case "rank":
			return player.getRank();
		case "tag":
			return player.getTag();
		case "tag_label":
			return player.getTagLabel();
		case "civilian_kills":
			return String.valueOf(player.getCivilianKills());
		case "deaths":
			return String.valueOf(player.getDeaths());
		case "kdr":
			return String.valueOf(player.getKDR());
		case "neutral_kills":
			return String.valueOf(player.getNeutralKills());
		case "rival_kills":
			return String.valueOf(player.getRivalKills());
		case "weighted_kills":
			return String.valueOf(player.getWeightedKills());
		case "is_leader":
			return player.isLeader() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (player.getClan() == null) {
			return "";
		}
		
		Clan c = player.getClan();
		
		switch (identifier) {
		
		case "clan_tag":
			return c.getTag();
		case "clan_tag_label":
			return c.getTagLabel(true);
		case "clan_color_tag":
			return c.getColorTag();
		case "clan_founded_string":
			return c.getFoundedString();
		case "clan_name":
			return c.getName();
		case "clan_average_wk":
			return String.valueOf(c.getAverageWK());
		case "clan_balance":
			return String.valueOf(c.getBalance());
		case "clan_size":
			return String.valueOf(c.getSize());
		case "clan_total_civilian":
			return String.valueOf(c.getTotalCivilian());
		case "clan_total_deaths":
			return String.valueOf(c.getTotalDeaths());
		case "clan_total_kdr":
			return String.valueOf(c.getTotalKDR());
		case "clan_total_neutral":
			return String.valueOf(c.getTotalNeutral());
		case "clan_total_rival":
			return String.valueOf(c.getTotalRival());
		case "clan_friendly_fire":
			return c.isFriendlyFire() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "clan_allow_deposit":
			return c.isAllowDeposit() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "clan_allow_withdraw":
			return c.isAllowWithdraw() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "clan_is_unrivable":
			return c.isUnrivable() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "clan_leader_size":
			return String.valueOf(c.getLeaders().size());
		
		}
		return null;
	}
}
