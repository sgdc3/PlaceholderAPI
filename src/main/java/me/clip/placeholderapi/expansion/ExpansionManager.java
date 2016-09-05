package me.clip.placeholderapi.expansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.expansion.included.PlayerExpansion;
import me.clip.placeholderapi.expansion.included.ServerExpansion;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public final class ExpansionManager {

    private final Map<String, PlaceholderExpansion> cache = new HashMap<String, PlaceholderExpansion>();
    private PlaceholderAPIPlugin plugin;

    public ExpansionManager(PlaceholderAPIPlugin instance) {
        plugin = instance;
    }

    public void clean() {
        cache.clear();
    }

    public PlaceholderExpansion getWaitingExpansion(String plugin) {
        return cache.containsKey(plugin) ? cache.get(plugin) : null;
    }

    public boolean removeWaitingExpansion(String identifier) {
        return cache.remove(identifier) != null;
    }

    public PlaceholderExpansion getLoadedExpansion(String name) {
        PlaceholderExpansion ex = null;

        ex = getWaitingExpansion(name);

        if (ex == null) {
            for (Entry<String, PlaceholderHook> hook : PlaceholderAPI.getPlaceholders().entrySet()) {
                if (hook.getValue() instanceof PlaceholderExpansion) {
                    if (name.equalsIgnoreCase(hook.getKey())) {
                        ex = (PlaceholderExpansion) hook.getValue();
                        break;
                    }
                }
            }
        }

        return ex;
    }

    public boolean registerExpansion(PlaceholderExpansion c) {

        if (c.getIdentifier() == null) {
            return false;
        }

        if (!c.canRegister()) {
            if (c.getPlugin() != null) {
                cache.put(c.getPlugin().toLowerCase(), c);
            }
            return false;
        }

        if (!c.register()) {
            if (c.getPlugin() != null) {
                cache.put(c.getPlugin().toLowerCase(), c);
            }
            return false;
        }

        if (c instanceof Configurable) {

            Map<String, Object> defaults = ((Configurable) c).getDefaults();

            String pre = "expansions." + c.getIdentifier() + ".";

            if (defaults != null && !defaults.isEmpty()) {

                FileConfiguration cfg = plugin.getConfig();

                boolean save = false;

                for (Entry<String, Object> entries : defaults.entrySet()) {
                    if (entries.getKey() == null || entries.getKey().isEmpty()) {
                        continue;
                    }

                    if (entries.getValue() == null) {
                        if (cfg.contains(pre + entries.getKey())) {
                            save = true;
                            cfg.set(pre + entries.getKey(), null);
                        }
                    } else {
                        if (!cfg.contains(pre + entries.getKey())) {
                            save = true;
                            cfg.set(pre + entries.getKey(), entries.getValue());
                        }
                    }
                }

                if (save) {
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    plugin.getLogger().info("Configuration section has been updated for expansion: " + c.getIdentifier());
                }
            }
        }

        plugin.getLogger().info("Successfully registered " + c.getIdentifier() + " placeholder expansion!");

        if (c instanceof Taskable) {
            ((Taskable) c).start();
            plugin.getLogger().info("Started task for " + c.getIdentifier() + " placeholders!");
        }

        return true;
    }

    public void registerAllExpansions() {

        if (plugin == null) {
            return;
        }

        registerExpansion(new PlayerExpansion());
        registerExpansion(new ServerExpansion());

        List<Class<? extends PlaceholderExpansion>> subs = getAllClasses("expansions");

        if (subs == null || subs.isEmpty()) {
            return;
        }

        for (Class<? extends PlaceholderExpansion> klass : subs) {

            if (klass == null) {
                continue;
            }

            try {

                PlaceholderExpansion ex = null;

                Constructor<?>[] c = klass.getConstructors();

                if (c.length == 0) {
                    ex = klass.newInstance();
                } else {
                    for (Constructor<?> con : c) {
                        if (con.getParameterTypes().length == 0) {
                            ex = klass.newInstance();
                        }
                    }
                }

                if (ex == null) {
                    continue;
                }

                if (ex instanceof VersionSpecific) {
                    VersionSpecific nms = (VersionSpecific) ex;
                    if (nms.getVersion() != PlaceholderAPIPlugin.getNMSVersion()) {
                        continue;
                    }
                }

                if (!registerExpansion(ex)) {
                    continue;
                }

            } catch (Exception e) {
                plugin.getLogger().severe("Failed to register placeholder expansion from class: " + klass.getName());
                e.printStackTrace();
            }
        }
    }

    // find all subclasses of PlaceholderExpansion in the folder
    private List<Class<? extends PlaceholderExpansion>> getAllClasses(String folder) {

        List<Class<? extends PlaceholderExpansion>> list = new ArrayList<Class<? extends PlaceholderExpansion>>();

        try {

            File placeholders = new File(plugin.getDataFolder(), folder);

            if (!placeholders.exists()) {
                if (!placeholders.mkdir()) {
                    PlaceholderAPIPlugin.getInstance().getLogger().severe("Failed to create expansions folder!");
                }
                return list;
            }

            // create new filename filter
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    int i = name.lastIndexOf('.');
                    if (i > 0) {
                        if (name.substring(i).equals(".jar")) {
                            return true;
                        }
                    }
                    return false;
                }
            };

            File[] jars = placeholders.listFiles(fileNameFilter);

            for (File f : jars) {
                list = getSubClasses(f.toURI().toURL(), list);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"resource", "unchecked"})
    private List<Class<? extends PlaceholderExpansion>> getSubClasses(URL jar, List<Class<? extends PlaceholderExpansion>> list) {

        if (list == null) {
            list = new ArrayList<Class<? extends PlaceholderExpansion>>();
        }

        try {

            URLClassLoader cl = new URLClassLoader(new URL[]{jar}, PlaceholderExpansion.class.getClassLoader());

            JarInputStream jis = new JarInputStream(jar.openStream());

            while (true) {

                JarEntry j = jis.getNextJarEntry();

                if (j == null) {
                    break;
                }

                String name = j.getName();

                if (name.endsWith(".class")) {
                    name = name.replace("/", ".");
                    String cname = name.substring(0, name.lastIndexOf(".class"));
                    Class<?> c = cl.loadClass(cname);
                    if (PlaceholderExpansion.class.isAssignableFrom(c)) {
                        Class<? extends PlaceholderExpansion> clazz = (Class<? extends PlaceholderExpansion>) c;
                        list.add(clazz);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
