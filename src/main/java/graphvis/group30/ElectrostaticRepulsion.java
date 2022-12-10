package graphvis.group30;

public class ElectrostaticRepulsion extends Vector{
    static final double charge = 450000;
    public ElectrostaticRepulsion(double x, double y) {
        super(x, y);
    }

    public static ElectrostaticRepulsion getElectrostaticBetween(SimBody v1, SimBody v2) {
        Vector diff = Vector.subtract(v1.getPosition(), v2.getPosition());
        ElectrostaticRepulsion ef;
        if (diff.getLength() <= 50) {
            Vector direction = new Vector(1, 1);
            if (diff.getLength() == 0) {
                direction = Vector.normalize(new Vector(Math.random(), Math.random()));
            } else {
                direction = Vector.normalize(diff);
            }
            Vector eVector = Vector.div(Vector.mult(direction, charge), 50*50);
            ef = new ElectrostaticRepulsion(eVector.x, eVector.y);
        } else {
            Vector direction = Vector.normalize(diff);
            Vector eVector = Vector.div(Vector.mult(direction, charge), diff.getLength()*diff.getLength());
            ef = new ElectrostaticRepulsion(eVector.x, eVector.y);
        }
        if (Double.isNaN(ef.x)) {
            System.out.println("nan");
        }
        return ef;
    }
    
}
