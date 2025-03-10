package me.a8kj.lootbox.parent.serializiation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import com.google.gson.JsonObject;

public class LocationSerializer implements JsonSerializer<Location> {

    @Override
    public JsonObject toJson(Location location) {
        JsonObject jsonLocation = new JsonObject();
        if (location != null) {
            jsonLocation.addProperty("x", location.getX());
            jsonLocation.addProperty("y", location.getY());
            jsonLocation.addProperty("z", location.getZ());
            jsonLocation.addProperty("world", location.getWorld() != null ? location.getWorld().getName() : null);
        }
        return jsonLocation;
    }

    @Override
    public Location fromJson(JsonObject jsonLocation) {
        double x = jsonLocation.has("x") ? jsonLocation.get("x").getAsDouble() : 0;
        double y = jsonLocation.has("y") ? jsonLocation.get("y").getAsDouble() : 0;
        double z = jsonLocation.has("z") ? jsonLocation.get("z").getAsDouble() : 0;
        String worldName = jsonLocation.has("world") ? jsonLocation.get("world").getAsString() : null;

        World world = (worldName != null) ? Bukkit.getWorld(worldName) : null;

        return new Location(world, x, y, z);
    }
}
