package com.example.couriercompany.gui.form;

import com.example.couriercompany.gui.form.OrderDetailsPanel;
import com.example.couriercompany.gui.model.OrderTableModel;
import com.example.couriercompany.gui.service.OrderService;
import com.example.couriercompany.payload.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminMenu extends JPanel {
    private final JTable orderTable;
    private final OrderService service = new OrderService();
    private final JTextField usernameField;
    private final JButton filterButton;
    private final JButton deleteButton;
    private long statusId;

    public AdminMenu() {
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
        deleteButton = new JButton("Delete Order");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        filterPanel.add(new JLabel("Enter Username:"));
        filterPanel.add(usernameField);
        filterPanel.add(filterButton);
        filterPanel.add(deleteButton);
        add(filterPanel, BorderLayout.NORTH);

        List<OrderDTO> orders = new ArrayList<>();
        OrderTableModel tableModel = new OrderTableModel(orders);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
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

    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            OrderDTO selectedOrder = ((OrderTableModel) orderTable.getModel()).getDeliveryAtRow(selectedRow);
            Long orderId = selectedOrder.id();
            ResponseEntity<Void> response = service.fetchDeleteOrder(orderId);
            JOptionPane.showMessageDialog(this, "Order deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            fetchOrders(usernameField.getText());
        } catch (HttpClientErrorException ex) {
            JOptionPane.showMessageDialog(this, ex.getResponseBodyAsString(), "Server Error", JOptionPane.ERROR_MESSAGE);
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
