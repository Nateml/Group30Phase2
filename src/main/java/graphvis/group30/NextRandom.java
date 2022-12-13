package graphvis.group30;

public abstract class NextRandom implements MersenneTwisterRandom {
    protected long seed;


    protected abstract void setSeed(long seed);
    protected abstract long generateSeed();
    protected abstract long next();

    public class RandomGenerator {

        public NextRandom getInstance(Long seed) {

            if (seed != null) {
                return new MersenneTwister(seed);
            }
            return new MersenneTwister();
        }

        public NextRandom getInstance() {
            return getInstance(null);
        }
    }
    
}
