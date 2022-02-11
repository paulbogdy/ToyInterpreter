package Model.expression;

import Model.ADT.IDict;
import Model.types.BoolType;
import Model.types.IType;
import Model.utils.IHeap;
import Model.value.BoolValue;
import Model.value.IValue;

public final class LogicExp implements IExp{

    public enum Op{
        AND,
        OR,
    }

    private final IExp _first;
    private final IExp _second;
    private final Op _op;

    LogicExp(IExp first, IExp second, Op op){
        _first = first;
        _second = second;
        _op = op;
    }
    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        IValue firstValue, secondValue;
        firstValue = _first.eval(symTable, heap);
        if(firstValue.getType() != BoolType.BOOLEAN) {
            throw new ExprException("Invalid first operand.");
        }
        secondValue = _second.eval(symTable, heap);
        if(secondValue.getType() != BoolType.BOOLEAN) {
            throw new ExprException("Invalid second operand.");
        }
        boolean firstBool, secondBool;
        firstBool = ((BoolValue)firstValue).getValue();
        secondBool = ((BoolValue)secondValue).getValue();
        switch (_op){
            case AND -> {
                return BoolValue.wrap(firstBool && secondBool);
            }
            case OR -> {
                return BoolValue.wrap(firstBool || secondBool);
            }
            default -> {
                throw new ExprException("Invalid logic operation.");
            }
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType type1, type2;
        type1 = _first.typeCheck(typeEnv);
        type2 = _second.typeCheck(typeEnv);
        if(type1 == BoolType.BOOLEAN){
            if(type2 == BoolType.BOOLEAN){
                return BoolType.BOOLEAN;
            }
            throw new ExprException("Second operand is not a Boolean!");
        }
        else{
            throw new ExprException("First operand is not a Boolean!");
        }
    }

    @Override
    public String toString(){
        return _first.toString() + " " + _op.toString() + " " + _second.toString();
    }
}
