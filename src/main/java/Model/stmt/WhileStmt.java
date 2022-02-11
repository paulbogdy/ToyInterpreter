package Model.stmt;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.expression.IExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public class WhileStmt implements IStmt{

    private final IExp _exp;
    private final IStmt _stmt;

    public WhileStmt(IExp exp, IStmt stmt){
        _exp = exp;
        _stmt = stmt;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IStack<IStmt> exeStack = state.getExeStack();
        IValue val = _exp.eval(state.getSymTable(), state.getHeap());
        if (val.getType() != BoolType.BOOLEAN){
            throw new WhileStmtException("Condition must be a boolean!");
        }
        boolean check = ((BoolValue)val).getValue();

        List<PrgState> prgStates = new LinkedList<PrgState>();
        if(check){
            prgStates.add(state.setStack(exeStack.push(this).push(_stmt)));
        }
        else{
            prgStates.add(state);
        }
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType typeExp = _exp.typeCheck(typeEnv);
        if(typeExp == BoolType.BOOLEAN){
            return _stmt.typeCheck(typeEnv);
        }
        else{
            throw new WhileStmtException("The condition of WHILE must be a boolean!");
        }
    }

    @Override
    public String toString(){
        return "while(" + _exp.toString() + "){" + _stmt.toString() + ";}";
    }
}
