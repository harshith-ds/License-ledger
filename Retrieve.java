/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author Harshith
 */

public class Retrieve extends JFrame implements ActionListener {

    private JLabel idLabel, nameLabel, typeLabel, fineLabel, modelLabel, yearLabel;
    private JTextField idField, nameField, typeField, fineField, modelField, yearField;
    private JButton retrieveButton, clearButton,returnButton;
    private JTextArea outputArea;
    
    private Connection connection;
    
    public Retrieve() {
        setTitle("License Ledger - Retrieve");
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
        
        retrieveButton = new JButton("Retrieve");
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
        
        buttonPanel.add(retrieveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(returnButton);
        
        
        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputArea));
        
        Container contentPane = getContentPane();
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(outputPanel, BorderLayout.EAST);
        
        
        retrieveButton.addActionListener(this);
        
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
       if (e.getSource() == retrieveButton) {
String vehicleID = idField.getText();
String nameID=nameField.getText();
        // Retrieve the vehicle information from the database
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM vehicles WHERE id = ?");
            statement.setString(1, vehicleID);
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM vehicles WHERE name = ?");
            statement2.setString(1, nameID);
            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSet2 = statement2.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String vehicleType = resultSet.getString("type");
                String fine = resultSet.getString("fine");
                String model = resultSet.getString("model");
                String year = resultSet.getString("year");
                outputArea.setText("Vehicle ID: " + vehicleID + "\nName: " + name + "\nType: " + vehicleType + "\nMake: " + fine + "\nModel: " + model + "\nYear: " + year);
            } 
            
            else if(resultSet2.next()){
                String name = resultSet2.getString("name");
                String vehicleType = resultSet2.getString("type");
                String fine = resultSet2.getString("fine");
                String model = resultSet2.getString("model");
                String year = resultSet2.getString("year");
                outputArea.setText("Vehicle ID: " + vehicleID + "\nName: " + name + "\nType: " + vehicleType + "\nMake: " + fine + "\nModel: " + model + "\nYear: " + year);      
            }
            
            else {
                outputArea.setText("Vehicle not found in database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

public static void main(String[] args) {
    new Retrieve();
}
}