package me.a8kj.config.impl;

import java.io.*;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigFile;
import me.a8kj.config.ConfigService;

@RequiredArgsConstructor
@Getter
public class TextConfigFile implements ConfigFile {
    private final String name;
    private final String path;
    private final Map<String, Object> values = new HashMap<>();

    @Override
    public void load() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            return;
        }

        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
            for (String key : properties.stringPropertyNames()) {
                values.put(key.toLowerCase(), properties.getProperty(key));
            }
        }
    }

    @Override
    public void save() throws IOException {
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            properties.setProperty(entry.getKey().toLowerCase(), entry.getValue().toString());
        }

        try (FileWriter writer = new FileWriter(path)) {
            properties.store(writer, "Compiler site settings \n" + new Date());
        }
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
