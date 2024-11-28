import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PhysicsQuiz extends JFrame implements ActionListener {

    private JLabel questionLabel, timerLabel, feedbackLabel;
    private JButton[] optionButtons;
    private JButton skipButton, close;
    private JProgressBar progressBar;

    private int currentQuestion = 0;
    private int score = 0;
    private Timer questionTimer, feedbackTimer;
    private int timeLeft = 10;

    private String[] questions = {
            "What is the speed of light in a vacuum?",
            "What is the SI unit of force?",
            "Who proposed the theory of relativity?",
            "What is the formula for kinetic energy?",
            "What is the unit of electrical resistance?",
            "What is the acceleration due to gravity on Earth?",
            "What is the SI unit of power?",
            "What does a voltmeter measure?",
            "Who discovered the law of universal gravitation?",
            "What is the unit of magnetic flux?"
    };

    private String[][] options = {
            {"3 x 10^8 m/s", "3 x 10^6 m/s", "3 x 10^10 m/s", "3 x 10^5 m/s"},
            {"Joule", "Newton", "Watt", "Pascal"},
            {"Newton", "Einstein", "Galileo", "Bohr"},
            {"1/2 mv", "mv^2", "1/2 mv^2", "mv/2"},
            {"Ohm", "Coulomb", "Henry", "Tesla"},
            {"9.8 m/s^2", "8.9 m/s^2", "10.5 m/s^2", "7.8 m/s^2"},
            {"Joule", "Newton", "Watt", "Ampere"},
            {"Current", "Voltage", "Resistance", "Power"},
            {"Newton", "Galileo", "Kepler", "Cavendish"},
            {"Weber", "Tesla", "Henry", "Coulomb"}
    };

    private int[] answers = {0, 1, 1, 2, 0, 0, 2, 1, 0, 0}; // Index of the correct options

    String username;

    PhysicsQuiz(String username) {
        this.username = username;

        setTitle("Physics Quiz");
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
        feedbackLabel.setVisible(true);
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
                showFeedback("Time's up!", true);
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
            JOptionPane.showMessageDialog(this, "Quiz Completed! Your Score: " + score + "/" + questions.length + ". Score updated.");
        } else {
            JOptionPane.showMessageDialog(this, "Quiz Completed! Your Score: " + score + "/" + questions.length + ".");
        }

        dispose();
    }

    private int getCurrentScore(String username) {
        int currentScore = 0;
        try {
            Conn c = new Conn();
            String query = "SELECT physics_score FROM quiz_scores WHERE username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                currentScore = rs.getInt("physics_score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentScore;
    }

    private void updateScore(String username, int score) {
        try {
            Conn c = new Conn();
            String query = "UPDATE quiz_scores SET physics_score = " + score + " WHERE username = '" + username + "'";
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
                        showFeedback("Wrong! The correct answer was: " + options[currentQuestion][answers[currentQuestion]], true);
                    }
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new PhysicsQuiz("");
    }
}
