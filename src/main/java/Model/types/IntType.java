package Model.types;

import Model.value.IValue;
import Model.value.IntValue;

public final class IntType implements IType {
    public static final IntType INTEGER = new IntType();
    private IntType(){};

    @Override
    public IValue init() { return new IntValue(0); }

    @Override
    public String toString(){
        return "int";
    }
}
