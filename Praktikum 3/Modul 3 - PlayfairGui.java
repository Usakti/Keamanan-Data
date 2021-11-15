import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A GUI that use the Playfair class to encode/decode messages
 * The code is almost the same as the VigenereGui class but the class to encode/decode
 * is Playfair rather than Vigenere
 * The Playfair Grid is also displayed
 */
public class PlayfairGui extends JFrame {

    private static final long serialVersionUID = 1L;

    // the PlayFair class to encode/decode
    private Playfair playfair;

    // the JLabel to display the Grid
    private JLabel[][] gridLabel;

    // the key to use
    private JTextField keyText;
    // The message to encode
    private JTextField clearTextIn;
    private JLabel encodedTextOut;
    // the message to decode
    private JTextField codedTextIn;
    private JLabel decodedTextOut;

    /*
     *
     * Constructor
     */

    PlayfairGui() {

        super("Playfair encoding/decoding");

        // we will use a BorderLayout to store our GUI component
        setLayout(new BorderLayout());

        // to store the key, coded text input output fields
        JPanel textPanel = new JPanel(new GridLayout(15, 1));

        // the Key
        textPanel.add(new JLabel());
        textPanel.add(createCenteredLabel("Enter the Key here:"));
        textPanel.add(createCenteredLabel("(without a key no translation would happen)"));
        keyText = new JTextField(50);

        // with a documentListener to be informed when the Key changes
        keyText.getDocument().addDocumentListener(new KeyListener());
        textPanel.add(keyText);
        // and a Gap
        textPanel.add(new JLabel());

        // text to encode header
        textPanel.add(createCenteredLabel("Enter text to encode here"));
        // the JTextField to enter the text to encode
        clearTextIn = new JTextField(50);
        // with a documentListener to be informed when the text changes
        clearTextIn.getDocument().addDocumentListener(new ClearListener());
        textPanel.add(clearTextIn);
        // the header for the coded text
        textPanel.add(createCenteredLabel("Coded Text"));
        // and the JLabel to display it
        encodedTextOut = createOutputLabel();
        textPanel.add(encodedTextOut);

        // same thing for the string to decode
        textPanel.add(new JLabel());
        textPanel.add(createCenteredLabel("Enter text to decode here"));

        // the JTextField to enter the coded text with its listener
        codedTextIn = new JTextField(50);
        codedTextIn.getDocument().addDocumentListener(new CodedListener());
        textPanel.add(codedTextIn);
        textPanel.add(createCenteredLabel("Decoded text"));
        decodedTextOut = createOutputLabel();
        textPanel.add(decodedTextOut);
        textPanel.add(new JLabel());

        // the gridPanel shouldn't take the whole width (just for esthetic reasons)
        // so we will put it into a GridLayout(1,3) and use only the center square
        JPanel lowerPanel = new JPanel(new GridLayout(1,3));
        lowerPanel.add(new JLabel("")); // fill the right part
        lowerPanel.add(buildLabelPanel()); // add a new GridPanel
        lowerPanel.add(new JLabel("")); // fill the right part
        // add the main Panel to the frame
        add(textPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
        add(new JLabel(" "), BorderLayout.WEST);
        add(new JLabel(" "), BorderLayout.EAST);

        // init the key to ""
        playfair = new Playfair("");

        // standard operation to show the JFrame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocation(30, 30);
        pack();
        setVisible(true);
    }

    /**
     * A method to create a JLabel with foreground color Blue and with text centered
     */
    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.BLUE);
        return label;
    }

    /**
     * A method to create the output labels with White background and a border
     */
    private JLabel createOutputLabel() {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        return label;
    }

    /**
     * To update the key string
     */
    private void updateKeyString() {

        playfair.setKey(keyText.getText());
        // update the Grid Label displaying the Grid
        char[][] grid = playfair.getGrid();
        // to convert char into String
        String text;
        // loop through the Grid
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                // convert I into I/J
                if(grid[i][j] == 'I')
                    text = "I/J";
                else // else just convert to String
                    text = "" + grid[i][j];
                // update Jlabel text
                gridLabel[i][j].setText(text);
            }
        }

        // and update the coded/decoded Strings
        updateCodedString();
        updateDecodedString();
    }

    /**
     * To update coded string
     */
    private void updateCodedString() {
        // get the text from the JTextField
        String line = clearTextIn.getText();
        // set the label containing the text out with the new conversion
        encodedTextOut.setText(playfair.encode(line));
    }

    /**
     * To update decoded string
     */
    private void updateDecodedString() {
        // get the text from the JTextField
        String line = codedTextIn.getText();
        // set the label containing the decoded string
        decodedTextOut.setText(playfair.decode(line));
    }

    /**
     * To build the JPanel that will display the Grid used to encode/decode
     */

    private JPanel buildLabelPanel() {
        // build a GridLayout of 6 rows (one for the header and the five rows of the grid)

        JPanel panel = new JPanel(new GridLayout(6, 1, 3, 3));
        // the header
        panel.add(createCenteredLabel("Translation Grid"));

        // inti the label array
        gridLabel = new JLabel[5][5];
        // the five rows of five labels for the letter

        for(int i = 0; i < 5; i++)
        {
            // a GridLayout for the 5 columns of the grid
            JPanel p = new JPanel(new GridLayout(1, 5, 3, 3));
            // add the row of JLabel
            for(int j = 0; j < 5; j++)
            {
                gridLabel[i][j] = new JLabel();
                gridLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                gridLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // add the label to the row panel
                p.add(gridLabel[i][j]);
            }
            // add the row to the panel
            panel.add(p);
        }

        // return the new panel
        return panel;
    }
    /**
     * To start the GUI
     */
    public static void main(String[] args) {
        new PlayfairGui();
    }

    /**
     * A listener to be informed whenever the JTextField for the coded string is changed
     */
    class CodedListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            updateDecodedString();
        }
        @Override
        public void insertUpdate(DocumentEvent arg0) {
            updateDecodedString();
        }
        @Override
        public void removeUpdate(DocumentEvent arg0) {

            updateDecodedString();
        }

    }

    /**
     * A listener to be informed whenever the JTextField of the clear text is changed
     */
    class ClearListener implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent arg0) {
            updateCodedString();
        }
        @Override
        public void insertUpdate(DocumentEvent arg0) {
            updateCodedString();
        }
        @Override
        public void removeUpdate(DocumentEvent arg0) {
            updateCodedString();
        }

    }

    /**
     * A listener to be informed whenever the JTextField of the key text is changed
     */
    class KeyListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            updateKeyString();
        }
        @Override
        public void insertUpdate(DocumentEvent arg0) {
            updateKeyString();
        }
        @Override
        public void removeUpdate(DocumentEvent arg0) {
            updateKeyString();
        }
    }
}
