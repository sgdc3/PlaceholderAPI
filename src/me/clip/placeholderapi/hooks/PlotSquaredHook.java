package me.clip.placeholderapi.hooks;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.UUIDHandler;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class PlotSquaredHook extends IPlaceholderHook {

	public PlotSquaredHook(InternalHook hook) {
		super(hook);
	}
	
	private PS api = null;

	@Override
	public boolean hook() {
		
		boolean hooked = false;
		
		api = PS.get();
			
		if (api != null) {
				
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (api == null || p == null) {
			return "";
		}
		
		PlotPlayer pl = PlotPlayer.get(p.getName());
		
		if (pl == null) {
			return "";
		}
		
		switch (identifier) {
		
		case "currentplot_alias":
			return pl.getCurrentPlot() != null ? pl.getCurrentPlot().getAlias() : "";
		case "currentplot_owner":
			if (pl.getCurrentPlot() != null) {
				Set<UUID> o = pl.getCurrentPlot().getOwners();
				
				if (o == null || o.isEmpty()) {
					return "";
				}
				
				UUID uid = (UUID)o.toArray()[0];
				
				if (uid == null) {
					return "";
				}
				
				String name = UUIDHandler.getName(uid);
				
				return name != null ? name : Bukkit.getOfflinePlayer(uid) != null ? Bukkit.getOfflinePlayer(uid).getName() : "unknown";
			} else {
				return "";
			}
		case "currentplot_members_size":
			if (pl.getCurrentPlot() == null) {
				return "0";
			}
			return pl.getCurrentPlot().getMembers().size() + "";
		case "currentplot_world":
			return p.getWorld().getName();
		case "has_plot":
			return pl.getPlotCount() > 0 ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "has_build_rights":
			return pl.getCurrentPlot() != null ? pl.getCurrentPlot().getMembers().contains(pl.getUUID()) || pl.getCurrentPlot().getOwners().contains(pl.getUUID()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse() : "";
		case "plot_count":
			return pl.getPlotCount() + "";
		case "allowed_plot_count":
			return pl.getAllowedPlots() + "";						
		}
		return null;
	}
}
