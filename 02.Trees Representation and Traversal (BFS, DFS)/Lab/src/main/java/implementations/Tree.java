package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> child : subtrees) {
            child.parent = this;
            this.children.add(child);
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();

        if (this.value == null) {
            return result;
        }

        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            result.add(current.value);

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        if (this.value == null) {
            return result;
        }

        ArrayDeque<Tree<E>> stack1 = new ArrayDeque<>();
        ArrayDeque<Tree<E>> stack2 = new ArrayDeque<>();

        stack1.push(this);

        while (!stack1.isEmpty()) {
            Tree<E> current = stack1.pop();
            stack2.push(current);

            for (Tree<E> child : current.children) {
                stack1.push(child);
            }
        }

        while (!stack2.isEmpty()) {
            result.add(stack2.pop().value);
        }

        return result;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parent = find(parentKey);

        if (parent == null) {
            throw new IllegalArgumentException();
        }

        parent.children.add(child);
        child.parent = parent;

    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> treeToRemove = find(nodeKey);

        if (treeToRemove == null) {
            throw new IllegalArgumentException();
        }

        treeToRemove.children.clear();

        Tree<E> parentTree = treeToRemove.parent;

        if (parentTree != null) {
            parentTree.children.remove(treeToRemove);
        }
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = find(firstKey);
        Tree<E> secondNode = find(secondKey);

        if (firstNode == null || secondNode == null) {
            throw new IllegalArgumentException();
        }

        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;

        if (firstParent == null) {
            swapRoot(secondNode);
            secondNode.parent = null;
            return;
        } else if (secondParent == null) {
            swapRoot(firstNode);
            firstNode.parent = null;
            return;
        }

        firstNode.parent = secondParent;
        secondNode.parent = firstParent;

        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex, secondNode);
        secondParent.children.set(secondIndex, firstNode);
    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.children = node.children;
        this.parent = null;
        node.parent = null;
    }

    private Tree<E> find(E parentKey) {
        ArrayDeque<Tree<E>> queue = new ArrayDeque<>();

        queue.offer(this);
        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();

            if (current.value == parentKey) {
                return current;
            }

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return null;
    }
}



