package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.service.AuthenticationService;
import com.example.couriercompany.payload.CreateRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField numberField;
    private JTextField addressField;
    private JButton registerButton;

    private final AuthenticationService authenticationService;

    public RegistrationForm() {

        setLayout(new GridBagLayout());
        setBackground(new Color(189, 20, 124, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initializeComponents(gbc);

        authenticationService = new AuthenticationService();

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String telephoneNumber = numberField.getText();
            String addressForDelivery = addressField.getText();
            try {
                ResponseEntity<String> response = authenticationService
                        .fetchSignUp(new CreateRegistrationRequest(username, password, "user",telephoneNumber,addressForDelivery));
                JOptionPane.showMessageDialog(this, response.getBody(),
                        "Successful sign up!", JOptionPane.INFORMATION_MESSAGE);
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();
            } catch (HttpClientErrorException exception) {
                JOptionPane.showMessageDialog(this, exception.getResponseBodyAsString(), "Server Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initializeComponents(GridBagConstraints gbc) {

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        addressField = new JTextField();
        numberField = new JTextField();
        registerButton = new JButton("Register");

        setLayout(new GridLayout(0, 2));


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Number:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(numberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(addressField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(registerButton, gbc);
    }
}
