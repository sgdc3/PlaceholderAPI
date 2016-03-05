package me.clip.placeholderapi.placeholders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.Configurable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.Taskable;

public class BetterPingerPlaceholders extends IPlaceholderHook implements Cacheable, Taskable, Configurable {

	private BukkitTask pingTask = null;
	
	private String online = "&aOnline";
	
	private String offline = "&cOffline";
	
	private final Map<String, Pinger> servers = new ConcurrentHashMap<String, Pinger>();
	
	private final Map<String, InetSocketAddress> toPing = new ConcurrentHashMap<String, InetSocketAddress>();
	
	private int interval = 60;
	
	public BetterPingerPlaceholders(InternalHook hook) {
		super(hook);
	
		String on = getPlaceholderAPI().getConfig().getString(getIdentifier() + ".online", "&aOnline");
		
		online = on != null ? on : "&aOnline";
		
		String off = getPlaceholderAPI().getConfig().getString(getIdentifier() + ".offine", "&cOffline");
		
		offline = off != null ? off : "&cOffline";
		
		int time = getPlaceholderAPI().getConfig().getInt(getIdentifier() + ".check_interval", 60);
		
		if (time > 0) {
			interval = time;
		}
	}
	

	@Override
	public Map<String, Object> getDefaults() {
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("check_interval", 30);
		defaults.put("online", "&aOnline");
		defaults.put("offline", "&cOffline");
		return defaults;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		int place = identifier.indexOf("_");
		
		if (place == -1) {
			return null;
		}
		
		String type = identifier.substring(0, place);

		String address = identifier.substring(place+1);
		
		Pinger r = null;
		
		for (String a : servers.keySet()) {
			if (a.equalsIgnoreCase(address)) {
				r = servers.get(a);
				break;
			}
		}
		
		if (r == null) {
			
			if (!toPing.containsKey(address)) {
				int port = 25565;
				String add = address;
				if (address.contains(":")) {
					add = address.substring(0, address.indexOf(":"));
					try {
						port = Integer.parseInt(address.substring(address.indexOf(":")+1));
					} catch (Exception ex) {
					}
				}
				toPing.put(address, new InetSocketAddress(add, port));
			}
		}
		
		if (type.equalsIgnoreCase("motd")) {
			return r != null ? r.getMotd() : "";
		}
		if (type.equalsIgnoreCase("count") || type.equalsIgnoreCase("players")) {
			return r != null ? String.valueOf(r.getPlayersOnline()) : "0";
		}
		if (type.equalsIgnoreCase("max") || type.equalsIgnoreCase("maxplayers")) {
			return r != null ? String.valueOf(r.getMaxPlayers()) : "0";
		}
		if (type.equalsIgnoreCase("pingversion") || type.equalsIgnoreCase("pingv")) {
			return r != null ? String.valueOf(r.getPingVersion()) : "-1";
		}
		if (type.equalsIgnoreCase("gameversion") || type.equalsIgnoreCase("version")) {
			return r != null && r.getGameVersion() != null ? String.valueOf(r.getGameVersion()) : "";
		}
		if (type.equalsIgnoreCase("online") || type.equalsIgnoreCase("isonline")) {
			return r != null ? online : offline;
		}
		
		return null;
	}
	
	@Override
	public void start() {
		pingTask = new BukkitRunnable() {

			@Override
			public void run() {
				
				if (toPing.isEmpty()) {
					return;
				}
				
				for (Entry<String, InetSocketAddress> address : toPing.entrySet()) {
					
					Pinger r = null;
					
					try {
						
						r = new Pinger(address.getValue().getHostName(), address.getValue().getPort());
						
						if (r.fetchData()) {
							servers.put(address.getKey(), r);
						} else {
							if (servers.containsKey(address.getKey())) {
								servers.remove(address.getKey());
							}
						}
						
					} catch (Exception ex) {
					}
				}
			}
			
		}.runTaskTimerAsynchronously(getPlaceholderAPI(), 20L, 20L*interval);
	}
	

public final class Pinger {
	private String address = "localhost";
	private int port = 25565;

	private int timeout = 2000;

	private int pingVersion = -1;
	private int protocolVersion = -1;
	private String gameVersion;
	private String motd;
	private int playersOnline = -1;
	private int maxPlayers = -1;

	public Pinger(String address,int port) {
		this.setAddress(address);
		this.setPort(port);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return this.timeout;
	}

	private void setPingVersion(int pingVersion) {
		this.pingVersion = pingVersion;
	}

	public int getPingVersion() {
		return this.pingVersion;
	}

	private void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	private void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public String getGameVersion() {
		return this.gameVersion;
	}

	private void setMotd(String motd) {
		this.motd = motd;
	}

	public String getMotd() {
		return this.motd;
	}

	private void setPlayersOnline(int playersOnline) {
		this.playersOnline = playersOnline;
	}

	public int getPlayersOnline() {
		return this.playersOnline;
	}

	private void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public boolean fetchData() {
		try {
			Socket socket = new Socket();
			OutputStream outputStream;
			DataOutputStream dataOutputStream;
			InputStream inputStream;
			InputStreamReader inputStreamReader;

			socket.setSoTimeout(this.timeout);

			socket.connect(new InetSocketAddress(
				this.getAddress(),
				this.getPort()
			),this.getTimeout());

			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);

			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-16BE"));

			dataOutputStream.write(new byte[]{
				(byte) 0xFE,
				(byte) 0x01
			});

			int packetId = inputStream.read();

			if (packetId == -1) {
				
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
				return false;
			}

			if (packetId != 0xFF) {
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
				return false;
			}

			int length = inputStreamReader.read();

			if (length == -1) {
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
				return false;
			}

			if (length == 0) {
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
				return false;
			}

			char[] chars = new char[length];

			if (inputStreamReader.read(chars,0,length) != length) {
				try {
					socket.close();
				} catch (IOException e) {
				}
				socket = null;
				return false;
			}

			String string = new String(chars);

			if (string.startsWith("§")) {
				String[] data = string.split("\0");

				this.setPingVersion(Integer.parseInt(data[0].substring(1)));
				this.setProtocolVersion(Integer.parseInt(data[1]));
				this.setGameVersion(data[2]);
				this.setMotd(data[3]);
				this.setPlayersOnline(Integer.parseInt(data[4]));
				this.setMaxPlayers(Integer.parseInt(data[5]));
			} else {
				String[] data = string.split("§");

				this.setMotd(data[0]);
				this.setPlayersOnline(Integer.parseInt(data[1]));
				this.setMaxPlayers(Integer.parseInt(data[2]));
			}

			dataOutputStream.close();
			outputStream.close();

			inputStreamReader.close();
			inputStream.close();

			socket.close();
		} catch (SocketException exception) {
			return false;
		} catch (IOException exception) {
			return false;
		}

		return true;
	}
}

	@Override
	public void stop() {
		try {
			pingTask.cancel();
		} catch (Exception ex) {
		}
		pingTask = null;
	}


	@Override
	public void clear() {
		servers.clear();
		toPing.clear();
	}
}
