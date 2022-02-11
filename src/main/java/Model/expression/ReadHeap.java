package Model.expression;

import Model.ADT.IDict;
import Model.types.IType;
import Model.types.RefType;
import Model.utils.IHeap;
import Model.value.IValue;
import Model.value.RefValue;

public class ReadHeap implements IExp{

    private final IExp _exp;

    public ReadHeap(IExp exp){
        _exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        IValue val = _exp.eval(symTable, heap);
        if(!(val.getType() instanceof RefType)){
            throw new ExprException("Expression must be a Reference Type");
        }
        int address = ((RefValue)val).getAddress();
        if(!heap.containsKey(address)){
            throw new ExprException("Memory access violation!");
        }

        return heap.get(address);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType type = _exp.typeCheck(typeEnv);
        if(type instanceof RefType ref){
            return ref.getInner();
        }
        else{
            throw new ExprException("The rH argument is not of type RefType!");
        }
    }

    @Override
    public String toString(){
        return "*(" + _exp.toString() + ")";
    }
}
