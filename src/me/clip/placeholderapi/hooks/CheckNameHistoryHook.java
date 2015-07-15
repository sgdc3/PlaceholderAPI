package me.clip.placeholderapi.hooks;

import java.util.Date;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.vk2gpz.checknamehistory.CheckNameHistory;

public class CheckNameHistoryHook {
	
	private PlaceholderAPIPlugin plugin;

	public CheckNameHistoryHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("CheckNameHistory")) {
			
			CheckNameHistory cnh = (CheckNameHistory) Bukkit.getPluginManager().getPlugin("CheckNameHistory");
			
			if (cnh != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(cnh, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}
						
				        Map<String, Date> map = CheckNameHistory.getPreviousNamesWithDates(p.getName(), false);
				        
				        if (map == null || map.size() == 0) {
				            return "";
				        }
				        
				        if (identifier.startsWith("previous_name_date_")) {
				        	
				        	String val = identifier.split("previous_name_date_")[1];
				        	
				        	int no = 1;
				        	
				        	try {
				        		no = Integer.parseInt(val);
				        	} catch (Exception e) {
				        		return null;
				        	}
				        	
				        	if (no > map.size()) {
				        		return "";
				        	}
				        	
				        	int c = 1;
				        	
				        	for (Date s : map.values()) {
				        		if (c == no) {
				        			
				        			if (s == null) {
				        				return "";
				        			}
				        			
				        			return PlaceholderAPIPlugin.getDateFormat().format(s);
				        		}
				        		c++;
				        	}
				        }
					
				        if (identifier.startsWith("previous_names_")) {
				        	
				        	String val = identifier.split("previous_names_")[1];
				        	
				        	int no = 1;
				        	
				        	try {
				        		no = Integer.parseInt(val);
				        	} catch (Exception e) {
				        		return null;
				        	}
				        	
				        	if (no > map.size()) {
				        		return "";
				        	}
				        	
				        	int c = 1;
				        	
				        	for (String s : map.keySet()) {
				        		if (c == no) {
				        			return s;
				        		}
				        		c++;
				        	}
				        }
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into CheckNameHistory for placeholders!");
				}
			}
		}
	}
}
