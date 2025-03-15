package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private int size;

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.size == 0) {
            this.head = newNode;
        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.head.element;

        if (this.size == 1) {
            this.head = null;
        } else {
            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }

        this.size--;
        return element;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                if (current.next == null) {
                    throw new NoSuchElementException("No such element");
                }

                Node<E> currentToReturn = current;
                current = current.next;
                return currentToReturn.element;
            }
        };
    }

    private void ensureNonEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("There are no elements in the stack");
        }
    }

    private static class Node<E> {
        private final E element;
        private Node<E> next;

        public Node(E element) {
            this.element = element;
            this.next = null;
        }
    }
}
