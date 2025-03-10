package me.a8kj.lootbox.internal.manager;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import me.a8kj.config.impl.JsonConfigFile;
import me.a8kj.lootbox.parent.manager.LocationManager;
import me.a8kj.lootbox.parent.serializiation.LocationSerializer;

import org.bukkit.Location;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LocationManagerImpl implements LocationManager {

    private final Set<Location> locations = Sets.newHashSet();

    @Override
    public void addLocation(Location location) {
        locations.add(location);
    }

    @Override
    public void updateLocations(Collection<Location> locations) {
        this.locations.addAll(locations);
    }

    @Override
    public void removeLocation(Location location) {
        locations.remove(location);
    }

    @Override
    public boolean hasLocation(Location location) {
        return locations.contains(location);
    }

    @Override
    public Set<Location> getLocations() {
        return locations;
    }

    @Override
    public void saveLocations(JsonConfigFile jsonConfigFile) {
        JsonArray jsonArray = new JsonArray();
        for (Location location : locations) {
            jsonArray.add(new LocationSerializer().toJson(location));
        }
        jsonConfigFile.setValue("locations", jsonArray);
        try {
            jsonConfigFile.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Location> loadLocations(JsonConfigFile jsonConfigFile) {
        Set<Location> locations = new HashSet<>();
        JsonArray jsonArray = (JsonArray) jsonConfigFile.getValue("locations");
        if (jsonArray != null) {
            for (JsonElement element : jsonArray) {
                Location location = new LocationSerializer().fromJson(element.getAsJsonObject());
                locations.add(location);
            }
        }
        return locations;
    }
}
