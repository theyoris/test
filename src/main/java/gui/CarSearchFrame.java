package gui;

import api.entities.Car;
import api.services.CarService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarSearchFrame extends JFrame {

    private CarService carService;

    private JTextField brandField;
    private JTextField modelField;
    private JTextField plateField;
    private JTextField colorField;
    private JComboBox<String> statusBox;
    private JButton searchButton;
    private JTextArea resultArea;

    public CarSearchFrame(CarService carService) {
        this.carService = carService;

        setTitle("Αναζήτηση Αυτοκινήτου");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        brandField = createFieldWithLabel(mainPanel, "Μάρκα:");
        modelField = createFieldWithLabel(mainPanel, "Μοντέλο:");
        plateField = createFieldWithLabel(mainPanel, "Πινακίδα:");
        colorField = createFieldWithLabel(mainPanel, "Χρώμα:");

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Κατάσταση:");
        String[] options = {"Όλα", "Διαθέσιμο", "Ενοικιασμένο"};
        statusBox = new JComboBox<>(options);
        statusPanel.add(statusLabel);
        statusPanel.add(statusBox);
        mainPanel.add(statusPanel);

        searchButton = new JButton("Αναζήτηση");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scrollPane);

        add(mainPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCars();
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

    private void searchCars() {
        String brand = brandField.getText().isEmpty() ? null : brandField.getText();
        String model = modelField.getText().isEmpty() ? null : modelField.getText();
        String plate = plateField.getText().isEmpty() ? null : plateField.getText();
        String color = colorField.getText().isEmpty() ? null : colorField.getText();

        Boolean isAvailable = null;
        String status = (String) statusBox.getSelectedItem();

        if (status.equals("Διαθέσιμο")) isAvailable = true;
        if (status.equals("Ενοικιασμένο")) isAvailable = false;

        List<Car> results = carService.searchCars(brand, model, plate, color, isAvailable);

        resultArea.setText("");
        if (results.isEmpty()) {
            resultArea.append("Δεν βρέθηκαν αυτοκίνητα.\n");
        } else {
            for (Car car : results) {
                resultArea.append("ID: " + car.getId() +
                        " | Πινακίδα: " + car.getLicensePlate() +
                        " | Μάρκα: " + car.getBrand() +
                        " | Μοντέλο: " + car.getModel() +
                        " | Έτος: " + car.getYear() +
                        " | Χρώμα: " + car.getColor() +
                        " | Κατάσταση: " + (car.isAvailable() ? "Διαθέσιμο" : "Ενοικιασμένο") +
                        "\n--------------------------------------------------\n");
            }
        }
    }
}
