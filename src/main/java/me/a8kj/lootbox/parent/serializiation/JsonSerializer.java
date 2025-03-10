package me.a8kj.lootbox.parent.serializiation;

import com.google.gson.JsonObject;

public interface JsonSerializer<U> {

    JsonObject toJson(U u);

    U fromJson(JsonObject jsonObject);
}
