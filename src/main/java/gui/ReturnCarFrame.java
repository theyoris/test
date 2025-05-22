package gui;

import api.entities.Rental;
import api.services.CarService;
import api.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnCarFrame extends JFrame {

    private RentalService rentalService;
    private CarService carService;

    private JTextField afmField;
    private JTextField plateField;
    private JButton searchButton;
    private JList<String> rentalList;
    private DefaultListModel<String> listModel;
    private JButton returnButton;

    private Map<String, Rental> rentalMap = new HashMap<>();

    public ReturnCarFrame(RentalService rentalService, CarService carService) {
        this.rentalService = rentalService;
        this.carService = carService;

        setTitle("Επιστροφή Αυτοκινήτου");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        afmField = createFieldWithLabel(mainPanel, "ΑΦΜ Πελάτη:");
        plateField = createFieldWithLabel(mainPanel, "Πινακίδα Αυτοκινήτου:");

        searchButton = new JButton("Αναζήτηση");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(searchButton);

        listModel = new DefaultListModel<>();
        rentalList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(rentalList);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scrollPane);

        returnButton = new JButton("Επιστροφή Αυτοκινήτου");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(returnButton);

        add(mainPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRentals();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar();
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

    private void searchRentals() {
        String afm = afmField.getText();
        String plate = plateField.getText();
        List<Rental> results = null;

        if (!afm.isEmpty()) {
            results = rentalService.getRentalsByCustomerAfm(afm);
        } else if (!plate.isEmpty()) {
            results = rentalService.getRentalsByCarPlate(plate);
        }

        if (results != null) {
            List<Rental> activeRentals = new ArrayList<>();
            for (Rental rental : results) {
                if (rental.isActive()) {
                    activeRentals.add(rental);
                }
            }

            listModel.clear();
            rentalMap.clear();

            if (activeRentals.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν ενεργές ενοικιάσεις!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Rental rental : activeRentals) {
                    String display = "Κωδικός: " + rental.getRentalId() +
                            " | Πινακίδα: " + rental.getCar().getLicensePlate() +
                            " | Μάρκα: " + rental.getCar().getBrand() +
                            " | Μοντέλο: " + rental.getCar().getModel() +
                            " | Χρώμα: " + rental.getCar().getColor() +
                            " | Πελάτης: " + rental.getCustomer().getAfm() +
                            " | Από: " + rental.getStartDate() + " έως: " + rental.getEndDate();
                    listModel.addElement(display);
                    rentalMap.put(display, rental);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν ενεργές ενοικιάσεις!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnCar() {
        String selected = rentalList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Επιλέξτε μια ενοικίαση από τη λίστα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Rental rental = rentalMap.get(selected);
        rentalService.returnCar(rental);
        carService.saveCarsToFile();
        rentalService.saveRentalsToFile();

        JOptionPane.showMessageDialog(this, "Το αυτοκίνητο επιστράφηκε επιτυχώς!");
        dispose();
    }
}
