import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;

public class Transposisi {
    public static void main (String[]args) {
        new MenuGUI();
    }
}

class MenuGUI extends JFrame implements ActionListener {
    JTextArea io = new JTextArea();
    JButton btnEncry = new JButton("Encrypt");
    JButton btnExit = new JButton("Exit");
    JButton btnDecry = new JButton("Decrypt");
    // JButton btnClear = new JButton("Clear");

    MenuGUI() {
        super("Transposisi");
        getContentPane().setLayout(new BorderLayout());
        setSize(350, 300);
        setLocation(200, 250);
        io.setLineWrap(true);
        io.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(io);
        btnEncry.addActionListener(this);
        btnEncry.setActionCommand("Encry");
        btnExit.addActionListener(this);
        btnExit.setActionCommand("Exit");
        btnDecry.addActionListener(this);
        btnDecry.setActionCommand("Decry");
        JPanel pnlBawah = new JPanel();
        pnlBawah.setLayout(new FlowLayout());
        pnlBawah.add(btnEncry);
        pnlBawah.add(btnDecry);
        // pnlBawah.add(btnClear);
        pnlBawah.add(btnExit);
        getContentPane().add(jsp, BorderLayout.CENTER);
        getContentPane().add(pnlBawah, BorderLayout.SOUTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static int[] arrangeKey(String key) {
        // arrange position of grid
        String[] keys = key.split("");
        Arrays.sort(keys);
        int[] num = new int[key.length()];
        for (int x = 0; x < keys.length; x++) {
            for (int y = 0; y < key.length(); y++) {
                if (keys[x].equals(key.charAt(y) + "")) {
                    num[y] = x;
                    break;
                }
            }
        }
        return num;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Encry")) {
            String plainTeks = io.getText();
            String cipherTeks = "";
            double panjang = plainTeks.length();
            int ukuran_matrik = (int)Math.ceil(Math.sqrt(panjang));
            char[][]data = new char[ukuran_matrik + 1][ukuran_matrik + 1];
            int k = 0;
            selesai:
            for (int i = 1; i < data.length; i++) {
                for(int j = 1; j < data[i].length; j++) {
                    if(k >= (int)panjang) {
                        break selesai;
                    }
                    data[i][j] = plainTeks.charAt(k);
                    k = k + 1;
                }
            }
            for (int i = 0; i < data.length; i++) {
                for(int j = 0; j < data[i].length; j++) {
                    System.out.print(data[i][j] + " ");
                }
                System.out.println();
            }
            String kunci = JOptionPane.showInputDialog(this, "Key(" + ukuran_matrik + ")",
                    "Transposition", JOptionPane.QUESTION_MESSAGE);
            for (int j = 0; j < kunci.length(); j++) {
                int kunci_pisah = Integer.parseInt(kunci.substring(j, j+1));
                for(int i = 1; i < data.length; i++) {
                    if((int)data[i][kunci_pisah] == 0) {
                        continue;
                    }
                    cipherTeks += data[i][kunci_pisah];
                }
            }
            io.setText(cipherTeks);
        } else if (e.getActionCommand().equalsIgnoreCase("Decry")) {
            String text = io.getText();
            double panjang = text.length();
            int ukuran_matrik = (int)Math.ceil(Math.sqrt(panjang));
            String key = JOptionPane.showInputDialog(this, "Kata Kunci (Maksimal " +
                    ukuran_matrik + " Digit)", "Transposition", JOptionPane.QUESTION_MESSAGE);
            int[] arrange = arrangeKey(key);
            int lenkey = arrange.length;
            int lentext = text.length();
            int row = (int) Math.ceil((double) lentext / lenkey);
            String regex = "(?<=\\G.{" + row + "})";
            String[] get = text.split(regex);

            char[][] grid = new char[row][lenkey];

            for(int x = 0; x < lenkey; x++) {
                for(int y = 0; y < lenkey; y++) {
                    if(arrange[x] == y) {
                        for(int z = 0; z < row; z++) {
                            grid[z][y] = get[arrange[y]].charAt(z);
                        }
                    }
                }
            }
            String dec = "";
            for(int x = 0; x < row; x++) {
                for(int y = 0; y < lenkey; y++) {
                    dec = dec + grid[x][y];
                }
            }
            io.setText(dec);
        } else {
            this.dispose();
            System.exit(0);
        }
    }
}
