package repository;

import java.util.Collection;

public interface Repository <T, Tid>{

    void add(T t);

    void update();

    void delete();

    T getById(Tid id);

    Collection<T> getAll();

}
