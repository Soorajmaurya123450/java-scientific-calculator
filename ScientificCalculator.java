import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScientificCalculator extends JFrame
        implements ActionListener {

    // VARIABLES
    private JTextField display;
    private JTextArea historyArea;

    private ArrayList<String> history =
            new ArrayList<>();

    private double firstNumber = 0;
    private String operator = "";
    private boolean newNumber = true;

    private String mode = "DEG";

    // Colors
    private final Color BACKGROUND = new Color(25, 25, 30);
    private final Color DISPLAY_COLOR = new Color(35, 35, 42);
    private final Color NUMBER_COLOR = new Color(55, 55, 65);
    private final Color FUNCTION_COLOR = new Color(70, 70, 85);
    private final Color OPERATOR_COLOR = new Color(90, 70, 150);
    private final Color EQUAL_COLOR = new Color(45, 130, 90);
    private final Color CLEAR_COLOR = new Color(160, 60, 60);
    private final Color TEXT_COLOR = Color.WHITE;

    // CONSTRUCTOR

    public ScientificCalculator() {

        setTitle("Scientific Calculator");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        setVisible(true);
    }

    // CREATE GUI

    private void createGUI() {

        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout(10, 10));

        // DISPLAY

        display = new JTextField();

        display.setFont(
                new Font("Segoe UI", Font.BOLD, 34));

        display.setHorizontalAlignment(
                JTextField.RIGHT);

        display.setEditable(false);

        display.setForeground(Color.WHITE);
        display.setBackground(DISPLAY_COLOR);

        display.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15));

        add(display, BorderLayout.NORTH);

        // MAIN PANEL

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBackground(BACKGROUND);

        // BUTTON PANEL

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                7, 6, 8, 8));

        buttonPanel.setBackground(BACKGROUND);

        String[] buttons = {

                "MODE", "UNDO", "CLEAR",
                "HISTORY", "CLEAR H", "EXIT",

                "sin", "cos", "tan",
                "sec", "cosec", "√",

                "log", "ln", "1/x",
                "eˣ", "x²", "xʸ",

                "7", "8", "9",
                "÷", "(", ")",

                "4", "5", "6",
                "×", ".", "π",

                "1", "2", "3",
                "−", "Ans", "=",

                "0", "00", "+",
                "DEL", "", ""
        };

        for (String text : buttons) {

            if (text.equals("")) {

                JLabel empty = new JLabel();
                buttonPanel.add(empty);
                continue;
            }

            JButton button =
                    new JButton(text);

            styleButton(button, text);

            button.addActionListener(this);

            buttonPanel.add(button);
        }

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER);

        // HISTORY

        JPanel historyPanel =
                new JPanel(new BorderLayout());

        historyPanel.setBackground(
                new Color(30, 30, 36));

        JLabel historyTitle =
                new JLabel("  CALCULATION HISTORY");

        historyTitle.setFont(
                new Font("Segoe UI",
                        Font.BOLD, 15));

        historyTitle.setForeground(Color.WHITE);

        historyArea =
                new JTextArea();

        historyArea.setEditable(false);

        historyArea.setFont(
                new Font("Consolas",
                        Font.PLAIN, 13));

        historyArea.setForeground(Color.WHITE);

        historyArea.setBackground(
                new Color(35, 35, 42));

        historyArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10));

        JScrollPane scrollPane =
                new JScrollPane(historyArea);

        scrollPane.setPreferredSize(
                new Dimension(250, 0));

        historyPanel.add(
                historyTitle,
                BorderLayout.NORTH);

        historyPanel.add(
                scrollPane,
                BorderLayout.CENTER);

        mainPanel.add(
                historyPanel,
                BorderLayout.EAST);

        add(
                mainPanel,
                BorderLayout.CENTER);
    }

    // BUTTON DESIGN

    private void styleButton(
            JButton button,
            String text) {

        button.setFont(
                new Font("Segoe UI",
                        Font.BOLD, 15));

        button.setForeground(TEXT_COLOR);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 8, 8, 8));

        // Number buttons

        if (text.matches("[0-9]+")
                || text.equals(".")
                || text.equals("π")
                || text.equals("Ans")) {

            button.setBackground(
                    NUMBER_COLOR);
        }

        // Scientific functions

        else if (
                text.equals("sin") ||
                text.equals("cos") ||
                text.equals("tan") ||
                text.equals("sec") ||
                text.equals("cosec") ||
                text.equals("√") ||
                text.equals("log") ||
                text.equals("ln") ||
                text.equals("1/x") ||
                text.equals("eˣ") ||
                text.equals("x²") ||
                text.equals("xʸ")) {

            button.setBackground(
                    FUNCTION_COLOR);
        }

        // Operators
        else if (
                text.equals("+") ||
                text.equals("−") ||
                text.equals("×") ||
                text.equals("÷") ||
                text.equals("=")) {

            button.setBackground(
                    text.equals("=")
                            ? EQUAL_COLOR
                            : OPERATOR_COLOR);
        }

        // Clear
        else if (
                text.equals("CLEAR") ||
                text.equals("CLEAR H")) {

            button.setBackground(
                    CLEAR_COLOR);
        }

        else {

            button.setBackground(
                    new Color(50, 50, 58));
        }
    }

    // ACTION LISTENER

    @Override
    public void actionPerformed(
            ActionEvent e) {

        String command =
                e.getActionCommand();

        switch (command) {

            case "MODE":
                changeMode();
                break;

            case "UNDO":
                undo();
                break;

            case "CLEAR":
                clearDisplay();
                break;

            case "CLEAR H":
                clearHistory();
                break;

            case "HISTORY":
                showHistory();
                break;

            case "EXIT":
                System.exit(0);
                break;

            case "=":
                calculate();
                break;

            case "sin":
            case "cos":
            case "tan":
            case "sec":
            case "cosec":
            case "√":
            case "log":
            case "ln":
            case "1/x":
            case "eˣ":
            case "x²":
                scientificOperation(command);
                break;

            case "xʸ":
                setOperator("^");
                break;

            case "+":
            case "−":
            case "×":
            case "÷":
                setOperator(command);
                break;

            case "DEL":
                deleteLast();
                break;

            case "π":
                enterValue(String.valueOf(Math.PI));
                break;

            case "Ans":
                if (!history.isEmpty()) {
                    String last =
                            history.get(
                                    history.size() - 1);

                    int index =
                            last.lastIndexOf("=");

                    if (index != -1) {

                        enterValue(
                                last.substring(
                                        index + 1)
                                        .trim());
                    }
                }
                break;

            case "(":
            case ")":
                enterValue(command);
                break;

            default:
                enterValue(command);
        }
    }

    // ENTER NUMBER

    private void enterValue(
            String value) {

        if (newNumber) {

            display.setText(value);

            newNumber = false;

        } else {

            display.setText(
                    display.getText() + value);
        }
    }

    // SET OPERATOR

    private void setOperator(
            String op) {

        try {

            firstNumber =
                    Double.parseDouble(
                            display.getText());

            operator = op;

            newNumber = true;

        } catch (Exception e) {

            display.setText("Error");
        }
    }

    // CALCULATE

    private void calculate() {

        try {

            double secondNumber =
                    Double.parseDouble(
                            display.getText());

            double result;

            switch (operator) {

                case "+":
                    result =
                            add(firstNumber,
                                    secondNumber);
                    break;

                case "−":
                    result =
                            subtract(firstNumber,
                                    secondNumber);
                    break;

                case "×":
                    result =
                            multiply(firstNumber,
                                    secondNumber);
                    break;

                case "÷":
                    result =
                            divide(firstNumber,
                                    secondNumber);
                    break;

                case "^":
                    result =
                            power(firstNumber,
                                    secondNumber);
                    break;

                default:
                    return;
            }

            String calculation =
                    format(firstNumber)
                    + " "
                    + operator
                    + " "
                    + format(secondNumber)
                    + " = "
                    + format(result);

            display.setText(
                    format(result));

            addHistory(calculation);

            operator = "";

            newNumber = true;

        } catch (Exception e) {

            display.setText("Error");
        }
    }

    // BASIC MATH METHODS

    private double add(
            double a, double b) {

        return a + b;
    }

    private double subtract(
            double a, double b) {

        return a - b;
    }

    private double multiply(
            double a, double b) {

        return a * b;
    }

    private double divide(
            double a, double b) {

        if (b == 0) {

            throw new ArithmeticException(
                    "Division by zero");
        }

        return a / b;
    }

    private double power(
            double a, double b) {

        return Math.pow(a, b);
    }

    // SCIENTIFIC FUNCTIONS

    private void scientificOperation(
            String function) {

        try {

            double number =
                    Double.parseDouble(
                            display.getText());

            double result;

            switch (function) {

                case "√":

                    if (number < 0)
                        throw new Exception();

                    result =
                            Math.sqrt(number);

                    break;

                case "log":

                    if (number <= 0)
                        throw new Exception();

                    result =
                            Math.log10(number);

                    break;

                case "ln":

                    if (number <= 0)
                        throw new Exception();

                    result =
                            Math.log(number);

                    break;

                case "1/x":

                    if (number == 0)
                        throw new Exception();

                    result = 1 / number;

                    break;

                case "eˣ":

                    result =
                            Math.exp(number);

                    break;

                case "x²":

                    result =
                            Math.pow(number, 2);

                    break;

                case "sin":

                    result =
                            Math.sin(
                                    toRadian(number));

                    break;

                case "cos":

                    result =
                            Math.cos(
                                    toRadian(number));

                    break;

                case "tan":

                    result =
                            Math.tan(
                                    toRadian(number));

                    break;

                case "sec":

                    double cos =
                            Math.cos(
                                    toRadian(number));

                    if (Math.abs(cos)
                            < 0.00000001)

                        throw new Exception();

                    result = 1 / cos;

                    break;

                case "cosec":

                    double sin =
                            Math.sin(
                                    toRadian(number));

                    if (Math.abs(sin)
                            < 0.00000001)

                        throw new Exception();

                    result = 1 / sin;

                    break;

                default:

                    return;
            }

            String calculation =
                    function
                    + "("
                    + format(number)
                    + ") = "
                    + format(result);

            display.setText(
                    format(result));

            addHistory(calculation);

            newNumber = true;

        } catch (Exception e) {

            display.setText("Error");
        }
    }

    // DEG / RAD

    private double toRadian(
            double value) {

        if (mode.equals("DEG"))

            return Math.toRadians(value);

        return value;
    }

    private void changeMode() {

        if (mode.equals("DEG"))

            mode = "RAD";

        else

            mode = "DEG";

        JOptionPane.showMessageDialog(
                this,
                "Angle Mode: " + mode);
    }

    // HISTORY

    private void addHistory(
            String calculation) {

        history.add(calculation);

        historyArea.append(
                calculation + "\n");
    }

    private void showHistory() {

        if (history.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "History is empty!");

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                historyArea,
                "Calculation History",
                JOptionPane.PLAIN_MESSAGE);
    }

    // UNDO

    private void undo() {

        if (history.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nothing to undo!");

            return;
        }

        history.remove(
                history.size() - 1);

        updateHistory();

        clearDisplay();
    }

    // DELETE LAST

    private void deleteLast() {

        String value =
                display.getText();

        if (!value.isEmpty()) {

            display.setText(
                    value.substring(
                            0,
                            value.length() - 1));
        }
    }

    // CLEAR DISPLAY

    private void clearDisplay() {

        display.setText("");

        firstNumber = 0;

        operator = "";

        newNumber = true;
    }

    // CLEAR HISTORY

    private void clearHistory() {

        history.clear();

        historyArea.setText("");

        JOptionPane.showMessageDialog(
                this,
                "History cleared!");
    }

    // UPDATE HISTORY

    private void updateHistory() {

        historyArea.setText("");

        for (String item : history) {

            historyArea.append(
                    item + "\n");
        }
    }

    // FORMAT

    private String format(
            double value) {

        if (value == (long) value)

            return String.valueOf(
                    (long) value);

        return String.format(
                "%.10f", value)
                .replaceAll(
                        "0+$", "")
                .replaceAll(
                        "\\.$", "");
    }

    // MAIN

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> new ScientificCalculator());
    }
}