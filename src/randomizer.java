public class randomizer {

    private int n;
    private int state;

    public randomizer(int seed) {
        int p = 383;
        int q = 503;
        n = p * q;

        if (seed <= 0 || seed >= n) {
            seed = seed % n;
            if (seed < 0) {
                seed += n;
            }
        }
        if (seed % p == 0 || seed % q == 0) {
            seed += 1;
        }

        long sq = (long) seed * seed;
        state = (int) (sq % n);
    }

    public int nextBit() {
        long sq = (long) state * state;
        state = (int) (sq % n);
        return state % 2;
    }

    public int nextInt(int max) {
        int result = 0;
        for (int i = 0; i < 128; i++) {
            result = (result << 1) | nextBit();
        }
        return Math.abs(result) % max;
    }
}

