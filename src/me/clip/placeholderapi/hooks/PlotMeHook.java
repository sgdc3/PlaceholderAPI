package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.bukkit.api.BukkitLocation;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class PlotMeHook {

	private PlaceholderAPIPlugin plugin;
	
	private PlotMeCoreManager api;
	
	public PlotMeHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
	
		if (Bukkit.getPluginManager().isPluginEnabled("PlotMe")) {
			
			api = PlotMeCoreManager.getInstance();
			
			if (api != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("plotme", new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						BukkitLocation location = new BukkitLocation(p.getLocation());
						
						if (!api.isPlotWorld(location)) {
							switch (identifier){
							
							case "in_plot":
							case "in_plot_world":
								return PlaceholderAPIPlugin.booleanFalse();
							}
							return "";
						} else {
							if (identifier.equals("in_plot_world")) {
								return PlaceholderAPIPlugin.booleanTrue();
							}
						}
						
						String id = api.getPlotId(location);
						
						if (id == null) {
							if (identifier.equals("in_plot")) {
								return PlaceholderAPIPlugin.booleanFalse();
							} else {
								return "";
							}
						} else {
							if (identifier.equals("in_plot")) {
								return PlaceholderAPIPlugin.booleanTrue();
							}
						}
						
						Plot plot = api.getMap(location).getPlot(id);
						
						if (plot == null) {
							return "";
						}
						
						switch (identifier){
						
						case "current_plot_id":
							return plot.getId();
						case "current_plot_owner":
							return plot.getOwner();	
						case "current_plot_owner_uuid":
							return plot.getOwnerId().toString();	
						case "current_plot_allowed":
							return plot.getAllowed();	
						case "current_plot_denied":
							return plot.getDenied();
						case "current_plot_finished_date":
							return plot.getFinishedDate();
						case "current_plot_world":
							return plot.getWorld();
						case "current_plot_current_bid":
							return String.valueOf(plot.getCurrentBid());
						case "current_plot_custom_price":
							return String.valueOf(plot.getCustomPrice());
						case "current_plot_expired_date":
							return plot.getExpiredDate() != null ? 
									PlaceholderAPIPlugin.getDateFormat().format(plot.getExpiredDate()) : "";
						case "current_plot_is_allowed":
							return plot.isAllowed(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_allowed_consulting":
							return plot.isAllowedConsulting(p.getName()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_denied":
							return plot.isDenied(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_denied_consulting":
							return plot.isDeniedConsulting(p.getName()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_finished":
							return plot.isFinished() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_for_sale":
							return plot.isForSale() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_protect":
							return plot.isProtect() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_auctioned":
							return plot.isAuctioned()? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						}
						return null;
					}
					
				});
				
				if (hooked) {
					plugin.log.info("Successfully hooked into PlotMe for placeholders!");
				}
			}
		}
	}

}
