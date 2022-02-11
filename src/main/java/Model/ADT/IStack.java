package Model.ADT;

public interface IStack<T> {
    public T top() throws StackException;
    public IStack<T> pop() throws StackException;
    public IStack<T> push(T elem);
    public int size();
    public boolean isEmpty();
}
