package Model.expression;

import Model.ADT.IDict;
import Model.types.IType;
import Model.utils.IHeap;
import Model.value.IValue;

public final class ValueExp implements IExp{
    private final IValue _value;

    public ValueExp(IValue value){
        _value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        return _value;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        return _value.getType();
    }

    @Override
    public String toString(){
        return _value.toString();
    }
}
