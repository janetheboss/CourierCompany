package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.model.OrderTableModel;
import com.example.couriercompany.gui.utils.DateLabelFormatter;
import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.OrderDTO;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class UserMenu extends JPanel {

    private final OrderService orderService;
    private final JDatePickerImpl datePicker;
    private LocalDate afterDate = null;
    private LocalDate fiveDaysAgo = null;
    private final JTable orderTable;

    public UserMenu(String username) {
        orderService = new OrderService();

        setLayout(new BorderLayout());

        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel datePickerPanel = new JPanel();
        datePickerPanel.add(new JLabel("Select Date:"));
        datePickerPanel.add(datePicker);
        add(datePickerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(e -> createOrderForm(username));
        buttonPanel.add(createOrderButton);

        JButton fetchPastDeliveriesButton = new JButton("Fetch Past Deliveries");
        fetchPastDeliveriesButton.addActionListener(e -> fetchDeliveriesPastDate((UtilDateModel) datePicker.getModel(), username));
        buttonPanel.add(fetchPastDeliveriesButton);

        JButton fetchRecentDeliveriesButton = new JButton("Fetch Recent Deliveries");
        fetchRecentDeliveriesButton.addActionListener(e -> fetchDeliveriesMadeRecentFiveDays(username));
        buttonPanel.add(fetchRecentDeliveriesButton);

        add(buttonPanel, BorderLayout.CENTER);

        List<OrderDTO> orders = new ArrayList<>();
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.SOUTH);

        fetchOrders(username);
    }

    private void fetchDeliveriesPastDate(UtilDateModel model, String username) {
        fiveDaysAgo = null;
        afterDate = LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
        fetchOrders(username);
    }

    private void fetchDeliveriesMadeRecentFiveDays(String username) {
        afterDate = null;
        fiveDaysAgo = LocalDate.now().minusDays(5);
        fetchOrders(username);
    }

    private void fetchOrders(String username) {
        try {
            ResponseEntity<List<OrderDTO>> response = orderService.fetchUserOrders(username, afterDate, fiveDaysAgo);
            setTableData(Objects.requireNonNull(response.getBody()));
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Server Error",
                    "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTableData(List<OrderDTO> orders) {
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable.setModel(tableModel);
    }

    private void createOrderForm(String username) {
        OrderForm orderForm = new OrderForm(username);
        JDialog dialog = new JDialog();
        dialog.setTitle("Create Order");
        dialog.setModal(true);
        dialog.setContentPane(orderForm);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
