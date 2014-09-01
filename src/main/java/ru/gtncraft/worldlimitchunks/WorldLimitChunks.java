package ru.gtncraft.worldlimitchunks;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class WorldLimitChunks extends JavaPlugin {
    private final Map<String, Integer> worlds = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        for (Map.Entry<String, Object> entry : getConfig().getValues(false).entrySet()) {
            String world = entry.getKey();
            int radius = (int) entry.getValue();
            worlds.put(world, radius);
        }
        new Listeners(this);
    }

    public Map<String, Integer> getLimits() {
        return worlds;
    }
}
