package Model.types;

import Model.value.IValue;
import Model.value.StringValue;

public class StringType implements IType{
    public static final StringType STRING = new StringType();
    private StringType(){}

    @Override
    public IValue init() {
        return new StringValue();
    }

    @Override
    public String toString(){
        return "str";
    }
}
