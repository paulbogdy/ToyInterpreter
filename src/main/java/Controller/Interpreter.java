package Controller;

import Model.stmt.PrgState;
import Model.stmt.PrgStateException;
import Model.utils.IHeap;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.IRepo;
import Repo.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Interpreter {

    private IRepo _repo;
    private ExecutorService _executor;
    private List<PrgState> _prgList;
    private final boolean prettyPrint = true;

    public Interpreter(Repository repo){
        _repo = repo;
    }

    public int nrThreads(){
        return _repo.getPrgList().size();
    }

    public IRepo getRepo(){
        return _repo;
    }

    public PrgState getPrg(int id){
        for(var prgState: _repo.getPrgList()){
            if(prgState.getId() == id){
                return prgState;
            }
        }
        return null;
    }

    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heapDict){
        return heapDict.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, IHeap heap){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .flatMap(v -> {List<Integer> list = new LinkedList<Integer>();
                    while(v instanceof RefValue){
                        int address = ((RefValue)v).getAddress();
                        if(address != 0){
                            list.add(address);
                            v = heap.get(address);
                        }
                        else{
                            break;
                        }
                    }
                    return list.stream();})
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, RuntimeException {

        List<Callable<List<PrgState>>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<List<PrgState>>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = _executor.invokeAll(callList).stream()
                .flatMap(nextPrg -> {
                            try {
                                return nextPrg.get().stream();
                            } catch (InterruptedException | ExecutionException e) {
                                _executor.shutdown();
                                throw new PrgStateException(e.getMessage());
                            }
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        newPrgList.forEach(prg -> _repo.logPrgStateExec(prg));
        _repo.setPrgList(newPrgList);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> prgList){
        return prgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void createExecutor(){
        _executor = Executors.newFixedThreadPool(8);
        _prgList= removeCompletedPrg(_repo.getPrgList());
        _prgList.forEach(prg -> _repo.logPrgStateExec(prg));
    }

    public boolean trueOneStep() throws InterruptedException{
        if(_prgList.size() > 0){
            List<Integer> symTables = _prgList.stream()
                    .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap()).stream())
                    .collect(Collectors.toList());

            IHeap heap = _prgList.get(0).getHeap();
            heap.setContent(unsafeGarbageCollector(symTables, heap.getContent()));

            oneStepForAllPrg(_prgList);
            _prgList = removeCompletedPrg(_repo.getPrgList());
            return true;
        }
        else{
            _executor.shutdown();
            return false;
        }
    }

    public void allSteps() throws InterruptedException {
        _executor = Executors.newFixedThreadPool(8);

        List<PrgState> prgList = removeCompletedPrg(_repo.getPrgList());
        prgList.forEach(prg -> _repo.logPrgStateExec(prg));
        while(prgList.size() > 0){
            List<Integer> symTables = prgList.stream()
                            .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap()).stream())
                            .collect(Collectors.toList());

            IHeap heap = prgList.get(0).getHeap();
            heap.setContent(unsafeGarbageCollector(symTables, heap.getContent()));

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(_repo.getPrgList());
        }

        _executor.shutdown();
    }
}
