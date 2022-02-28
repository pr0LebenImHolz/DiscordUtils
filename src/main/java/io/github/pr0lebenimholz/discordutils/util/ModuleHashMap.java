package io.github.pr0lebenimholz.discordutils.util;

import java.util.HashMap;

public class ModuleHashMap extends HashMap<String, Module> {

    /**
     * Returns the value to which the specified key is mapped, or the value of the super class if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or the super class if this map contains no mapping for the key
     */
    @Override
    public Module get(Object key) {
        return super.getOrDefault(key, new Module(null));
    }
}
