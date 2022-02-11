package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{

    private final String _str;

    public StringValue(String str){
        _str = str;
    }

    public StringValue(){
        _str = "";
    }

    public String getValue(){
        return _str;
    }

    public StringValue setValue(String str){
        return new StringValue(str);
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof StringValue)) return false;
        return _str.equals(((StringValue) other).getValue());
    }

    @Override
    public IType getType() {
        return StringType.STRING;
    }

    @Override
    public String toString(){
        return "\"" + _str + "\"";
    }

    @Override
    public int hashCode() {
        return _str.hashCode();
    }
}
