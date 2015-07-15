package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.skill.Skill;

public class HeroesHook {

	PlaceholderAPIPlugin plugin;
	
	private Heroes heroes = null;
	
	public HeroesHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("Heroes")) {
			
			heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
			
			if (heroes != null) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("Heroes", 
						new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								
								if (p == null) {
									return "";
								}

								Hero h = heroes.getCharacterManager().getHero(p);
								
								if (h == null) {
									return "";
								}
								
								if (identifier.startsWith("skill_level_")) {
									
									String sk = identifier.split("skill_level_")[1];
									
									if (sk == null || sk.isEmpty()) {
										return "";
									}
									
									Skill skill = heroes.getSkillManager().getSkill(sk);
									
									if (skill == null) {
										return "INVALID SKILL";
									}
									
									return String.valueOf(h.getSkillLevel(skill));
								}
								
								switch (identifier) {
								
								case "main_class_name":
									return h.getHeroClass() != null ? h.getHeroClass().getName() : "";
								case "main_class_description":
									return h.getHeroClass() != null ? h.getHeroClass().getDescription() : "";
								case "main_class_tier":
									return h.getHeroClass() != null ? String.valueOf(h.getHeroClass().getTier()) : "0";
								case "main_class_level":
									return h.getHeroClass() != null ? String.valueOf(h.getLevel(h.getHeroClass())) : "0";
								case "second_class_name":
									return h.getSecondClass() != null ? h.getSecondClass().getName() : "";
								case "second_class_description":
									return h.getSecondClass() != null ? h.getSecondClass().getDescription() : "";
								case "second_class_tier":
									return h.getSecondClass() != null ? String.valueOf(h.getSecondClass().getTier()) : "0";
								case "second_class_level":
									return h.getSecondClass() != null ? String.valueOf(h.getLevel(h.getSecondClass())) : "0";
								case "level":
									return String.valueOf(h.getLevel());
								case "mana":
									return String.valueOf(h.getMana());
								case "mana_regen":
									return String.valueOf(h.getManaRegen());
								case "max_mana":
									return String.valueOf(h.getMaxMana());
								case "mastered_classes":
									return h.getMasteredClasses() != null ? h.getMasteredClasses().toString() : "";
								case "mastered_classes_amount":
									return h.getMasteredClasses() != null ? String.valueOf(h.getMasteredClasses().size()) : "0";
								case "party_is_no_pvp":
									return h.getParty() != null && h.getParty().isNoPvp() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
								case "party_leader":
									return h.getParty() != null && h.getParty().getLeader() != null ? h.getParty().getLeader().getName() : "";
								case "party_size":
									return h.getParty() != null ? String.valueOf(h.getParty().getMembers().size()) : "0";
								}
								return null;
							}
					
				}, true);
				
				
				if (hooked) {
					plugin.getLogger().info("Successfully hooked into Heroes for placeholders!");
				}
			
			
			}
		}
		
	}
}
