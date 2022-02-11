package Model.stmt;

public class HeapWritingException extends RuntimeException{
    public HeapWritingException(String errorMsg){
        super(errorMsg);
    }
}
