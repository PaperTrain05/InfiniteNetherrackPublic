package io.paper.infinitenetherrack.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.paper.infinitenetherrack.Heart;
import io.paper.infinitenetherrack.templates.AbstractConfiguration;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

public class ConfigUtil {
    public static String ObjectToJson(AbstractConfiguration object) {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create().toJson(new JsonParser().parse(((JsonObject)new Gson().fromJson(new Gson().toJson(object), JsonObject.class)).toString()));
    }

    public static JsonObject ObjectToJsonObj(Object object) {
        return (JsonObject)new Gson().fromJson(new Gson().toJson(object), JsonObject.class);
    }

    public static JsonArray ObjectToJsonArr(Object object) {
        return (JsonArray)new Gson().fromJson(new Gson().toJson(object), JsonArray.class);
    }

    public static Object JsonToObject(JsonElement json, Class<?> clazz) {
        return new Gson().fromJson(new Gson().toJson(json), clazz);
    }

    public static void save(AbstractConfiguration object, String path) {
        try {
            Files.write(Paths.get(path, new String[0]), ConfigUtil.ObjectToJson(object).getBytes(), new OpenOption[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject getJsonObject(String path) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path, new String[0]));
            return (JsonObject)new Gson().fromJson(reader, JsonObject.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static JsonElement getObjectFromInternalPath(String path, JsonObject json) {
        String[] paths = path.split("\\.");
        if (paths.length == 1) {
            return json.get(paths[0]);
        }
        JsonObject finalProduct = new JsonObject();
        for (int i = 0; i < paths.length - 1; ++i) {
            finalProduct = i == 0 ? json.get(paths[i]).getAsJsonObject() : finalProduct.get(paths[i]).getAsJsonObject();
        }
        return finalProduct.get(paths[paths.length - 1]);
    }

    public static AbstractConfiguration load(AbstractConfiguration object, Heart heart) {
        String configString;
        File file;
        String path = object.getPath();
        JsonObject savedObject = ConfigUtil.getJsonObject(path);
        if (!heart.getDataFolder().exists()) {
            heart.getDataFolder().mkdir();
        }
        if (!(file = new File(path)).exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            if ("".equals(configString = IOUtils.toString(Files.newBufferedReader(Paths.get(path, new String[0]))))) {
                ConfigUtil.save(object, path);
                return ConfigUtil.load(object, heart);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AbstractConfiguration config = (AbstractConfiguration)new Gson().fromJson(savedObject, object.getClass());
        if (config == null) {
            try {
                throw new IOException("The config file: [" + path + "] is not in valid json format.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ConfigUtil.save(config, path);
        config.setHeart(heart);
        config.setPath(path);
        System.out.println("Loaded config: " + object.getClass().getSimpleName() + " from storage.");
        return config;
    }
}
