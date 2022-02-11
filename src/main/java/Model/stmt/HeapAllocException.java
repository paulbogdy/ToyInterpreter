package Model.stmt;

public class HeapAllocException extends RuntimeException{
    HeapAllocException(String errorMsg){
        super(errorMsg);
    }
}
