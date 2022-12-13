package graphvis.group30;

public class SimBody {
    private Vector position, velocity, acceleration, netForce;

    /**
     * Creates a SimBody object.
     * Initializes the simbody's vectors.
     */
    public SimBody() {
        position = new Vector(0, 0);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        netForce = new Vector(0,0);
    }

    /**
     * Sets the position of the simbody.
     * @param pos
     */
    public void setPosition(Vector pos) {
        position = pos;
    }

    /**
     * 
     * @return the position vector of the simbody.
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * 
     * @return the velocity vector of the simbody.
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * 
     * @return the acceleration vector of the simbody.
     */
    public Vector getAcceleration() {
        return acceleration;
    }

    /**
     * Adjusts the velocity of the simbody to make sure that the position of the simbody is within the bounds of the specified width and height.
     * width*0.1 < position.x < width*(1-margin)
     * height*0.1 < position.y < height*(1-margin)
     * @param width the width of the simulation bounds
     * @param height the height of the simulation bounds
     */
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

    /**
     * Adds a force vector to the acceleration of the simbody.
     * @param f
     */
    public void addForce(Vector f) {
        if (Double.isNaN(f.x) || Double.isNaN(f.y)) {
            System.out.println("is NaN");
        }
        acceleration.add(f);
    }

    /**
     * Returns the net force acting on the simbody.
     * @return
     */
    public Vector getNetForce() {
        return netForce;
    }

    /**
     * Updates the position of the simbody. 
     * @param temp scales the velocity of the simbody
     */
    public void update(double temp) {
        netForce = new Vector(acceleration.x, acceleration.y);
        //Vector oldVelocity = new Vector(velocity.x, velocity.y);
        velocity.add(acceleration);
        velocity.mult(temp); // scale the velocity

        if (velocity.getLength() < 0.05) velocity.mult(0); // freeze simbody if its moving extremely slowly to prevent vertices from drifting indefinitely.
        else {
            // add a form of "air resistance" to the simbody
            double c = 0.2;
            Vector resistance = new Vector(velocity.x, velocity.y);
            resistance.mult(-1);
            resistance.mult(c);
            velocity.add(resistance);
        }

        position.add(velocity); // update position of the simbody

        acceleration.mult(0); // reset acceleration
    }

}
