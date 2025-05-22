package gui;

import api.entities.Customer;
import api.services.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerFormFrame extends JFrame {

    private CustomerService customerService;

    private JTextField afmField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton saveButton;
    private JButton checkButton;

    private boolean isUpdateMode = false;

    public CustomerFormFrame(CustomerService customerService) {
        this.customerService = customerService;

        setTitle("Προσθήκη / Ενημέρωση Πελάτη");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Φτιάχνουμε το βασικό panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ΑΦΜ και κουμπί ελέγχου
        JPanel afmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel afmLabel = new JLabel("ΑΦΜ:");
        afmField = new JTextField(10);
        checkButton = new JButton("Έλεγχος");
        afmPanel.add(afmLabel);
        afmPanel.add(afmField);
        afmPanel.add(checkButton);
        mainPanel.add(afmPanel);

        // Τα υπόλοιπα πεδία είναι αρχικά απενεργοποιημένα
        nameField = createDisabledField(mainPanel, "Ονοματεπώνυμο:");
        phoneField = createDisabledField(mainPanel, "Τηλέφωνο:");

        // Το κουμπί αποθήκευσης
        saveButton = new JButton("Καταχώρηση Πελάτη");
        saveButton.setEnabled(false);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(200, 40));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(saveButton);

        add(mainPanel);

        // Όταν πατηθεί Έλεγχος
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCustomer();
            }
        });

        // Όταν πατηθεί Καταχώρηση
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer();
            }
        });
    }

    private JTextField createDisabledField(JPanel panel, String labelText) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(15);
        textField.setEnabled(false);
        fieldPanel.add(label);
        fieldPanel.add(textField);
        panel.add(fieldPanel);
        return textField;
    }

    private void enableFields(boolean enable) {
        nameField.setEnabled(enable);
        phoneField.setEnabled(enable);
        saveButton.setEnabled(enable);
    }

    private void checkCustomer() {
        String afm = afmField.getText();
        if (afm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε το ΑΦΜ.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Customer customer = customerService.findCustomerByAfm(afm);
        if (customer != null) {
            // Αν υπάρχει τότε ενημέρωση
            nameField.setText(customer.getFullName());
            phoneField.setText(customer.getPhoneNumber());
            isUpdateMode = true;
            saveButton.setText("Ενημέρωση Πελάτη");
        } else {
            // Αν δεν υπάρχει τότε προσθήκη
            nameField.setText("");
            phoneField.setText("");
            isUpdateMode = false;
            saveButton.setText("Καταχώρηση Πελάτη");
        }
        enableFields(true);
    }

    private void saveCustomer() {
        String afm = afmField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();

        if (afm.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα πεδία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Το τηλέφωνο πρέπει να έχει μόνο αριθμούς.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = new Customer(afm, name, phone);

        if (isUpdateMode) {
            customerService.updateCustomer(customer);
            JOptionPane.showMessageDialog(this, "Ο πελάτης ενημερώθηκε.");
        } else {
            customerService.addCustomer(customer);
            JOptionPane.showMessageDialog(this, "Ο πελάτης προστέθηκε.");
        }
        dispose();
    }
}
