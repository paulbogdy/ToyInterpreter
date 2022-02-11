package Model.stmt;

import Model.ADT.IDict;
import Model.types.IType;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public final class VarDecl implements IStmt{

    private final String _id;
    private final IType _type;

    public VarDecl(String id, IType type){
        _id = id;
        _type = type;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();

        if(symTable.containsKey(_id)){
            throw new DeclException("Variable is already declared.");
        }

        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setSymTable(symTable.put(_id, _type.init())));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return typeEnv.put(_id, _type);
    }

    @Override
    public String toString(){
        return _type.toString() + " " + _id;
    }
}
