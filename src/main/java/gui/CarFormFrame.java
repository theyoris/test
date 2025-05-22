package gui;

import api.entities.Car;
import api.services.CarService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarFormFrame extends JFrame {

    private final CarService carService;

    private JTextField idField;
    private JTextField plateField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField colorField;
    private JCheckBox availableCheck;
    private JButton saveButton;
    private JButton checkButton;

    private boolean isUpdateMode = false;

    public CarFormFrame(CarService carService) {
        this.carService = carService;

        setTitle("Προσθήκη / Ενημέρωση Αυτοκινήτου");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ID και Έλεγχος
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Κωδικός ID:");
        idField = new JTextField(10);
        checkButton = new JButton("Έλεγχος");
        idPanel.add(idLabel);
        idPanel.add(idField);
        idPanel.add(checkButton);
        mainPanel.add(idPanel);

        plateField = createDisabledField(mainPanel, "Πινακίδα:");
        brandField = createDisabledField(mainPanel, "Μάρκα:");
        modelField = createDisabledField(mainPanel, "Μοντέλο:");
        yearField = createDisabledField(mainPanel, "Έτος Κατασκευής:");
        colorField = createDisabledField(mainPanel, "Χρώμα:");

        JPanel availablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel availableLabel = new JLabel("Διαθέσιμο:");
        availableCheck = new JCheckBox();
        availableCheck.setEnabled(false);
        availablePanel.add(availableLabel);
        availablePanel.add(availableCheck);
        mainPanel.add(availablePanel);

        saveButton = new JButton("Καταχώρηση");
        saveButton.setEnabled(false);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(200, 40));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(saveButton);

        add(mainPanel);

        // Listener Έλεγχος
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCar();
            }
        });

        // Listener Καταχώρηση
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCar();
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
        plateField.setEnabled(enable);
        brandField.setEnabled(enable);
        modelField.setEnabled(enable);
        yearField.setEnabled(enable);
        colorField.setEnabled(enable);
        availableCheck.setEnabled(enable);
        saveButton.setEnabled(enable);
    }

    private void checkCar() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (id <= 0) {
                JOptionPane.showMessageDialog(this, "Το ID πρέπει να είναι θετικός αριθμός.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Car car = carService.findCarById(id);
            if (car != null) {
                // Αν Υπάρχει τότε fill και ενημέρωση
                plateField.setText(car.getLicensePlate());
                brandField.setText(car.getBrand());
                modelField.setText(car.getModel());
                yearField.setText(String.valueOf(car.getYear()));
                colorField.setText(car.getColor());
                availableCheck.setSelected(car.isAvailable());
                isUpdateMode = true;
                saveButton.setText("Ενημέρωση");
            } else {
                // Αν δεν υπάρχει τότε καθαρισμός
                plateField.setText("");
                brandField.setText("");
                modelField.setText("");
                yearField.setText("");
                colorField.setText("");
                availableCheck.setSelected(true);
                isUpdateMode = false;
                saveButton.setText("Καταχώρηση");
            }
            enableFields(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Το ID πρέπει να είναι αριθμός.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveCar() {
        try {
            int id = Integer.parseInt(idField.getText());
            int year = Integer.parseInt(yearField.getText());
            if (year <= 0) {
                JOptionPane.showMessageDialog(this, "Το Έτος πρέπει να είναι θετικός αριθμός.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Car existingByPlate = carService.findCarByPlate(plateField.getText());
            if (existingByPlate != null && (!isUpdateMode || existingByPlate.getId() != id)) {
                JOptionPane.showMessageDialog(this, "Υπάρχει ήδη αυτοκίνητο με αυτή την πινακίδα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Car car = new Car(id, plateField.getText(), brandField.getText(), modelField.getText(), year, colorField.getText(), availableCheck.isSelected());
            if (isUpdateMode) {
                carService.updateCar(car);
                JOptionPane.showMessageDialog(this, "Το αυτοκίνητο ενημερώθηκε.");
            } else {
                carService.addCar(car);
                JOptionPane.showMessageDialog(this, "Το αυτοκίνητο προστέθηκε.");
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Το ID και το Έτος πρέπει να είναι αριθμοί.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}