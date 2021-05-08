
import static org.junit.Assert.*;
import org.junit.*;

public class QueueTest{
    
    @Test
    public void size() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        assertTrue(nums.size() == 4);
    }

    @Test
    public void sizeEmpty() {
        Queue<Integer> nums = new Queue<Integer>();

        assertTrue(nums.size() == 0);
    }

    @Test
    public void enqueueSize() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);
        assertTrue(nums.size() == 4);
    }

    @Test
    public void enqueueElement() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);
        assertTrue(nums.get(3) == 4);
    }

    @Test
    public void dequeueSize() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        nums.dequeue();

        assertTrue(nums.size() == 3);
    }

    @Test
    public void dequeueReturnElement() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        assertTrue(nums.dequeue() == 1);
    }

    @Test
    public void dequeueNewElement() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        nums.dequeue();

        assertTrue(nums.get(0) == 2);
    }

    @Test (expected = NullPointerException.class)
    public void dequeueEmpty() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.dequeue();
    }

    @Test
    public void peekElement() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        assertTrue(nums.peek() == 1);
    }

    @Test
    public void peekSize() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        nums.peek();

        assertTrue(nums.size() == 4);
    }

    @Test (expected = NullPointerException.class)
    public void peekEmpty() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.peek();
    }

    @Test
    public void isEmptyTrue() {
        Queue<Integer> nums = new Queue<Integer>();

        assertTrue(nums.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        Queue<Integer> nums = new Queue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        nums.enqueue(4);

        assertFalse(nums.isEmpty());
    }
}
