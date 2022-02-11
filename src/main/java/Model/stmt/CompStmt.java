package Model.stmt;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.types.IType;

import java.util.LinkedList;
import java.util.List;

public final class CompStmt implements IStmt{
    private final IStmt _first;
    private final IStmt _second;

    public CompStmt(IStmt first, IStmt second){
        _first = first;
        _second = second;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IStack<IStmt> exeStack = state.getExeStack();
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setStack(exeStack.push(_second).push(_first)));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return _second.typeCheck(_first.typeCheck(typeEnv));
    }

    @Override
    public String toString(){
        return _first.toString() + ";" + _second.toString();
    }
    public String recursiveString() {
        String result = "";
        if(_first instanceof CompStmt){
            result += ((CompStmt)_first).recursiveString();
        }
        else{
            result += _first.toString();
        }
        result += ";\n";
        if(_second instanceof CompStmt){
            result += ((CompStmt)_second).recursiveString();
        }
        else {
            result += _second.toString();
        }
        return result;
    }
}
