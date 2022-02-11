package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{

    private final int _address;
    private final IType _locationType;

    public RefValue(int address, IType locationType){
        _address = address;
        _locationType = locationType;
    }

    public IType getInner(){
        return _locationType;
    }
    public int getAddress(){
        return _address;
    }

    @Override
    public IType getType() {
        return new RefType(_locationType);
    }

    @Override
    public String toString(){
        return "(" + Integer.toString(_address) + ", " + _locationType.toString() + ")";
    }
}
