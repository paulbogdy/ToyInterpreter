package View;

import Controller.Interpreter;

public class RunExample extends Command {
    private Interpreter _itr;
    public RunExample(String key, String desc, Interpreter itr){
        super(key, desc);
        _itr = itr;
    }

    @Override
    public void execute(){
        try{
            _itr.allSteps();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}