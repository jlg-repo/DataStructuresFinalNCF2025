public class Randomizer {
    private int p;
    private int q;
    private int n;
    private int state;

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public Randomizer(int seed, int p, int q) {
        this.p = p;
        this.q = q;
        if (!isPrime(p) || !isPrime(q) || p % 4 != 3 || q % 4 != 3 || p == q) {
            throw new IllegalArgumentException("p and q must be a blum prime.");
        }
        this.n = p * q;
        if (seed <= 0 || seed >= this.n) {
            seed %= this.n;
            if (seed < 0) {
                seed += this.n;
            }
        }

        if (seed % p == 0 || seed % q == 0) {
            ++seed;
        }

        long sq = (long)seed * (long)seed;
        this.state = (int)(sq % (long)this.n);
    }

    public int nextBit() {
        long sq = (long)this.state * (long)this.state;
        this.state = (int)(sq % (long)this.n);
        return this.state % 2;
    }

    public int nextInt(int max) {
        int result = 0;

        for(int i = 0; i < 64; ++i) {
            result = result << 1 | this.nextBit();
        }

        return Math.abs(result) % max;
    }
}
