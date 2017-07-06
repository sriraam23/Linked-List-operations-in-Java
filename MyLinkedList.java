/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    /* Adds an item after the position */
    private void addAfter( Node<AnyType> p, AnyType x )
    {
       Node<AnyType> newNode = new Node<>( x, p, p.next );
       newNode.next.prev = newNode;
       p.next = newNode;         
       theSize++;
       modCount++;
    }
    
    /*
    Swap function that swaps the nodes takes 2 index positions as parameters
    */
    public void swap(int i1, int i2)
    {
       Node<AnyType> p1, p2, t1, t2;
       p1 = getNode(i1);
       p2 = getNode(i2);
       /*Assuming i1 < i2 */
       if (p1.next == p2)
        {
          p1.prev.next = p2;
          p2.next.prev = p1;
          p1.next = p2.next;
          p2.prev = p1.prev;
          p1.prev = p2;
          p2.next = p1;
        }
       else
        { 
         p1.prev.next = p2;
         p1.next.prev = p2;
         p2.prev.next = p1;
         p2.next.prev = p1;
         t1 = p1.prev;
         t2 = p1.next;
         p1.prev = p2.prev;
         p1.next = p2.next;
         p2.prev = t1;
         p2.next = t2;
        }
    }
    
    public void shift(int k)
    {
      if(k >= theSize)
        k = k % theSize;
      else if (-k >= theSize)
        k = -1 * (-k % theSize);
      Node<AnyType> p1, p2;
      p1 = getNode(0);
      p2 = getNode(theSize-1);
      if (k > 0)
       {
         for (int i = 1; i<=k; i++)
         {
          p2.next = p1;
          p1.prev = p2;
          p1 = p1.next;
          p2 = p2.next;
          p1.prev = beginMarker;
          p2.next = endMarker;
         }  
         beginMarker.next = p1;
         endMarker.prev = p2;
       }
      else
       {
         for (int i = 1; i<= -k; i++)
         {
           p2.next = p1;
           p1.prev = p2;
           p1 = p1.prev;
           p2 = p2.prev;
           p1.prev = beginMarker;
           p2.next = endMarker;
         }
         beginMarker.next = p1;
         endMarker.prev = p2;
       }
    }
    
    public void erase(int idx, int no)
    {
      if (idx >= theSize || idx+no > theSize)
        throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
      Node<AnyType> p1,p2,left;
      p1 = getNode(idx);
      p2 = p1;
      left = p1.prev;
      for (int i=1;i<=no;i++)
      {
        p2.prev = null;
        p2 = p2.next;
        p2.prev.next = null;
        theSize--;
      }
      left.next = p2;
      p2.prev = left;
    }
    
    public void insertList(MyLinkedList<AnyType> lst2, int idx)
    {
      Node<AnyType> p1, p2, t1, t2;
      p1 = getNode(idx);
      p2 = p1.next;
      t1 = lst2.gettheNode(0);
      t2 = lst2.gettheNode(lst2.size()-1);
      p1.next = t1;
      t1.prev = p1;
      t2.next = p2;
      p2.prev = t2;
      theSize = theSize + lst2.size();
      //System.out.println(t1.data);
      //System.out.println(t2.data);
    }
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    public Node<AnyType> gettheNode(int idx)
    {
       return getNode(idx, 0, size( ) - 1);
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        MyLinkedList<Integer> lst2 = new MyLinkedList<>( );
        for(int i = 0; i < 10; i++)
                lst.add( i );
        for(int i = 20; i < 30; i++)
                lst2.add( i );

       lst.remove(0);
       lst.remove(lst.size( )-1);
       
       lst2.remove(0);
       lst2.remove(lst2.size( )-1);
       
       System.out.println("List1:");
       System.out.println(lst);
       System.out.println(" ");
       
       System.out.println("List2:");
       System.out.println(lst2);
       System.out.println(" ");
       
       System.out.println("Swapping the first and last element of List 1");
       lst.swap(0,7);  /*The first index should be smaller than the second index*/
       System.out.println(lst);
       System.out.println(" ");
       
       System.out.println("Shifting List 1 by 10");
       lst.shift(10);
       System.out.println(lst);
       System.out.println(" ");
       
       System.out.println("Erasing 3 numbers from List 1 starting at index 2");
       lst.erase(2,3);
       System.out.println(lst);
       System.out.println(" ");
       
       System.out.println("Inserting List 2 after index 1 into List 1");
       lst.insertList(lst2,1);
       System.out.println(lst);
       System.out.println(" ");       

       java.util.Iterator<Integer> itr = lst.iterator( );
        /*
        while( itr.hasNext( ) )
        {
                itr.next( );
                itr.remove( );
                System.out.println( lst );
        }*/
    }
}
