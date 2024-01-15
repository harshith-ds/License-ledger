/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author Harshith
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public class PlaceholderTextField extends JTextField {
    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY); // Set placeholder text color
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK); // Set normal text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
}
    public class PlaceholderPasswordField extends JPasswordField {
    private String placeholder;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setEchoChar((char) 0); // Set echo char to make placeholder visible
        setText(placeholder);
        setForeground(Color.GRAY);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(getPassword()).equals(placeholder)) {
                    setText("");
                    setEchoChar('\u2022'); // Restore echo char for password
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setText(placeholder);
                    setEchoChar((char) 0);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
}

    public LoginGUI() {
        setTitle("License Ledger - Login");
        emailField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);

        // Create a JPanel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2)); // 3 rows, 2 columns
        
         emailField = new PlaceholderTextField("Enter email");
    passwordField = new PlaceholderPasswordField("Enter password");
    loginButton = new JButton("Login");

        // Add labels and fields to the panel
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for layout spacing
        panel.add(loginButton);

        JLabel label = new JLabel("Label Text");
Font font = new Font("Bahnschrift", Font.PLAIN, 14);
label.setFont(font);

JButton button = new JButton("Button Text");
Font buttonFont = new Font("Bahnschrift", Font.PLAIN, 14);
button.setFont(buttonFont);
        // Add action listener to the loginButton
        loginButton.addActionListener(this::loginButtonActionPerformed);

        // Add the panel to the JFrame
        getContentPane().add(panel, BorderLayout.CENTER);

        // Other GUI setup code
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Database connection and query
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/license", "root", "pass");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password_hash");
                // Perform password hashing and comparison using passwordMatches method
                if (passwordMatches(password, storedPasswordHash)) {
                    // Successful login
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    // Proceed to the main application window or further processing
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);
                    this.dispose();
                } else {
                    // Incorrect password
                    JOptionPane.showMessageDialog(this, "Incorrect password.");
                }
            } else {
                // User not found
                JOptionPane.showMessageDialog(this, "User not found.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private boolean passwordMatches(String enteredPassword, String storedPasswordHash) {
        // Implement your password hashing and comparison logic here
        return enteredPassword.equals(storedPasswordHash);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setSize(400, 200);
            loginGUI.setVisible(true);
        });
    }
}
