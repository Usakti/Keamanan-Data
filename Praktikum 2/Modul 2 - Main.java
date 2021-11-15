import java.util.Scanner;
public class Main {
    private final static String[] CHARS = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z"
    };
    private final static String REGEX = "[^A-Za-z0-9]";
    private static String[][] fillChars(String[][] temp) {
        int k = 1;
        for(int i = 0; i < temp.length; i++) {
            for(int j = 0; j < temp[i].length; j++) {
                temp[i][j] = CHARS[(i + k) % 26];
                k++;
            }
        }
        return temp;
    }
    private static void display(String[][] temp) {
        for(int i = 0; i < temp.length; i++) {
            System.out.print((i + 1) + "\t");
            for(int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {
        String[][] temp = new String[26][26];
        String key, hash;
        temp = fillChars(temp);
        display(temp);
        Scanner scanner = new Scanner(System.in);
        System.out.print("please enter keyword:\t");
        hash = scanner.nextLine().toLowerCase();
        System.out.print("please enter text:\t");
        key = scanner.nextLine().toLowerCase();
        key = key.replaceAll(REGEX, "");
        hash = hash.replaceAll(REGEX, "");
        Vigene vigene = new Vigene(key);
        hash = vigene.rebuildHash(hash);
        String result = vigene.crypt(temp, hash);
        System.out.print("enciphered message:\t" + result + "\n");
    }
}
