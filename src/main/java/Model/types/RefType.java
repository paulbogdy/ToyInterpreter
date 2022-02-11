package Model.types;

import Model.value.IValue;
import Model.value.RefValue;

public class RefType implements IType{

    private final IType _inner;

    public RefType(IType inner){
        _inner = inner;
    }

    public IType getInner(){
        return _inner;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof RefType){
            return _inner.equals(((RefType) other).getInner());
        }
        return false;
    }

    @Override
    public IValue init() {
        return new RefValue(0, _inner);
    }

    @Override
    public String toString(){
        return "Ref<" + _inner.toString() + ">";
    }
}
