package customAarrayList;

import java.util.*;
/**
 * Resizable-array implementation of the Custom interface which extends Iterable interface.
 * @author akrafit
 * @version 1.0
 * Type parameters:
 * @param <E> – the type of elements in this list
 * */
public class CustomArrayList<E> implements Custom<E> {

    /**
     *The array buffer into which the elements of the ArrayList are stored.
     * */
    private E[] elements;

    /**
     * Constructor for create empty exemplar of CustomArrayList
     * */
    public CustomArrayList() {
        elements = (E[]) new Object[0];
    }

    /**
     * Method for add elements on elements array
     * @param e - any type element
     * @return true if adding is successfully and false if we have exception
     * */
    public boolean add(E e) {
        try {
            E[] lastArray = elements;
            elements = (E[]) new Object[lastArray.length + 1];
            System.arraycopy(lastArray, 0, elements, 0, lastArray.length);
            elements[elements.length - 1] = e;
            return true;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Method for add elements on elements array with index of put element
     * @param e - any type element
     * @return true if adding is successfully and false if we have exception or index is greater than the number of array cells
     * */
    public boolean add(int index, E e) {

        try {
            if(checkSize(index)) {
                E[] lastArray = elements;
                elements = (E[]) new Object[lastArray.length + 1];
                System.arraycopy(lastArray, 0, elements, 0, index);
                elements[index] = e;
                System.arraycopy(lastArray, index, elements, index + 1, lastArray.length - index);
                return true;
            }
        } catch (ClassCastException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Method for delete element on position
     * @param index - position of element
     * @throws IndexOutOfBoundsException if index out of bounds on elements
     * */
    public void delete(int index) {
        try {
            if(checkSize(index)) {
                E[] lastArray = elements;
                elements = (E[]) new Object[lastArray.length - 1];
                System.arraycopy(lastArray, 0, elements, 0, index);
                System.arraycopy(lastArray, index + 1, elements, index, lastArray.length - index - 1);
            }
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Private method for check size of elements array
     * */
    private boolean checkSize(int index) {
        if(index <= elements.length ) {
            return true;
        }else{
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        }
    }

    /**
     * Method for add elements on elements array with index of put element
     * @param e - any type element
     * @param index - position of updated element
     * @throws IndexOutOfBoundsException if index out of bounds on elements
     * */
    public void update(int index, E e) {
        if(checkSize(index)) {
            elements[index] = e;
        }
    }

    /**
     * Method for delete all elements
     * */
    @Override
    public void deleteAll() {
        elements = (E[]) new Object[0];
    }

    /**
     * Method for get elements by index
     * @param index - position of updated element
     * @throws IndexOutOfBoundsException if index out of bounds on elements
     * */
    public E get(int index) {
        if(checkSize(index)) {
            return elements[index];
        }
        return null;
    }

    /**
     * Method for return size of CustomArrayList
     * @return integer size
     * */
    public int size() {
        return elements.length;
    }

    /**
     * Method for realize Iterators method
     * @return iterable array
     * @see ArrayIterator
     * */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elements);
    }


    /**
     * Method for sorting elements array
     * @param c A comparison function, which imposes a total ordering on some collection of objects.
     * */
    public void sort(Comparator<? super E> c) {
        if (elements.length != 0) {
            quickSort(elements, 0, elements.length - 1, c);
        }
    }

    /**
     * Private method for sort(Comparator c);
     * Quicksort is a sorting algorithm, which is leveraging the divide-and-conquer principle.
     * It has an average O(n log n) complexity and it’s one of the most used sorting algorithms,
     * especially for big data volumes.
     * The crucial point in QuickSort is to choose the best pivot. The middle element is, of course, the best,
     * as it would divide the list into two equal sub-lists.
     * @param elementsBody array
     * Parameters the array to be sorted:
     * @param low   the first index
     * @param high the last index.
     * @param c A comparison function, which imposes a total ordering on some collection of objects.
     * */
    private void quickSort(E[] elementsBody, int low, int high, Comparator<? super E> c) {
        int middle = low + (high - low) / 2;
        E border = elementsBody[middle];

        int i = low, j = high;
        while (i <= j) {
            while (c.compare(elementsBody[i], border) < 0) i++;
            while (c.compare(elementsBody[j], border) > 0) j--;
            if (i <= j) {
                E swap = elementsBody[i];
                elementsBody[i] = elementsBody[j];
                elementsBody[j] = swap;
                i++;
                j--;
            }
        }
        if (low < j) quickSort(elementsBody, low, j, c);
        if (high > i) quickSort(elementsBody, i, high, c);
    }
}


