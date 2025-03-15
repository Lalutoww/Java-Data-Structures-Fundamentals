package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 4;
    private int size;
    private int capacity;
    private Object[] arr;

    public ArrayList() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        arr = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E element) {
        if (this.size == this.capacity) {
            this.arr = grow();
        }

        this.arr[size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        validateIndex(index);
        if (this.size == this.capacity) {
            this.arr = grow();
        }

        if (index != this.size) {
            shiftRight(index);
        }

        this.arr[index] = element;
        this.size++;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        validateIndex(index);
        return (E) this.arr[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        validateIndex(index);
        E elementToReturn = (E) this.arr[index];

        this.arr[index] = element;

        return elementToReturn;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        validateIndex(index);


        E elementToReturn = (E) this.arr[index];

        if (this.size < this.capacity / 3) {
            this.arr = shrink();
        }

        if (index != this.size - 1) {
            shiftLeft(index);
        }

        this.size--;
        return elementToReturn;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.arr[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (arr[index] == (null) && hasNext()) {
                    throw new NoSuchElementException("There is no such element in the collection");
                }
                return (E) arr[index++];
            }
        };
    }

    private Object[] grow() {
        this.capacity *= 2;
        return Arrays.copyOf(this.arr, this.capacity);
    }

    private Object[] shrink() {
        this.capacity /= 2;
        return Arrays.copyOf(this.arr, this.capacity);
    }

    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.arr[i + 1] = this.arr[i];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size; i++) {
            this.arr[i] = this.arr[i + 1];
        }
    }

    private boolean checkIndex(int index) {
        return index < 0 || index >= this.size;
    }

    private void validateIndex(int index) {
        if (checkIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds." + System.lineSeparator() + "Current size is: " + this.size);
        }
    }
}
