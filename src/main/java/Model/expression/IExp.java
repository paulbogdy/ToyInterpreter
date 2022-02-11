package Model.expression;

import Model.ADT.IDict;
import Model.types.IType;
import Model.utils.IHeap;
import Model.value.IValue;

public interface IExp {
    IValue eval(IDict<String, IValue> symTable, IHeap heap);
    IType typeCheck(IDict<String, IType> typeEnv);
}
