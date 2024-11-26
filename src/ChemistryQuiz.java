import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ChemistryQuiz extends JFrame implements ActionListener {

    private JLabel questionLabel, timerLabel, feedbackLabel;
    private JButton[] optionButtons;
    private JButton skipButton, close;
    private JProgressBar progressBar;

    private int currentQuestion = 0;
    private int score = 0;
    private Timer questionTimer, feedbackTimer;
    private int timeLeft = 10;

    private String[] questions = {
            "What is the chemical symbol for water?",
            "Which gas is known as laughing gas?",
            "What is the pH of pure water?",
            "What element does 'O' represent on the periodic table?",
            "Which acid is found in lemons?",
            "What is the atomic number of Carbon?",
            "Which gas is used in the process of photosynthesis?",
            "What is the common name for NaCl?",
            "Who is known as the father of modern chemistry?",
            "What is the formula of ammonia?"
    };

    private String[][] options = {
            { "H2O", "HO", "H2", "O2" },
            { "Nitrogen", "Oxygen", "Nitrous Oxide", "Carbon Dioxide" },
            { "7", "5", "0", "14" },
            { "Oxygen", "Gold", "Osmium", "Oganesson" },
            { "Sulfuric Acid", "Citric Acid", "Hydrochloric Acid", "Acetic Acid" },
            { "6", "12", "14", "8" },
            { "Carbon Dioxide", "Oxygen", "Nitrogen", "Hydrogen" },
            { "Sugar", "Salt", "Baking Soda", "Vinegar" },
            { "Antoine Lavoisier", "Dmitri Mendeleev", "John Dalton", "Marie Curie" },
            { "NH3", "CH4", "H2O", "NO2" }
    };

    private int[] answers = { 0, 2, 0, 0, 1, 0, 0, 1, 0, 0 }; 
    String username;

    ChemistryQuiz(String username) {
        this.username = username;

        setTitle("Chemistry Quiz");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(63, 81, 181));

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 700, 50);
        questionLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
        questionLabel.setForeground(Color.WHITE);
        add(questionLabel);

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setBounds(240, 130 + i * 60, 300, 50);
            optionButtons[i].setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
            optionButtons[i].setBackground(new Color(76, 175, 80));
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].addActionListener(this);
            add(optionButtons[i]);
        }

        skipButton = new JButton("Skip");
        skipButton.setBounds(500, 400, 200, 50);
        skipButton.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        skipButton.setBackground(new Color(255, 87, 34));
        skipButton.setForeground(Color.WHITE);
        skipButton.addActionListener(this);
        add(skipButton);

        close = new JButton("Close");
        close.setBounds(75, 400, 200, 50);
        close.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        close.setBackground(new Color(255, 87, 34));
        close.setForeground(Color.WHITE);
        close.addActionListener(this);
        add(close);

        feedbackLabel = new JLabel("", JLabel.CENTER);
        feedbackLabel.setBounds(50, 470, 700, 30);
        feedbackLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        feedbackLabel.setForeground(Color.YELLOW);
        add(feedbackLabel);

        timerLabel = new JLabel("Time Left: 10 sec", JLabel.RIGHT);
        timerLabel.setBounds(600, 20, 180, 30);
        timerLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        timerLabel.setForeground(Color.WHITE);
        add(timerLabel);

        progressBar = new JProgressBar(0, questions.length);
        progressBar.setBounds(50, 520, 700, 20);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        add(progressBar);

        loadQuestion();
        startQuestionTimer();

        setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[currentQuestion][i]);
                optionButtons[i].setEnabled(true);
            }
            feedbackLabel.setText("");
        } else {
            endQuiz();
        }
    }

    private void startQuestionTimer() {
        timeLeft = 10;
        timerLabel.setText("Time Left: 10 sec");
        if (questionTimer != null) {
            questionTimer.stop();
        }
        questionTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + " sec");
            if (timeLeft == 0) {
                questionTimer.stop();
                showFeedback("Time's up!", false);
            }
        });
        questionTimer.start();
    }

    private void endQuiz() {
        if (questionTimer != null) {
            questionTimer.stop();
        }

        int currentScore = getCurrentScore(username);

        if (score > currentScore) {
            updateScore(username, score);
            JOptionPane.showMessageDialog(this,
                    "Quiz Completed! Your Score: " + score + "/" + questions.length + ". Score updated.");
        } else {
            JOptionPane.showMessageDialog(this, "Quiz Completed! Your Score: " + score + "/" + questions.length
                    + ". Your previous score is higher.");
        }

        dispose();
    }

    private int getCurrentScore(String username) {
        int currentScore = 0;
        try {
            Conn c = new Conn();
            String query = "SELECT chem_score FROM quiz_scores WHERE username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                currentScore = rs.getInt("chem_score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentScore;
    }

    private void updateScore(String username, int score) {
        try {
            Conn c = new Conn();
            String query = "UPDATE quiz_scores SET chem_score = " + score + " WHERE username = '" + username + "'";
            int rowsUpdated = c.s.executeUpdate(query);
            if (rowsUpdated > 0) {
                System.out.println("Score updated successfully for " + username);
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showFeedback(String message, boolean proceed) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(Color.YELLOW);
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
        if (questionTimer != null) {
            questionTimer.stop();
        }
    
        feedbackTimer = new Timer(2000, e -> {
            feedbackTimer.stop();
            if (proceed) {
                currentQuestion++;
                progressBar.setValue(currentQuestion);
                loadQuestion();
                startQuestionTimer();
            } else {
                endQuiz();
            }
        });
        feedbackTimer.setRepeats(false);
        feedbackTimer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            endQuiz();
        } else if (e.getSource() == skipButton) {
            showFeedback("Skipped!", true);
        } else {
            for (int i = 0; i < 4; i++) {
                if (e.getSource() == optionButtons[i]) {
                    if (i == answers[currentQuestion]) {
                        score++;
                        showFeedback("Correct!", true);
                    } else {
                        showFeedback("Wrong Answer!", true);
                    }
                    break;
                }
            }
        }
    }
    

    public static void main(String[] args) {
        new ChemistryQuiz("");
    }
}
