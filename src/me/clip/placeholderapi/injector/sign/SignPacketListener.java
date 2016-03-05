package me.clip.placeholderapi.injector.sign;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerUpdateSign;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

/**
@author extended_clip
*/
public class SignPacketListener extends PacketAdapter {
	
	private final Map<String, Location> signs = new HashMap<String, Location>();
	
	private PlaceholderAPIPlugin plugin;
	
	private int refreshInterval;
	
	private BukkitTask updateTask;
	
	public SignPacketListener(PlaceholderAPIPlugin i, int refreshInterval){
		
		super(i, ListenerPriority.HIGHEST, PacketType.Play.Server.UPDATE_SIGN);
		
		plugin = i;
		
		this.refreshInterval = refreshInterval;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
		
		if (refreshInterval > 0) {
			startTask();
		}
	}
	
	public void stopTask() {
		if (updateTask != null) {
			try {
			    updateTask.cancel();
			} catch (Exception ex) {}
			updateTask = null;
		}
	}
	
	public void clear() {
		signs.clear();
	}
	
	public void startTask() {
		
		updateTask = new BukkitRunnable() {

			@Override
			public void run() {

				Iterator<Entry<String, Location>> locs = signs.entrySet().iterator();
				
				while (locs.hasNext()) {
					
					Entry<String, Location> entry = locs.next();
					
					Location l = entry.getValue();
					
					if (!l.getChunk().isLoaded()) {
						locs.remove();
						continue;
					}
					
					Block b = l.getWorld().getBlockAt(l);
					
					if (b == null || (!(b.getState() instanceof Sign))) {
						locs.remove();
						continue;
					}
					
					Sign s = (Sign) b.getState();
						
					s.update();
				}
			
			}
			
		}.runTaskTimer(plugin, refreshInterval * 20L, refreshInterval * 20L);	
	}
	
	@Override
	public void onPacketSending(PacketEvent e) {
		
		if (e.getPlayer() == null) {
			return;
		}
		
		WrapperPlayServerUpdateSign  packet = new WrapperPlayServerUpdateSign(e.getPacket());
		
		WrappedChatComponent[] lines = packet.getLines();
		
		Location l = new Location(e.getPlayer().getWorld(), packet.getLocation().getX(), packet.getLocation().getY(), packet.getLocation().getZ());
		
		for (WrappedChatComponent component : lines) {
			
			if (component == null || component.getJson() == null) {
				continue;
			}
			
			String json = component.getJson();
			
			if (PlaceholderAPI.getPlaceholderPattern().matcher(json).find()) {
				
				if (!signs.containsKey(l.toString()) && refreshInterval > 0) {
					
					signs.put(l.toString(), l);
				}
				
				json = PlaceholderAPI.setPlaceholders(e.getPlayer(), json);
				
				component.setJson(json);
			}
		}
		
		packet.setLines(lines);
	}
}
