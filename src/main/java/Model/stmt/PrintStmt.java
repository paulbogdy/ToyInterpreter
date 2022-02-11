package Model.stmt;

import Model.ADT.IDict;
import Model.ADT.IList;
import Model.expression.IExp;
import Model.types.IType;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public final class PrintStmt implements IStmt{

    private final IExp _expression;


    public PrintStmt(IExp expression){
        _expression = expression;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IList<IValue> out = state.getOut();
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setOut(out.add(_expression.eval(state.getSymTable(), state.getHeap()))));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        _expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "Print(" + _expression.toString() + ")";
    }
}
