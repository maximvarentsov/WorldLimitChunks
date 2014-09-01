package ru.gtncraft.worldlimitchunks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;

import java.util.WeakHashMap;

class Listeners implements Listener {
    private final WorldLimitChunks plugin;
    private final WeakHashMap<World, WorldLimit> worldLimits = new WeakHashMap<>();

    public Listeners(final WorldLimitChunks plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getWorlds().forEach(this::init);
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    void init(final World world) {
        Integer radius = plugin.getLimits().get(world.getName());
        if (radius != null) {
            Chunk spawn = world.getSpawnLocation().getChunk();
            worldLimits.put(world, new WorldLimit(spawn.getX(), spawn.getZ(), radius));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("unused")
    void onWorldInit(final WorldInitEvent event) {
        init(event.getWorld());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("unused")
    void onChunkLoad(final ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        WorldLimit worldLimit = worldLimits.get(event.getWorld());
        if (worldLimit != null && worldLimit.outside(chunk.getX(), chunk.getZ())) {
            chunk.unload(false, false);
        }
    }
}
