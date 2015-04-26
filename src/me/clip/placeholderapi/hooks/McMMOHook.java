package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;

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
					
					McMMOPlayer player = UserManager.getPlayer(p);
					
					if (player == null) {
						return "";
					}
					
					if (identifier.startsWith("level_")) {
						
						String skill = identifier.split("level_")[1];
						
						return getSkillLevel(player, skill);
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
	
	private String getSkillLevel(McMMOPlayer p, String skill) {
		
		switch (skill) {
		
		case "acrobatics":
			return String.valueOf(p.getAcrobaticsManager().getSkillLevel());
		case "alchemy":
			return String.valueOf(p.getAlchemyManager().getSkillLevel());
		case "archery":
			return String.valueOf(p.getArcheryManager().getSkillLevel());
		case "axes":
			return String.valueOf(p.getAxesManager().getSkillLevel());
		case "excavation":
			return String.valueOf(p.getExcavationManager().getSkillLevel());
		case "fishing":
			return String.valueOf(p.getFishingManager().getSkillLevel());
		case "herbalism":
			return String.valueOf(p.getHerbalismManager().getSkillLevel());
		case "mining":
			return String.valueOf(p.getMiningManager().getSkillLevel());
		case "repair":
			return String.valueOf(p.getRepairManager().getSkillLevel());
		case "salvage":
			return String.valueOf(p.getSalvageManager().getSkillLevel());
		case "smelting":
			return String.valueOf(p.getSmeltingManager().getSkillLevel());
		case "swords":
			return String.valueOf(p.getSwordsManager().getSkillLevel());
		case "taming":
			return String.valueOf(p.getTamingManager().getSkillLevel());
		case "unarmed":
			return String.valueOf(p.getUnarmedManager().getSkillLevel());
		case "woodcutting":
			return String.valueOf(p.getWoodcuttingManager().getSkillLevel());
		}
		
		
		return null;
	}
	
	private String getSkillRank(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill.toUpperCase())) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getPlayerRankSkill(p.getUniqueId(), skill.toUpperCase()));
	}
	
	private String getSkillXP(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill.toUpperCase())) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXP(p, skill.toUpperCase()));
	}

	private String getXPRemaining(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill.toUpperCase())) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXPRemaining(p, skill.toUpperCase()));
	}
	
	private String getXPToNextLevel(Player p, String skill) {
		
		if (!ExperienceAPI.isValidSkillType(skill.toUpperCase())) {
			return "";
		}
		
		return String.valueOf(ExperienceAPI.getXPToNextLevel(p, skill.toUpperCase()));
	}
}
