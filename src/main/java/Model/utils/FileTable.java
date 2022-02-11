package Model.utils;

import Model.ADT.DictException;
import Model.ADT.IDict;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileTable implements IDict<StringValue, BufferedReader>{

    private Map<StringValue, BufferedReader> _files;

    public FileTable(){
        _files = new HashMap<StringValue, BufferedReader>();
    }

    public Set<StringValue> files(){
        return _files.keySet();
    }

    @Override
    public boolean isEmpty() {
        return _files.isEmpty();
    }

    @Override
    public IDict<StringValue, BufferedReader> put(StringValue k, BufferedReader val) {
        _files.put(k, val);
        return this;
    }

    @Override
    public BufferedReader get(StringValue k) throws DictException {
        if(!containsKey(k)){
            throw new FileTableException("Invalid Key");
        }
        return _files.get(k);
    }

    @Override
    public IDict<StringValue, BufferedReader> remove(StringValue k) throws DictException {
        if(!containsKey(k)){
            throw new FileTableException("Invalid Key");
        }
        _files.remove(k);
        return this;
    }

    @Override
    public boolean containsKey(StringValue k) {
        return _files.containsKey(k);
    }

    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return _files;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<StringValue, BufferedReader> entry: _files.entrySet()) {
            result.append(entry.getKey().toString()).append("\n");
        }
        return result.toString();
    }
}
