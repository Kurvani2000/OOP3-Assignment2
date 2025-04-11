
package implementations;

import utilities.StackADT;
import java.util.EmptyStackException;
import java.util.Iterator;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> stack;

    public MyStack() {
        stack = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null to the stack.");
        }
        stack.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return stack.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return stack.toArray(holder);
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return stack.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        int index = stack.indexOf(toFind);
        return index == -1 ? -1 : stack.size() - index;
    }

    @Override
    public Iterator<E> iterator() {
        return stack.iterator();
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }
        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();
        while (thisIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean stackOverflow() {
        return false; // Not applicable for dynamic stacks
    }
}
package implementations;

public class MyStack {

}

