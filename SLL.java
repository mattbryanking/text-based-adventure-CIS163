

import java.util.NoSuchElementException;

/*************************************************************************
 * Class to create and manipulate linked lists of any object type.
 *
 * @author Matthew King
 * @version 3/28/2020
 ************************************************************************/

public class SLL<E> {

    private Node<E> head;
    private int size;

    /******************************************************************
	 * Constructs an empty list, containing an empty head and a 
     * counter for the amount of elements in the list.
	 *****************************************************************/
    public SLL(){
        head = null;
        size = 0;
    }

    /******************************************************************
	 * Node class to hold data and reference to next node.
	 *****************************************************************/
    private class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data){
            this.data = data;
        }
    }

    /******************************************************************
     * Retrieves the number of elements in this list.
     * 
     * @return the number of elements in this list
     *****************************************************************/
    public int size(){
        return this.size;
    }

    /******************************************************************
     * Removes all elements from this list.
     * 
     * @throws NullPointerException if list is already empty
     *****************************************************************/
    public void clear(){
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }
        head = null;
        size = 0;
    }

    /******************************************************************
     * Removes the element at the desired index, adjusting list to
     * fill in empty spot (shifts all following objects down one).
     * Returns removed element.
     * 
     * @param index the index of the element that will be removed
     * @return the removed element 
     * @throws NullPointerException if list is empty
     * @throws IndexOutOfBoundsException if index is out of range
     *****************************************************************/
    public E remove(int index) {
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        E tmp = get(index);
        if(index == 0){
            head = head.next;
        } 
        else if (index == size() - 1) {
            Node<E> prior = getNode(index - 1);
            prior.next = null;
        }
        else {
            Node<E> prior = getNode(index - 1);
            prior.next = getNode(index + 1);
        }
        size--;
        return tmp;
    }

    /******************************************************************
     * Returns true if current list contains specified element.
     * 
     * @param element element to be searched for in list
     * @return true if element is found in list
     * @throws NullPointerException if list is empty
     * @throws NoSuchElementException if element does not exist
     *****************************************************************/
    public Boolean contains(E element) {
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }
        if (element == null) {
            throw new NoSuchElementException("Provided element does not exist!");
        }

        boolean contains = false;

        for (int i = 0; i < size; i++) {
            if (element.equals(get(i))) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /******************************************************************
     * Replaces element at desired position with provided element.
     * 
     * @param index the index of the element that will be replaced
     * @param element new element to be stored 
     * @return the replaced element
     * @throws NullPointerException if list is empty
     * @throws IndexOutOfBoundsException if index is out of range
     * @throws NoSuchElementException if provided element does not 
     * exist
     *****************************************************************/
    public E set(int index, E element) {
        if (size == 0) {
            throw new NullPointerException("List is empty, no object to replace!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (element == null) {
            throw new NoSuchElementException("Provided element does not exist!");
        }

        E tmpE = get(index);
        
        add(index + 1, element);
        remove(index);
        return tmpE;
    }

    /******************************************************************
     * Stores list of elements in correct order inside array, and 
     * returns the array.
     * 
     * @return array containing all elements in order
     * @throws NullPointerException if list is empty
     *****************************************************************/
    public Object[] toArray() {
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }

        Object list[] = new Object[size];

        for (int i = 0; i < size(); i++) {
            list[i] = get(i);
        }
        return list;
    }

    /******************************************************************
     * Adds provided element at desired location in list. 
     * 
     * @param index the index of the element that will be inserted
     * @param element new element to be stored 
     * @throws IndexOutOfBoundsException if index is out of range
     * @throws NoSuchElementException if provided element does not 
     * exist
     *****************************************************************/
    public void add(int index, E element){
        if (element == null) {
            throw new NoSuchElementException("Provided element does not exist!");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> tmp = new Node(element);
        if(index == 0){
            tmp.next = head;
            head = tmp;
        } else {
            Node<E> prior = getNode(index - 1);
            tmp.next = prior.next;
            prior.next = tmp;
        }
        size++;
    }

    /******************************************************************
     * Retrieves element at desired location in list. 
     * 
     * @param index the index of the element to be returned
     * @return element at desired location
     * @throws IndexOutOfBoundsException if index is out of range
     * @throws NullPointerException if list is empty
     *****************************************************************/
    public E get(int index){
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return getNode(index).data;
    }

    /******************************************************************
     * Retrieves node at desired location in list. 
     * 
     * @param index the index of the node to be returned
     * @return node at desired location
     * @throws IndexOutOfBoundsException if index is out of range
     * @throws NullPointerException if list is empty
     *****************************************************************/
    private Node<E> getNode(int index){
        if (size == 0) {
            throw new NullPointerException("List is empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        int count = 0;
        Node<E> current = head;
        while(count < index){
            current = current.next;
            count++;
        }
        return current;
    }

     /******************************************************************
     * Adds provided element to the end of this list. 
     * 
     * @param element new element to be stored 
     * @return true if element is successfully added
     * @throws NoSuchElementException if provided element does not 
     * exist
     *****************************************************************/
    public boolean add(E data){
        if (data == null) {
            throw new NoSuchElementException("Provided element does not exist!");
        }

        Node<E> tmp = new Node(data);
        tmp.next = null;
        if(head == null){
            head = tmp;
        } else {
            Node<E> current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = tmp;
        }

        size++;
        return true;
    }
}