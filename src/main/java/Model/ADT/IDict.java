package Model.ADT;

import java.util.Map;

public interface IDict<Key, T> {
    public boolean isEmpty();
    public IDict<Key, T> put(Key k, T val);
    public T get(Key k) throws DictException;
    public IDict<Key, T> remove(Key k) throws DictException;
    public boolean containsKey(Key k);

    Map<Key, T> getContent();
}
