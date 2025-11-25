public class Main {
    public static void main(String[] args) {
        randomizer randomizer = new randomizer(424234234);

        System.out.println("A random number in the range of 0-500:");
        for (int i = 0; i < 10; i++) {
            System.out.println(randomizer.nextInt(500));
        }
    }
}