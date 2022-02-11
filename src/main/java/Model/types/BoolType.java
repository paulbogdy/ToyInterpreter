package Model.types;

import Model.value.BoolValue;
import Model.value.IValue;

public final class BoolType implements IType {
    public static final BoolType BOOLEAN = new BoolType();
    private BoolType(){};

    @Override
    public IValue init() {
        return BoolValue.FALSE;
    }

    @Override
    public String toString(){
        return "bool";
    }
}
