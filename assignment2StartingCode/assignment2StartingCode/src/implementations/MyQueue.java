
package implementations;

import utilities.QueueADT;
import exceptions.EmptyQueueException;
import java.util.Iterator;

public class MyQueue<E> implements QueueADT<E> {
    private MyDLL<E> queue;

    public MyQueue() {
        queue = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null to the queue.");
        }
        queue.add(queue.size(), toAdd);
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty.");
        }
        return queue.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty.");
        }
        return queue.get(0);
    }

    @Override
    public void dequeueAll() {
        queue.clear();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return queue.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        return queue.indexOf(toFind) + 1; // 1-based index
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean equals(QueueADT<E> that) {
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
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return queue.toArray(holder);
    }

    @Override
    public boolean isFull() {
        return false; // Not applicable for dynamic queues
    }

    @Override
    public int size() {
        return queue.size();
    }
}
package implementations;

public class MyQueue {

}
