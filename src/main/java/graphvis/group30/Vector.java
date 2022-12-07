package graphvis.group30;

public class Vector {
    double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mult(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public static Vector normalize(Vector v) {
        Vector newV = new Vector(v.x, v.y);
        newV.normalize();
        return newV;
    }

    public static Vector mult(Vector v1, double scalar) {
        Vector newVector = new Vector(v1.x*scalar, v1.y*scalar);
        return newVector;
    }

    public static Vector div(Vector v1, double scalar) {
        Vector newVector = new Vector(v1.x/scalar, v1.y/scalar);
        return newVector;
    }

    public static Vector add(Vector v1, Vector v2) {
        Vector newVector = new Vector(v1.x+v2.x, v1.y+v2.y);
        return newVector;
    }

    public static Vector subtract(Vector v1, Vector v2) {
        Vector newVector = new Vector(v1.x-v2.x, v1.y-v2.y);
        return newVector;
    }

    public void normalize() {
        double length = this.getLength();
        x = x/length;
        y = y/length;
    } 
    
    public double getLength() {
        double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y,2));
        return length;
    }

}
