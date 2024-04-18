package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.model.OrderTableModel;
import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourierMenu extends JPanel {
    private final JTable orderTable;
    private final OrderService service = new OrderService();
    private final JTextField usernameField;
    private final JButton filterButton;
    private long statusId;

    public CourierMenu() {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel();
        usernameField = new JTextField(20);
        filterButton = new JButton("Filter Orders");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                fetchOrders(username);
            }
        });
        filterPanel.add(new JLabel("Enter Username:"));
        filterPanel.add(usernameField);
        filterPanel.add(filterButton);
        add(filterPanel, BorderLayout.NORTH);

        List<OrderDTO> orders = new ArrayList<>();
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = orderTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < orderTable.getRowCount() && evt.getClickCount() == 2) {
                    OrderDTO selectedOrder = ((OrderTableModel) orderTable.getModel()).getDeliveryAtRow(row);
                    if (selectedOrder != null) {
                        openOrderDetailsPanel(selectedOrder);
                    }
                }
            }
        });

        fetchOrders(null);
    }

    public void setTableData(List<OrderDTO> orders) {
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable.setModel(tableModel);
    }

    private void fetchOrders(String username) {
        try {
            ResponseEntity<List<OrderDTO>> response;
            if (username != null && !username.isEmpty()) {
                response = service.fetchOrdersFiltered(username, statusId);
            } else {
                response = service.fetchOrdersFiltered(null, statusId);
            }
            setTableData(Objects.requireNonNull(response.getBody()));
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Server Error",
                    "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openOrderDetailsPanel(OrderDTO orderDTO) {
        OrderDetailsPanel detailsPanel = new OrderDetailsPanel(orderDTO);
        JDialog dialog = new JDialog();
        dialog.setTitle("Order Information");
        dialog.setModal(true);
        dialog.setContentPane(detailsPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
