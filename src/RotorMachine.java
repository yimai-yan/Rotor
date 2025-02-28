import java.util.*;

public class RotorMachine {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int ALPHABET_SIZE = ALPHABET.length();
    private static final String EXIT_COMMAND = "exit";
    private static final String STATUS_COMMAND = "status";
    private static Map<Character, Character> innerCylinder = new HashMap<>();
    private static Map<Character, Character> outerCylinder = new HashMap<>();
    private static int innerRotation = 0;
    private static int outerRotation = 0;

    public static void main(String[] args) {
        initializeCylinders();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter text to encrypt (or type 'exit' to quit): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals(EXIT_COMMAND)) {
                System.out.println("Exiting program...");
                break;
            }

            if (input.equals(STATUS_COMMAND)) {
                printCylinderState();
                continue;
            }

            String encryptedText = encryptText(input);
            System.out.println("Encrypted text: " + encryptedText);
        }

        scanner.close();
    }

    private static void initializeCylinders() {
        List<Character> shuffledAlphabet = new ArrayList<>();
        for (char c : ALPHABET.toCharArray()) {
            shuffledAlphabet.add(c);
        }

        Collections.shuffle(shuffledAlphabet);
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            innerCylinder.put(ALPHABET.charAt(i), shuffledAlphabet.get(i));
        }

        Collections.shuffle(shuffledAlphabet);
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            outerCylinder.put(ALPHABET.charAt(i), shuffledAlphabet.get(i));
        }
    }

    private static String encryptText(String input) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char mappedInner = getMappedCharacter(innerCylinder, c, innerRotation);
                char mappedOuter = getMappedCharacter(outerCylinder, mappedInner, outerRotation);

                System.out.println("Encrypting '" + c + "' -> Inner: '" + mappedInner + "' -> Outer: '" + mappedOuter + "'");
                encryptedText.append(mappedOuter);

                updateRotations();
            } else {
                encryptedText.append(c); // Preserve whitespace, numbers, and special characters
            }
        }

        return encryptedText.toString();
    }

    private static char getMappedCharacter(Map<Character, Character> cylinder, char input, int rotation) {
        int originalIndex = ALPHABET.indexOf(input);
        int rotatedIndex = (originalIndex + rotation) % ALPHABET_SIZE;
        char rotatedChar = ALPHABET.charAt(rotatedIndex);
        return cylinder.get(rotatedChar);
    }

    private static void updateRotations() {
        innerRotation = (innerRotation + 1) % ALPHABET_SIZE;
        if (innerRotation % ALPHABET_SIZE == 0) {
            outerRotation = (outerRotation + 1) % ALPHABET_SIZE;
        }
    }

    private static void printCylinderState() {
        System.out.println("Current Cylinder State:");
        System.out.println("Inner Rotation: " + innerRotation);
        System.out.println("Outer Rotation: " + outerRotation);
        System.out.println("Inner Cylinder Mapping: " + innerCylinder);
        System.out.println("Outer Cylinder Mapping: " + outerCylinder);
    }
}




