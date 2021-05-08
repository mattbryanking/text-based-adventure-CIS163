

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.*;

public class SLLTest { 

    @Test
    public void size() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(3);
        nums.add(1);
        nums.add(4);
        nums.add(42);

        assertTrue (nums.size() == 4);
    }  

    @Test
    public void sizeEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        assertTrue (nums.size() == 0);
    }    

    @Test (expected = NullPointerException.class)
    public void clearEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.clear();
    }  

    @Test
    public void clearSmall() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.clear();
        assertTrue (nums.size() == 0);
    }  

    @Test
    public void clearLarge() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.add(1);
        nums.clear();
        assertTrue (nums.size() == 0);
    }  

    @Test
    public void removeHeadSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(0);
        assertTrue (nums.size() == 2);
    }  

    @Test
    public void removeHeadElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(0);
        assertTrue (nums.get(0) == 2);
    }  

    @Test
    public void removeHeadCheckLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.remove(0);
        assertTrue (nums.get(2) == 4);
    }  

    @Test
    public void removeMidSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(1);
        assertTrue (nums.size() == 2);
    }  

    @Test
    public void removeMidElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(1);
        assertTrue (nums.get(1) == 3);
    }  

    @Test
    public void removeMidCheckLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.remove(1);
        assertTrue (nums.get(2) == 4);
    }  

    @Test
    public void removeLastSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(2);
        assertTrue (nums.size() == 2);
    }  

    @Test
    public void removeLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.remove(2);
        assertTrue (nums.get(1) == 2);
    }  

    @Test
    public void removeReturnElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        Integer tmp = nums.get(1);
        assertTrue (nums.remove(1) == tmp);
    }  

    @Test (expected = NullPointerException.class)
    public void removeEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.remove(2);
    }  

    @Test (expected = IndexOutOfBoundsException.class)
    public void removeUpperBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.remove(2);
    }  

    @Test (expected = IndexOutOfBoundsException.class)
    public void removeLowerBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.remove(-10);
    }  

    @Test
    public void containsTruePrimative() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        assertTrue (nums.contains(1));
        assertTrue (nums.contains(2));
        assertTrue (nums.contains(3));
    }  

    @Test
    public void containsFalsePrimative() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        assertFalse (nums.contains(7));
    }  

    @Test
    public void containsTrueString() {
        SLL<String> words = new SLL<String>();
        words.add("Is");
        words.add("this");
        words.add("working?");
        assertTrue (words.contains("Is"));
        assertTrue (words.contains("this"));
        assertTrue (words.contains("working?"));
    }  

    @Test
    public void containsFalseString() {
        SLL<String> words = new SLL<String>();
        words.add("Is");
        words.add("this");
        words.add("working?");
        assertFalse (words.contains("I hope so!"));
    }  

    @Test (expected = NullPointerException.class)
    public void containsEmpty() {
        SLL<String> words = new SLL<String>();
        words.contains("hmmmmmm");
    }  

    @Test (expected = NoSuchElementException.class)
    public void containsNull() {
        SLL<String> words = new SLL<String>();
        words.add("Is");
        words.add("this");
        words.add("working?");
        words.contains(null);
    }  

    @Test
    public void setFirstSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(0, 10);
        assertTrue (nums.size() == 3);
    }  

    @Test
    public void setFirstElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(0, 10);
        assertTrue (nums.get(0) == 10);
    }  

    @Test
    public void setFirstCheckLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.set(0, 10);
        assertTrue (nums.get(3) == 4);
    }  

    @Test
    public void setMidSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(1, 10);
        assertTrue (nums.size() == 3);
    }  

    @Test
    public void setMidElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(1, 10);
        assertTrue (nums.get(1) == 10);
    }  

    @Test
    public void setMidCheckLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.set(1, 10);
        assertTrue (nums.get(3) == 4);
    }  

    @Test
    public void setLastSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(2, 10);
        assertTrue (nums.size() == 3);
    }  

    @Test
    public void setLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(2, 10);
        assertTrue (nums.get(2) == 10);
    }  

    @Test
    public void setReturnElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        Integer tmp = nums.get(1);
        assertTrue (nums.set(1, 10) == tmp);
    }  

    @Test (expected = NullPointerException.class)
    public void setEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.set(2, 10);
    }  
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void setUpperBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(10, 10);
    }  

    @Test (expected = IndexOutOfBoundsException.class)
    public void setLowerBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(-10, 10);
    }  

    @Test (expected = NoSuchElementException.class)
    public void setNull() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.set(1, null);
    }  

    @Test 
    public void toArrayInt() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        Object[] numsArray = nums.toArray();
        assertTrue (numsArray[0] == (Integer) 1);
        assertTrue (numsArray[1] == (Integer) 2);
        assertTrue (numsArray[2] == (Integer) 3);
    }  

    @Test 
    public void toArrayString() {
        SLL<String> words = new SLL<String>();
        words.add("Is");
        words.add("this");
        words.add("working?");
        Object[] wordsArray = words.toArray();
        assertTrue (wordsArray[0] == "Is");
        assertTrue (wordsArray[1] == "this");
        assertTrue (wordsArray[2] == "working?");
    }  

    @Test (expected = NullPointerException.class)
    public void toArrayEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.toArray();
    }  

    @Test (expected = NoSuchElementException.class)
    public void addNull() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(null);
    }  

    @Test
    public void addTrue() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        assertTrue (nums.add(4)); 
    } 
    
    @Test
    public void addIndexSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0,1);
        nums.add(1,2);
        nums.add(2,3);
        assertTrue (nums.size() == 3);
    }  

    @Test
    public void addIndexMidSize() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0,1);
        nums.add(1,2);
        nums.add(2,3);
        nums.add(1,10);
        assertTrue (nums.size() == 4);
    }  

    @Test
    public void addIndexMidElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0,1);
        nums.add(1,2);
        nums.add(2,3);
        nums.add(1,10);
        assertTrue (nums.get(1) == 10);
    } 

    @Test
    public void addIndexMidCheckLastElement() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0,1);
        nums.add(1,2);
        nums.add(2,3);
        nums.add(1,10);
        assertTrue (nums.get(3) == 3);
    } 

    @Test (expected = NoSuchElementException.class)
    public void addIndexNull() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1, null);
    } 

    @Test (expected = IndexOutOfBoundsException.class)
    public void addIndexUpperBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0, 10);
        nums.add(4, 10);
    } 

    @Test (expected = IndexOutOfBoundsException.class)
    public void addIndexLowerBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(0, 10);
        nums.add(-10, 10);
    } 

    @Test (expected = NullPointerException.class)
    public void getEmpty() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.get(1);
    }  

    @Test (expected = IndexOutOfBoundsException.class)
    public void getUpperBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.get(100);
    }  

    @Test (expected = IndexOutOfBoundsException.class)
    public void getLowerBound() {
        SLL<Integer> nums = new SLL<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.get(-100);
    }  
}