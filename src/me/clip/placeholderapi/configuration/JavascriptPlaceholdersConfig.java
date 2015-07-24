package me.clip.placeholderapi.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.placeholders.JavascriptPlaceholders;
import me.clip.placeholderapi.javascript.JavascriptPlaceholder;
import me.clip.placeholderapi.javascript.JavascriptReturnType;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class JavascriptPlaceholdersConfig {

	private PlaceholderAPIPlugin plugin;
	private FileConfiguration config = null;
	private File file = null;

	public JavascriptPlaceholdersConfig(PlaceholderAPIPlugin i) {
		plugin = i;
		reload();
	}

	public void reload() {
		
		if (file == null) {
			file = new File(plugin.getDataFolder(), "javascript_placeholders.yml");
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		config.options().header("javascript_placeholders.yml"
				+ "\nYou can create custom placeholders which utilize javascript to determine the result of the custom placeholder you create."
				+ "\nYou can specify if the result is based on a boolean or the actual javascript."
				+ "\n"
				+ "\nIf you do not specify a type: the placeholder will default to a boolean type"
				+ "\nA boolean type must contain a true_result: and false_result:"
				+ "\n"
				+ "\nA string type only requires the expression: entry"
				+ "\n"
				+ "\nJavascript placeholders can contain normal placeholders in the expression, true_result, or false_result"
				+ "\nThese placeholders will be parsed to the correct values before the expression is evaluated."
				+ "\n"
				+ "\nYour javascript placeholders will be identified by: %javascript_<identifier>%"
				+ "\n"
				+ "\nJavascript placeholder format:"
				+ "\n"
				+ "\n    BOOLEAN TYPE"
				+ "\n<identifier>:"
				+ "\n  expression: <expression>"
				+ "\n  type: 'boolean'"
				+ "\n  true_result: <result if expression is true>"
				+ "\n  false_result: <result if expression is false>"
				+ "\n"
				+ "\n    STRING TYPE"
				+ "\n<identifier>:"
				+ "\n  expression: <expression>"
				+ "\n  type: 'string'"
				+ "\n"
				+ "\nExamples:"
				+ "\n"
				+ "\nmillionaire:"
				+ "\n  expression: '%vaulteco_balance% >= 1000000'"
				+ "\n  type: 'boolean'"
				+ "\n  true_result: '&aMillionaire'"
				+ "\n  false_result: '&cbroke'"
				+ "\nis_staff:"
				+ "\n  expression: '\"%vault_group%\" == \"Moderator\" || \"%vault_group%\" == \"Admin\" || \"%vault_group%\" == \"Owner\"'"
				+ "\n  type: 'boolean'"
				+ "\n  true_result: '&bStaff'"
				+ "\n  false_result: '&ePlayer'"
				+ "\nhealth_rounded:"
				+ "\n  expression: 'Math.round(%player_health%)'"
				+ "\n  type: 'string'");
		
		if (config.getKeys(false) == null || config.getKeys(false).isEmpty()) {
			config.set("millionaire.expression", "%vaulteco_balance% >= 1000000");
			config.set("millionaire.type", "boolean");
			config.set("millionaire.true_result", "&aMillionaire");
			config.set("millionaire.false_result", "&cbroke");
			config.set("is_staff.expression", "\"%vault_group%\" == \"Moderator\" || \"%vault_group%\" == \"Admin\" || \"%vault_group%\" == \"Owner\"");
			config.set("is_staff.type", "boolean");
			config.set("is_staff.true_result", "&bStaff");
			config.set("is_staff.false_result", "&ePlayer");
			config.set("health_rounded.expression", "Math.round(%player_health%)");
			config.set("health_rounded.type", "string");
		}
		
		save();
	}

	public FileConfiguration load() {
		if (config == null) {
			reload();
		}
		return config;
	}

	public void save() {
		if ((config == null) || (file == null))
			return;
		try {
			load().save(file);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save to " + file, ex);
		}
	}

	public int loadPlaceholders() {
		
		if (config.getKeys(false) == null || config.getKeys(false).isEmpty()) {
			return 0;
		}
		
		JavascriptPlaceholders.cleanup();
		
		for (String identifier : config.getKeys(false)) {
			
			JavascriptReturnType type = JavascriptReturnType.BOOLEAN;
			
			if (config.contains(identifier + ".type")) {
				
				String t = config.getString(identifier + ".type");
				
				if (JavascriptReturnType.getType(t) != null) {
					type = JavascriptReturnType.getType(t);
				}
			}
			
			if (!isValid(identifier, type)) {
				plugin.getLogger().warning("Javascript " + type.getType() + " placeholder " + identifier + " is invalid!");
				continue;
			}
			
			JavascriptPlaceholder pl = null;
			
			String expression = config.getString(identifier + ".expression");
			
			if (type == JavascriptReturnType.BOOLEAN) {
				
				String trueResult = config.getString(identifier + ".true_result");
				
				String falseResult = config.getString(identifier + ".false_result");
				
				pl = new JavascriptPlaceholder(identifier, type, expression, trueResult, falseResult);
			} else {
				
				pl = new JavascriptPlaceholder(identifier, type, expression, null, null);
			}
			
			boolean added = JavascriptPlaceholders.addJavascriptPlaceholder(pl);
			
			if (added) {
				plugin.getLogger().info("Javascript " + type.getType() + " placeholder %javascript_" + identifier + "% has been loaded!");
			} else {
				plugin.getLogger().warning("Javascript " + type.getType() + " placeholder %javascript_" + identifier + "% is a duplicate!");
			}
		}
		return JavascriptPlaceholders.getJavascriptPlaceholdersAmount();
	}
	
	private boolean isValid(String identifier, JavascriptReturnType type) {
		if (type == JavascriptReturnType.BOOLEAN) {
			return config.isString(identifier + ".expression") 
				&& config.isString(identifier + ".true_result") 
				&& config.isString(identifier + ".false_result");
		} else {
			
			return config.isString(identifier + ".expression");
		}
	}

}
