package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements AbstractStack<E> {
    private Node<E> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
        newNode.next = this.top;
        this.top = newNode;
        this.size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty();
        E elementToReturn = this.top.element;

        this.top = this.top.next;
        this.size--;

        return elementToReturn;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.top.element;
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
            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (current == null) {
                    throw new NoSuchElementException("No such element.");
                }
                E element = current.element;
                this.current = this.current.next;
                return element;
            }
        };
    }

    private void ensureNonEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("There are no elements in the stack");
        }
    }

    private static class Node<E> {
        private Node<E> next;
        private final E element;

        public Node(E element) {
            this.element = element;
            this.next = null;
        }
    }
}
