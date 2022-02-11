package View;

public abstract class Command {
    private String _key, _description;
    public Command(String key, String description){
        _key = key;
        _description = description;
    }
    public abstract void execute();
    public String getKey(){ return _key; }
    public String getDescription(){ return _description; }
}
