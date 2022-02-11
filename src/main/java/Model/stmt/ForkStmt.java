package Model.stmt;

import Model.ADT.IDict;
import Model.types.IType;

import java.util.LinkedList;
import java.util.List;

public class ForkStmt implements IStmt{

    private final IStmt _stmt;

    public ForkStmt(IStmt stmt){
        _stmt = stmt;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state);
        prgStates.add(new PrgState(state.getExeStack().push(_stmt),
                state.getSymTable(),
                state.getOut(),
                state.getFileTable(),
                state.getHeap()));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        _stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "If(fork() == 0){" + _stmt.toString() + "}";
    }
}
