package rubensandreoli.imageorganizer.io;

public interface Progressable {
    
    void setMaximum(int max);
    void setValue(int value);
    void reset();
    void increase();
    void done();
    
}
