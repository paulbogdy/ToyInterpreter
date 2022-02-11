package Model.stmt;

import Model.ADT.IDict;
import Model.expression.IExp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadFile implements IStmt{

    private final IExp _exp;
    private final String _varName;

    ReadFile(IExp exp, String varName){
        _exp = exp;
        _varName = varName;
    }

    @Override
    public List<PrgState> execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();
        if(!symTable.containsKey(_varName)){
            throw new ReadFileException("Variable name not defined.");
        }
        IValue value = symTable.get(_varName);
        if(value.getType() != IntType.INTEGER){
            throw new ReadFileException("Invalid variable type.");
        }
        IValue expVal = _exp.eval(symTable, state.getHeap());
        if(expVal.getType() != StringType.STRING){
            throw new ReadFileException("Invalid file name, must be a string value.");
        }
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        StringValue stringVal = (StringValue) expVal;
        if(!fileTable.containsKey(stringVal)){
            throw new ReadFileException("Invalid file name, file must be open.");
        }
        BufferedReader fileBuffer = fileTable.get(stringVal);
        try {
            String line = fileBuffer.readLine();
            IValue newValue = IntType.INTEGER.init();
            if(line != null){
                newValue = new IntValue(Integer.parseInt(line));
            }
            List<PrgState> prgStates = new LinkedList<PrgState>();
            prgStates.add(state.setSymTable(symTable.put(_varName, newValue)));
            return prgStates;
        }
        catch (IOException e) {
            throw new ReadFileException("IO error occurred while reading from file.");
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = _exp.typeCheck(typeEnv);
        if(expType == StringType.STRING){
            return typeEnv;
        }
        else{
            throw new ReadFileException("Expression is not a string value.");
        }
    }

    @Override
    public String toString(){
        return "read(" + _exp.toString() + ", &" + _varName + ")";
    }
}
