package Model.utils;

import Model.ADT.IDict;
import Model.types.IType;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;

import java.util.Map;

public class TypeEnv implements IDict<String, IType> {
    private final PMap<String, IType> _dict;

    public TypeEnv(){
        _dict = HashTreePMap.empty();
    }

    private TypeEnv(PMap<String, IType> newDict){
        _dict = newDict;
    }

    @Override
    public boolean isEmpty() {
        return _dict.isEmpty();
    }

    @Override
    public IDict<String, IType> put(String k, IType val) {
        if(_dict.containsKey(k)){
            return new TypeEnv(_dict.minus(k).plus(k, val));
        }
        return new TypeEnv(_dict.plus(k, val));
    }

    @Override
    public IType get(String k) {
        if(!_dict.containsKey(k)){
            throw new SymTableException("Invalid Key (m:get).");
        }
        return _dict.get(k);
    }

    @Override
    public IDict<String, IType> remove(String k) {
        if(!_dict.containsKey(k)){
            throw new TypeEnvException("Invalid Key (m:remove).");
        }
        return new TypeEnv(_dict.minus(k));
    }

    @Override
    public boolean containsKey(String k) {
        return _dict.containsKey(k);
    }

    @Override
    public Map<String, IType> getContent() {
        return _dict;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, IType> entry: _dict.entrySet()) {
            result.append(entry.getKey().toString()).append(" --> ").append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }
}
