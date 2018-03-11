import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int capacity;
    private int size;
    private Item[] array;
    
    public RandomizedQueue() // construct an empty randomized queue
    {
        capacity = 10;
        array = (Item[])new Object[capacity];
        size = 0;
    }
    public boolean isEmpty() // is the queue empty?
    {
        return size == 0;
    }
    public int size() // return the number of items on the queue
    {
        return size;
    }
    public void enqueue(Item item) // add the item
    {
        if(item == null)
            throw new IllegalArgumentException();
        size++;
        if(capacity == size)
            expand();
        array[size-1] = item;
    }
    public Item dequeue() // remove and return a random item
    {
        if(isEmpty())
            throw new NoSuchElementException();
        int pos = StdRandom.uniform(size);
        Item temp, last;
        size--;
        last = array[size];
        temp = array[pos];
        array[pos]=last;
        array[size] = null;
        if(capacity>10 && size<=capacity/4)
            reduce();
        return temp;
    }
    public Item sample() // return (but do not remove) a random item
    {
        if(isEmpty())
            throw new NoSuchElementException();
        return array[StdRandom.uniform(size)];
    }
    private void expand(){
        capacity*=2;
        Item[] array = this.array;
        this.array = (Item[])new Object[capacity];
        for(int i =0; i< size; i++){
            this.array[i] = array[i];
        }
    }
    private void reduce(){
        capacity/=2;
        Item[] array = this.array;
        this.array = (Item[])new Object[capacity]; 
        for(int i =0; i< size; i++){
            this.array[i] = array[i];
        }
    }
    public Iterator<Item> iterator() // return an independent iterator over items in random order
    {
        return new itemIterator();
    }
    
    private class itemIterator implements Iterator<Item>
    {
            int current;
            int v[];
            
            public itemIterator()
            {
                if(size>0)
                    v = StdRandom.permutation(size);
                current =0;
            }
            
            @Override
            public boolean hasNext() {
                return current!=size;
            }

            @Override
            public Item next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                return array[v[current++]];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
    }
}