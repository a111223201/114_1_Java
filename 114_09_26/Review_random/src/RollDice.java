import java.security.SecureRandom;

public class RollDice {
    public static void main(String[] args) {
        SecureRandom randomNumbers = new SecureRandom();

        int[] frequency = new int[7]; // Array to hold frequency of sums (1-6)

        for (int i = 0; i < 60000000; i++) {
            int face = 1 + randomNumbers.nextInt(6); // Roll first die (1-6)
            frequency[face]++;
        }

        System.out.printf("%s%10s%n", "Face", "Frequency");
        for (int face = 1; face < frequency.length; face++) {
            System.out.printf("%4d%10d%n", face, frequency[face]);
        }
    }
}
