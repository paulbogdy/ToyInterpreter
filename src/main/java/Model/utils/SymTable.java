package Model.utils;

import Model.ADT.IDict;
import Model.value.IValue;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.Iterator;
import java.util.Map;

public class SymTable implements IDict<String, IValue>, Iterable<Map.Entry<String, IValue>> {
    private final PMap<String, IValue> _dict;

    public SymTable(){
        _dict = HashTreePMap.empty();
    }

    private SymTable(PMap<String, IValue> newDict){
        _dict = newDict;
    }

    @Override
    public Iterator<Map.Entry<String, IValue>> iterator() {
        return _dict.entrySet().iterator();
    }

    @Override
    public boolean isEmpty() {
        return _dict.isEmpty();
    }

    @Override
    public IDict<String, IValue> put(String k, IValue val) {
        if(_dict.containsKey(k)){
            return new SymTable(_dict.minus(k).plus(k, val));
        }
        return new SymTable(_dict.plus(k, val));
    }

    @Override
    public IValue get(String k) {
        if(!_dict.containsKey(k)){
            throw new SymTableException("Invalid Key (m:get).");
        }
        return _dict.get(k);
    }

    @Override
    public IDict<String, IValue> remove(String k) {
        if(!_dict.containsKey(k)){
            throw new SymTableException("Invalid Key (m:remove).");
        }
        return new SymTable(_dict.minus(k));
    }

    @Override
    public boolean containsKey(String k) {
        return _dict.containsKey(k);
    }

    @Override
    public Map<String, IValue> getContent() {
        return _dict;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, IValue> entry: _dict.entrySet()) {
            result.append(entry.getKey().toString()).append(" --> ").append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }
}
