package gui;

import api.entities.Customer;
import api.services.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerSearchFrame extends JFrame {

    private CustomerService customerService;

    private JTextField afmField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton searchButton;
    private JTextArea resultArea;

    public CustomerSearchFrame(CustomerService customerService) {
        this.customerService = customerService;

        setTitle("Αναζήτηση Πελάτη");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        afmField = createFieldWithLabel(mainPanel, "ΑΦΜ:");
        nameField = createFieldWithLabel(mainPanel, "Ονοματεπώνυμο:");
        phoneField = createFieldWithLabel(mainPanel, "Τηλέφωνο:");

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
                searchCustomers();
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

    private void searchCustomers() {
        String afm = afmField.getText().isEmpty() ? null : afmField.getText();
        String name = nameField.getText().isEmpty() ? null : nameField.getText();
        String phone = phoneField.getText().isEmpty() ? null : phoneField.getText();

        List<Customer> results = customerService.searchCustomers(afm, name, phone);

        resultArea.setText("");
        if (results.isEmpty()) {
            resultArea.append("Δεν βρέθηκαν πελάτες.\n");
        } else {
            for (Customer customer : results) {
                resultArea.append("Πελάτης:\n");
                resultArea.append("ΑΦΜ: " + customer.getAfm() + "\n");
                resultArea.append("Ονοματεπώνυμο: " + customer.getFullName() + "\n");
                resultArea.append("Τηλέφωνο: " + customer.getPhoneNumber() + "\n");
                resultArea.append("--------------------------------------------------\n");
            }
        }
    }
}
