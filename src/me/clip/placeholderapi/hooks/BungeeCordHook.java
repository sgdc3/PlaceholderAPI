package me.clip.placeholderapi.hooks;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.Configurable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.Taskable;

public class BungeeCordHook extends IPlaceholderHook implements PluginMessageListener, Taskable, Cacheable, Configurable {

	private final Map<String, Integer> servers = new ConcurrentHashMap<String, Integer>();
	
	private int total = 0;
	
	private BukkitTask task;
	
	private static boolean registered = false;
	
	private final String CHANNEL = "BungeeCord";
	
	private int fetchInterval = 60;
	
	public BungeeCordHook(InternalHook hook) {
		super(hook);
		int interval = getPlaceholderAPI().getConfig().getInt(getIdentifier() + ".check_interval", 60);
		
		if (interval > 0) {
			fetchInterval = interval;
		} else {
			fetchInterval = 60;
		}
	}
	
	@Override
	public Map<String, Object> getDefaults() {
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("check_interval", 30);
		return defaults;
	}

	@Override
	public boolean hook() {
		
		boolean hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		
		if (hooked) {
			
			if (!registered) {
				Bukkit.getMessenger().registerOutgoingPluginChannel(getPlaceholderAPI(), CHANNEL);
				Bukkit.getMessenger().registerIncomingPluginChannel(getPlaceholderAPI(), CHANNEL, this);
				registered = true;
			}	
		}
		return hooked;
	}
	
	private int count = 0;
	
	private void getServers() {
		
		if (Bukkit.getOnlinePlayers().isEmpty()) {
			return;
		}
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		try {
			
			out.writeUTF("GetServers");
			
			Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(getPlaceholderAPI(), CHANNEL, out.toByteArray());
			
		} catch (Exception e) {
		}
	}
	
	private void getPlayers(String server) {
		
		if (Bukkit.getOnlinePlayers().isEmpty()) {
			return;
		}
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		try {
			
			out.writeUTF("PlayerCount");
			
			out.writeUTF(server);
			
			Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(getPlaceholderAPI(), CHANNEL, out.toByteArray());
			
		} catch (Exception e) {
		}
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {

		if (!channel.equals(CHANNEL)) {
			return;
		}
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		 
		try {
			
			String subChannel = in.readUTF();
			 
			if (subChannel.equals("PlayerCount")) {
				 
				String server = in.readUTF();				
				
				if (in.available() > 0) {
					
					int count = in.readInt();
					
					if (server.equals("ALL")) {
						total = count;
					} else {
						servers.put(server, count);	
					}
				}
				
				
			} else if (subChannel.equals("GetServers")) {
				
				String[] serverList = in.readUTF().split(", ");
				
				if (serverList == null || serverList.length == 0) {
					return;
				}
				
				for (String server : serverList) {					
					if (!servers.containsKey(server)) {
						servers.put(server, 0);
					}
				}
			}
		 
		} catch (Exception e) {
		}
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		
		if (identifier.equalsIgnoreCase("total") || identifier.equalsIgnoreCase("all")) {
			return String.valueOf(total);
		}
		
		if (servers == null || servers.isEmpty()) {
			return "0";
		}
		
		Iterator<Entry<String, Integer>> i = servers.entrySet().iterator();
		
		while (i.hasNext()) {
			Entry<String, Integer> entry = i.next();
			if (entry.getKey().equalsIgnoreCase(identifier)) {
				return String.valueOf(entry.getValue());
			}
		}
	
		return null;
	
	}

	@Override
	public void start() {

		task = new BukkitRunnable() {

			@Override
			public void run() {
				
				if (servers.isEmpty()) {
					
					getServers();
					
					getPlayers("ALL");

					return;
				}
				
				for (String server : servers.keySet()) {
					getPlayers(server);
				}
				
				getPlayers("ALL");
				
				count = count+1;
				
				if (count == 10) {
					
					count = 0;
					
					Bukkit.getScheduler().runTaskLater(getPlaceholderAPI(), new Runnable() {

						@Override
						public void run() {
							getServers();
						}
						
					}, 5L);
				}
				
			}
		}.runTaskTimer(getPlaceholderAPI(), 100L, 20L*fetchInterval);
	}

	@Override
	public void stop() {
		if (task != null) {
			try {
				task.cancel();
			} catch (Exception ex) {	
			}
			task = null;
		}
	}

	@Override
	public void clear() {
		servers.clear();
	}
}
