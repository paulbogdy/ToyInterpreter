package Model.stmt;

public class CloseFileException extends RuntimeException{
    CloseFileException(String errorMsg){
        super(errorMsg);
    }
}
