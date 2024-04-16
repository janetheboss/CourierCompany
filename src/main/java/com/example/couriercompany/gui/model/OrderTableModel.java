package com.example.couriercompany.gui.model;

import com.example.couriercompany.payload.OrderDTO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
    private final List<OrderDTO> orders;
    private final String[] columnNames = {"registration", "status","name of product","price","kg","CityTo","CityFrom"};

    public OrderTableModel(List<OrderDTO> orders){this.orders = orders;}

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderDTO order = orders.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> order.registration();
            case 1 -> order.status();
            case 2 -> order.nameOfProduct();
            case 3 -> order.price();
            case 4 -> order.kg();
            case 5 -> order.cityTo();
            case 6 -> order.cityFrom();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public OrderDTO getDeliveryAtRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < orders.size()) {
            return orders.get(rowIndex);
        }
        return null;
    }
}
