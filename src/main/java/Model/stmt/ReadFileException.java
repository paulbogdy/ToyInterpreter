package Model.stmt;

public class ReadFileException extends RuntimeException{
    ReadFileException(String errorMsg){
        super(errorMsg);
    }
}
