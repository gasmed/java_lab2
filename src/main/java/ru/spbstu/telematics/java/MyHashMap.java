package ru.spbstu.telematics.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements Iterable<MyHashMap.Entry<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private int size;
    private Node<K, V>[] table;

    public MyHashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    private int hash(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode % table.length);
    }

    public int getSize() {
        return size;
    }

    private void resizeTable() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (int i = 0; i < oldCapacity; i++) {
            Node<K, V> node = oldTable[i];
            while (node != null) {
                Node<K, V> next = node.next;
                int index = hash(node.key);
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }

        table = newTable;
    }

    private void addEntry(K key, V value) {
        int index = hash(key);
        Node<K, V> current = table[index];

        if (current == null) {
            table[index] = new Node<>(key, value);
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(key, value);
        }

        size++;

        if (size >= table.length * LOAD_FACTOR) {
            resizeTable();
        }
    }

    private V getEntry(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    private void removeEntry(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    public V put(K key, V value) {
        V oldValue = getEntry(key);
        if (oldValue != null) {
            return oldValue;
        }
        addEntry(key, value);
        return null;
    }

    public V get(K key) {
        return getEntry(key);
    }

    public V remove(K key) {
        V oldValue = getEntry(key);
        removeEntry(key);
        return oldValue;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        int index = hash((K) key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (Node<K, V> node : table) {
            while (node != null) {
                if (node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public MyHashMap<K, V> clone() {
        MyHashMap<K, V> clonedMap = new MyHashMap<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                clonedMap.addEntry(node.key, node.value);
                node = node.next;
            }
        }
        return clonedMap;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<Entry<K, V>> {
        private int currentIndex = 0;
        private Node<K, V> currentNode = null;

        public boolean hasNext() {
            if (currentNode != null && currentNode.next != null) {
                return true;
            }
            for (int i = currentIndex + 1; i < table.length; i++) {
                if (table[i] != null) {
                    return true;
                }
            }
            return false;
        }

        public Entry<K, V> next() {
            if (currentNode != null && currentNode.next != null) {
                currentNode = currentNode.next;
                return new Entry<>(currentNode.key, currentNode.value);
            }
            while (currentIndex < table.length) {
                if (table[currentIndex] != null) {
                    currentNode = table[currentIndex++];
                    return new Entry<>(currentNode.key, currentNode.value);
                }
                currentIndex++;
            }
            throw new NoSuchElementException("No more elements in the map");
        }
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
