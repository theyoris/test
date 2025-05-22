package gui;

import api.authentication.LoginManager;
import api.entities.Employee;
import api.services.CarService;
import api.services.CustomerService;
import api.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginFrame extends JFrame {

    private LoginManager loginManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private CarService carService;
    private CustomerService customerService;
    private RentalService rentalService;

    public LoginFrame(LoginManager loginManager, CarService carService, CustomerService customerService, RentalService rentalService) {
        this.loginManager = loginManager;
        this.carService = carService;
        this.customerService = customerService;
        this.rentalService = rentalService;

        // Ρυθμίζουμε το frame
        setTitle("Σύστημα Ενοικίασης - Σύνδεση");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Φτιάχνουμε το βασικό panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Φορτώνουμε και κεντράρουμε το logo μας
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/login_logo.png")));
        JLabel imageLabel = new JLabel(logo);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(imageLabel);

        // Προσθέτουμε λίγο κενό ανάμεσα σε logo και φόρμα
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Φτιάχνουμε panel για τα πεδία login
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Δημιουργούμε τα labels και τα text fields
        JLabel usernameLabel = new JLabel("Όνομα Χρήστη:");
        JLabel passwordLabel = new JLabel("Κωδικός:");
        usernameField = new JTextField(12);
        passwordField = new JPasswordField(12);
        loginButton = new JButton("Είσοδος");

        // Τοποθετούμε τα components στο form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);

        // Προσθέτουμε το form panel στο main panel
        mainPanel.add(formPanel);

        // Προσθέτουμε το main panel στο frame
        add(mainPanel);

        // Action listener για το κουμπί login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    // Μέθοδος για έλεγχο login
    private void doLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean success = loginManager.checkLogin(username, password);

        if (success) {
            // Αν είναι σωστά τότε εμφανίζει μήνυμα και ανοίγει main menu
            JOptionPane.showMessageDialog(this, "Καλώς ήρθες " + username);
            dispose();
            Employee employee = loginManager.findByUsername(username);
            new MainMenuFrame(carService, customerService, rentalService, employee).setVisible(true);
        } else {
            // Αν όμως είναι λάθος τότε δείχνει ένα μήνυμα λάθους
            JOptionPane.showMessageDialog(this, "Λάθος όνομα χρήστη ή κωδικός!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
