package Model.expression;

import Model.ADT.IDict;
import Model.types.IType;
import Model.types.IntType;
import Model.utils.IHeap;
import Model.value.IValue;
import Model.value.IntValue;

public final class ArithExp implements IExp {

    public enum Op {
        PLUS("+"),
        MINUS("-"),
        MULTIPLICATION("*"),
        DIVIDE("/");

        private final String _str;
        Op(String str){ _str = str; }
        @Override
        public String toString(){
            return _str;
        }
    }

    private final Op _op;
    private final IExp _first;
    private final IExp _second;

    public ArithExp(IExp first, IExp second, Op op){
        _first = first;
        _second = second;
        _op = op;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        IValue firstValue, secondValue;
        firstValue = _first.eval(symTable, heap);
        if(firstValue.getType() != IntType.INTEGER){
            throw new ExprException("Invalid first operand.");
        }
        secondValue = _second.eval(symTable, heap);
        if(secondValue.getType() != IntType.INTEGER){
            throw new ExprException("Invalid second operand.");
        }
        int firstInt = ((IntValue)firstValue).getValue();
        int secondInt = ((IntValue)secondValue).getValue();
        switch (_op){
            case PLUS -> {
                return new IntValue(firstInt + secondInt);
            }
            case MINUS -> {
                return new IntValue(firstInt - secondInt);
            }
            case MULTIPLICATION -> {
                return new IntValue(firstInt * secondInt);
            }
            case DIVIDE -> {
                if(secondInt == 0){
                    throw new ExprException("Division by 0.");
                }
                return new IntValue(firstInt / secondInt);
            }
            default -> {
                throw new ExprException("Invalid arithmetic operation.");
            }
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType type1, type2;
        type1 = _first.typeCheck(typeEnv);
        type2 = _second.typeCheck(typeEnv);
        if(type1 == IntType.INTEGER){
            if(type2 == IntType.INTEGER){
                return IntType.INTEGER;
            }
            throw new ExprException("Second operand is not an Integer!");
        }
        else{
            throw new ExprException("First operand is not an Integer!");
        }
    }

    @Override
    public String toString(){
        return _first.toString() + _op.toString() + _second.toString();
    }
}