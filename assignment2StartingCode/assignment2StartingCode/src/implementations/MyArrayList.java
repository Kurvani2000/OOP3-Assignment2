package implementations;

import java.util.Arrays;
import utilities.Iterator;
import java.util.NoSuchElementException;

import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> 
{
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    public MyArrayList() 
    {
        this.elementData = new Object[DEFAULT_CAPACITY];
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
        for (int i = 0; i < size; i++) 
        {
            elementData[i] = null;
        }
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

        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException 
    {
        if (toAdd == null) 
        {
            throw new NullPointerException("Cannot add null element to the list");
        }
        
        ensureCapacity(size + 1);
        elementData[size++] = toAdd;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException 
    {
        if (toAdd == null) 
        {
            throw new NullPointerException("Cannot add null list");
        }
        
        ensureCapacity(size + toAdd.size());
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
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        return (E) elementData[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        
        if (numMoved > 0) 
        {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException 
    {
        if (toRemove == null) 
        {
            throw new NullPointerException("Cannot remove null element");
        }
        
        for (int i = 0; i < size; i++) 
        {
            if (toRemove.equals(elementData[i])) 
            {
                return remove(i);
            }
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
        
        E oldValue = (E) elementData[index];
        elementData[index] = toChange;
        return oldValue;
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
        
        for (int i = 0; i < size; i++) 
        {
            if (toFind.equals(elementData[i])) 
            {
                return true;
            }
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
            return (E[]) Arrays.copyOf(elementData, size, toHold.getClass());
        }
        
        System.arraycopy(elementData, 0, toHold, 0, size);
        
        if (toHold.length > size) 
        {
            toHold[size] = null;
        }
        
        return toHold;
    }

    @Override
    public Object[] toArray() 
    {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public Iterator<E> iterator() 
    {
        return new ArrayIterator();
    }

    private void ensureCapacity(int minCapacity) 
    {
        if (minCapacity > elementData.length) 
        {
            int newCapacity = elementData.length * 2;
            
            if (newCapacity < minCapacity) 
            {
                newCapacity = minCapacity;
            }
            
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    private class ArrayIterator implements Iterator<E> 
    {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() 
        {
            return currentIndex < size;
        }

        @Override
        public E next() throws NoSuchElementException 
        {
            if (!hasNext()) 
            {
                throw new NoSuchElementException("No more elements in the list");
            }
            
            return (E) elementData[currentIndex++];
        }
    }
}
