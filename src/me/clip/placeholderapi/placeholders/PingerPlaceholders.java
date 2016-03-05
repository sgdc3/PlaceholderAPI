package me.clip.placeholderapi.placeholders;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.gson.Gson;

import lombok.Data;
import lombok.Setter;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.Configurable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.Taskable;
import me.clip.placeholderapi.placeholders.PingerPlaceholders.ServerListPing.StatusResponse;

public class PingerPlaceholders extends IPlaceholderHook implements Cacheable, Taskable, Configurable {

	private BukkitTask pingTask = null;
	
	private String online = "&aOnline";
	
	private String offline = "&cOffline";
	
    private static final Gson gson = new Gson();
	
	private final Map<String, StatusResponse> servers = new ConcurrentHashMap<String, StatusResponse>();
	
	private final Map<String, InetSocketAddress> toPing = new ConcurrentHashMap<String, InetSocketAddress>();
	
	private int interval = 60;
	
	public PingerPlaceholders(InternalHook hook) {
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
		
		StatusResponse r = null;
		
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
			return r != null ? r.getDescription() : "";
		}
		if (type.equalsIgnoreCase("count") || type.equalsIgnoreCase("players")) {
			return r != null ? String.valueOf(r.getPlayers().online) : "0";
		}
		if (type.equalsIgnoreCase("max") || type.equalsIgnoreCase("maxplayers")) {
			return r != null ? String.valueOf(r.getPlayers().max) : "0";
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
					
					StatusResponse r = null;
					
					try {
						
						ServerListPing ping = new ServerListPing();
						
						ping.setHost(address.getValue());
						
						 r = ping.fetchData();
						
					} catch (Exception ex) {
					}
					if (r != null) {
						servers.put(address.getKey(), r);
					} else {
						if (servers.containsKey(address.getKey())) {
							servers.remove(address.getKey());
						}
					}
				}
			}
			
		}.runTaskTimerAsynchronously(getPlaceholderAPI(), 20L, 20L*interval);
	}

	@Override
	public void stop() {
		try {
			pingTask.cancel();
		} catch (Exception ex) {
		}
		pingTask = null;
	}

	public class ServerListPing {

	    @Setter
	    private InetSocketAddress host;
	    @Setter
	    private int timeout = 2000;
	    
		public StatusResponse fetchData() throws IOException {
	        Socket socket = null;
	        OutputStream oStr = null;
	        InputStream inputStream = null;
	        StatusResponse response = null;

	        try {
	            socket = new Socket();
	            socket.setSoTimeout(timeout);
	            socket.connect(host, timeout);

	            oStr = socket.getOutputStream();
	            DataOutputStream dataOut = new DataOutputStream(oStr);

	            inputStream = socket.getInputStream();
	            DataInputStream dIn = new DataInputStream(inputStream);

	            sendPacket(dataOut, prepareHandshake());
	            sendPacket(dataOut, preparePing());

	            response = receiveResponse(dIn);

	            dIn.close();
	            dataOut.close();
	            
	        } catch(Exception ex) {
	        }
	        
            if (oStr != null) {
                oStr.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
            
            return response;
	    }

	    private StatusResponse receiveResponse(DataInputStream dIn) throws IOException {
	        @SuppressWarnings("unused")
			int size = readVarInt(dIn);
	        int packetId = readVarInt(dIn);

	        if (packetId != 0x00) {
	            throw new IOException("Invalid packetId");
	        }

	        int stringLength = readVarInt(dIn);

	        if (stringLength < 1) {
	            throw new IOException("Invalid string length.");
	        }

	        byte[] responseData = new byte[stringLength];
	        dIn.readFully(responseData);
	        String jsonString = new String(responseData, Charset.forName("utf-8"));
	        StatusResponse response = gson.fromJson(jsonString, StatusResponse.class);
	        return response;
	    }

	    private void sendPacket(DataOutputStream out, byte[] data) throws IOException {
	        writeVarInt(out, data.length);
	        out.write(data);
	    }

	    private byte[] preparePing() throws IOException {
	        return new byte[] {0x00};
	    }

	    private byte[] prepareHandshake() throws IOException {
	        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	        DataOutputStream handshake = new DataOutputStream(bOut);
	        bOut.write(0x00); //packet id
	        writeVarInt(handshake, 4); //protocol version
	        writeString(handshake, host.getHostString());
	        handshake.writeShort(host.getPort());
	        writeVarInt(handshake, 1); //target state 1
	        return bOut.toByteArray();
	    }

	    public void writeString(DataOutputStream out, String string) throws IOException {
	        writeVarInt(out, string.length());
	        out.write(string.getBytes(Charset.forName("utf-8")));
	    }

	    public int readVarInt(DataInputStream in) throws IOException {
	        int i = 0;
	        int j = 0;
	        while (true) {
	            int k = in.readByte();
	            i |= (k & 0x7F) << j++ * 7;
	            if (j > 5) throw new RuntimeException("VarInt too big");
	            if ((k & 0x80) != 128) break;
	        }
	        return i;
	    }

	    public void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
	        while (true) {
	            if ((paramInt & 0xFFFFFF80) == 0) {
	                out.write(paramInt);
	                return;
	            }

	            out.write(paramInt & 0x7F | 0x80);
	            paramInt >>>= 7;
	        }
	    }

	    @Data
	    public class StatusResponse {
	        private String description;
	        private Players players;
	        private Version version;
	        private String favicon;
	        private int time;

	        @Data
	        public class Players {
	            private int max;
	            private int online;
	            private List<Player> sample;
	        }
	        @Data
	        public class Player {
	            private String name;
	            private String id;

	        }
	        @Data
	        public class Version {
	            private String name;
	            private String protocol;
	        }
	    }
	}

	@Override
	public void clear() {
		servers.clear();
		toPing.clear();
	}
}
