package Model.value;

import Model.types.IType;
import Model.types.IntType;

public class IntValue implements IValue {
    private final int _value;
    public IntValue(){
        _value = 0;
    }
    public IntValue(int value){
        _value = value;
    }
    public int getValue(){
        return _value;
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof IntValue)) return false;
        return _value == ((IntValue) other).getValue();
    }
    public IntValue setValue(int value){
        return new IntValue(value);
    }
    @Override
    public IType getType(){
        return IntType.INTEGER;
    }
    @Override
    public String toString(){ return Integer.toString(_value); }
}
