/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.doppler.views;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import jakarta.persistence.criteria.Order;
import org.doppler.dao.*;
import org.doppler.models.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

/**
 * @author Eddie
 */
public class AddOrder extends javax.swing.JFrame {
    private Map<String, Double> productPrices;
    private Map<String, Double> servicePrices;
    private List<Customer> customers;
    private List<OrderStatus> orderStatus;
    private List<Product> products;
    private boolean isEditing;
    private SaleOrder existingOrder;

    /**
     * Creates new form AddOrder
     */
    public AddOrder() {
        initComponents();
        loadProductPrices();
        loadServicePrices();
        loadCustomers();
        loadOrderStatus();
    }
    public AddOrder(SaleOrder order) {
        this.isEditing = true;
        this.existingOrder = order;
        initComponents();
        loadProductPrices();
        loadServicePrices();
        loadCustomers();
        loadOrderStatus();
        loadEditWindow();
    }

    private void loadEditWindow() {
        // Fill in the form fields with the existing order data
        jComboBoxCustomer.setSelectedItem(this.existingOrder.getCustomerId().getName());
        jComboBoxOrderStatus.setSelectedItem(this.existingOrder.getOrderStatusId().getOrderStatusName());
        date.setText(this.existingOrder.getDate().toString());
        total.setText(this.existingOrder.getTotal().toString());
        tax.setText(this.existingOrder.getTax().toString());
        discount.setText(this.existingOrder.getDiscount().toString());
        jCheckBoxInstallation.setSelected(this.existingOrder.isRequiresInstallation());

        // Load the products and services associated with the order
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();

        for (int i = 0; i < jTableProductsAdded.getRowCount(); i++) {
            int productId = (Integer) jTableProductsAdded.getValueAt(i, 0);
            int quantity = (Integer) jTableProductsAdded.getValueAt(i, 1);
            BigDecimal total = BigDecimal.valueOf((Double) jTableProductsAdded.getValueAt(i, 2));

            Product productObject = products.stream()
                    .filter(product -> product.getId() == productId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        }
        DefaultTableModel productsModel = (DefaultTableModel) jTableProductsAdded.getModel();
        for (Product product : products) {
            Object[] row = new Object[3];
            row[0] = product.getId();
            row[1] = product.getQuantity();
            row[2] = product.getPrice();
            productsModel.addRow(row);
        }

        // Fill jTableServicesAdded with the services of the existing order
        ServiceDao serviceDao = new ServiceDao();
        List<Service> services = serviceDao.getAll();

        for (int i = 0; i < jTableServicesAdded.getRowCount(); i++) {
            int serviceId = (Integer) jTableServicesAdded.getValueAt(i, 0);
            BigDecimal total = BigDecimal.valueOf((Double) jTableServicesAdded.getValueAt(i, 1));

            Service serviceObject = services.stream()
                    .filter(service -> service.getId() == serviceId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Service not found: " + serviceId));
        }
        DefaultTableModel servicesModel = (DefaultTableModel) jTableServicesAdded.getModel();
        for (Service service : services) {
            Object[] row = new Object[2];
            row[0] = service.getId();
            row[1] = service.getPrice();
            servicesModel.addRow(row);
        }
    }

    private void loadProductPrices() {
        productPrices = new HashMap<>();
        try {
            ProductDao productDao = new ProductDao();
            List<Product> products = productDao.getAll();
            for (Product product : products) {
                productPrices.put(product.getProductName(), product.getPrice().doubleValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrderStatus() {
        try {
            OrderStatusDao orderStatusDao = new OrderStatusDao();
            orderStatus = orderStatusDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadCustomers() {
        try {
            CustomerDao customerDao = new CustomerDao();
            customers = customerDao.getAll();
            for (Customer customer : customers) {
                String customerName = customer.getName();
                jComboBoxCustomer.addItem(customerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadServicePrices() {
        servicePrices = new HashMap<>();
        try {
            ServiceDao serviceDao = new ServiceDao();
            List<Service> services = serviceDao.getAll();
            for (Service service : services) {
                servicePrices.put(service.getServiceName(), service.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateAndShowTotal() {
        double totalCost = 0;

        // Sumar el costo total de los productos
        for (int i = 0; i < jTableProductsAdded.getRowCount(); i++) {
            Object value = jTableProductsAdded.getValueAt(i, 2);
            if (value != null) {
                totalCost += ((Number) value).doubleValue();
            }
        }

        // Sumar el precio de los servicios
        for (int i = 0; i < jTableServicesAdded.getRowCount(); i++) {
            Object value = jTableServicesAdded.getValueAt(i, 1);
            if (value != null) {
                totalCost += ((Number) value).doubleValue();
            }
        }

        // Mostrar el total en el campo de texto 'total'
        total.setText(String.valueOf(totalCost));
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        customerTxt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProductsAdded = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableServicesAdded = new javax.swing.JTable();
        jComboBoxCustomer = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_CreateOrder = new javax.swing.JButton();
        btn_addServiceToOrder = new javax.swing.JButton();
        btn_addProductToOrder = new javax.swing.JButton();
        jComboBoxOrderStatus = new javax.swing.JComboBox<>();
        customerTxt1 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        customerTxt2 = new javax.swing.JLabel();
        tax = new javax.swing.JTextField();
        customerTxt3 = new javax.swing.JLabel();
        discount = new javax.swing.JTextField();
        customerTxt4 = new javax.swing.JLabel();
        customerTxt5 = new javax.swing.JLabel();
        jCheckBoxInstallation = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        customerTxt.setText("Customer");

        jLabel2.setText("Order Status");

        jTableProductsAdded.setBackground(new java.awt.Color(255, 255, 255));
        jTableProductsAdded.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableProductsAdded.setForeground(new java.awt.Color(0, 0, 0));
        jTableProductsAdded.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Item", "Quantity", "Total"
                }
        ));
        jScrollPane1.setViewportView(jTableProductsAdded);

        jTableServicesAdded.setBackground(new java.awt.Color(255, 255, 255));
        jTableServicesAdded.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableServicesAdded.setForeground(new java.awt.Color(0, 0, 0));
        jTableServicesAdded.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane2.setViewportView(jTableServicesAdded);

        jComboBoxCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jComboBoxCustomer.removeAllItems();
        try {
            CustomerDao customerDao = new CustomerDao();

            List<Customer> customers = customerDao.getAll();


            for (Customer customer : customers) {

                String customerName = customer.getName();

                jComboBoxCustomer.addItem(customerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jLabel1.setText("products added ");

        jLabel3.setText("Services added ");

        btn_CreateOrder.setBackground(new java.awt.Color(140, 55, 182));
        btn_CreateOrder.setForeground(new java.awt.Color(255, 255, 255));
        btn_CreateOrder.setText("Create Order");
        btn_CreateOrder.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_CreateOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CreateOrderActionPerformed(evt);
            }
        });

        btn_addServiceToOrder.setBackground(new java.awt.Color(140, 55, 182));
        btn_addServiceToOrder.setForeground(new java.awt.Color(255, 255, 255));
        btn_addServiceToOrder.setText("Add Service");
        btn_addServiceToOrder.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_addServiceToOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addServiceToOrderActionPerformed(evt);
            }
        });

        btn_addProductToOrder.setBackground(new java.awt.Color(140, 55, 182));
        btn_addProductToOrder.setForeground(new java.awt.Color(255, 255, 255));
        btn_addProductToOrder.setText("Add Product");
        btn_addProductToOrder.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_addProductToOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addProductToOrderActionPerformed(evt);
            }
        });

        jComboBoxOrderStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jComboBoxOrderStatus.removeAllItems();
        try {
            OrderStatusDao orderStatusDao = new OrderStatusDao();
            List<OrderStatus> orderStatuses = orderStatusDao.getAll();

            for (OrderStatus orderStatus : orderStatuses) {
                jComboBoxOrderStatus.addItem(orderStatus.getOrderStatusName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerTxt1.setText("Date");

        date.setBackground(new java.awt.Color(204, 204, 204));
        date.setForeground(new java.awt.Color(0, 0, 0));
        date.setText("");

        total.setBackground(new java.awt.Color(204, 204, 204));
        total.setForeground(new java.awt.Color(0, 0, 0));
        total.setText("");

        customerTxt2.setText("Total");

        tax.setBackground(new java.awt.Color(204, 204, 204));
        tax.setForeground(new java.awt.Color(0, 0, 0));
        tax.setText("");

        customerTxt3.setText("Tax");

        discount.setBackground(new java.awt.Color(204, 204, 204));
        discount.setForeground(new java.awt.Color(0, 0, 0));
        discount.setText("");

        customerTxt4.setText("Discount");

        customerTxt5.setText("Requires Installation");

        jCheckBoxInstallation.setText("Installation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_CreateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(customerTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(customerTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(customerTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(tax, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(customerTxt3, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_addServiceToOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(btn_addProductToOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(195, 195, 195)
                                                                .addComponent(jLabel3))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(412, 412, 412)
                                                                .addComponent(jLabel1))
                                                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(customerTxt5, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(customerTxt4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jComboBoxOrderStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jCheckBoxInstallation))))
                                .addGap(0, 125, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(customerTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(customerTxt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(customerTxt5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBoxInstallation)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(customerTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(customerTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBoxOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tax, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32)
                                                .addComponent(jLabel3))
                                        .addComponent(btn_addProductToOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_addServiceToOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btn_CreateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(182, 182, 182))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addServiceToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addServiceToOrderActionPerformed
        AddToTableService addToTableServiceWindow = new AddToTableService(jTableServicesAdded, servicePrices);
        addToTableServiceWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                calculateAndShowTotal();
            }
        });
        addToTableServiceWindow.setVisible(true);
    }//GEN-LAST:event_btn_addServiceToOrderActionPerformed

    private void btn_CreateOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CreateOrderActionPerformed
        SaleOrderDao saleOrderDao = new SaleOrderDao();
        try {
            Customer selectedCustomer = getSelectedCustomer();
            OrderStatus selectedOrderStatus = getSelectedOrderStatus();
            BigDecimal total = getTotal();
            boolean requiresInstallation = jCheckBoxInstallation.isSelected();

            SaleOrder order;
            if (isEditing) {
                // Update the existing order
                order = this.existingOrder;
                order.setCustomerId(selectedCustomer);
                order.setOrderStatusId(selectedOrderStatus);
                order.setTotal(total);
                order.setRequiresInstallation(requiresInstallation);
                saleOrderDao.update(order);
                JOptionPane.showMessageDialog(this, "Order updated successfully!");
            } else {
                // Create a new order
                order = createOrder(selectedCustomer, selectedOrderStatus, total, requiresInstallation);
                saveOrder(order);
            }

            addProductsToOrder(order);
            addServicesToOrder(order);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Failed to create order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_CreateOrderActionPerformed

    private Customer getSelectedCustomer() {
        String selectedCustomerName = (String) jComboBoxCustomer.getSelectedItem();
        return customers.stream()
                .filter(customer -> customer.getName().equals(selectedCustomerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    private OrderStatus getSelectedOrderStatus() {
        String selectedOrderStatusName = (String) jComboBoxOrderStatus.getSelectedItem();
        return orderStatus.stream()
                .filter(orderStatus -> orderStatus.getOrderStatusName().equals(selectedOrderStatusName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order status not found"));
    }

    private BigDecimal getTotal() {
        try {
            String totalText = this.total.getText().replace(',', '.');
            String discountText = this.discount.getText().replace(',', '.');
            String taxText = this.tax.getText().replace(',', '.');
            BigDecimal totalAmount = new BigDecimal(totalText);
            BigDecimal discountAmount = new BigDecimal(discountText);
            BigDecimal taxAmount = new BigDecimal(taxText);
            return totalAmount.subtract(discountAmount).add(taxAmount);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Please enter a valid number for total, discount, and tax");
        }
    }

    private SaleOrder createOrder(Customer selectedCustomer, OrderStatus selectedOrderStatus, BigDecimal total, boolean requiresInstallation) {
        SaleOrder order = new SaleOrder();
        order.setCustomerId(selectedCustomer);
        order.setOrderStatusId(selectedOrderStatus);
        order.setDate(new Date());
        order.setTotal(total);
        String taxText = this.tax.getText().replace(',', '.');
        BigDecimal taxAmount = new BigDecimal(taxText);
        order.setTax(taxAmount);
        BigDecimal discountAmount = new BigDecimal(this.discount.getText().replace(',', '.'));
        order.setDiscount(discountAmount);
        order.setRequiresInstallation(requiresInstallation);
        return order;
    }

    private void saveOrder(SaleOrder order) {
        SaleOrderDao saleOrderDao = new SaleOrderDao();
        saleOrderDao.save(order);
        JOptionPane.showMessageDialog(this, "Order created successfully!");
    }

    private void addProductsToOrder(SaleOrder order) {
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();

        for (int i = 0; i < jTableProductsAdded.getRowCount(); i++) {
            String productName = (String) jTableProductsAdded.getValueAt(i, 0);
            int quantity = (Integer) jTableProductsAdded.getValueAt(i, 1);
            BigDecimal total = BigDecimal.valueOf((Double) jTableProductsAdded.getValueAt(i, 2));

            Product productObject = products.stream()
                    .filter(product -> product.getProductName().equals(productName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

            ProductOrderDetail productOrderDetail = new ProductOrderDetail();
            productOrderDetail.setOrderId(order);
            productOrderDetail.setProductId(productObject);
            productOrderDetail.setQuantity(quantity);
            productOrderDetail.setSubtotal(total);

            ProductOrderDetailDao productOrderDetailDao = new ProductOrderDetailDao();
            productOrderDetailDao.save(productOrderDetail);
        }

    }

    private void addServicesToOrder(SaleOrder order) {
        ServiceDao serviceDao = new ServiceDao();
        List<Service> services = serviceDao.getAll();

        ServiceStatus defaultServiceStatus = new ServiceStatus();
        defaultServiceStatus.setId(1);

        for (int i = 0; i < jTableServicesAdded.getRowCount(); i++) {
            String serviceName = (String) jTableServicesAdded.getValueAt(i, 0);
            BigDecimal total = BigDecimal.valueOf((Double) jTableServicesAdded.getValueAt(i, 1));

            Service serviceObject = services.stream()
                    .filter(service -> service.getServiceName().equals(serviceName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Service not found: " + serviceName));

            ServiceOrderDetail serviceOrderDetail = new ServiceOrderDetail();
            serviceOrderDetail.setOrderId(order);
            serviceOrderDetail.setServiceId(serviceObject);
            serviceOrderDetail.setSubtotal(total);
            serviceOrderDetail.setServiceStatusId(defaultServiceStatus);

            ServiceOrderDetailDao serviceOrderDetailDao = new ServiceOrderDetailDao();
            serviceOrderDetailDao.save(serviceOrderDetail);
        }
    }

    private void btn_addProductToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addProductToOrderActionPerformed
        AddToTableProduct addToTableProductWindow = new AddToTableProduct(jTableProductsAdded, productPrices);
        addToTableProductWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                calculateAndShowTotal();
            }
        });
        addToTableProductWindow.setVisible(true);
    }//GEN-LAST:event_btn_addProductToOrderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMaterialLighterIJTheme.setup();
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CreateOrder;
    private javax.swing.JButton btn_addProductToOrder;
    private javax.swing.JButton btn_addServiceToOrder;
    private javax.swing.JLabel customerTxt;
    private javax.swing.JLabel customerTxt1;
    private javax.swing.JLabel customerTxt2;
    private javax.swing.JLabel customerTxt3;
    private javax.swing.JLabel customerTxt4;
    private javax.swing.JLabel customerTxt5;
    private javax.swing.JTextField date;
    private javax.swing.JTextField discount;
    private javax.swing.JCheckBox jCheckBoxInstallation;
    private javax.swing.JComboBox<String> jComboBoxCustomer;
    private javax.swing.JComboBox<String> jComboBoxOrderStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableProductsAdded;
    private javax.swing.JTable jTableServicesAdded;
    private javax.swing.JTextField tax;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
