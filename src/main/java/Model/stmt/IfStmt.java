package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

import java.util.LinkedList;
import java.util.List;

public final class IfStmt implements IStmt{

    private final IExp _expression;
    private final IStmt _thenStmt;
    private final IStmt _elseStmt;

    public IfStmt(IExp expression, IStmt thenStmt, IStmt elseStmt){
        _expression = expression;
        _thenStmt = thenStmt;
        _elseStmt = elseStmt;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();
        IValue condition = _expression.eval(symTable, state.getHeap());
        if(condition.getType() != BoolType.BOOLEAN) {
            throw new IfStmtException("Condition must be a boolean.");
        }
        List<PrgState> prgStates = new LinkedList<PrgState>();
        if(((BoolValue)condition).getValue()){
            prgStates.add(state.setStack(state.getExeStack().push(_thenStmt)));
            return prgStates;
        }
        else{
            prgStates.add(state.setStack(state.getExeStack().push(_elseStmt)));
            return prgStates;
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType typeExp = _expression.typeCheck(typeEnv);
        if(typeExp == BoolType.BOOLEAN){
            _thenStmt.typeCheck(typeEnv);
            _elseStmt.typeCheck(typeEnv);
            return typeEnv;
        }
        else{
            throw new IfStmtException("The condition of IF must be a boolean!");
        }
    }

    @Override
    public String toString(){
        return "If (" + _expression.toString() + ") then {" + _thenStmt.toString() + "} else {" + _elseStmt.toString() + "}";
    }
}

