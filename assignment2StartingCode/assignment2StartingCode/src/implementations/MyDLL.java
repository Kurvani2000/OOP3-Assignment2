package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.ListADT;
import utilities.Iterator;

public class MyDLL<E> implements ListADT<E> 
{
    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    public MyDLL() 
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public int size() 
    {
        return size;
    }

    @Override
    public void clear() 
    {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException 
    {
        if (toAdd == null) 
        {
            throw new NullPointerException("Cannot add null element to the list");
        }
        if (index < 0 || index > size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (size == 0) 
        {
            head = tail = newNode;
        } 
        else if (index == 0) 
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } 
        else if (index == size) 
        {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        } 
        else 
        {
            MyDLLNode<E> current = getNode(index);
            newNode.setPrev(current.getPrev());
            newNode.setNext(current);
            current.getPrev().setNext(newNode);
            current.setPrev(newNode);
        }

        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException 
    {
        return add(size, toAdd);
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException 
    {
        if (toAdd == null) 
        {
            throw new NullPointerException("Cannot add null list");
        }

        Object[] elements = toAdd.toArray();
        for (Object element : elements) 
        {
            add((E) element);
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException 
    {
        return getNode(index).getElement();
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> toRemove = getNode(index);
        E element = toRemove.getElement();

        if (size == 1) 
        {
            head = tail = null;
        } 
        else if (toRemove == head) 
        {
            head = head.getNext();
            head.setPrev(null);
        } 
        else if (toRemove == tail) 
        {
            tail = tail.getPrev();
            tail.setNext(null);
        } 
        else 
        {
            toRemove.getPrev().setNext(toRemove.getNext());
            toRemove.getNext().setPrev(toRemove.getPrev());
        }

        size--;
        return element;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException 
    {
        if (toRemove == null) 
        {
            throw new NullPointerException("Cannot remove null element");
        }

        MyDLLNode<E> current = head;
        while (current != null) 
        {
            if (toRemove.equals(current.getElement())) 
            {
                if (current == head) 
                {
                    head = head.getNext();
                    if (head != null) 
                    {
                        head.setPrev(null);
                    }
                } 
                else if (current == tail) 
                {
                    tail = tail.getPrev();
                    tail.setNext(null);
                } 
                else 
                {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                
                size--;
                return current.getElement();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException 
    {
        if (toChange == null) 
        {
            throw new NullPointerException("Cannot set null element");
        }
        
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> node = getNode(index);
        E oldElement = node.getElement();
        node.setElement(toChange);
        return oldElement;
    }

    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException 
    {
        if (toFind == null) 
        {
            throw new NullPointerException("Cannot search for null element");
        }

        MyDLLNode<E> current = head;
        while (current != null) 
        {
            if (toFind.equals(current.getElement())) 
            {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException 
    {
        if (toHold == null) 
        {
            throw new NullPointerException("Array to hold cannot be null");
        }

        if (toHold.length < size) 
        {
            toHold = (E[]) Arrays.copyOf(toHold, size, toHold.getClass());
        }

        int i = 0;
        for (MyDLLNode<E> current = head; current != null; current = current.getNext()) 
        {
            toHold[i++] = current.getElement();
        }

        if (toHold.length > size) 
        {
            toHold[size] = null;
        }

        return toHold;
    }

    @Override
    public Object[] toArray() 
    {
        Object[] array = new Object[size];
        int i = 0;
        
        for (MyDLLNode<E> current = head; current != null; current = current.getNext()) 
        {
            array[i++] = current.getElement();
        }
        
        return array;
    }

    @Override
    public Iterator<E> iterator() 
    {
        return new DLLIterator();
    }

    private MyDLLNode<E> getNode(int index) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyDLLNode<E> current;
        if (index < size / 2) 
        {
            current = head;
            for (int i = 0; i < index; i++) 
            {
                current = current.getNext();
            }
        } 
        else 
        {
            current = tail;
            for (int i = size - 1; i > index; i--) 
            {
                current = current.getPrev();
            }
        }
        return current;
    }

    private class DLLIterator implements Iterator<E> 
    {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() 
        {
            return current != null;
        }

        @Override
        public E next() throws NoSuchElementException 
        {
            if (!hasNext()) 
            {
                throw new NoSuchElementException("No more elements in the list");
            }
            
            E element = current.getElement();
            current = current.getNext();
            return element;
        }
    }
}
