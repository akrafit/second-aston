package customAarrayList;

import java.util.Iterator;
/**
 * Interface implements Iterable interface.
 * @author akrafit
 * @version 1.0
 * Type parameters:
 * @param <E> – the type of elements in this list
 * */
public class ArrayIterator<E> implements Iterator<E> {
    /**
     * Index of position
     * */
    private int index = 0;

    /**
     * Elements array
     * */
    private E[] elements;

    /**
     * Constructor for create exemplar
     * @param elements input arrays
     * */
    ArrayIterator(E[] elements) {
        this.elements = elements;
    }

    /**
     * Method for check next element
     * @return boolean
     * */
    @Override
    public boolean hasNext() {
        return index < elements.length;
    }

    /**
     * Method for shift on next element
     * */
    @Override
    public E next() {
        return elements[index++];
    }
}
