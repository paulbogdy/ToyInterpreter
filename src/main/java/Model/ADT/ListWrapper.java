package Model.ADT;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public final class ListWrapper<T> implements IList<T> {
    private final PStack<T> _list;

    public ListWrapper(){
        _list = ConsPStack.empty();
    }

    private ListWrapper(PStack<T> newList){
        _list = newList;
    }

    @Override
    public int size() {
        return _list.size();
    }

    @Override
    public IList<T> add(T elem) {
        return new ListWrapper<T>(_list.plus(_list.size(), elem));
    }

    @Override
    public T getIndex(int idx) {
        return _list.get(idx);
    }

    @Override
    public IList<T> set(T newElem, int poz) {
        if(poz > _list.size()){
            throw new ListException("Null Pointer exception, can't access item at poz: " + Integer.toString(poz));
        }
        return new ListWrapper<T>(_list.minus(poz).plus(poz, newElem));
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(T element: _list){
            result.append(element.toString()).append("\n");
        }
        return result.toString();
    }
}
