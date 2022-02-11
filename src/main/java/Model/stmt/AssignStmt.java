package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.IType;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public final class AssignStmt implements IStmt{
    private final String _id;
    private final IExp _expression;

    public AssignStmt(String id, IExp expression){
        _id = id;
        _expression = expression;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();
        if(!symTable.containsKey(_id)){
            throw new AssignException("Variable isn't declared.");
        }
        IValue inDict = symTable.get(_id);
        IValue newVar = _expression.eval(symTable, state.getHeap());
        if(inDict.getType() != newVar.getType()){
            throw new AssignException("Value type doesn't match declaration.");
        }
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setSymTable(symTable.put(_id, newVar)));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType typeVar = typeEnv.get(_id);
        IType typeExp = _expression.typeCheck(typeEnv);
        if(typeVar.equals(typeExp)){
            return typeEnv;
        }
        else{
            throw new AssignException("Right hand side and left hand side have different types!");
        }
    }

    @Override
    public String toString(){
        return _id + "=" + _expression.toString();
    }
}
