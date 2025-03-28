package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
    private List<E> elements;

    public MaxHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);

    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && isParentLess(index, parentIndex)) {
            Collections.swap(this.elements, index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    private boolean isParentLess(int childIndex, int parentIndex) {
        //! when using max heap, every parent's value should be bigger or equal to its child's value
        //! that is why we are comparing values, not indices.
        return this.getAt(childIndex).compareTo(this.getAt(parentIndex)) > 0;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2; // formula to find the parent index
    }

    @Override
    public E peek() {
        if (this.size() == 0) {
            throw new IllegalStateException("Heap is empty, there is not elements to peek!");
        }
        return this.getAt(0);
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }
}
