package Repo;

import Model.stmt.PrgState;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepo {

    private List<PrgState> _threads;
    private final String _filePath;

    public Repository(String filePath){
        _threads = new LinkedList<PrgState>();
        _filePath = filePath;
        try {
            File myObj = new File(_filePath);
            myObj.createNewFile();
        } catch (IOException e) {
            throw new RepoException("IO exception occurred while creating log file.");
        }
    }

    private Repository(List<PrgState> threads, String filePath){
        _threads = threads;
        _filePath = filePath;
    }

    public Repository(PrgState onlyThread, String filePath){
        _threads = new LinkedList<PrgState>();
        _threads.add(onlyThread);
        _filePath = filePath;
        try {
            File myObj = new File(_filePath);
            myObj.createNewFile();
        } catch (IOException e) {
            throw new RepoException("IO exception occurred while creating log file.");
        }
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(_filePath, true)));
            logFile.write(state.toString());
            logFile.flush();
            logFile.close();
        } catch (IOException e) {
            throw new RepoException("IO exception occurred while writing into the log file.");
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return _threads;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        _threads = newList;
    }
}
