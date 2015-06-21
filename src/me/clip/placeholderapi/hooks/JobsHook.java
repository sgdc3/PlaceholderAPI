package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.JobsPlugin;

public class JobsHook {

	private PlaceholderAPIPlugin plugin;

	public JobsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("Jobs")) {
		
			JobsPlugin jobs = (JobsPlugin) Bukkit.getPluginManager().getPlugin("Jobs");
			
			if (jobs != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(jobs, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (identifier.equals("jobs")) {
							
							return getJobsIn(p);
							
						} else if (identifier.equals("format")) {
							
							return getJobsFormat(p);
							
						} else if (identifier.contains("name_")) {
							
							int job = 1;
							
							try {
								
								job = Integer.parseInt(identifier.split("name_")[1]);
								
							} catch (NumberFormatException e) {
								
								e.printStackTrace();
							}

							if (Jobs.getJobsDAO().getAllJobs(p.getPlayer()).size() >= (job)) {
								return String.valueOf(Jobs.getJobsDAO().getAllJobs(p.getPlayer()).get(job-1).getJobName());
							} else {
								return "";
							}
						} else if (identifier.contains("level_")) {
							
							int job = 1;
							
							try {
								
								job = Integer.parseInt(identifier.split("level_")[1]);
								
							} catch (NumberFormatException e) {
								
								e.printStackTrace();
							}

							if (Jobs.getJobsDAO().getAllJobs(p.getPlayer()).size() >= (job)) {
								return String.valueOf(Jobs.getJobsDAO().getAllJobs(p.getPlayer()).get(job-1).getLevel());
							} else {
								return "0";
							}
						} else if (identifier.contains("maxlevel_")) {
							
							int job = 1;
							
							try {
								
								job = Integer.parseInt(identifier.split("maxlevel_")[1]);
								
							} catch (NumberFormatException e) {
								
								e.printStackTrace();
							}

							if (Jobs.getJobsDAO().getAllJobs(p.getPlayer()).size() >= (job)) {
								return String.valueOf(Jobs.getJob((Jobs.getJobsDAO().getAllJobs(p.getPlayer()).get(job-1)).getJobName()).getMaxLevel());
							} else {
								return "0";
							}
						} else if (identifier.contains("exp_")) {
							
							int job = 1;
							
							try {
								
								job = Integer.parseInt(identifier.split("exp_")[1]);
								
							} catch (NumberFormatException e) {
								
								e.printStackTrace();
							}

							if (Jobs.getJobsDAO().getAllJobs(p.getPlayer()).size() >= (job)) {
								return String.valueOf(Jobs.getJobsDAO().getAllJobs(p.getPlayer()).get(job-1).getExperience());
							} else {
								return "0";
							}
						}

						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into Jobs for placeholders!");
				}
			}
		}
	}

	private String getJobsFormat(Player p) {
		return getJobsIn(p).equals("0") ? "" : 
			ChatColor.stripColor(String.valueOf(Jobs.getPlayerManager().getJobsPlayer(p).getDisplayHonorific()));
	}
	
	private String getJobsIn(Player p) {
		if (Jobs.getJobsDAO().getAllJobs(p.getPlayer()) == null) {
			return "0";
		}
		return String.valueOf(Jobs.getJobsDAO().getAllJobs(p.getPlayer()).size());
	}
}
