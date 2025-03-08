package com.logic.symbols;

import java.util.*;

public class Env<T> {

    private final Map<String, T> table;
    private Env<T> prev;

    public Env() {
        table = new LinkedHashMap<>();
    }

    Env(Env<T> prev) {
        this();
        this.prev = prev;
    }

    public void bind(String id, T val) {
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

    public T find(String id) {
        T value = table.get(id);

        if (value != null)
            return value;
        else if (prev != null)
            return prev.find(id);
        return null;
    }

    public Env<T> beginScope() {
        return new Env<>(this);
    }

    public Env<T> endScope() {
        return prev;
    }


}
