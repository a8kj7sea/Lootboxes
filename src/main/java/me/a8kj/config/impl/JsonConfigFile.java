package me.a8kj.config.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigFile;
import me.a8kj.config.ConfigService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class JsonConfigFile implements ConfigFile {
    private final String name;
    private final String path;
    private final Map<String, Object> values = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void load() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            return;
        }

        values.clear();
        @SuppressWarnings("unchecked")
        Map<String, Object> loadedValues = objectMapper.readValue(file, Map.class);
        if (loadedValues != null) {
            values.putAll(loadedValues);
        }
    }

    @Override
    public void save() throws IOException {
        File file = new File(path);
        objectMapper.writeValue(file, values);
    }

    @Override
    public void serve(ConfigService service) {
        service.apply(this);
    }

    @Override
    public void setValue(String key, Object value) {
        values.put(key.toLowerCase(), value);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue(String key) {
        return values.get(key.toLowerCase());
    }

    @Override
    public void removeValue(String key) {
        values.remove(key.toLowerCase());
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
