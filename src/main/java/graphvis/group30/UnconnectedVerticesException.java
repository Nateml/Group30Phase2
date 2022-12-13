package graphvis.group30;

public class UnconnectedVerticesException extends Exception {
    public UnconnectedVerticesException() {
        super("A graph cannot be created with unconnected vertices");
    }
}
