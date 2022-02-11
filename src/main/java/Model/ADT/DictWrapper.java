package Model.ADT;

import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.Map;

public final class DictWrapper<Key, T> implements IDict<Key, T> {

    private final PMap<Key, T> _dict;

    public DictWrapper(){
        _dict = HashTreePMap.empty();
    }

    private DictWrapper(PMap<Key, T> newDict){
        _dict = newDict;
    }

    @Override
    public boolean isEmpty() {
        return _dict.isEmpty();
    }

    @Override
    public IDict<Key, T> put(Key k, T val) {
        if(_dict.containsKey(k)){
            return new DictWrapper<Key, T>(_dict.minus(k).plus(k, val));
        }
        return new DictWrapper<Key, T>(_dict.plus(k, val));
    }

    @Override
    public T get(Key k) {
        if(!_dict.containsKey(k)){
            throw new DictException("Invalid Key (m:get).");
        }
        return _dict.get(k);
    }

    @Override
    public IDict<Key, T> remove(Key k) {
        if(!_dict.containsKey(k)){
            throw new DictException("Invalid Key (m:remove).");
        }
        return new DictWrapper<Key, T>(_dict.minus(k));
    }

    @Override
    public boolean containsKey(Key k) {
        return _dict.containsKey(k);
    }

    @Override
    public Map<Key, T> getContent() {
        return _dict;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<Key, T> entry: _dict.entrySet()) {
            try {
                if(entry.getValue().getClass().getMethod("toString").getDeclaringClass() != Object.class) {
                    result.append(entry.getKey().toString()).append(" --> ").append(entry.getValue().toString()).append("\n");
                }
                else{
                    result.append(entry.getKey().toString()).append("\n");
                }
            } catch (NoSuchMethodException e) {
                throw new DictException("The value has no method toString... Pretty much impossible tho...");
            }
        }
        return result.toString();
    }
}
