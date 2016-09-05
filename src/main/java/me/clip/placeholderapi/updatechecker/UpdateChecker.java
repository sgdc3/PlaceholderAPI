package me.clip.placeholderapi.updatechecker;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class UpdateChecker implements Listener {

    private static String latestVersion = "";
    private static boolean updateAvailable = false;
    final int resourceId = 6245;
    private PlaceholderAPIPlugin plugin;

    public UpdateChecker(PlaceholderAPIPlugin i) {
        plugin = i;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (checkForUpdate()) {
                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            plugin.getLogger().info("An update for PlaceholderAPI (v" + getLatestVersion() + ") is available at:");
                            plugin.getLogger().info("https://www.spigotmc.org/resources/placeholderapi.6245/");
                            register();
                        }
                    });
                }
            }
        });
    }

    public static boolean updateAvailable() {
        return updateAvailable;
    }

    public static String getLatestVersion() {
        return latestVersion;
    }

    private void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp()) {
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAn update for &fPlaceholder&7API &e(&fPlaceholder&7API &fv" + getLatestVersion() + "&e)"));
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bis available at &ehttps://www.spigotmc.org/resources/placeholderapi.6245/"));
        }
    }

    private String getSpigotVersion() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resourceId)
                    .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (version.length() <= 7) {
                return version;
            }
        } catch (Exception ex) {
            plugin.getLogger().info("Failed to check for a update on spigot.");
        }
        return null;
    }

    private boolean checkHigher(String currentVersion, String newVersion) {
        String current = toReadable(currentVersion);
        String newVers = toReadable(newVersion);
        return current.compareTo(newVers) < 0;
    }

    public boolean checkForUpdate() {
        String version = getSpigotVersion();
        if (version != null) {
            if (checkHigher(plugin.getDescription().getVersion(), version)) {
                latestVersion = version;
                updateAvailable = true;
                return true;
            }
        }
        return false;
    }

    private String toReadable(String version) {
        String[] split = Pattern.compile(".", Pattern.LITERAL).split(
                version.replace("v", ""));
        version = "";
        for (String s : split)
            version += String.format("%4s", s);
        return version;
    }


}
