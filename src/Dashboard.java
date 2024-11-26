import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Dashboard extends JFrame implements ActionListener {

    JButton startQuiz, math, physics, chemistry, account, close, yes, no;
    JLabel lblUsername, mathematicsscore, chemistryscore, physicsscore;
    JPanel quizPanel, accountPanel, closePanel;
    String username;
    private int radius;

    Dashboard(String username) {
        
        this.username = username;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(63, 81, 181));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/logo.png"));
        Image i2 = i1.getImage().getScaledInstance(520, 180, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(470, 50, 600, 220);
        add(image);

        startQuiz = new JButton("Start Quiz");
        startQuiz.setBounds(620, 300, 300, 60); 
        startQuiz.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        startQuiz.setBackground(new Color(76, 175, 80));
        startQuiz.setForeground(Color.WHITE);
        startQuiz.setFocusPainted(false);
        startQuiz.setBorder(new RoundedBorder(10));
        startQuiz.addActionListener(this);
        add(startQuiz);

        quizPanel = new RoundedPanel(30); 
        quizPanel.setBounds(425, 430, 700, 60); 
        quizPanel.setBackground(new Color(63, 81, 181));
        quizPanel.setLayout(null);
        quizPanel.setVisible(false);
        add(quizPanel);

        accountPanel = new RoundedPanel(30); 
        accountPanel.setBounds(0, 0, 400, 600);
        accountPanel.setBackground(new Color(13, 21, 81));
        accountPanel.setLayout(null);
        accountPanel.setVisible(false);
        add(accountPanel);

        closePanel = new RoundedPanel(30); 
        closePanel.setBounds(1010, 500, 500, 200);
        closePanel.setBackground(new Color(13, 21, 61));
        closePanel.setLayout(null);
        closePanel.setVisible(false);
        add(closePanel);

        math = new JButton("Mathematics");
        math.setBounds(0, 0, 200, 60); 
        math.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        math.setBackground(new Color(76, 175, 80));
        math.setForeground(Color.WHITE);
        math.setFocusPainted(false);
        math.setBorder(new RoundedBorder(10)); 
        math.addActionListener(this);
        quizPanel.add(math);

        chemistry = new JButton("Chemistry");
        chemistry.setBounds(250, 0, 200, 60);
        chemistry.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        chemistry.setBackground(new Color(76, 175, 80));
        chemistry.setForeground(Color.WHITE);
        chemistry.setFocusPainted(false);
        chemistry.setBorder(new RoundedBorder(10)); 
        chemistry.addActionListener(this);
        quizPanel.add(chemistry);

        physics = new JButton("Physics");
        physics.setBounds(500, 0, 200, 60);
        physics.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        physics.setBackground(new Color(76, 175, 80));
        physics.setForeground(Color.WHITE);
        physics.setFocusPainted(false);
        physics.setBorder(new RoundedBorder(10)); 
        physics.addActionListener(this);
        quizPanel.add(physics);

        account = new JButton("Account : " + username);
        account.setBounds(20, 710, 200, 60);
        account.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        account.setBackground(new Color(76, 175, 80));
        account.setForeground(Color.WHITE);
        account.setFocusPainted(false);
        account.setBorder(new RoundedBorder(10)); 
        account.addActionListener(this);
        add(account);

        JLabel lblDetailsUsername = new JLabel("Username : " + username);
        lblDetailsUsername.setBounds(50, 100, 500, 30);
        lblDetailsUsername.setFont(new Font("SAN_SERIF", Font.PLAIN, 26));
        lblDetailsUsername.setForeground(Color.WHITE);
        accountPanel.add(lblDetailsUsername);

        JLabel lblDetailsScore = new JLabel("Highest Score");
        lblDetailsScore.setBounds(50, 200, 500, 30);
        lblDetailsScore.setFont(new Font("SAN_SERIF", Font.PLAIN, 26));
        lblDetailsScore.setForeground(Color.WHITE);
        accountPanel.add(lblDetailsScore);

        mathematicsscore = new JLabel("Mathematics : ");
        mathematicsscore.setBounds(50, 260, 500, 30);
        mathematicsscore.setFont(new Font("SAN_SERIF", Font.PLAIN, 22));
        mathematicsscore.setForeground(Color.WHITE);
        accountPanel.add(mathematicsscore);

        chemistryscore = new JLabel("Chemistry : ");
        chemistryscore.setBounds(50, 320, 500, 30);
        chemistryscore.setFont(new Font("SAN_SERIF", Font.PLAIN, 22));
        chemistryscore.setForeground(Color.WHITE);
        accountPanel.add(chemistryscore);

        physicsscore = new JLabel("Physics : ");
        physicsscore.setBounds(50, 380, 500, 30);
        physicsscore.setFont(new Font("SAN_SERIF", Font.PLAIN, 22));
        physicsscore.setForeground(Color.WHITE);
        accountPanel.add(physicsscore);

        close = new JButton("Quit");
        close.setBounds(1310, 710, 200, 60);
        close.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        close.setBackground(new Color(183, 28, 28));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setBorder(new RoundedBorder(10));
        close.addActionListener(this);
        add(close);

        JLabel exit = new JLabel("Did you really want to Exit ?");
        exit.setBounds(80, 40, 500, 30);
        exit.setFont(new Font("SAN_SERIF", Font.PLAIN, 27));
        exit.setForeground(Color.WHITE);
        closePanel.add(exit);

        yes = new JButton("Yes");
        yes.setBounds(56, 110, 150, 40);
        yes.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        yes.setBackground(new Color(183, 28, 28));
        yes.setForeground(Color.WHITE);
        yes.setFocusPainted(false);
        yes.setBorder(new RoundedBorder(10));
        yes.addActionListener(this);
        closePanel.add(yes);

        no = new JButton("No");
        no.setBounds(280, 110, 150, 40);
        no.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        no.setBackground(new Color(183, 28, 28));
        no.setForeground(Color.WHITE);
        no.setFocusPainted(false);
        no.setBorder(new RoundedBorder(10));
        no.addActionListener(this);
        closePanel.add(no);

        fetchScores();

        setVisible(true);
    }

    private void fetchScores() {
        try{

            Conn c = new Conn();

            String query = "SELECT * FROM quiz_scores WHERE username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                mathematicsscore.setText("Mathematics : " + rs.getInt("math_score"));
                chemistryscore.setText("Chemistry : " + rs.getInt("chem_score"));
                physicsscore.setText("Physics : " + rs.getInt("physics_score"));
            } else {
                mathematicsscore.setText("Mathematics : 0");
                chemistryscore.setText("Chemistry : 0");
                physicsscore.setText("Physics : 0");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startQuiz) {
            quizPanel.setVisible(!quizPanel.isVisible());
        } else if (e.getSource() == math) {
            new MathematicsQuiz(username);
        } else if (e.getSource() == chemistry) {
           new ChemistryQuiz(username);
        } else if (e.getSource() == physics) {
            new PhysicsQuiz(username);
        } else if (e.getSource() == account) {
            fetchScores();
            accountPanel.setVisible(!accountPanel.isVisible());
        } else if (e.getSource() == close) {
            closePanel.setVisible(!closePanel.isVisible());
        } else if (e.getSource() == yes) {
            System.exit(0);
        } else if (e.getSource() == no) {
            closePanel.setVisible(!closePanel.isVisible());
        }
    }

    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getForeground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    class RoundedPanel extends JPanel {
        private int radius;

        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
    }

    public static void main(String[] args) {
        new Dashboard("");
    }
}
