package com.example.toyinterpreter;

import Controller.Interpreter;
import Model.ADT.IList;
import Model.ADT.IStack;
import Model.stmt.IStmt;
import Model.stmt.OpenFileException;
import Model.stmt.PrgState;
import Model.utils.*;
import Model.value.IValue;
import Model.value.StringValue;
import Repo.Repository;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    private Interpreter _currentController;
    private TextField _numberOfPrg;
    private TableView _heap;
    private ListView _out;
    private ListView _fileTable;
    private ListView<Integer> _prgStates;
    private TableView _symTable;
    private ListView _exeStack;
    private int _selectedId;
    private boolean _allDone;

    private void setNrPrg(){
        if(_currentController == null){
            _numberOfPrg.setText("No program running!");
        }
        else {
            _numberOfPrg.setText("Number of PrgStates: " + Integer.toString(_currentController.nrThreads()));
        }
    }

    private void populatePrgStates(){
        _prgStates.getItems().clear();
        if(_currentController != null){
            for(var prgState: _currentController.getRepo().getPrgList()){
                _prgStates.getItems().add(prgState.getId());
            }
        }
    }

    private void populateOut(){
        if(_currentController == null) {
            _out.getItems().clear();
            return;
        }
        int start = _out.getItems().size();
        int id = _prgStates.getItems().get(0);
        IList<IValue> out = _currentController.getPrg(id).getOut();
        for (int i = start; i < out.size(); i++){
            _out.getItems().add(out.getIndex(i).toString());
        }
    }

    private void populateFileTable(){
        _fileTable.getItems().clear();
        if(_currentController == null) {
            return;
        }
        int id = _prgStates.getItems().get(0);
        FileTable fileTable = (FileTable) _currentController.getPrg(id).getFileTable();
        for(StringValue file: fileTable.files()){
            _fileTable.getItems().add(file.toString());
        }
    }

    private void populateHeap(){
        _heap.getItems().clear();
        if(_currentController == null) {
            return;
        }
        int id = _prgStates.getItems().get(0);
        Heap heap = (Heap) _currentController.getPrg(id).getHeap();
        for(var elem: heap){
            _heap.getItems().add(new Pair<>(elem.getKey(), elem.getValue()));
        }
    }

    private void populateSymTable(){
        _symTable.getItems().clear();
        if(_prgStates.getItems().size() == 0) return;
        if(!_prgStates.getItems().contains(_selectedId)){
            _selectedId = -1;
        }
        if(_selectedId == -1){
            _selectedId = _prgStates.getItems().get(0);
        }
        SymTable symTable = (SymTable) _currentController.getPrg(_selectedId).getSymTable();
        for(var elem: symTable){
            _symTable.getItems().add(elem);
        }
    }

    private void populateExeStack(){
        _exeStack.getItems().clear();
        if(_prgStates.getItems().size() == 0) return;
        if(!_prgStates.getItems().contains(_selectedId)){
            _selectedId = -1;
        }
        if(_selectedId == -1){
            _selectedId = _prgStates.getItems().get(0);
        }
        ExeStack exeStack = (ExeStack) _currentController.getPrg(_selectedId).getExeStack();
        for(IStmt stmt: exeStack){
            _exeStack.getItems().add(stmt.toString());
        }
    }

    private void populateAll(){
        setNrPrg();
        populatePrgStates();
        populateExeStack();
        populateSymTable();
        populateFileTable();
        populateOut();
        populateHeap();
    }

    @Override
    public void start(Stage stage) throws IOException {
        List<IStmt> programs = new ArrayList<>();

        programs.add(IStmt.ex1);
        programs.add(IStmt.ex2);
        programs.add(IStmt.ex3);
        programs.add(IStmt.ex4);
        programs.add(IStmt.ex5);
        programs.add(IStmt.ex6);
        programs.add(IStmt.ex7);
        programs.add(IStmt.ex8);
        programs.add(IStmt.ex9);
        programs.add(IStmt.ex10);
        programs.add(IStmt.ex11);

        ObservableList<String> contents = FXCollections.observableArrayList();
        for(var program: programs){
            contents.add(program.toString());
        }

        ListView examples = new ListView(contents);
        examples.setPrefWidth(contents.stream().map(str -> str.length()*7).max(Integer::compare).orElse(240));

        HBox examplesWrapper = new HBox(examples);

        Scene examplesScene = new Scene(examplesWrapper, examples.getPrefWidth(), 640);

        stage.setTitle("Programs");
        stage.setScene(examplesScene);
        stage.show();

        VBox runWrapper = new VBox();

        _numberOfPrg = new TextField("No program running!");
        _numberOfPrg.setEditable(false);
        _numberOfPrg.setMouseTransparent(true);
        _numberOfPrg.setFocusTraversable(false);
        _numberOfPrg.setAlignment(Pos.CENTER);

        _heap = new TableView();
        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn heapVal = new TableColumn("Value");
        heapVal.setCellValueFactory(new PropertyValueFactory<>("value"));

        _heap.getColumns().add(address);
        _heap.getColumns().add(heapVal);
        _heap.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        _out = new ListView();

        _fileTable = new ListView();

        HBox sharedStuff = new HBox();
        sharedStuff.getChildren().add(_heap);
        sharedStuff.getChildren().add(_out);
        sharedStuff.getChildren().add(_fileTable);

        _prgStates = new ListView();
        _prgStates.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                // Your action here
                if(newValue == null) {
                    _selectedId = -1;
                }
                else{
                    _selectedId = newValue;
                }
                populateExeStack();
                populateSymTable();
            }
        });

        _symTable = new TableView();
        TableColumn name = new TableColumn("Var Name");
        name.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn stackVal = new TableColumn("Value");
        stackVal.setCellValueFactory(new PropertyValueFactory<>("value"));

        _symTable.getColumns().add(name);
        _symTable.getColumns().add(stackVal);
        _symTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        _exeStack = new ListView();

        HBox unsharedStuff = new HBox();
        unsharedStuff.getChildren().add(_prgStates);
        unsharedStuff.getChildren().add(_symTable);
        unsharedStuff.getChildren().add(_exeStack);

        Button nextStep = new Button("Start Program");
        nextStep.setMinWidth(250);
        nextStep.setMinHeight(100);

        VBox controls = new VBox();
        controls.getChildren().add(nextStep);
        controls.setAlignment(Pos.CENTER);

        nextStep.setOnAction(whatever -> {
            if(_allDone){
                _currentController = null;
                nextStep.setText("Start Program");
                _selectedId = -1;
                _allDone = false;
                populateAll();
                return;
            }
            if(_currentController == null){
                ObservableList<Integer> indices = examples.getSelectionModel().getSelectedIndices();
                if(indices.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Human Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No program selected!!!");

                    alert.showAndWait();
                }
                else{
                    int index = indices.get(0);
                    PrgState start;
                    try {
                        start = new PrgState(programs.get(index));
                        Repository repo = new Repository(start, "outputLog.txt");
                        _currentController = new Interpreter(repo);
                        _currentController.createExecutor();
                        _selectedId = -1;

                        nextStep.setText("Next Step");
                        _allDone = false;
                        populateAll();
                    }
                    catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Human Error");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());

                        alert.showAndWait();
                    }
                }
            }
            else{
                try {
                    _allDone = !_currentController.trueOneStep();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Human Error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());

                    alert.showAndWait();

                    _currentController = null;
                    _allDone = false;
                    populateAll();
                }
                populateAll();
                if(_allDone){
                    nextStep.setText("End Program");
                }
            }
        });
        runWrapper.getChildren().add(_numberOfPrg);
        runWrapper.getChildren().add(sharedStuff);
        runWrapper.getChildren().add(unsharedStuff);
        runWrapper.getChildren().add(controls);

        Stage runStage = new Stage();
        Scene runScene = new Scene(runWrapper, sharedStuff.getPrefWidth(), 840);

        runStage.setTitle("Run Program");
        runStage.setScene(runScene);
        runStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}