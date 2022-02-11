package Model.utils;

import Model.value.IValue;

import java.util.Map;

public interface IHeap {
    public int freePosition();
    public boolean isEmpty();
    public IHeap put(int k, IValue val);
    public IValue get(int k) throws HeapException;
    public IHeap remove(int k) throws HeapException;
    public boolean containsKey(int k);
    public void setContent(Map<Integer, IValue> newDict);

    Map<Integer, IValue> getContent();
}
