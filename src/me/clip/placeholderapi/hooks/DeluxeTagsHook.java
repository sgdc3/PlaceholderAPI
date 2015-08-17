package me.clip.placeholderapi.hooks;

import java.util.List;

import me.clip.deluxetags.DeluxeTag;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class DeluxeTagsHook extends IPlaceholderHook {
	
	public DeluxeTagsHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String s) {

		if (p == null) {
			return "";
		}
		
		if (s.startsWith("has_tag_")) {
			s = s.replace("has_tag_", "");
			
			DeluxeTag tag = DeluxeTag.getLoadedTag(s);
			
			if (tag == null) {
				return "invalid tag";
			}
			
			return tag.hasTagPermission(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (s.startsWith("get_tag_")) {
			s = s.replace("get_tag_", "");
			
			DeluxeTag tag = DeluxeTag.getLoadedTag(s);
			
			if (tag == null) {
				return "invalid tag";
			}
			
			return tag.getDisplayTag();
		}
		
		if (s.startsWith("get_description_")) {
			s = s.replace("get_description_", "");
			
			DeluxeTag tag = DeluxeTag.getLoadedTag(s);
			
			if (tag == null) {
				return "invalid tag";
			}
			
			return tag.getDescription();
		}
		
		switch (s) {

		case "tag":
			return DeluxeTag.getPlayerDisplayTag(p);

		case "identifier":
			String tagId = DeluxeTag.getPlayerTagIdentifier(p);
			return tagId != null ? tagId : "";

		case "description":
			return DeluxeTag.getPlayerTagDescription(p);

		case "amount":
			List<String> tmp = DeluxeTag.getAvailableTagIdentifiers(p);
			return tmp != null ? String.valueOf(tmp.size())
					: "0";
		}

		return null;
	
	}
}

