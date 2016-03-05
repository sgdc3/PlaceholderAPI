package me.clip.placeholderapi.injector.title;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerTitle;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

/**
@author extended_clip
*/
public class TitlePacketListener extends PacketAdapter {
	
	public TitlePacketListener(PlaceholderAPIPlugin i){
		super(i, ListenerPriority.HIGHEST, PacketType.Play.Server.TITLE);
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}
	
	@Override
	public void onPacketSending(PacketEvent e) {
		
		if (e.getPlayer() == null) {
			return;
		}
		
		WrapperPlayServerTitle packet = new WrapperPlayServerTitle(e.getPacket());

		if (packet.getTitle() == null) {
			return;
		}
		
		WrappedChatComponent wcp = packet.getTitle();
		
		String json = wcp.getJson();
		
		if (PlaceholderAPI.getPlaceholderPattern().matcher(json).find()) {
			
			json = PlaceholderAPI.setPlaceholders(e.getPlayer(), json);
		}
		
		wcp.setJson(json);
		
		packet.setTitle(wcp);
	}
}
