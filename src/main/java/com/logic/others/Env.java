package com.logic.others;

import java.util.*;

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

    public List<T> list() {
        return list(new LinkedList<>());
    }

    private List<T> list(List<T> list) {
        if (prev != null)
            list = prev.list(list);
        list.addAll(table.values());
        return list;
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
