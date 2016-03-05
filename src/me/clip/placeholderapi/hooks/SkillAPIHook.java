package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;

public class SkillAPIHook extends IPlaceholderHook {
	
	public SkillAPIHook(InternalHook hook) {
		super(hook);
	}
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

		if (!SkillAPI.hasPlayerData(p)) {
			return "";
		}
		
		PlayerData data = SkillAPI.getPlayerData(p);
		
		if (data == null) {
			return "";
		}
		
		switch(identifier) {
		
		case "attribute_points":
			return String.valueOf(data.getAttributePoints());
		case "mana":
			return String.valueOf(data.getMana());
		case "max_mana":
			return String.valueOf(data.getMaxMana());					
		}
		
		if (identifier.startsWith("skill_level_")) {
			
			String skill = identifier.split("skill_level_")[1];
			
			return data.hasSkill(skill) ? String.valueOf(data.getSkill(skill).getLevel()) : "0";
		}
		
		if (identifier.startsWith("skill_points_")) {
			
			String skill = identifier.split("skill_points_")[1];
			
			return data.hasSkill(skill) ? String.valueOf(data.getSkill(skill).getPoints()) : "0";
		}
		
		if (identifier.startsWith("skill_cost_")) {
			
			String skill = identifier.split("skill_cost_")[1];
			
			return data.hasSkill(skill) ? String.valueOf(data.getSkill(skill).getCost()) : "0";
		}
		
		if (identifier.startsWith("skill_levelreq_")) {
			
			String skill = identifier.split("skill_levelreq_")[1];
			
			return data.hasSkill(skill) ? String.valueOf(data.getSkill(skill).getLevelReq()) : "0";
		}
		
		if (identifier.startsWith("skill_message_")) {
			
			String skill = identifier.split("skill_message_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).getData().getMessage() : "";
		}
		
		if (identifier.startsWith("skill_req_")) {
			
			String skill = identifier.split("skill_req_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).getData().getSkillReq() : "";
		}
		
		if (identifier.startsWith("skill_type_")) {
			
			String skill = identifier.split("skill_type_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).getData().getType() : "";
		}
		
		if (identifier.startsWith("skill_is_maxed_")) {
			
			String skill = identifier.split("skill_is_maxed_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).isMaxed() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (identifier.startsWith("skill_can_cast_")) {
			
			String skill = identifier.split("skill_can_cast_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).getData().canCast() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse() : PlaceholderAPIPlugin.booleanFalse();
		}	
		
		if (identifier.startsWith("skill_can_autolevel_")) {
			
			String skill = identifier.split("skill_can_autolevel_")[1];
			
			return data.hasSkill(skill) ? data.getSkill(skill).getData().canAutoLevel() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (identifier.startsWith("player_skill_points_")) {
			
			String num = identifier.split("player_skill_points_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "0";
			}
			
			if (n > data.getSkills().size()) {
				return "0";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return String.valueOf(skill.getPoints());
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_level_")) {
			
			String num = identifier.split("player_skill_level_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "0";
			}
			
			if (n > data.getSkills().size()) {
				return "0";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return String.valueOf(skill.getLevel());
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_levelreq_")) {
			
			String num = identifier.split("player_skill_levelreq_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "0";
			}
			
			if (n > data.getSkills().size()) {
				return "0";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return String.valueOf(skill.getLevelReq());
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_name_")) {
			
			String num = identifier.split("player_skill_name_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().getName();
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_message_")) {
			
			String num = identifier.split("player_skill_message_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().getMessage();
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_req_")) {
			
			String num = identifier.split("player_skill_req_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().getSkillReq();
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_type_")) {
			
			String num = identifier.split("player_skill_type_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().getType();
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_can_autolevel_")) {
			
			String num = identifier.split("player_skill_can_autolevel_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().canAutoLevel() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				}

				count = count + 1;
			}
		}
		
		if (identifier.startsWith("player_skill_can_cast_")) {
			
			String num = identifier.split("player_skill_can_cast_")[1];
			
			int n = isInt(num) ? Integer.parseInt(num) : 1;
			
			if (data.getSkills() == null || data.getSkills().isEmpty()) {
				return "";
			}
			
			if (n > data.getSkills().size()) {
				return "";
			}
			
			int count = 1;
			
			for (PlayerSkill skill : data.getSkills()) {
				if (count == n) {
					return skill.getData().canCast() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				}

				count = count + 1;
			}
		}
		
		return null;
	}
	
	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}