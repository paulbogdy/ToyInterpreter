package Model.stmt;

public class ForkStmtException extends RuntimeException{
    ForkStmtException(String errorMsg){
        super(errorMsg);
    }
}
