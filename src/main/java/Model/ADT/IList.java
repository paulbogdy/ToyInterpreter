package Model.ADT;

public interface IList<T> {
    public int size();
    public IList<T> add(T elem);
    public T getIndex(int i);
    public IList<T> set(T newElem, int poz);
}
