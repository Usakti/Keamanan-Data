import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Playfair {

    private char[][] grid;

    public Playfair(String key) {
        grid = new char[5][5];
        setKey(key);
    }

    public void setKey(String key) {
        if (key == null)
            key = "A";
        char[] single = new char[26];
        int nbIn = 0;
        boolean[] done = new boolean[26];
        char[] keyDigit = key.toUpperCase().toCharArray();
        for (char c : keyDigit) {
            if (c < 'A' || c > 'Z')
                continue;
            char actual = c;
            if (actual == 'J')
                actual = 'I';
            int index = actual - 'A';
            if (done[index])
                continue;
            done[index] = true;
            single[nbIn++] = actual;
        }

        if (nbIn == 0) {
            single[nbIn++] = 'A';
            done[0] = true;
        }

        for (char c = 'A'; c <= 'Z'; c++) {

            if (c == 'J')
                continue;

            if (done[c - 'A'])
                continue;

            single[nbIn++] = c;
        }

        nbIn = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = single[nbIn++];
            }
        }
    }

    public String encode(String clear) {

        if (clear == null)
            return "";

        char[] digit = clear.toUpperCase().toCharArray();

        ArrayList<LetterPair> aList = new ArrayList<LetterPair>();

        int index = 0;

        int i = 0;

        LetterPair lp = null;
        while (i < digit.length) {
            if (digit[i] == 'J')
                digit[i] = 'I';

            if (digit[i] < 'A' || digit[i] > 'Z') {
                i++;
                continue;
            }
            if (index == 0) {

                lp = new LetterPair(digit[i++], true);
                aList.add(lp);
                index = 1;
                continue;
            }
            if (lp.left == digit[i]) {
                lp.setRight('X');
            } else {

                lp.setRight(digit[i++]);
            }

            index = 0;
        }

        if (index == 1)
            lp.setRight('X');

        return aListToString(aList);
    }

    public String decode(String coded) {

        if (coded == null) {
            return "";
        }

        char[] digit = coded.toUpperCase().toCharArray();

        StringBuilder sb = new StringBuilder(digit.length);
        for (int i = 0; i < digit.length; i++) {

            if (digit[i] < 'A' || digit[i] > 'Z')
                continue;

            if (digit[i] == 'J')
                digit[i] = 'I';

            sb.append(digit[i]);
        }

        if (sb.length() % 2 != 0)
            return "--- Invalid coded message ---";

        ArrayList<LetterPair> aList = new ArrayList<LetterPair>();

        digit = sb.toString().toCharArray();

        for (int i = 0; i < digit.length; i += 2) {

            LetterPair lp = new LetterPair(digit[i], false);
            lp.setRight(digit[i + 1]);
            aList.add(lp);
        }

        return aListToString(aList).toLowerCase();
    }

    private String aListToString(ArrayList<LetterPair> aList) {

        if (aList.size() == 0)
            return "";

        StringBuilder sb = new StringBuilder();

        sb.append(aList.get(0).getPair());

        for (int i = 1; i < aList.size(); i++) {
            sb.append('-');
            sb.append(aList.get(i).getPair());
        }
        return sb.toString();

    }

    public void dumpGrid() {
        System.out.println("--- GRID ---");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(" " + grid[i][j]);
            }
            System.out.println();
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    /**
     * To unit test our class
     */
    public static void main(String[] args) {

        Playfair pf = new Playfair("123456");
        System.out.println("Grid that should start with \"A\"");
        pf.dumpGrid();
        pf = new Playfair("DreamInCode");
        System.out.print("Grid that should start with \"DreamInCode\"");
        System.out.println("  (Note: \"D\" and \"E\" won't be repeated)");
        pf.dumpGrid();

        String clear = "secret message two";
        System.out.println("Original: " + clear);
        String coded = pf.encode(clear);
        System.out.println("   Coded: " + coded);
        System.out.println(" Decoded: " + pf.decode(coded));

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the key: ");

        String key = scan.nextLine();
        Playfair userPlayfair = new Playfair(key);

        System.out.print("Enter message: ");
        String message = scan.nextLine();
        System.out.println("          Original: " + message);

        coded = userPlayfair.encode(message);
        System.out.println("Encoded message is: " + coded);
        System.out.println("translated back ? : " + userPlayfair.decode(coded));
        scan.close();
    }

    private class LetterPair {

        private char left;

        private Point pLeft, pRight;

        private char[] digit;

        boolean coding;

        private LetterPair(char left, boolean coding) {

            this.left = left;

            this.coding = coding;

            pLeft = findPos(left);

            digit = new char[2];
        }

        private void setRight(char right) {

            pRight = findPos(right);

            if (pLeft.x == pRight.x)
                sameRow();
            else if (pLeft.y == pRight.y)
                sameColumn();
            else
                diffRowCol();

            digit[0] = grid[pLeft.x][pLeft.y];
            digit[1] = grid[pRight.x][pRight.y];
        }

        private void sameRow() {
            if (coding) {
                pLeft.y++;
                pRight.y++;

                pLeft.y %= 5;
                pRight.y %= 5;
            } else {

                pLeft.y--;
                pRight.y--;

                if (pLeft.y < 0)
                    pLeft.y = 4;
                if (pRight.y < 0)
                    pRight.y = 4;
            }

        }

        private void sameColumn() {
            if (coding) {

                pLeft.x++;
                pRight.x++;

                pLeft.x %= 5;
                pRight.x %= 5;
            } else {

                pLeft.x--;
                pRight.x--;

                if (pLeft.x < 0)
                    pLeft.x = 4;
                if (pRight.x < 0)
                    pRight.x = 4;
            }
        }

        private void diffRowCol() {

            int leftColumn = pRight.y;
            int rightColumn = pLeft.y;

            pLeft.y = leftColumn;
            pRight.y = rightColumn;
        }

        private Point findPos(char c) {

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    // if found
                    if (grid[x][y] == c) {
                        return new Point(x, y);
                    }
                }
            }
            // not found ? BUG !!!
            throw new IllegalStateException("Letter " + c
                    + " not found in the Grid");
        }

        private String getPair() {
            return new String(digit);
        }
    }
}
