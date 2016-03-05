package me.clip.placeholderapi.injector.tab;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerListHeaderFooter;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

/**
@author extended_clip
*/
public class TabPacketListener extends PacketAdapter {
	
	public TabPacketListener(PlaceholderAPIPlugin i){
		super(i, ListenerPriority.HIGHEST, PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}
	
	@Override
	public void onPacketSending(PacketEvent e) {
		
		if (e.getPlayer() == null) {
			return;
		}
		
		WrapperPlayServerPlayerListHeaderFooter  packet = new WrapperPlayServerPlayerListHeaderFooter(e.getPacket());

		if (packet.getHeader() != null) {
			
			WrappedChatComponent wcpHeader = packet.getHeader();
			
			String headerJson = wcpHeader.getJson();
			
			if (PlaceholderAPI.getPlaceholderPattern().matcher(headerJson).find()) {
				
				headerJson = PlaceholderAPI.setPlaceholders(e.getPlayer(), headerJson);
				
				wcpHeader.setJson(headerJson);
				
				packet.setHeader(wcpHeader);
			}
		}
		
		if (packet.getFooter() != null) {
			
			WrappedChatComponent wcpFooter = packet.getFooter();
			
			String footerJson = wcpFooter.getJson();
			
			if (PlaceholderAPI.getPlaceholderPattern().matcher(footerJson).find()) {
				
				footerJson = PlaceholderAPI.setPlaceholders(e.getPlayer(), footerJson);
				
				wcpFooter.setJson(footerJson);
				
				packet.setFooter(wcpFooter);
			}
		}
	}
}
