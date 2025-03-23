package com.logic.others;

import java.util.LinkedHashMap;
import java.util.Map;

public class Env<K, T> {

    private final Map<K, T> table;
    private Env<K, T> prev;

    public Env() {
        table = new LinkedHashMap<>();
    }

    Env(Env<K, T> prev) {
        this();
        this.prev = prev;
    }

    public void bind(K id, T val) {
        table.put(id, val);
    }

    public Map<K, T> map() {
        return map(new LinkedHashMap<>());
    }

    private Map<K, T> map(Map<K, T> map) {
        if (prev != null)
            map = prev.map(map);
        map.putAll(table); // Merge current table values
        return map;
    }

    public T find(K id) {
        T value = table.get(id);

        if (value != null)
            return value;
        else if (prev != null)
            return prev.find(id);
        return null;
    }

    public Env<K, T> beginScope() {
        return new Env<>(this);
    }

    public Env<K, T> endScope() {
        return prev;
    }


}
