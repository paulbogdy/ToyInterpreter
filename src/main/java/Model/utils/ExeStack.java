package Model.utils;

import Model.ADT.IStack;
import Model.stmt.CompStmt;
import Model.stmt.IStmt;
import org.pcollections.ConsPStack;

import java.util.Iterator;

public class ExeStack implements IStack<IStmt>, Iterable<IStmt> {

    private final ConsPStack<IStmt> _stack;

    public ExeStack(){
        _stack = ConsPStack.empty();
    }

    private ExeStack(ConsPStack<IStmt> newStack){
        _stack = newStack;
    }

    @Override
    public Iterator<IStmt> iterator(){
        return  _stack.iterator();
    }

    @Override
    public IStmt top() {
        if(size() == 0){
            throw new ExeStackException("Stack is empty, can't get top elem.");
        }
        return _stack.get(0);
    }

    @Override
    public IStack<IStmt> pop() {
        if(size() == 0){
            throw new ExeStackException("Can't pop empty stack.");
        }
        return new ExeStack(_stack.minus(0));
    }

    @Override
    public IStack<IStmt> push(IStmt elem) {
        return new ExeStack(_stack.plus(elem));
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
        StringBuilder result = new StringBuilder();
        for(IStmt elem: _stack){
            if(elem instanceof CompStmt){
                result.append(((CompStmt) elem).recursiveString()).append(";\n");
            }
            else {
                result.append(elem.toString()).append(";\n");
            }
        }
        return result.toString();
    }
}
