package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CloseRFile implements IStmt{

    private final IExp _exp;

    public CloseRFile(IExp exp){
        _exp = exp;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IValue stringVal = _exp.eval(state.getSymTable(), state.getHeap());
        if (stringVal.getType() != StringType.STRING) {
            throw new CloseFileException("Invalid expression, must be a string value.");
        }
        StringValue key = (StringValue) stringVal;
        if (!state.getFileTable().containsKey(key)) {
            throw new CloseFileException("File must be open in order to close it.");
        }
        BufferedReader toClose = state.getFileTable().get(key);
        try {
            toClose.close();
        } catch (IOException e) {
            throw new CloseFileException("IO exception occurred while closing the file.");
        }
        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setFileTable(state.getFileTable().remove(key)));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = _exp.typeCheck(typeEnv);
        if(expType == StringType.STRING){
            return typeEnv;
        }
        else{
            throw new CloseFileException("Expression is not a string value.");
        }
    }

    @Override
    public String toString(){
        return "close(" + _exp.toString() + ")";
    }
}
