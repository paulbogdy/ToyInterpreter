package Model.expression;

import Model.ADT.IDict;
import Model.types.IType;
import Model.utils.IHeap;
import Model.value.IValue;

public final class VarExp implements IExp{
    private final String _id;

    public VarExp(String id){
        _id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        if(symTable.containsKey(_id))
            return symTable.get(_id);
        throw new ExprException("Invalid Variable id.");
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        return typeEnv.get(_id);
    }

    @Override
    public String toString(){
        return _id;
    }
}
