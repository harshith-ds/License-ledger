/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author Harshith
 */
public class VehicleIdentificationSystem extends JFrame implements ActionListener {

    private JLabel idLabel, nameLabel, typeLabel, fineLabel, modelLabel, yearLabel;
    private JTextField idField, nameField, typeField, fineField, modelField, yearField;
    private JButton submitButton, clearButton, returnButton;
    private JTextArea outputArea;
    
    private Connection connection;
    
    public VehicleIdentificationSystem() {
        setTitle("License Ledger - Vechile Fine");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        idLabel = new JLabel("Vehicle ID:");
        nameLabel = new JLabel("Owner Name:");
        typeLabel = new JLabel("Vechile Type:");
        fineLabel = new JLabel("Fine:");
        modelLabel = new JLabel("Vechile Model:");
        yearLabel = new JLabel("Year:");
        
        idField = new JTextField(10);
        nameField = new JTextField(20);
        typeField = new JTextField(10);
        fineField = new JTextField(10);
        modelField = new JTextField(10);
        yearField = new JTextField(10);
        
        submitButton = new JButton("Submit");
        returnButton = new JButton("Return");
        returnButton.addActionListener(this::returnButtonActionPerformed);
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this::clearButtonActionPerformed);
        
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        
        JLabel label = new JLabel("Label Text");
Font font = new Font("Bahnschrift", Font.PLAIN, 14);
label.setFont(font);

JButton button = new JButton("Button Text");
Font buttonFont = new Font("Bahnschrift", Font.PLAIN, 14);
button.setFont(buttonFont);
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(typeLabel);
        inputPanel.add(typeField);
        inputPanel.add(fineLabel);
        inputPanel.add(fineField);
        inputPanel.add(modelLabel);
        inputPanel.add(modelField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(returnButton);
        
        
        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputArea));
        
        Container contentPane = getContentPane();
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(outputPanel, BorderLayout.EAST);
        
        submitButton.addActionListener(this);
        
        
        setVisible(true);
        
        // Initialize the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/license";
            String user = "root";
            String password = "pass";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void clearButtonActionPerformed(ActionEvent evt) {
        // Clear text fields
        idField.setText("");
        nameField.setText("");
        typeField.setText("");
        fineField.setText("");
        modelField.setText("");
        yearField.setText("");
        
        
    }
    private void returnButtonActionPerformed(ActionEvent evt) {                                         
        // TODO add your handling code here:
            Dashboard sc = new Dashboard();
    sc.setVisible(true);
        this.dispose();
    
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String vehicleID = idField.getText();
            String name = nameField.getText();
            String vehicleType = typeField.getText();
            String fine = fineField.getText();
            String model = modelField.getText();
            String year = yearField.getText();
            
            // Insert the vehicle information into the database
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO vehicles (id, name, type, fine, model, year) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, vehicleID);
                statement.setString(2, name);
                statement.setString(3, vehicleType);
                statement.setString(4, fine);
                statement.setString(5, model);
                statement.setString(6, year);
                statement.executeUpdate();
               outputArea.setText("Vehicle information added to database.");
} catch (SQLException ex) {
ex.printStackTrace();
}
} 
}

public static void main(String[] args) {
    new VehicleIdentificationSystem();
}
}

