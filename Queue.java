

public class Queue<E> extends SLL<E>{
    
    public Queue() {
        super();
    }

    public void enqueue(E element) {
        add(element);
    }

    public E dequeue() {
        return remove(0);
    }

    public E peek() {
        return get(0);
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
