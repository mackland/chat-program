package view;

public interface IObserver {
    public void notify(String a, int b);
    
    public boolean notify(String a);
    
    public void notify(String a, String col);
}
