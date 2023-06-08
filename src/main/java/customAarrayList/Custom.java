package customAarrayList;

/**
 * Interface extends Iterable interface. Implementing this interface allows an object to be the target
 * of the enhanced for statement (sometimes called the "for-each loop" statement).
 * @author akrafit
 * @version 1.0
 * Type parameters:
 * @param <E> – the type of elements in this list
 * */
public interface Custom<E> extends Iterable<E>{
    boolean add(E e);
    void delete(int index);
    E get(int index);
    int size();
    void update(int index, E e);
    void deleteAll();
}
