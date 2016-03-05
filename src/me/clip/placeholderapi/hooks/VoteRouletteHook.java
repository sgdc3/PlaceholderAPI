package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.mythicacraft.voteroulette.VoteRoulette;
import com.mythicacraft.voteroulette.Voter;

public class VoteRouletteHook extends IPlaceholderHook {

	public VoteRouletteHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {
		
		boolean hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
				
			if (hooked) {
				PlaceholderAPIPlugin.getInstance().getLogger().warning("Be aware, VoteRoulette does not have a cache system to store vote stats!");
				PlaceholderAPIPlugin.getInstance().getLogger().warning("All placeholder values are queried by VoteRoulette upon placeholder request!");
				PlaceholderAPIPlugin.getInstance().getLogger().warning("These placeholders may cause performance issues if the plugin requesting them is not requested async!");
			}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		Voter voter = VoteRoulette.getVoterManager().getVoter(p.getName());
		
		if (voter == null) {
			return "0";
		}
		
		if (identifier.equals("votes_lifetime")) {
			return voter.getLifetimeVotes()+"";
		}
		
		if (identifier.equals("votes_day")) {
			return voter.getVotesForTheDay()+"";
		}
		
		if (identifier.equals("current_vote_cycle")) {
			return voter.getCurrentVoteCycle()+"";
		}
		
		if (identifier.equals("current_vote_streak")) {
			return voter.getCurrentVoteStreak()+"";
		}
		
		if (identifier.equals("hours_since_last_vote")) {
			return voter.getHoursSinceLastVote()+"";
		}
		
		if (identifier.equals("longest_vote_streak")) {
			return voter.getLongestVoteStreak()+"";
		}
		
		if (identifier.equals("unclaimed_milestone_count")) {
			return voter.getUnclaimedMilestoneCount()+"";
		}
		
		if (identifier.equals("unclaimed_reward_count")) {
			return voter.getUnclaimedRewardCount()+"";
		}
		
		if (identifier.equals("last_vote_timestamp")) {
			String time = voter.getLastVoteTimeStamp();
			return time != null ? PlaceholderAPIPlugin.getDateFormat().format(time) : "";
		}
		
		return null;
	}
}