package me.clip.placeholderapi.hooks;

import java.util.Date;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.util.TimeUtil;
import me.edge209.OnTime.DataIO;
import me.edge209.OnTime.OnTime;
import me.edge209.OnTime.OnTimeAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnTimeHook {

	private PlaceholderAPIPlugin plugin;

	public OnTimeHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("OnTime")) {
			
			OnTime onTime = (OnTime) Bukkit.getPluginManager().getPlugin("OnTime");
			
			if (onTime != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(onTime, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}

						String name = p.getName();
						
						switch (identifier) {
						
						case "last_login":
							long login = DataIO.getPlayerTimeData(name, OnTimeAPI.data.LASTLOGIN);
							return login != -1 ? PlaceholderAPIPlugin.getDateFormat().format(new Date(login)) : "";
						case "last_vote":
							long lastVote = DataIO.getPlayerTimeData(name, OnTimeAPI.data.LASTVOTE);
							return lastVote != -1 ? PlaceholderAPIPlugin.getDateFormat().format(new Date(lastVote)) : "";
						case "time_played_month":
							long monthPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.MONTHPLAY);
							return monthPlay != -1 ? TimeUtil.getTime((int)monthPlay*1000) : "0s";
						case "referrals_month":
							long monthRefer = DataIO.getPlayerTimeData(name, OnTimeAPI.data.MONTHREFER);
							return monthRefer != -1 ? monthRefer+"" : "0";
						case "votes_month":
							long monthVotes = DataIO.getPlayerTimeData(name, OnTimeAPI.data.MONTHVOTE);
							return monthVotes != -1 ? monthVotes+"" : "0";
						case "time_played_today":
							long todayPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYPLAY);
							return todayPlay != -1 ? TimeUtil.getTime((int)todayPlay*1000) : "0s";
						case "referrals_today":
							long todayRefer = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYREFER);
							return todayRefer != -1 ? todayRefer+"" : "0";
						case "votes_today":
							long todayVote = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYVOTE);
							return todayVote != -1 ? todayVote+"" : "0";
						case "time_played_total":
							long totalPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TOTALPLAY);
							return totalPlay != -1 ? TimeUtil.getTime((int)totalPlay*1000) : "0s";
						case "points_total":
							long totalPoints = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TOTALPOINT);
							return totalPoints != -1 ? totalPoints+"" : "0";
						case "referrals_total":
							long totalReferrals = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TOTALREFER);
							return totalReferrals != -1 ? totalReferrals+"" : "0";
						case "votes_total":
							long totalVotes = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TOTALVOTE);
							return totalVotes != -1 ? totalVotes+"" : "0";	
						case "time_played_week":
							long weekPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.WEEKPLAY);
							return weekPlay != -1 ? TimeUtil.getTime((int)weekPlay*1000) : "0s";	
						case "referrals_week":
							long weekReferrals = DataIO.getPlayerTimeData(name, OnTimeAPI.data.WEEKREFER);
							return weekReferrals != -1 ? weekReferrals+"" : "0";
						case "votes_week":
							long weekVotes = DataIO.getPlayerTimeData(name, OnTimeAPI.data.WEEKVOTE);
							return weekVotes != -1 ? weekVotes+"" : "0";	
						}
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into OnTime for nicknames!");
				}
			}
		}
	}
}