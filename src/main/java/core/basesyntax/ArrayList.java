package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double INCREASE_CAPACITY = 1.5;
    private static final int ELEMENT_NOT_FOUND = -1;
    private T[] elements;
    private int size;

    public ArrayList() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
    }

    private T[] growIfArrayFull() {
        int newCapacity = (int) (elements.length * INCREASE_CAPACITY);
        T[] newElements = (T[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        return newElements;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = growIfArrayFull();
        }
    }

    private void checkIndexForAccess(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index
                    + " is out of bounds " + size);
        }
    }

    private void checkIndexForAddition(int index) {
        if (index < 0 || index > size) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index
                    + " is out of bounds " + size);
        }
    }

    private int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element || elements[i] != null
                    && elements[i].equals(element)) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private void reduceSize() {
        elements[--size] = null;
    }

    @Override
    public void add(T value) {
        ensureCapacity();
        elements[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        ensureCapacity();
        checkIndexForAddition(index);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexForAccess(index);
        return elements[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndexForAccess(index);
        elements[index] = value;
    }

    @Override
    public T remove(int index) {
        checkIndexForAccess(index);
        T removedElement = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        reduceSize();
        return removedElement;
    }

    @Override
    public T remove(T element) {
        int index = indexOf(element);
        if (index == ELEMENT_NOT_FOUND) {
            throw new NoSuchElementException(element + "element doesn't exist: " + index);
        }
        return remove(index);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
