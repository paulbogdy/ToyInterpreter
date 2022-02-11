package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class OpenRFile implements IStmt{
    private final IExp _exp;

    public OpenRFile(IExp exp){
        _exp = exp;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IValue value = _exp.eval(state.getSymTable(), state.getHeap());
        if(value.getType() != StringType.STRING){
            throw new OpenFileException("Expression is not a string value.");
        }
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue stringVal = (StringValue) value;
        if(fileTable.containsKey(stringVal)){
            throw new OpenFileException("File is already open.");
        }
        BufferedReader newReader;
        try {
            newReader = new BufferedReader(new FileReader(stringVal.getValue()));
        } catch (FileNotFoundException e) {
            throw new OpenFileException("Could not open file.");
        }
        IDict<StringValue, BufferedReader> newFileTable = fileTable.put(stringVal, newReader);

        List<PrgState> prgStates = new LinkedList<PrgState>();
        prgStates.add(state.setFileTable(newFileTable));
        return prgStates;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = _exp.typeCheck(typeEnv);
        if(expType == StringType.STRING){
            return typeEnv;
        }
        else{
            throw new OpenFileException("Expression is not a string value.");
        }
    }

    @Override
    public String toString(){
        return "open(" + _exp.toString() + ")";
    }
}
