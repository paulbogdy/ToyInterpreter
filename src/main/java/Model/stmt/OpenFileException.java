package Model.stmt;

public class OpenFileException extends RuntimeException{
    OpenFileException(String errorMsg){
        super(errorMsg);
    }
}
