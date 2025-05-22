package gui;

import api.entities.Car;
import api.entities.Customer;
import api.entities.Employee;
import api.services.CarService;
import api.services.CustomerService;
import api.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RentalFormFrame extends JFrame {

    private RentalService rentalService;
    private CarService carService;
    private CustomerService customerService;
    private Employee loggedInEmployee;

    private JTextField afmField;
    private JComboBox<Car> carComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton rentButton;

    public RentalFormFrame(RentalService rentalService, CarService carService, CustomerService customerService, Employee loggedInEmployee) {
        this.rentalService = rentalService;
        this.carService = carService;
        this.customerService = customerService;
        this.loggedInEmployee = loggedInEmployee;

        // Ρυθμίσεις παραθύρου
        setTitle("Νέα Ενοικίαση");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Πεδίο εισαγωγής ΑΦΜ
        afmField = createFieldWithLabel(mainPanel, "ΑΦΜ Πελάτη:");

        // Combo box με διαθέσιμα αυτοκίνητα
        JPanel carPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel carLabel = new JLabel("Αυτοκίνητο:");
        carComboBox = new JComboBox<>();
        loadAvailableCars();
        carPanel.add(carLabel);
        carPanel.add(carComboBox);
        mainPanel.add(carPanel);

        // Πεδία ημερομηνιών
        startDateField = createFieldWithLabel(mainPanel, "Ημερομηνία Έναρξης (YYYY-MM-DD):");
        endDateField = createFieldWithLabel(mainPanel, "Ημερομηνία Λήξης (YYYY-MM-DD):");

        // Κουμπί ενοικίασης
        rentButton = new JButton("Καταχώρηση Ενοικίασης");
        rentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(rentButton);

        add(mainPanel);

        // Action listener για κουμπί
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRental();
            }
        });
    }

    private JTextField createFieldWithLabel(JPanel panel, String labelText) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(15);
        fieldPanel.add(label);
        fieldPanel.add(textField);
        panel.add(fieldPanel);
        return textField;
    }

    // Φορτώνει τα διαθέσιμα αυτοκίνητα στο combo box
    private void loadAvailableCars() {
        List<Car> availableCars = carService.getAvailableCars();
        for (Car car : availableCars) {
            carComboBox.addItem(car);
        }
    }

    // Τι γίνεται όταν πατηθεί η Καταχώρηση
    private void createRental() {
        String afm = afmField.getText();
        String startDateStr = startDateField.getText();
        String endDateStr = endDateField.getText();

        // Έλεγχος πεδίων
        if (afm.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() || carComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα πεδία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = customerService.findCustomerByAfm(afm);
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Δεν βρέθηκε πελάτης με αυτό το ΑΦΜ!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Car car = (Car) carComboBox.getSelectedItem();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Έλεγχος ημερομηνιών
            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(this, "Η ημερομηνία λήξης πρέπει να είναι μετά την ημερομηνία έναρξης!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Δημιουργία ενοικίασης
            rentalService.rentCar(car, customer, startDate, endDate, loggedInEmployee);
            JOptionPane.showMessageDialog(this, "Η ενοικίαση καταχωρήθηκε επιτυχώς!");

            dispose(); // κλείνει το παράθυρο

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ημερομηνία σε λάθος μορφή! Χρησιμοποιήστε YYYY-MM-DD.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
