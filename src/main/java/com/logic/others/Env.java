package com.logic.others;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Env<K, T> {

    private final Map<K, T> table;
    private Env<K, T> prev;
    private List<Env<K, T>> children;

    public Env() {
        table = new LinkedHashMap<>();
        children = new ArrayList<>();
    }

    Env(Env<K, T> prev) {
        this();
        this.prev = prev;
    }

    public Env<K, T> root() {
        if (prev == null)
            return this;
        return prev.root();
    }

    public void bind(K id, T val) {
        table.put(id, val);
    }
    public void unbind(K id) {
        table.remove(id);
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

    public T findParent(K id) {
        T value = table.get(id);

        if (value != null)
            return value;
        else if (prev != null)
            return prev.findParent(id);
        return null;
    }

    public T findChild(K id) {
        T value = table.get(id);

        if (value != null)
            return value;

        for (Env<K, T> child : children) {
            if (value == null)
                value = child.findChild(id);
        }

        return value;
    }

    public Env<K, T> beginScope() {
        Env<K, T> env = new Env<>(this);
        children.add(env);
        return env;
    }

    public Env<K, T> endScope() {
        return prev;
    }


}
