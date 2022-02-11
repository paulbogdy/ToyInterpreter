package Repo;

import Model.stmt.PrgState;

import java.util.List;

public interface IRepo {
    public void logPrgStateExec(PrgState state) throws RepoException;
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> newList);
}
