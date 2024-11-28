import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

    JButton create, back;
    JTextField tfUsername, tfname;
    JPasswordField tfPassword;

    Signup() {
        
        setSize(600, 400);
        setLocation(500, 250);
        setLayout(null);

        getContentPane().setBackground(new Color(250, 250, 250));

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(63, 81, 181));
        p1.setBounds(0, 0, 600, 400);
        p1.setLayout(null);
        add(p1);

        JLabel title = new JLabel("SignUp");
        title.setBounds(250, 30, 200, 30);
        title.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        p1.add(title);

        JLabel lblUsername = new JLabel("Username :");
        lblUsername.setBounds(120, 100, 100, 25);
        lblUsername.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        lblUsername.setForeground(Color.WHITE);
        p1.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(230, 100, 200, 25);
        tfUsername.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        tfUsername.setBorder(new LineBorder(new Color(63, 81, 181)));
        p1.add(tfUsername);

        JLabel lblname = new JLabel("Name :");
        lblname.setBounds(120, 150, 100, 25);
        lblname.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        lblname.setForeground(Color.WHITE);
        p1.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(230, 150, 200, 25);
        tfname.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        tfname.setBorder(new LineBorder(new Color(63, 81, 181)));
        p1.add(tfname);

        JLabel lblPassword = new JLabel("Password :");
        lblPassword.setBounds(120, 200, 100, 25);
        lblPassword.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        lblPassword.setForeground(Color.WHITE);
        p1.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(230, 200, 200, 25);
        tfPassword.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        tfPassword.setBorder(new LineBorder(new Color(63, 81, 181)));
        p1.add(tfPassword);

        create = new JButton("Create");
        create.setBounds(150, 270, 120, 30);
        create.setBackground(new Color(76, 175, 80));
        create.setForeground(Color.WHITE);
        create.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        create.setFocusPainted(false);
        create.setBorder(new RoundedBorder(10)); 
        create.addActionListener(this);
        p1.add(create);

        back = new JButton("Back");
        back.setBounds(300, 270, 120, 30);
        back.setBackground(new Color(255, 87, 34));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setBorder(new RoundedBorder(10)); 
        back.addActionListener(this);
        p1.add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String username = tfUsername.getText();
            String name = tfname.getText();
            String password = String.valueOf(tfPassword.getPassword());

            try {
                String query = "INSERT INTO account VALUES('" + username + "', '" + name + "', '" + password + "')";
                String query1 = "INSERT INTO quiz_scores VALUES('" + username + "', '" + 0 + "', '" + 0 + "', '" + 0 + "')";


                Conn c = new Conn();

                c.s.executeUpdate(query);
                c.s.executeUpdate(query1);

                JOptionPane.showMessageDialog(null, "Account Created Successfully!");

                setVisible(false);
                new Login();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Main();
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

    public static void main(String[] args) {
        new Signup();
    }
}
