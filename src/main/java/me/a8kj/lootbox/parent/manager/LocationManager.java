package me.a8kj.lootbox.parent.manager;

import java.util.Collection;
import java.util.Set;
import org.bukkit.Location;

import me.a8kj.config.impl.JsonConfigFile;

public interface LocationManager {

    void addLocation(Location location);

    void removeLocation(Location location);

    void updateLocations(Collection<Location> locations);

    boolean hasLocation(Location location);

    Set<Location> getLocations();

    void saveLocations(JsonConfigFile jsonConfigFile);

    Set<Location> loadLocations(JsonConfigFile jsonConfigFile);
}
