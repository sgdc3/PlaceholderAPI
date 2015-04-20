package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.PartyAPI;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class McMMOHook {
	
	private PlaceholderAPIPlugin plugin;
	
	public McMMOHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
			
			boolean hooked = PlaceholderAPI.registerPlaceholderHook("mcmmo", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {
					
					if (identifier.startsWith("level_")) {
						
						String skill = identifier.split("level_")[1];
						
						return getSkillLevel(p, skill);
					}
					
					if (identifier.startsWith("rank_")) {
						
						String skill = identifier.split("rank_")[1];
						
						return getSkillRank(p, skill);
					}

					if (identifier.startsWith("xp_remaining_")) {
						
						String skill = identifier.split("xp_remaining_")[1];
						
						return getXPRemaining(p, skill);
					}
					
					if (identifier.startsWith("xp_needed_")) {
						
						String skill = identifier.split("xp_needed_")[1];
						
						return getXPToNextLevel(p, skill);
					}
					
					if (identifier.startsWith("xp_")) {
						
						String skill = identifier.split("xp_")[1];
						
						return getSkillXP(p, skill);
					}
					
					switch (identifier) {
					
					case "overall_rank":
						return String.valueOf(ExperienceAPI.getPlayerRankOverall(p.getUniqueId()));
					case "power_level":
						return String.valueOf(ExperienceAPI.getPowerLevel(p));
					case "power_level_cap":
						return String.valueOf(ExperienceAPI.getPowerLevelCap());
					case "in_party":
						return PartyAPI.inParty(p) ? "yes" : "no";	
					case "party_name":
						return PartyAPI.getPartyName(p) != null ? PartyAPI.getPartyName(p) : "";
					case "party_leader":
						return getPartyLeader(p);	
					case "is_party_leader":
						return getPartyLeader(p).equals(p.getName()) ? "yes" : "no";
					case "party_size":
						return PartyAPI.getMembersMap(p) != null ? String.valueOf(PartyAPI.getMembersMap(p).size()) : "0";
					}

					return null;
				}	
			});
			
			if (hooked) {
				plugin.getLogger().info("Hooked into mcMMO for placeholders!");
			}
		}
	}
	
	private String getPartyLeader(Player p) {
		
		if (PartyAPI.getPartyName(p) == null) {
			return "";
		}
		
		String leader = PartyAPI.getPartyLeader(PartyAPI.getPartyName(p));

		return leader != null ? leader : "";
	}
	
	private String getSkillLevel(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill)) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getLevel(p, skill));
	}
	
	private String getSkillRank(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill)) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getPlayerRankSkill(p.getUniqueId(), skill));
	}
	
	private String getSkillXP(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill)) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXP(p, skill));
	}

	private String getXPRemaining(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill)) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXPRemaining(p, skill));
	}
	
	private String getXPToNextLevel(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill)) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXPToNextLevel(p, skill));
	}
}
