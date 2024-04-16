package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.service.AuthenticationService;
import com.example.couriercompany.gui.service.CurierService;
import com.example.couriercompany.payload.CreateRegistrationCurierRequest;
import com.example.couriercompany.payload.CreateRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourierRegistrationForm extends JFrame implements ActionListener {
    private JTextField usernameField, passwordField, courierNameField, courierNumberField, yearsOfExperienceField, serviceAreaField;
    private JButton registerButton, backButton;
    private CurierService curierService;
    private AuthenticationService authenticationService;

    public CourierRegistrationForm() {
        setTitle("Courier Registration Form");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JLabel courierNameLabel = new JLabel("Courier Name:");
        courierNameField = new JTextField();
        add(courierNameLabel);
        add(courierNameField);

        JLabel courierNumberLabel = new JLabel("Courier Number:");
        courierNumberField = new JTextField();
        add(courierNumberLabel);
        add(courierNumberField);

        JLabel yearsOfExperienceLabel = new JLabel("Years of Experience:");
        yearsOfExperienceField = new JTextField();
        add(yearsOfExperienceLabel);
        add(yearsOfExperienceField);

        JLabel serviceAreaLabel = new JLabel("Service Area:");
        serviceAreaField = new JTextField();
        add(serviceAreaLabel);
        add(serviceAreaField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        add(registerButton);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton);

        curierService = new CurierService();
        authenticationService = new AuthenticationService(); // Initialize AuthenticationService
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String courierName = courierNameField.getText();
            String courierNumber = courierNumberField.getText();
            int yearsOfExperience = Integer.parseInt(yearsOfExperienceField.getText());
            String serviceArea = serviceAreaField.getText();

            try {
                // Register the courier
                ResponseEntity<String> response = curierService.fetchCreateCurier(
                        new CreateRegistrationCurierRequest(courierName, courierNumber, yearsOfExperience, serviceArea));

                // If the courier registration is successful, create a registration entry
                if (response.getStatusCode().is2xxSuccessful()) {
                    // Create registration for the courier
                    ResponseEntity<String> registrationResponse = authenticationService.fetchSignUp(
                            new CreateRegistrationRequest(username, password, "courier", courierNumber, serviceArea));
                    if (registrationResponse.getStatusCode().is2xxSuccessful()) {
                        JOptionPane.showMessageDialog(this, "Courier Registered Successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to create registration for the courier",
                                "Registration Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register courier",
                            "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid Years of Experience",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (HttpClientErrorException error) {
                JOptionPane.showMessageDialog(this, error.getResponseBodyAsString(),
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
