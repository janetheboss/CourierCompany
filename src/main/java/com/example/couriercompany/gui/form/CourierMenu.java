package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.model.OrderTableModel;
import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourierMenu extends JPanel {

    private final OrderService orderService;
    private final JTable orderTable;

    public CourierMenu(String username) {
        orderService = new OrderService();

        setLayout(new BorderLayout());

        List<OrderDTO> orders = new ArrayList<>();
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        fetchOrders(username);
    }

    private void fetchOrders(String username) {
        try {
            ResponseEntity<List<OrderDTO>> response = orderService.fetchUserOrders(username, null, null);
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
}
