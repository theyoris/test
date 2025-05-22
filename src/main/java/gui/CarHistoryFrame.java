package gui;

import api.entities.Rental;
import api.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarHistoryFrame extends JFrame {

    private RentalService rentalService;

    private JTextField plateField;
    private JButton searchButton;
    private JTextArea resultArea;

    public CarHistoryFrame(RentalService rentalService) {
        this.rentalService = rentalService;

        setTitle("Ιστορικό Ενοικιάσεων Αυτοκινήτου");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        plateField = createFieldWithLabel(mainPanel, "Πινακίδα Αυτοκινήτου:");

        searchButton = new JButton("Αναζήτηση");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scrollPane);

        add(mainPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
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

    private void showHistory() {
        String plate = plateField.getText();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε την πινακίδα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Rental> rentals = rentalService.getRentalsByCarPlate(plate);

        resultArea.setText("");
        if (rentals.isEmpty()) {
            resultArea.append("Δεν βρέθηκαν ενοικιάσεις για αυτό το αυτοκίνητο.\n");
        } else {
            for (Rental rental : rentals) {
                resultArea.append("Κωδικός: " + rental.getRentalId() +
                        " | Πινακίδα: " + rental.getCar().getLicensePlate() +
                        " | Μάρκα: " + rental.getCar().getBrand() +
                        " | Μοντέλο: " + rental.getCar().getModel() +
                        " | Χρώμα: " + rental.getCar().getColor() +
                        " | Πελάτης: " + rental.getCustomer().getAfm() +
                        " | Από: " + rental.getStartDate() + " έως: " + rental.getEndDate() +
                        " | Κατάσταση: " + (rental.isActive() ? "Ενεργή" : "Ολοκληρωμένη") +
                        "\n--------------------------------------------------\n");
            }
        }
    }
}