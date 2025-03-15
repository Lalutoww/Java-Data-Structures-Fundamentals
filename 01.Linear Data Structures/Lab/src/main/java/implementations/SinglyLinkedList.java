package implementations;

import interfaces.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.size == 0) {
            this.head = newNode;
        } else {
            Node<E> current = this.head;
            this.head = newNode;
            this.head.next = current;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
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
    public E removeFirst() {
        ensureNonEmpty();
        Node<E> current = this.head;
        this.head = this.head.next;
        return current.element;
    }

    @Override
    public E removeLast() {
        ensureNonEmpty();

        Node<E> secondLast = this.head;
        while (secondLast.next.next != null) {
            secondLast = secondLast.next;
        }

        Node<E> elementToReturn = secondLast.next;
        secondLast.next = null;
        return elementToReturn.element;

    }

    @Override
    public E getFirst() {
        ensureNonEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNonEmpty();

        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        return current.element;
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
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                if (current.next == null) {
                    throw new NoSuchElementException("No such element");
                }

                Node<E> elementToReturn = current;
                current = current.next;
                return elementToReturn.element;
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
