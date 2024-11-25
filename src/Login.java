import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, back;
    JTextField tfUsername;
    JPasswordField tfPassword;

    Login() {
        // Set window size and position
        setSize(600, 400);
        setLocation(500, 250);
        setLayout(null);

        // Set frame background
        getContentPane().setBackground(new Color(250, 250, 250));

        // Panel for design consistency
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(63, 81, 181));
        p1.setBounds(0, 0, 600, 400);
        p1.setLayout(null);
        add(p1);

        // Title label
        JLabel title = new JLabel("Login");
        title.setBounds(250, 30, 200, 30);
        title.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        p1.add(title);

        // Username input
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(120, 100, 100, 25);
        lblUsername.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        lblUsername.setForeground(Color.WHITE);
        p1.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(230, 100, 200, 25);
        tfUsername.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        tfUsername.setBorder(new LineBorder(new Color(63, 81, 181)));
        p1.add(tfUsername);

        // Password input
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(120, 150, 100, 25);
        lblPassword.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        lblPassword.setForeground(Color.WHITE);
        p1.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(230, 150, 200, 25);
        tfPassword.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        tfPassword.setBorder(new LineBorder(new Color(63, 81, 181)));
        p1.add(tfPassword);

        // Login button
        login = new JButton("Login");
        login.setBounds(150, 220, 120, 30);
        login.setBackground(new Color(76, 175, 80));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        login.setFocusPainted(false);
        login.setBorder(new RoundedBorder(10)); // Custom rounded border
        login.addActionListener(this);
        p1.add(login);

        // Back button
        back = new JButton("Back");
        back.setBounds(300, 220, 120, 30);
        back.setBackground(new Color(255, 87, 34));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setBorder(new RoundedBorder(10)); // Custom rounded border
        back.addActionListener(this);
        p1.add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Main();
        } else {
            try {
                String username = tfUsername.getText();
                String password = String.valueOf(tfPassword.getPassword());

                String query = "select * from account where username = '" + username + "' AND password = '" + password
                        + "'";
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    setVisible(false);
                    new Dashboard(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password !");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public static void main(String[] args) {
        new Login();
    }
}
