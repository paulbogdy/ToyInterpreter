package Model.stmt;

import Model.ADT.IDict;
import Model.ADT.IList;
import Model.ADT.IStack;
import Model.utils.*;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.List;

public final class PrgState {
    private final IStack<IStmt> _exeStack;
    private final IDict<String, IValue> _symTable;
    private final IList<IValue> _out;
    private final IDict<StringValue, BufferedReader> _fileTable;
    private final IHeap _heap;
    private final int _id;

    private static int incremented = 0;

    private synchronized static int newId(){
        incremented += 1;
        return incremented;
    }

    public PrgState(IStmt initial){
        initial.typeCheck(new TypeEnv());
        _exeStack = (new ExeStack()).push(initial);
        _symTable = new SymTable();
        _out = new Out();
        _fileTable = new FileTable();
        _heap = new Heap();
        _id = newId();
    }

    public PrgState(IStack<IStmt> exeStack,
                     IDict<String, IValue> symTable,
                     IList<IValue> out,
                     IDict<StringValue, BufferedReader> fileTable,
                     IHeap heap){
        _exeStack = exeStack;
        _symTable = symTable;
        _out = out;
        _fileTable = fileTable;
        _heap = heap;
        _id = newId();
    }

    private PrgState(IStack<IStmt> exeStack,
                     IDict<String, IValue> symTable,
                     IList<IValue> out,
                     IDict<StringValue, BufferedReader> fileTable,
                     IHeap heap,
                     int id){
        _exeStack = exeStack;
        _symTable = symTable;
        _out = out;
        _fileTable = fileTable;
        _heap = heap;
        _id = id;
    }

    public List<PrgState> oneStep(){
        if(_exeStack.isEmpty()){
            throw new PrgStateException("PrgState stack is empty!");
        }
        IStmt crtStmt = _exeStack.top();
        return crtStmt.execute(this.setStack(_exeStack.pop()));
    }

    public boolean isNotCompleted(){ return !_exeStack.isEmpty(); }

    public IStack<IStmt> getExeStack(){ return _exeStack; }
    public IDict<String, IValue> getSymTable(){ return _symTable; }
    public IList<IValue> getOut(){ return _out; }
    public IDict<StringValue, BufferedReader> getFileTable(){ return _fileTable; }
    public IHeap getHeap(){ return _heap; }
    public int getId(){ return _id; }

    public PrgState setStack(IStack<IStmt> newStack){ return new PrgState(newStack, _symTable, _out, _fileTable, _heap, _id); }
    public PrgState setSymTable(IDict<String, IValue> newSymTable){ return new PrgState(_exeStack, newSymTable, _out, _fileTable, _heap, _id); }
    public PrgState setOut(IList<IValue> newOut){ return new PrgState(_exeStack, _symTable, newOut, _fileTable, _heap, _id); }
    public PrgState setFileTable(IDict<StringValue, BufferedReader> newFileTable){ return new PrgState(_exeStack, _symTable, _out, newFileTable, _heap, _id); }
    public PrgState setHeap(IHeap newHeap){ return new PrgState(_exeStack, _symTable, _out, _fileTable, newHeap, _id); }

    @Override
    public String toString(){
        return  "Thread nr:" + Integer.toString(_id) + "\n" +
                "ExeStack:\n" + _exeStack.toString() +
                "SymTable:\n" + _symTable.toString() +
                "Out:\n" + _out.toString() +
                "FileTable:\n" + _fileTable.toString() +
                "Heap:\n" + _heap.toString() + "\n";
    }
}
