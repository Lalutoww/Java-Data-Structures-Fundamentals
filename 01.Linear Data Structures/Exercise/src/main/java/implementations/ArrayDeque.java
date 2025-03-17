package implementations;

import interfaces.Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
    private static final int DEFAULT_CAPACITY = 7;

    private Object[] elements;
    private int size;
    private int head;
    private int tail;
    private int middle;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.middle = DEFAULT_CAPACITY / 2;
        this.head = this.tail = this.middle;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.elements[this.head] = element;
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index;
        checkIndex(realIndex);

        if (realIndex - this.head < this.tail - realIndex) {
            insertAndShiftLeft(realIndex - 1, element);
        } else {
            insertAndShiftRight(realIndex, element);
        }
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        checkIndex(realIndex);
        this.elements[realIndex] = element;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.getAt(this.head);
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E elementToReturn = this.getAt(this.head);
        this.elements[this.head] = null;
        this.head++;
        this.size--;

        return elementToReturn;
    }

    @Override
    public E pop() {
        return poll();
    }

    @Override
    public E get(int index) {
        ensureNonEmpty();

        int realIndex = this.head + index;
        checkIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if (isEmpty()) {
            return null;
        }

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        if (isEmpty()) {
            return null;
        }

        int realIndex = this.head + index;
        checkIndex(realIndex);
        if (realIndex == this.head) {
            return removeFirst();
        }

        E elementToReturn = this.getAt(realIndex);
        for (int i = realIndex; i < this.tail; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[this.tail] = null;
        this.tail--;
        this.size--;
        return elementToReturn;
    }

    @Override
    public E remove(Object object) {
        if (isEmpty()) {
            return null;
        }

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                if (i == this.head) {
                    return removeFirst();
                }
                E elementToReturn = this.getAt(i);

                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }

                this.elements[this.tail] = null;
                this.tail--;
                this.size--;

                return elementToReturn;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E elementToReturn = this.getAt(this.head);

        this.elements[this.head++] = null;
        this.size--;

        return elementToReturn;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E elementToReturn = this.getAt(this.tail);

        this.elements[this.tail--] = null;
        this.size--;

        return elementToReturn;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] newElements = new Object[this.size];

        int index = 0;
        for (int i = this.head; i <= this.tail; i++) {
            newElements[index++] = this.elements[i];
        }

        this.elements = newElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = head;

            @Override
            public boolean hasNext() {
                return index <= tail;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;

        Object[] newElements = new Object[newCapacity];

        int newMiddle = newCapacity / 2;

        int begin = newMiddle - this.size / 2;
        int index = this.head;

        for (int i = begin; index <= this.tail; i++) {
            newElements[i] = this.elements[index++];
        }

        this.head = begin;
        this.tail = this.head + this.size - 1;

        return newElements;
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new NoSuchElementException("Array Deque is empty!");
        }
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    private void checkIndex(int index) {
        if (index < this.head || index > this.tail) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
    }

    private void insertAndShiftLeft(int realIndex, E element) {
        E firstElement = this.getAt(this.head);

        for (int i = this.head; i <= realIndex; i++) {
            this.elements[i] = this.elements[i + 1];
        }

        this.elements[realIndex] = element;
        this.addFirst(firstElement);
    }

    private void insertAndShiftRight(int realIndex, E element) {
        E lastElement = this.getAt(this.tail);

        for (int i = this.tail; i > realIndex; i--) {
            this.elements[i] = this.elements[i - 1];
        }

        this.elements[realIndex] = element;
        this.addLast(lastElement);
    }
}
