package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.IType;
import Model.types.RefType;
import Model.utils.IHeap;
import Model.value.IValue;
import Model.value.RefValue;

import java.util.LinkedList;
import java.util.List;

public class HeapWriting implements IStmt{

    private final String _varName;
    private final IExp _exp;

    public HeapWriting(String varName, IExp exp){
        _varName = varName;
        _exp = exp;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();
        if(!symTable.containsKey(_varName)){
            throw new HeapWritingException("Invalid variable name!");
        }
        IValue val = symTable.get(_varName);
        if(!(val.getType() instanceof RefType)){
            throw new HeapWritingException("The variable is not a reference!");
        }
        RefValue ref = (RefValue)val;
        IValue newVal = _exp.eval(symTable, state.getHeap());

        if(!newVal.getType().equals(ref.getInner())){
            throw new HeapWritingException("Types don't match!");
        }

        IHeap heap = state.getHeap();
        heap.put(ref.getAddress(), newVal);

        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state);
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType varType = typeEnv.get(_varName);
        IType expType = _exp.typeCheck(typeEnv);
        if(varType instanceof RefType ref){
            if(expType.equals(ref.getInner())){
                return typeEnv;
            }
            else{
                throw new HeapAllocException("Variable types do not match!");
            }
        }
        else{
            throw new HeapAllocException("The Variable is not a reference");
        }
    }

    @Override
    public String toString(){
        return "*" + _varName + "=" + _exp.toString();
    }
}
