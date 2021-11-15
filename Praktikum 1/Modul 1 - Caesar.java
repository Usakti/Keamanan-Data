import java.io.*;
public class Caesar {
    private Reader in;
    private int key;
    public Caesar(int k) {
        in = new InputStreamReader(System.in);
        key = k;
    }
    public void encrypt() { translate(key); }
    public void decrypt() { translate(-key); }

    private void translate(int k) {
        char c;
        while ((byte)(c = getNextChar()) != -1) {
            if (Character.isLowerCase(c)) {
                c = rotate(c, k);
            }
            System.out.print(c);
        }
    }
    public char getNextChar() {
        char ch = ' ';
        try {
            ch = (char)in.read();
        } catch (IOException e) {
            System.out.println("Exception reading character");
        }
        return ch;
    }
    public char rotate(char c, int key) {
        String s = "abcdefghijklmnopqrstuvwxyz";
        int i = 0;
        while (i < 26) {
            if (c == s.charAt(i)) return s.charAt((i + key + 26)%26);
            i++;
        }
        return c;
    }
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Caesar (-d | -e) key");
            System.exit(1);
        }
        Caesar cipher = new Caesar(Integer.parseInt(args[1]));
        if (args[0].equals("-e")) cipher.encrypt();
        else if (args[0].equals("-d")) cipher.decrypt();
        else {
            System.out.println("Usage: java Caesar (-d | -e) key");
            System.exit(1);
        }
    }
}
