package utilities;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.lang.reflect.Array;

public class KArray<T> {
    @SuppressWarnings("unchecked")
    T[] elements = (T[]) new Object[10];
    public int size = 0;
    Class<T> genericClass;

    public KArray(Class<T> genericClass) {
        this.genericClass = genericClass;
    }
    public KArray(T[] elements, Class<T> genericClass) {
        this.genericClass = genericClass;
        for (T element : elements) {
            add(element);
        }
    }
    @SuppressWarnings("unchecked")
    T[] newArray(int size) {
        return (T[]) Array.newInstance(genericClass, size);
    }
    void extend() {
        T[] extended = newArray(elements.length * 2);
        System.arraycopy(elements, 0, extended, 0, size);
        elements = extended;
    }
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element;
    }
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }
    public int findIndex(T element) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(element)) {
                return index;
            }
        }
        return -1;
    }
    public T findElement(T element) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(element)) {
                return elements[index];
            }
        }
        return null;
    }
    public boolean contains(T element) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(element)) {
                return true;
            }
        }
        return false;
    }
    public T customFind(Function<T, Boolean> find) {
        for (int index = 0; index < size; index++) {
            if (find.apply(elements[index])) {
                return elements[index];
            }
        }
        return null;
    }
    public void add(T element) {
        if (size == elements.length) extend();
        elements[size++] = element;
    }
    public void removeIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        elements[index] = null;
        for (; index < size; index++) {
            if (elements[index] == null) {
                if ((index + 1) < size) {
                    elements[index] = elements[index + 1];
                    elements[index + 1] = null;
                }
            }
        }
        size--;
    }
    public void removeElement(T element) {
        int index = findIndex(element);
        if (index == -1) throw new NoSuchElementException("");
        elements[index] = null;
        for (; index < size; index++) {
            if (elements[index] == null) {
                if ((index + 1) < size) {
                    elements[index] = elements[index + 1];
                    elements[index + 1] = null;
                }
            }
        }
        size--;
    }
    public void clear() {
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public T[] toArray() {
        T[] array = newArray(size);
        System.arraycopy(elements, 0, array, 0, size);
        return array;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < size; index++) {
            if (!builder.isEmpty()) builder.append(", ");
            builder.append(elements[index]);
        }
        return builder.insert(0, "[").append("]").toString();
    }
}
