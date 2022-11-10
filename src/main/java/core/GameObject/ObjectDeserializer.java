package core.GameObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import core.GameObject.components.Component;
import java.lang.reflect.Type;

public class ObjectDeserializer implements JsonDeserializer<GameObject> {
    @Override
    public GameObject deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray components = jsonObject.getAsJsonArray("components");
        Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);
        boolean alive = context.deserialize(jsonObject.get("alive"), boolean.class);
        ObjectType objType = context.deserialize(jsonObject.get("type"), ObjectType.class);
        GameObject gameObject = new GameObject(objType);
        gameObject.setAlive(alive);
        gameObject.setTransform(transform);
        for (JsonElement e : components) {
            Component c = context.deserialize(e, Component.class);
            gameObject.addComponent(c);
        }
        return gameObject;
    }
}
