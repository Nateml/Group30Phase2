package graphvis.group30;

public class SimBody {
    private Vector position, velocity, acceleration, netForce;

    public SimBody() {
        position = new Vector(0, 0);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        netForce = new Vector(0,0);
    }

    public void setPosition(Vector pos) {
        position = pos;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void checkEdges(double width, double height) {
        double margin = 0.1;
        if (position.x > width*(1-margin)) {
            position.x = width*(1-margin);
            velocity.x *= -1;
        } else if (position.x < width*margin) {
            position.x = width*margin;
            velocity.x *= -1;
        }

        if (position.y > height*(1-margin)) {
            position.y = height*(1-margin);
            velocity.y *= -1;
        } else if (position.y < height*margin) {
            position.y = height*margin;
            velocity.y *= -1;
        }
    }

    public void addForce(Vector f) {
        acceleration.add(f);
    }

    public Vector getNetForce() {
        return netForce;
    }

    public void update(double temp) {
        netForce = new Vector(acceleration.x, acceleration.y);
        velocity.add(acceleration);
        velocity.mult(temp);
        if (velocity.getLength() < 0.05) velocity.mult(0);
        else {
            double c = 0.2;
            Vector resistance = new Vector(velocity.x, velocity.y);
            resistance.mult(-1);
            resistance.mult(c);
            velocity.add(resistance);
        }
        position.add(velocity);
        acceleration.mult(0);
    }

}
