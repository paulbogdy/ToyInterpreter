package Model.expression;

import Model.ADT.IDict;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.utils.IHeap;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExp implements IExp{

    public enum Op {
        GREATER(">"),
        GREATER_EQUAL(">="),
        EQUAL("=="),
        NOT_EQUAL("!="),
        LOWER("<"),
        LOWER_EQUAL("<=");

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

    public RelationalExp(IExp first, IExp second, Op op){
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
            case GREATER -> {
                return BoolValue.wrap(firstInt > secondInt);
            }
            case GREATER_EQUAL -> {
                return BoolValue.wrap(firstInt >= secondInt);
            }
            case EQUAL -> {
                return BoolValue.wrap(firstInt == secondInt);
            }
            case NOT_EQUAL -> {
                return BoolValue.wrap(firstInt != secondInt);
            }
            case LOWER -> {
                return BoolValue.wrap(firstInt < secondInt);
            }
            case LOWER_EQUAL -> {
                return BoolValue.wrap(firstInt <= secondInt);
            }
            default -> {
                throw new ExprException("Invalid relational operation.");
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
                return BoolType.BOOLEAN;
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
