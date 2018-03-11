import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private structItem head;
    private int size;
    private class structItem{
        public Item item;
        public structItem next;
        public structItem past;
        public structItem(Item item, structItem next, structItem past)
        {
            if(item == null)
                throw new IllegalArgumentException();
            this.item = item;
            this.next = next;
            this.past = past;
            past.next = this;
            next.past = this;
            size++;
        }
        public structItem()
        {
            next = this;
            past = this;
            item = null;
        }
        public Item remove()
        {
            if(next == this)
                throw new NoSuchElementException();
            Item item = this.item;
            this.next.past = this.past;
            this.past.next = this.next;
            this.item = null;
            this.next = null;
            this.past = null;
            size--;
            return item;
        }
    }
    public Deque(){
        head = new structItem();
        size = 0;
    }
   public boolean isEmpty()                 // is the deque empty?
   {
       return size==0;
   }
   public int size()                        // return the number of items on the deque
   {
       return size;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       new structItem(item, head.next, head);
   }
   public void addLast(Item item)           // add the item to the end
   {
       new structItem(item, head, head.past);
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
       return head.next.remove();
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       return head.past.remove();
   }
   
   @Override
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
        Iterator<Item> it = new Iterator<Item>() {
            private structItem current = head;

            @Override
            public boolean hasNext() {
                return current.next!=head;
            }

            @Override
            public Item next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                current = current.next;
                return current.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
   }
}
