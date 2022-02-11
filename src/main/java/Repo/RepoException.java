package Repo;

public class RepoException extends RuntimeException{
    RepoException(String errorMsg){
        super(errorMsg);
    }
}
