package Model.stmt;

import Model.ADT.IDict;
import Model.types.IType;

import java.util.LinkedList;
import java.util.List;

public class Nope implements IStmt{
    @Override
    public List<PrgState> execute(PrgState state) {
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state);
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return typeEnv;
    }
}
