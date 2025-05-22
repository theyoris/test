package gui;

import api.entities.Employee;
import api.services.CarService;
import api.services.CustomerService;
import api.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {

    private JButton carButton;
    private JButton customerButton;
    private JButton carSearchButton;
    private JButton customerSearchButton;
    private JButton rentalButton;
    private JButton returnButton;
    private JButton customerHistoryButton;
    private JButton carHistoryButton;
    private JButton exitButton;
    private CarService carService;
    private CustomerService customerService;
    private RentalService rentalService;
    private Employee loggedInEmployee;

    public MainMenuFrame(CarService carService, CustomerService customerService, RentalService rentalService, Employee loggedInEmployee) {
        this.carService = carService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.loggedInEmployee = loggedInEmployee;

        // Βασικές ρυθμίσεις παραθύρου
        setTitle("Σύστημα Ενοικίασης - Κεντρικό Μενού");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Βασικό panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Δημιουργούμε όλα τα κουμπιά
        carButton = new JButton("Διαχείριση Αυτοκινήτων");
        customerButton = new JButton("Διαχείριση Πελατών");
        carSearchButton = new JButton("Αναζήτηση Αυτοκινήτου");
        customerSearchButton = new JButton("Αναζήτηση Πελάτη");
        rentalButton = new JButton("Ενοικιάσεις");
        returnButton = new JButton("Επιστροφή Αυτοκινήτου");
        customerHistoryButton = new JButton("Ιστορικό Πελάτη");
        carHistoryButton = new JButton("Ιστορικό Αυτοκινήτου");
        exitButton = new JButton("Έξοδος");

        // Προσθέτουμε τα κουμπιά στο panel
        addButtonToPanel(mainPanel, carButton);
        addButtonToPanel(mainPanel, customerButton);
        addButtonToPanel(mainPanel, carSearchButton);
        addButtonToPanel(mainPanel, customerSearchButton);
        addButtonToPanel(mainPanel, rentalButton);
        addButtonToPanel(mainPanel, returnButton);
        addButtonToPanel(mainPanel, customerHistoryButton);
        addButtonToPanel(mainPanel, carHistoryButton);
        addButtonToPanel(mainPanel, exitButton);

        add(mainPanel);

        carButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarFormFrame(carService).setVisible(true);
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerFormFrame(customerService).setVisible(true);
            }
        });

        carSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarSearchFrame(carService).setVisible(true);
            }
        });

        customerSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerSearchFrame(customerService).setVisible(true);
            }
        });

        rentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RentalFormFrame(rentalService, carService, customerService, loggedInEmployee).setVisible(true);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnCarFrame(rentalService, carService).setVisible(true);
            }
        });

        customerHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerHistoryFrame(rentalService).setVisible(true);
            }
        });

        carHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarHistoryFrame(rentalService).setVisible(true);
            }
        });

        // Action listener για exit
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Κλείνει το παράθυρο
            }
        });
    }

    // Μέθοδος για να προσθέτουμε κουμπιά με χώρο ανάμεσά τους
    private void addButtonToPanel(JPanel panel, JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 40));
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}
