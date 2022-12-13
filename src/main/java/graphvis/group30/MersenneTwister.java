package graphvis.group30;

public class MersenneTwister extends NextRandom{
    private final int WORD = 32;
    private final int N = 624;
    private final int MIDDLE = 397;
    private final int A = 0x9908B0DF;
    private final int U = 11;
    private final int S = 7;
    private final int T = 37;
    private final int L = 18;
    private final int B = 0x9D2C5680;
    private final int C = 0xEFC60000;
    private final int F = 0x6C078965;
    private final int UPPER_MASK = 0x80000000;
    private final int LOWER_MASK = 0x7FFFFFFF;

    private int[] mt = new int[N]; //to keep current condition
    private int index; //to check current position in array

    public MersenneTwister() { //constructor
        super.seed = generateSeed();
        setSeed(super.seed);
    }

    public MersenneTwister(long seed) { //constructor
        setSeed(seed);
    }

    @Override
    protected long generateSeed() {
       return System.currentTimeMillis() ^ System.nanoTime(); //use system time to generate first seed
    }

    @Override
    protected void setSeed(long seed) {
        index = N;
        mt[0] = (int) seed;
        for (int i = 1; i < N; i++) {
            mt[i] = (F * (mt[i - 1] ^ (mt[i - 1] >> (WORD - 2))) + i); //XOR shifts
        }
    }

    @Override
    protected long next() {
        if (index >= N) {
            twist();
        }

        long y = mt[index++];
        y ^= (y >> U);
        y ^= (y << S) & B;
        y ^= (y << T) & C;
        y ^= (y >> L);

        return y;
    }

    private void twist() {
        for (int i = 0; i < N; i++) {
            int x = (mt[i] & UPPER_MASK) + (mt[(i+1) % N] & LOWER_MASK);
            int x_shifted = x >> 1;
            if (x % 2 != 0) {
                x_shifted ^= A;
            }
            mt[i] = mt[(i + MIDDLE) % N] ^ x_shifted;
        }
        index = 0;
    } 
protected int next(int bits){
    if (bits < 0 || bits > 32) {
        throw new IllegalArgumentException("bits parameter out of range (0 - 32");
    }
    return (int) next() >>> (32 - bits);
}

@Override
public int nextInt() {
    return next(32);
}

@Override
public int nextInt(int n) {
    if (n <= 0) {
        throw new IllegalArgumentException("Range value must be non-zero");
    }
    return next(31) % n;
}

@Override
public int nextInt(int min, int max) {
    if (max <= min) {
        throw new IllegalArgumentException("min must be smaller than max.");
    }
    return nextInt((max - min) + 1) + min;
}
}
