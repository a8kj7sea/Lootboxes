package me.a8kj.config;

import java.io.IOException;
import java.util.Map;

import me.a8kj.config.properties.ConfigMeta;

public interface ConfigFile extends ConfigMeta {

    void load() throws IOException;

    void save() throws IOException;

    void serve(ConfigService service);

    Map<String, Object> getValues();

    void setValue(String key, Object value);

    Object getValue(String key);

    void removeValue(String key);
}
