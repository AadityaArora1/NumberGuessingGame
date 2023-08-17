import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends Frame implements ActionListener {
    private Label titleLabel, instructionLabel, resultLabel, roundLabel, attemptsLabel, totalScoreLabel;
    private TextField guessField;
    private Button guessButton;
    private int secretNumber;
    private int maxAttempts = 5;
    private int remainingAttempts;
    private int rounds = 3;
    private int currentRound = 1;
    private int totalScore = 0;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(600, 700);
        setBackground(new Color(255, 240, 220));
        setLayout(new BorderLayout());

        titleLabel = new Label("Number Guessing Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignment(Label.CENTER);

        instructionLabel = new Label("Guess a number between 1 and 100:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        guessField = new TextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 14));
        guessField.setPreferredSize(new Dimension(100, 25));

        guessButton = new Button("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.addActionListener(this);

        resultLabel = new Label("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setAlignment(Label.CENTER);

        roundLabel = new Label("Round: 1/" + rounds);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        attemptsLabel = new Label("Attempts left: " + maxAttempts);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        totalScoreLabel = new Label("Total Score: " + totalScore);
        totalScoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(instructionLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        Panel infoPanel = new Panel(new GridLayout(6, 1));
        infoPanel.add(resultLabel);
        infoPanel.add(roundLabel);
        infoPanel.add(attemptsLabel);
        infoPanel.add(totalScoreLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        secretNumber = generateRandomNumber();
        remainingAttempts = maxAttempts;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        int playerGuess = Integer.parseInt(guessField.getText());

        if (playerGuess < secretNumber) {
            resultLabel.setText("Too low! Try again.");
            totalScoreLabel.setText("Total Score: " + totalScore); // Update total score label
            guessField.setText(""); // Clear the guess field
        } else if (playerGuess > secretNumber) {
            resultLabel.setText("Too high! Try again.");
        } else {
            resultLabel.setText("Congratulations! You guessed the number.");
            totalScore += (remainingAttempts - 1); // Corrected total score calculation
        }

        if (remainingAttempts > 1) {
            remainingAttempts--;
            attemptsLabel.setText("Attempts left: " + remainingAttempts);
        } else {
            if (currentRound < rounds) {
                resultLabel.setText("Out of attempts! The number was " + secretNumber + ".");
                currentRound++;
                secretNumber = generateRandomNumber();
                remainingAttempts = maxAttempts;
                roundLabel.setText("Round: " + currentRound + "/" + rounds);
                attemptsLabel.setText("Attempts left: " + remainingAttempts);
                guessField.setText(""); // Clear the guess field
            } else {
                guessField.setEnabled(false);
                guessButton.setEnabled(false);
                attemptsLabel.setText("Attempts left: 0");
                secretNumber = generateRandomNumber(); // Display correct number in last round
                resultLabel.setText("Out of attempts! The number was " + secretNumber + 
                                    "\nGame over! Your total score is: " + totalScore);
            }
        }
        
        totalScoreLabel.setText("Total Score: " + totalScore); // Update total score label
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.setVisible(true);
    }
}
