package me.clip.placeholderapi.hooks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.util.TimeUtil;
import me.edge209.OnTime.DataIO;
import me.edge209.OnTime.OnTimeAPI;

import org.bukkit.entity.Player;

public class OnTimeHook extends IPlaceholderHook {

	public OnTimeHook(InternalHook hook) {
		super(hook);
	}

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
			return monthPlay != -1 ? TimeUtil.getTime((int) TimeUnit.MILLISECONDS.toSeconds(monthPlay)) : "0s";
		case "referrals_month":
			long monthRefer = DataIO.getPlayerTimeData(name, OnTimeAPI.data.MONTHREFER);
			return monthRefer != -1 ? monthRefer+"" : "0";
		case "votes_month":
			long monthVotes = DataIO.getPlayerTimeData(name, OnTimeAPI.data.MONTHVOTE);

			return monthVotes != -1 ? monthVotes+"" : "0";
		case "time_played_today":
			long todayPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYPLAY);
			return todayPlay != -1 ? TimeUtil.getTime((int) TimeUnit.MILLISECONDS.toSeconds(todayPlay)) : "0s";
		case "referrals_today":
			long todayRefer = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYREFER);
			return todayRefer != -1 ? todayRefer+"" : "0";
		case "votes_today":
			long todayVote = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TODAYVOTE);
			return todayVote != -1 ? todayVote+"" : "0";
		case "time_played_total":
			long totalPlay = DataIO.getPlayerTimeData(name, OnTimeAPI.data.TOTALPLAY);
			return totalPlay != -1 ? TimeUtil.getTime((int) TimeUnit.MILLISECONDS.toSeconds(totalPlay)) : "0s";
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
			return weekPlay != -1 ? TimeUtil.getTime((int) TimeUnit.MILLISECONDS.toSeconds(weekPlay)) : "0s";	
		case "referrals_week":
			long weekReferrals = DataIO.getPlayerTimeData(name, OnTimeAPI.data.WEEKREFER);
			return weekReferrals != -1 ? weekReferrals+"" : "0";
		case "votes_week":
			long weekVotes = DataIO.getPlayerTimeData(name, OnTimeAPI.data.WEEKVOTE);
			return weekVotes != -1 ? weekVotes+"" : "0";	
		}
		return null;
	}
}