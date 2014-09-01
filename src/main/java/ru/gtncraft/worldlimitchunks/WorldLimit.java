package ru.gtncraft.worldlimitchunks;

class WorldLimit {
    private final double maxX;
    private final double minX;
    private final double maxZ;
    private final double minZ;

    public WorldLimit(double x, double z, int radius) {
        this.maxX = x + radius;
        this.minX = x - radius;
        this.maxZ = z + radius;
        this.minZ = z - radius;
    }

    public boolean outside(double x, double z) {
        return x < minX || x > maxX || z < minZ || z > maxZ;
    }
}
