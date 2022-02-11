package Model.utils;

import Model.ADT.IList;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public class Out implements IList<IValue> {

    private List<IValue> _list;

    public Out(){
        _list = new LinkedList<IValue>();
    }

    @Override
    public int size() {
        return _list.size();
    }

    @Override
    public IList<IValue> add(IValue elem) {
        _list.add(elem);
        return this;
    }

    @Override
    public IValue getIndex(int i) {
        if(i > _list.size()){
            throw new OutException("Invalid index!");
        }
        return _list.get(i);
    }

    @Override
    public IList<IValue> set(IValue newElem, int poz) {
        if(poz > _list.size()){
            throw new OutException("Invalid position!");
        }
        _list.set(poz, newElem);
        return this;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(IValue element: _list){
            result.append(element.toString()).append("\n");
        }
        return result.toString();
    }
}
