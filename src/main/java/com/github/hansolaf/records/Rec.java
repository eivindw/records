package com.github.hansolaf.records;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Rec<T> implements
        Map<Key<?>, Object>,
        Function<Key<?>, Object>,
        Iterable<Map.Entry<Key<?>, Object>> {

    private final PMap<Key<?>, Object> map;

    public Rec() {
        this(HashTreePMap.empty());
    }

    public Rec(PMap<Key<?>, Object> map) {
        this.map = map;
    }

    public Rec(Map<Key<?>, Object> map) {
        this(HashTreePMap.from(map));
    }

    public Rec(Object... keysAndVals) {
        this(createPMap(keysAndVals));
    }

    private static PMap<Key<?>, Object> createPMap(Object... keysAndVals) {
        HashPMap<Key<?>, Object> pMap = HashTreePMap.empty();
        for (int i = 0; i < keysAndVals.length; i += 2) {
            Key key = (Key) keysAndVals[i];
            Object value = keysAndVals[i + 1];

            pMap = pMap.plus(key, value);
        }
        return pMap;
    }

    public static <X> Rec<X> create(Object... keysAndVals) {
        return new Rec<>(keysAndVals);
    }

    /* Record stuff */

    /**
     * Returns a new Rec of the same type with 'key' associated to 'value'
     */
    public <A> Rec<T> with(Key<A> key, A value) {
        return new Rec<>(map.plus(key, value));
    }

    /**
     * Returns a new Rec without mappings for the specified 'keys'
     */
    public Rec<T> without(Key<?>... keys) {
        return new Rec<>(map.minusAll(Arrays.asList(keys)));
    }

    /**
     * Returns a new Rec with only the mappings specified by 'keys'
     */
    public Rec<T> selectKeys(Key<?>... keys) {
        PMap<Key<?>, Object> res = HashTreePMap.empty();
        for (Key<?> key : keys) {
            if (containsKey(key))
                res = res.plus(key, get(key));
        }
        return new Rec<>(res);
    }

    /**
     * Returns the value associated with the given 'key'
     */
    @SuppressWarnings("unchecked")
    public <A> A get(Key<A> key) {
        return (A) map.get(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key
     */
    @SuppressWarnings("unchecked")
    public <A> A getOrDefault(Key<A> key, A defaultValue) {
        return (A) map.getOrDefault(key, defaultValue);
    }

    /**
     * Returns a stream of the map entries
     */
    public Stream<Entry<Key<?>, Object>> stream() {
        return entrySet().stream();
    }

    @Override
    public Iterator<Entry<Key<?>, Object>> iterator() {
        return entrySet().iterator();
    }

    @Override
    public Object apply(Key<?> key) {
        return get(key);
    }

    /* Map stuff */

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(Key<?> key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Key<?>, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Key<?>> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Key<?>, Object>> entrySet() {
        return map.entrySet();
    }

    /* Object stuff */

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return map.equals(obj);
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
