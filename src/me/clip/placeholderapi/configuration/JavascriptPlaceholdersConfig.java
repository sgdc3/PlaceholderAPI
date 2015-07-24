package me.clip.placeholderapi.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.placeholders.JavascriptPlaceholders;
import me.clip.placeholderapi.javascript.JavascriptPlaceholder;

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
				+ "\nAll javascript expressions must return a boolean result otherwise the placeholder will not work correctly."
				+ "\n"
				+ "\nJavascript placeholders can contain normal placeholders in the expression, true_result, or false_result"
				+ "\nThese placeholders will be parsed to the correct values before the expression is evaluated."
				+ "\n"
				+ "\nYour javascript placeholders will be identified by: %javascript_<identifier>%"
				+ "\n"
				+ "\nJavascript placeholder format:"
				+ "\n"
				+ "\n<identifier>:"
				+ "\n  expression: <expression>"
				+ "\n  true_result: <result if expression is true>"
				+ "\n  false_result: <result if expression is false>"
				+ "\n"
				+ "\nExamples:"
				+ "\n"
				+ "\nmillionaire:"
				+ "\n  expression: '%vaulteco_balance% >= 1000000'"
				+ "\n  true_result: '&aMillionaire'"
				+ "\n  false_result: '&cbroke'"
				+ "\nis_staff:"
				+ "\n  expression: '\"%vault_group%\" == \"Moderator\" || \"%vault_group%\" == \"Admin\" || \"%vault_group%\" == \"Owner\"'"
				+ "\n  true_result: '&bStaff'"
				+ "\n  false_result: '&ePlayer'");
		
		if (config.getKeys(false) == null || config.getKeys(false).isEmpty()) {
			config.set("millionaire.expression", "%vaulteco_balance% >= 1000000");
			config.set("millionaire.true_result", "&aMillionaire");
			config.set("millionaire.false_result", "&cbroke");
			config.set("is_staff.expression", "\"%vault_group%\" == \"Moderator\" || \"%vault_group%\" == \"Admin\" || \"%vault_group%\" == \"Owner\"");
			config.set("is_staff.true_result", "&bStaff");
			config.set("is_staff.false_result", "&ePlayer");
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
			
			if (!isValid(identifier)) {
				plugin.getLogger().warning("Javascript placeholder " + identifier + " is invalid!");
				continue;
			}
			
			String expression = config.getString(identifier + ".expression");
			
			String trueResult = config.getString(identifier + ".true_result");
			
			String falseResult = config.getString(identifier + ".false_result");
			
			JavascriptPlaceholder pl = new JavascriptPlaceholder(identifier, expression, trueResult, falseResult);
			
			boolean added = JavascriptPlaceholders.addJavascriptPlaceholder(pl);
			
			if (added) {
				plugin.getLogger().info("Javascript placeholder %javascript_" + identifier + "% has been loaded!");
			} else {
				plugin.getLogger().warning("Javascript placeholder %javascript_" + identifier + "% is a duplicate!");
			}
		}
		return JavascriptPlaceholders.getJavascriptPlaceholdersAmount();
	}
	
	private boolean isValid(String identifier) {
		return config.isString(identifier + ".expression") 
				&& config.isString(identifier + ".true_result") 
				&& config.isString(identifier + ".false_result");
	}

}
