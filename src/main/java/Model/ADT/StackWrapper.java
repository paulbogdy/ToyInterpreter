package Model.ADT;
import org.pcollections.*;

public final class StackWrapper<T> implements IStack<T>{

    private final ConsPStack<T> _stack;

    public StackWrapper(){
        _stack = ConsPStack.empty();
    }

    private StackWrapper(ConsPStack<T> newStack){
        _stack = newStack;
    }

    @Override
    public T top() {
        if(size() == 0){
            throw new StackException("Stack is empty, can't get top elem.");
        }
        return _stack.get(0);
    }

    @Override
    public IStack<T> pop() {
        if(size() == 0){
            throw new StackException("Can't pop empty stack.");
        }
        return new StackWrapper<T>(_stack.minus(0));
    }

    @Override
    public IStack<T> push(T elem) {
        return new StackWrapper<T>(_stack.plus(elem));
    }

    @Override
    public int size() {
        return _stack.size();
    }

    @Override
    public boolean isEmpty() {
        return _stack.isEmpty();
    }

    @Override
    public String toString() {
        return _stack.toString();
    }
}
