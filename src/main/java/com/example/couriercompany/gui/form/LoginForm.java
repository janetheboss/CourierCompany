package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.form.CourierRegistrationForm;
import com.example.couriercompany.gui.form.RegistrationForm;
import com.example.couriercompany.gui.form.UserMenu;
import com.example.couriercompany.gui.service.AuthenticationService;
import com.example.couriercompany.payload.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> openRegistrationForm());
        add(loginButton);
        add(registerButton);

        JButton courierRegisterButton = new JButton("Courier Registration");
        courierRegisterButton.addActionListener(e -> openCourierRegistrationForm());
        add(new JLabel());
        add(courierRegisterButton);

        setVisible(true);
    }

    private void openRegistrationForm() {
        RegistrationForm form = new RegistrationForm();

        JDialog dialog = new JDialog();
        dialog.setTitle("Register");
        dialog.setModal(true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openCourierRegistrationForm() {
        CourierRegistrationForm form = new CourierRegistrationForm();

        JDialog dialog = new JDialog();
        dialog.setTitle("Courier Registration");
        dialog.setModal(true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openMenu(String username, String role) {
        switch (role) {
            case "user":
                openUserMenu(username);
                break;
            case "courier":
                openCourierMenu(username);
                break;
            case "admin":
                openAdminMenu(username);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openUserMenu(String username) {
        UserMenu userMenu = new UserMenu(username);
        openMenuDialog(username, userMenu);
    }

    private void openCourierMenu(String username) {
        CourierMenu courierMenu = new CourierMenu(username);
        openMenuDialog(username, courierMenu);
    }

    private void openAdminMenu(String username) {
        //AdminMenu adminMenu = new AdminMenu(username);
        //openMenuDialog(username, adminMenu);
    }

    private void openMenuDialog(String username, JPanel menuPanel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Welcome " + username);
        dialog.setModal(true);
        dialog.setContentPane(menuPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void login() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            AuthenticationService service = new AuthenticationService();

            ResponseEntity<String> roleResponse = service.fetchUserRole(username);
            String role = roleResponse.getBody();

            ResponseEntity<String> response = service.fetchLogin(new LoginRequestDTO(username, password));
            JOptionPane.showMessageDialog(this, "Successful Login!", "Success", JOptionPane.INFORMATION_MESSAGE);
            openMenu(username, role);
        } catch (HttpClientErrorException exception) {
            JOptionPane.showMessageDialog(this, exception.getResponseBodyAsString(), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LoginForm());
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
