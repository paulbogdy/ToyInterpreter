package Model.utils;

import Model.value.IValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Heap implements IHeap, Iterable<Map.Entry<Integer, IValue>>{

    private Map<Integer, IValue> _dict;
    private Stack<Integer> _stack;
    private int _lastIncremented;

    public Heap(){
        _dict = new HashMap<Integer, IValue>();
        _stack = new Stack<Integer>();
        _lastIncremented = 0;
    }

    @Override
    public Iterator<Map.Entry<Integer, IValue>> iterator() {
        return _dict.entrySet().iterator();
    }

    @Override
    public int freePosition() {
        if(_stack.isEmpty()){
            _lastIncremented+=1;
            _stack.push(_lastIncremented);
        }
        return _stack.get(0);
    }

    @Override
    public boolean isEmpty() {
        return _dict.isEmpty();
    }

    @Override
    public IHeap put(int k, IValue val) {
        if(containsKey(k)){
            remove(k);
        }
        _dict.put(k, val);
        _stack.pop();
        return this;
    }

    @Override
    public IValue get(int k) throws HeapException {
        if(!containsKey(k)){
            throw new HeapException("Invalid Key!");
        }
        return _dict.get(k);
    }

    @Override
    public IHeap remove(int k) throws HeapException {
        _stack.push(k);
        _dict.remove(k);
        return this;
    }

    @Override
    public boolean containsKey(int k) {
        return _dict.containsKey(k);
    }

    @Override
    public void setContent(Map<Integer, IValue> newDict) {
        _dict = newDict;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return _dict;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<Integer, IValue> entry: _dict.entrySet()) {
            result.append(entry.getKey().toString()).append(" --> ").append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }
}
