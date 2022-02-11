package Model.value;

import Model.types.BoolType;
import Model.types.IType;

public class BoolValue implements IValue {
    private final boolean _value;
    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);
    private BoolValue(boolean value){
        _value = value;
    }
    public boolean getValue(){
        return _value;
    }
    public static BoolValue wrap(boolean bool){
        return bool ? TRUE : FALSE;
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof BoolValue)) return false;
        return _value == ((BoolValue) other).getValue();
    }
    @Override
    public IType getType(){
        return BoolType.BOOLEAN;
    }
    @Override
    public String toString() { return _value ? "TRUE" : "FALSE"; }
}
