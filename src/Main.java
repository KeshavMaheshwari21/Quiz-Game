import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Main extends JFrame implements ActionListener {

    JButton signup, login;

    Main() {
        setSize(900, 600);
        setLocation(350, 120);
        setLayout(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(250, 250, 250));

        // Panel with gradient-like background
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(63, 81, 181));
        p1.setBounds(0, 0, 900, 600);
        p1.setLayout(null);
        add(p1);

        // Adding logo image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/logo.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 220, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(130, 60, 600, 220);
        p1.add(image);

        // Adding text labels
        JLabel text = new JLabel("Already a Player?");
        text.setBounds(170, 350, 250, 25);
        text.setFont(new Font("SAN_SERIF", Font.BOLD, 22));
        text.setForeground(Color.WHITE);
        p1.add(text);

        JLabel signuptext = new JLabel("New Player?");
        signuptext.setBounds(520, 350, 200, 25);
        signuptext.setFont(new Font("SAN_SERIF", Font.BOLD, 22));
        signuptext.setForeground(Color.WHITE);
        p1.add(signuptext);

        // Buttons with larger size, rounded borders, and new color scheme
        login = new JButton("Sign In");
        login.setBounds(170, 400, 160, 40);
        login.setBackground(new Color(255, 87, 34));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        login.setFocusPainted(false);
        login.setBorder(new RoundedBorder(10)); // Custom border
        login.addActionListener(this);
        p1.add(login);

        signup = new JButton("Sign Up");
        signup.setBounds(520, 400, 160, 40);
        signup.setBackground(new Color(76, 175, 80));
        signup.setForeground(Color.WHITE);
        signup.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        signup.setFocusPainted(false);
        signup.setBorder(new RoundedBorder(10)); // Custom border
        signup.addActionListener(this);
        p1.add(signup);

        setVisible(true);
    }

    // Action handler
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signup) {
            new Signup();
            setVisible(false);
        } else if (ae.getSource() == login) {
            new Login();
            setVisible(false);
        }
    }

    // Custom border for rounded buttons
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

    public static void main(String args[]) {
        new Main();
    }
}
