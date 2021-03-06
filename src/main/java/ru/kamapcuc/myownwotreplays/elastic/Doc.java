package ru.kamapcuc.myownwotreplays.elastic;

import org.json.JSONObject;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.kamapcuc.myownwotreplays.base.Consts;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Hack for override JSONObject.map::get to receive localized serialization
 */
public class Doc extends JSONObject {

    public Doc(String id, String parent, Map<String, Object> source) {
        super();
        try {
            Field f = JSONObject.class.getDeclaredField("map");
            f.setAccessible(true);
            f.set(this, new Source(source));
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
        }
        put(Consts.ID_FIELD, id);
        put(Consts.PARENT_FIELD, parent);
    }

    public String getId() {
        return (String) get(Consts.ID_FIELD);
    }

    private class Source extends HashMap<String, Object> {

        public Source(Map<? extends String, ?> m) {
            super(m);
        }

        @Override
        public Object get(Object key) {
            Object value = super.get(key);
            if (key instanceof String && ((String) key).endsWith("_i18n"))
                return ((Map) value).get(LocaleContextHolder.getLocale().getLanguage());
            else
                return value;
        }

    }

}