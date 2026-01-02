package onlinegroceryshopping;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Member {

    private String memberId;
    private java.util.ArrayList<CartItem> cartItems = new java.util.ArrayList<>();

    private static class CartItem {
        String name, brand;
        double price;
        int quantity;
        CartItem(String n, String b, double p, int q) {
            name = n; brand = b; price = p; quantity = q;
        }
    }
    private String getLang(Connection con) {
        String lang = "EN";
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT M_lang FROM member WHERE M_id=?"
            );
            ps.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lang = rs.getString("M_lang");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lang;
    }


    public void Member_menu(String m_id, Statement smt, Connection con) {
        this.memberId = m_id;

        JFrame f = new JFrame("Welcome ");
        f.setLayout(null);
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().setBackground(Color.decode("#f8f9fa"));

        // Title
        JLabel title = new JLabel("Grocery Shopping ");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(350, 20, 400, 40);
        title.setForeground(Color.decode("#035e7b"));
        f.add(title);

        // Member ID display
        JLabel idLabel = new JLabel("Member ID: " + m_id);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setBounds(30, 20, 150, 35);
        idLabel.setForeground(Color.decode("#035e7b"));
        f.add(idLabel);

        // Search TextField
        JLabel searchLabel = new JLabel("Search Products:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setForeground(Color.decode("#035e7b"));
        searchLabel.setBounds(50, 80, 150, 30);
        f.add(searchLabel);

        JTextField searchField = new JTextField(25);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBounds(220, 80, 400, 35);
        searchField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.decode("#035e7b"), 2));
        f.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setBackground(Color.decode("#035e7b"));
        searchBtn.setOpaque(true);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setBounds(640, 80, 100, 35);
        f.add(searchBtn);

        // Product table
        String[] columns = {"Name", "Brand", "Price"};
        Object[][] data = new Object[0][3];
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable productTable = new JTable(model);
        productTable.setFont(new Font("Arial", Font.PLAIN, 13));
        productTable.setRowHeight(15);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.getTableHeader().setBackground(Color.decode("#e3e7af"));
        productTable.getTableHeader().setForeground(Color.decode("#002ec2"));
        productTable.setSelectionBackground(Color.decode("#fff3e0"));
        productTable.setSelectionForeground(Color.BLACK);
        productTable.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(50, 170, 890, 420);
        f.add(scrollPane);

        // View Cart Button
        JButton cartBtn = new JButton("View Cart");
        cartBtn.setFont(new Font("Arial", Font.BOLD, 16));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.setBackground(Color.decode("#28a745"));
        cartBtn.setOpaque(true);
        cartBtn.setBorderPainted(false);
        cartBtn.setFocusPainted(false);
        cartBtn.setBounds(800, 20, 120, 35);
        f.add(cartBtn);

        // View Orders Button
        JButton ordersBtn = new JButton(" My Orders");
        ordersBtn.setFont(new Font("Arial", Font.BOLD, 12));
        ordersBtn.setForeground(Color.BLACK);
        ordersBtn.setBackground(Color.decode("#ffc107"));
        ordersBtn.setOpaque(true);
        ordersBtn.setBorderPainted(false);
        ordersBtn.setFocusPainted(false);
        ordersBtn.setBounds(800, 70, 120, 35);
        ordersBtn.addActionListener(e -> showOrders(f, con));
        f.add(ordersBtn);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBackground(Color.decode("#dc3545"));
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBounds(800, 120, 120, 35);
        f.add(logoutBtn);

        // Filter Button
        JButton filterBtn = new JButton("Filter");
        filterBtn.setFont(new Font("Arial", Font.BOLD, 14));
        filterBtn.setForeground(Color.WHITE);
        filterBtn.setBackground(Color.decode("#17a2b8"));
        filterBtn.setOpaque(true);
        filterBtn.setBorderPainted(false);
        filterBtn.setFocusPainted(false);
        filterBtn.setBounds(200, 610, 150, 40);
        f.add(filterBtn);
        
        JButton supportBtn = new JButton("Feedback");
        supportBtn.setFont(new Font("Arial", Font.BOLD, 13));
        supportBtn.setBounds(530, 610, 150, 40);
        supportBtn.addActionListener(e -> showSupportChat(con));
        f.add(supportBtn);


        // Add to Cart Button
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setBackground(Color.decode("#28a745"));
        addToCartBtn.setOpaque(true);
        addToCartBtn.setBorderPainted(false);
        addToCartBtn.setFocusPainted(false);
        addToCartBtn.setBounds(370, 610, 130, 40);
        f.add(addToCartBtn);
        
        JButton profileBtn = new JButton("Profile");
        profileBtn.setFont(new Font("Arial", Font.BOLD, 14));
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setBackground(Color.decode("#6f42c1"));
        profileBtn.setOpaque(true);
        profileBtn.setBorderPainted(false);
        profileBtn.setFocusPainted(false);
        profileBtn.setBounds(650, 20, 120, 35);
        profileBtn.addActionListener(e -> showProfile(f, con));
        f.add(profileBtn);


        // Listeners
        searchBtn.addActionListener(e -> loadProductsTable(model, con, searchField.getText().trim()));
        filterBtn.addActionListener(e -> loadProductsTable(model, con, searchField.getText().trim()));
        cartBtn.addActionListener(e -> showCart(f, con));

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(f, "Logged out successfully!");
            f.dispose();
        });

        addToCartBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) model.getValueAt(selectedRow, 0);
                String brand = (String) model.getValueAt(selectedRow, 1);
                String priceStr = ((String) model.getValueAt(selectedRow, 2)).replace("₹", "");
                double price = Double.parseDouble(priceStr);
                cartItems.add(new CartItem(name, brand, price, 1));
                JOptionPane.showMessageDialog(f, name + " added to cart!");
            } else {
                JOptionPane.showMessageDialog(f, "Please select a product first!");
            }
        });
        String lang = getLang(con);

        if (lang.equals("HI")) {
            title.setText("किराना खरीदारी");
            searchLabel.setText("उत्पाद खोजें:");
            searchBtn.setText("खोजें");
            cartBtn.setText("कार्ट देखें");
            ordersBtn.setText("मेरे ऑर्डर");
            logoutBtn.setText("लॉग आउट");
            filterBtn.setText("फ़िल्टर");
            addToCartBtn.setText("कार्ट में जोड़ें");
            profileBtn.setText("प्रोफ़ाइल");
        } else {
            title.setText("Grocery Shopping");
            searchLabel.setText("Search Products:");
            searchBtn.setText("Search");
            cartBtn.setText("View Cart");
            ordersBtn.setText("My Orders");
            logoutBtn.setText("Logout");
            filterBtn.setText("Filter");
            addToCartBtn.setText("Add to Cart");
            profileBtn.setText("Profile");
        }


        // Load all products initially
        loadProductsTable(model, con, "");
        f.setVisible(true);
    }

    private void loadProductsTable(DefaultTableModel model, Connection con, String keyword) {
        model.setRowCount(0);
        try {
            if (!keyword.isEmpty()) {
                String sql = "SELECT caname, cabra, capr FROM course WHERE caname LIKE ? OR cabra LIKE ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("caname"),
                        rs.getString("cabra"),
                        "₹" + rs.getString("capr")
                    });
                }
                rs.close();
                pstmt.close();
            } else {
                String sql = "SELECT caname, cabra, capr FROM course";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("caname"),
                        rs.getString("cabra"),
                        "₹" + rs.getString("capr")
                    });
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadCartData(DefaultTableModel cartModel) {
        cartModel.setRowCount(0);
        for (CartItem item : cartItems) {
            cartModel.addRow(new Object[]{
                item.name,
                item.brand,
                "₹" + String.format("%.2f", item.price),
                item.quantity,
                "₹" + String.format("%.2f", item.price * item.quantity)
            });
        }
    }

    private void showCart(JFrame parent, Connection con) {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Cart is empty!");
            return;
        }
        String lang = getLang(con);

        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setLayout(null);
        cartFrame.setSize(900, 600);
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.getContentPane().setBackground(Color.decode("#f8f9fa"));

        JLabel totalLabel = new JLabel("Subtotal: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setBounds(20, 360, 200, 30);
        cartFrame.add(totalLabel);

        JLabel gstLabel = new JLabel("GST (18%): ₹0.00");
        gstLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gstLabel.setBounds(20, 395, 200, 30);
        cartFrame.add(gstLabel);

        JLabel finalTotalLabel = new JLabel("Grand Total: ₹0.00");
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        finalTotalLabel.setForeground(Color.decode("#28a745"));
        finalTotalLabel.setBounds(20, 430, 300, 35);
        cartFrame.add(finalTotalLabel);
      


        String[] cartColumns = {"Name", "Brand", "Price", "Qty", "Total"};

        DefaultTableModel cartModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JTable cartTable = new JTable(cartModel);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 13));
        cartTable.setRowHeight(30);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        cartTable.getTableHeader().setBackground(Color.decode("#e3e7af"));
        cartTable.getTableHeader().setForeground(Color.decode("#002ec2"));

        cartTable.getColumnModel()
                 .getColumn(3)
                 .setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JTextField()));

        cartTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBounds(20, 20, 850, 320);
        cartFrame.add(cartScroll);

        loadCartData(cartModel);

        Runnable updateTotals = () -> {
            double subtotal = 0;
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                try {
                    int qty = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
                    double price = Double.parseDouble(
                            cartModel.getValueAt(i, 2).toString().replace("₹", "").trim()
                    );
                    double rowTotal = price * qty;
                    subtotal += rowTotal;
                    cartModel.setValueAt("₹" + String.format("%.2f", rowTotal), i, 4);

                    // IMPORTANT: sync back to cartItems
                    CartItem item = cartItems.get(i);
                    item.quantity = qty;
                    item.price = price; // in case you ever change price in table
                } catch (Exception ex) {
                }
            }
            double gst = subtotal * 0.18;
            double grandTotal = subtotal + gst;

            totalLabel.setText("Subtotal: ₹" + String.format("%.2f", subtotal));
            gstLabel.setText("GST (18%): ₹" + String.format("%.2f", gst));
            finalTotalLabel.setText("Grand Total: ₹" + String.format("%.2f", grandTotal));
        };

        cartTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    if (cartTable.isEditing()) {
                        cartTable.getCellEditor().stopCellEditing();
                    }
                    updateTotals.run();
                }
            }
        });

        JButton updateBtn = new JButton("Update Cart");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 12));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(Color.decode("#17a2b8"));
        updateBtn.setOpaque(true);
        updateBtn.setBorderPainted(false);
        updateBtn.setFocusPainted(false);
        updateBtn.setBounds(20, 480, 120, 40);
        updateBtn.addActionListener(e -> {
            if (cartTable.isEditing()) {
                cartTable.getCellEditor().stopCellEditing();
            }
            updateTotals.run();
        });
        cartFrame.add(updateBtn);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setBackground(Color.decode("#007bff"));
        checkoutBtn.setOpaque(true);
        checkoutBtn.setBorderPainted(false);
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setBounds(700, 480, 120, 40);
        if (lang.equals("HI")) {
            totalLabel.setText("उप-योग: ₹0.00");
            gstLabel.setText("जीएसटी (18%): ₹0.00");
            finalTotalLabel.setText("कुल राशि: ₹0.00");
            updateBtn.setText("कार्ट अपडेट करें");
            checkoutBtn.setText("ऑर्डर करें");
        } else {
            totalLabel.setText("Subtotal: ₹0.00");
            gstLabel.setText("GST (18%): ₹0.00");
            finalTotalLabel.setText("Grand Total: ₹0.00");
            updateBtn.setText("Update Cart");
            checkoutBtn.setText("Checkout");
        }
        checkoutBtn.addActionListener(e -> {
            if (cartTable.isEditing()) {
                cartTable.getCellEditor().stopCellEditing();
            }
            updateTotals.run();

            // Confirm
            int confirm = JOptionPane.showConfirmDialog(
                    cartFrame,
                    "Place this order?",
                    "Confirm Checkout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                // 1. Start transaction
                con.setAutoCommit(false);

                // 2. Insert into orders table
                String insertOrderSql =
                    "INSERT INTO orders (m_id, order_date, status, total_amount) " +
                    "VALUES (?, NOW(), ?, ?)";
                try (PreparedStatement psOrder = con.prepareStatement(
                        insertOrderSql,
                        Statement.RETURN_GENERATED_KEYS)) {

                    psOrder.setInt(1, Integer.parseInt(memberId));     // m_id
                    psOrder.setString(2, "Ordered");
                    double subtotal = getCartSubtotal();
                    double gst = subtotal * 0.18;
                    psOrder.setDouble(3, subtotal + gst);
                   

                    int affected = psOrder.executeUpdate();
                    if (affected == 0) {
                        throw new SQLException("Creating order failed, no rows affected.");
                    }

                    // 3. Get generated order_id
                    int orderId;
                    try (ResultSet keys = psOrder.getGeneratedKeys()) {
                        if (keys.next()) {
                            orderId = keys.getInt(1);
                        } else {
                            throw new SQLException("Creating order failed, no ID obtained.");
                        }
                    }

                    // 4. Insert items into order_items
                    String insertItemSql =
                        "INSERT INTO order_items (order_id, product_name, quantity, unit_price) " +
                        "VALUES (?, ?, ?, ?)";
                    try (PreparedStatement psItem = con.prepareStatement(insertItemSql)) {
                        for (CartItem item : cartItems) {
                            psItem.setInt(1, orderId);
                            psItem.setString(2, item.name);
                            psItem.setInt(3, item.quantity);
                            psItem.setDouble(4, item.price);
                            psItem.addBatch();
                        }
                        psItem.executeBatch();
                    }
                }

                // 5. Commit transaction
                con.commit();
                con.setAutoCommit(true);

                JOptionPane.showMessageDialog(cartFrame, "Order placed successfully!");
                cartItems.clear();
                cartFrame.dispose();

            } catch (Exception ex2) {
                try { con.rollback(); } catch (SQLException ignore) {}
                JOptionPane.showMessageDialog(cartFrame, "Error placing order: " + ex2.getMessage());
                ex2.printStackTrace();
            }
        });

        cartFrame.add(checkoutBtn);

        updateTotals.run();
        cartFrame.setVisible(true);
    }
    private double getCartSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.price * item.quantity;
        }
        return subtotal;
    }

    private void showOrders(JFrame parent, Connection con) {
    	String lang = getLang(con);

        JFrame ordersFrame = new JFrame("My Orders");
        ordersFrame.setLayout(null);
        ordersFrame.setSize(900, 600);
        ordersFrame.setLocationRelativeTo(null);
        ordersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ordersFrame.getContentPane().setBackground(Color.decode("#f8f9fa"));
      


        JLabel title = new JLabel("My Order History");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(20, 10, 300, 30);
        ordersFrame.add(title);
        if (lang.equals("HI")) {
            title.setText("मेरे ऑर्डर");
        } else {
            title.setText("My Order History");
        }

        String[] cols = {"Order No", "Products", "Order Date","Price", "Status", "Action"};
        DefaultTableModel ordersModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable ordersTable = new JTable(ordersModel);
        ordersTable.setRowHeight(30);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 13));
        ordersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        ordersTable.getTableHeader().setBackground(Color.decode("#e3e7af"));
        ordersTable.getTableHeader().setForeground(Color.decode("#002ec2"));

        JScrollPane scroll = new JScrollPane(ordersTable);
        scroll.setBounds(20, 60, 850, 430);
        ordersFrame.add(scroll);

        loadOrdersData(ordersModel, con);

        ordersTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        ordersTable.getColumnModel().getColumn(5).setCellEditor(
            new ButtonEditor(new JCheckBox(), con, this.memberId, ordersModel)
        );
        if (lang.equals("HI")) {
            ordersTable.getColumnModel().getColumn(5).setHeaderValue("रद्द करें");
            ordersTable.getTableHeader().repaint();
        }


        ordersFrame.setVisible(true);
    }

    private void loadOrdersData(DefaultTableModel model, Connection con) {
        model.setRowCount(0);
        String sql =
        	
        	    "SELECT o.order_id, " +
        	    "       GROUP_CONCAT(oi.product_name SEPARATOR ', ') AS products, " +
        	    "       o.order_date, o.status, o.total_amount " +
        	    "FROM orders o " +
        	    "JOIN order_items oi ON o.order_id = oi.order_id " +
        	    "WHERE o.m_id = ? " +
        	    "GROUP BY o.order_id, o.order_date, o.status, o.total_amount " +
        	    "ORDER BY o.order_date DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Timestamp ts = rs.getTimestamp("order_date");
                String dateOnly = new java.text.SimpleDateFormat("yyyy-MM-dd").format(ts);

                model.addRow(new Object[]{
                    rs.getInt("order_id"),                   // Order No
                    rs.getString("products"),                // Products
                    dateOnly,                                // Order Date (no time)
                    "₹" + String.format("%.2f", rs.getDouble("total_amount")), // Price
                    rs.getString("status"),                  // Status
                    "Request Cancel"                         // Action
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading orders: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setBackground(Color.decode("#ffc107"));
            setForeground(Color.BLACK);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;
        private JTable table;
        private Connection con;
        private String memberId;
        private DefaultTableModel model;

        public ButtonEditor(JCheckBox checkBox, Connection con, String memberId, DefaultTableModel model) {
            super(checkBox);
            this.con = con;
            this.memberId = memberId;
            this.model = model;
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.decode("#ffc107"));
            button.setForeground(Color.BLACK);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int col) {
            this.table = table;
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int orderId = (int) model.getValueAt(row, 0);
                String status = model.getValueAt(row, 4).toString();
                if (!status.equalsIgnoreCase("Ordered")) {
                    JOptionPane.showMessageDialog(button, "Only 'Ordered' status can be cancelled.");
                } else {
                    int confirm = JOptionPane.showConfirmDialog(
                            button,
                            "Send cancellation request for Order #" + orderId + "?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        requestCancel(orderId);
                        model.setValueAt("Cancellation Requested", row, 3);
                    }
                }
            }
            clicked = false;
            return label;
        }

        private void requestCancel(int orderId) {
            String sql = "UPDATE orders SET status = ? WHERE order_id = ? AND m_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "Cancellation Requested");
                ps.setInt(2, orderId);
                ps.setInt(3, Integer.parseInt(memberId));
                int updated = ps.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(button, "Cancellation request sent.");
                } else {
                    JOptionPane.showMessageDialog(button, "Unable to send request.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(button, "DB error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
    private void showProfile(JFrame parent, Connection con) {

        JFrame pf = new JFrame("My Profile");
        pf.setSize(520, 550);
        pf.setLayout(null);
        pf.setLocationRelativeTo(parent);
        pf.getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Edit Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(170, 15, 200, 30);
        pf.add(title);

        JLabel nameLbl = new JLabel("Name:");
        JLabel addLbl = new JLabel("Address:");
        JLabel streetLbl = new JLabel("Street:");
        JLabel cityLbl = new JLabel("City:");
        JLabel zipLbl = new JLabel("Zip Code:");

        nameLbl.setBounds(40, 70, 100, 25);
        addLbl.setBounds(40, 110, 100, 25);
        streetLbl.setBounds(40, 150, 100, 25);
        cityLbl.setBounds(40, 190, 100, 25);
        zipLbl.setBounds(40, 230, 100, 25);

        JTextField nameTxt = new JTextField();
        JTextField addTxt = new JTextField();
        JTextField streetTxt = new JTextField();
        JTextField cityTxt = new JTextField();
        JTextField zipTxt = new JTextField();
        
        JButton changePassBtn = new JButton("Change Password");
        changePassBtn.setBounds(170, 350, 180, 40);
       // changePassBtn.setBackground(Color.decode("#e3e7af"));
      //  changePassBtn.setForeground(Color.black);
        changePassBtn.setFont(new Font("Arial", Font.BOLD, 13));
       // changePassBtn.setBorderPainted(false);
      //  changePassBtn.setOpaque(true);
      //  changePassBtn.setContentAreaFilled(true);
      //  changePassBtn.setBorderPainted(true);

        pf.add(changePassBtn);   // if inside profile window


        changePassBtn.addActionListener(e -> showChangePassword(con));


        nameTxt.setBounds(160, 70, 250, 25);
        addTxt.setBounds(160, 110, 250, 25);
        streetTxt.setBounds(160, 150, 250, 25);
        cityTxt.setBounds(160, 190, 250, 25);
        zipTxt.setBounds(160, 230, 250, 25);

        pf.add(nameLbl); pf.add(nameTxt);
        pf.add(addLbl); pf.add(addTxt);
        pf.add(streetLbl); pf.add(streetTxt);
        pf.add(cityLbl); pf.add(cityTxt);
        pf.add(zipLbl); pf.add(zipTxt);

        // 🔹 LOAD MEMBER DATA
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT M_name, M_add, M_Street, M_City, M_zip FROM member WHERE M_id = ?"
            );
            ps.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameTxt.setText(rs.getString("M_name"));
                addTxt.setText(rs.getString("M_add"));
                streetTxt.setText(rs.getString("M_Street"));
                cityTxt.setText(rs.getString("M_City"));
                zipTxt.setText(rs.getString("M_zip"));
            }

            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(170, 300, 180, 40);
      //  saveBtn.setBackground(Color.decode("#28a745"));
     //   saveBtn.setForeground(Color.black);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
     //   saveBtn.setBorderPainted(false);
     //   saveBtn.setOpaque(true);
     //   saveBtn.setContentAreaFilled(true);
        
        JButton langBtn = new JButton("Change Language");
        langBtn.setBounds(170, 400, 180, 40);
      //  langBtn.setBackground(Color.decode("#007bff"));
       // langBtn.setForeground(Color.black);
        langBtn.setFont(new Font("Arial", Font.BOLD, 13));
    //    langBtn.setBorderPainted(true);
    //    langBtn.setOpaque(true);
   //     langBtn.setContentAreaFilled(true);
        langBtn.addActionListener(e -> showLanguageSettings(con));

        saveBtn.addActionListener(e -> {
            try {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE member SET M_name=?, M_add=?, M_Street=?, M_City=?, M_zip=? WHERE M_id=?"
                );

                ps.setString(1, nameTxt.getText().trim());
                ps.setString(2, addTxt.getText().trim());
                ps.setString(3, streetTxt.getText().trim());
                ps.setString(4, cityTxt.getText().trim());
                ps.setString(5, zipTxt.getText().trim());
                ps.setInt(6, Integer.parseInt(memberId));

                ps.executeUpdate();
                ps.close();

                JOptionPane.showMessageDialog(pf, "Profile updated successfully!");
                pf.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pf, "Error updating profile");
                ex.printStackTrace();
            }
        });

        pf.add(saveBtn);
        pf.add(changePassBtn);
        pf.add(langBtn);

        pf.revalidate();
        pf.repaint();

        pf.setVisible(true);
    }
    private void showChangePassword(Connection con) {

        JFrame cp = new JFrame("Change Password");
        cp.setSize(400, 300);
        cp.setLayout(null);
        cp.setLocationRelativeTo(null);
        cp.getContentPane().setBackground(Color.WHITE);

        JLabel oldLbl = new JLabel("Old Password:");
        JLabel newLbl = new JLabel("New Password:");
        JLabel confirmLbl = new JLabel("Confirm Password:");

        oldLbl.setBounds(40, 40, 120, 25);
        newLbl.setBounds(40, 80, 120, 25);
        confirmLbl.setBounds(40, 120, 120, 25);

        javax.swing.JPasswordField oldTxt = new javax.swing.JPasswordField();
        javax.swing.JPasswordField newTxt = new javax.swing.JPasswordField();
        javax.swing.JPasswordField confirmTxt = new javax.swing.JPasswordField();

        oldTxt.setBounds(170, 40, 160, 25);
        newTxt.setBounds(170, 80, 160, 25);
        confirmTxt.setBounds(170, 120, 160, 25);

        JButton saveBtn = new JButton("Update");
        saveBtn.setBounds(140, 180, 120, 35);
     //   saveBtn.setBackground(Color.decode("#28a745"));  //==========================================================================================================================================================================================================================================
     //   saveBtn.setForeground(Color.black);
     //   saveBtn.setBorderPainted(false);

        saveBtn.addActionListener(e -> {
            try {
                String oldPass = new String(oldTxt.getPassword());
                String newPass = new String(newTxt.getPassword());
                String confirmPass = new String(confirmTxt.getPassword());

                if (!newPass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(cp, "Passwords do not match!");
                    return;
                }

                PreparedStatement ps = con.prepareStatement(
                    "SELECT M_pass FROM member WHERE M_id = ?"
                );
                ps.setInt(1, Integer.parseInt(memberId));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    if (!rs.getString("M_pass").equals(oldPass)) {
                        JOptionPane.showMessageDialog(cp, "Old password incorrect!");
                        return;
                    }
                }

                rs.close();
                ps.close();

                PreparedStatement update = con.prepareStatement(
                    "UPDATE member SET M_pass=? WHERE M_id=?"
                );
                update.setString(1, newPass);
                update.setInt(2, Integer.parseInt(memberId));
                update.executeUpdate();
                update.close();

                JOptionPane.showMessageDialog(cp, "Password updated successfully!");
                cp.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(cp, "Error updating password");
            }
        });

        cp.add(oldLbl); cp.add(oldTxt);
        cp.add(newLbl); cp.add(newTxt);
        cp.add(confirmLbl); cp.add(confirmTxt);
        cp.add(saveBtn);

        cp.setVisible(true);
    }
    
    private void showLanguageSettings(Connection con) {

        JFrame lf = new JFrame("Language Settings");
        lf.setSize(300, 200);
        lf.setLayout(null);
        lf.setLocationRelativeTo(null);
        lf.getContentPane().setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Select Language:");
        lbl.setBounds(40, 30, 150, 25);

        javax.swing.JComboBox<String> langBox =
                new javax.swing.JComboBox<>(new String[]{"English", "Hindi"});
        langBox.setBounds(40, 60, 200, 25);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(90, 110, 100, 30);
      //  saveBtn.setBackground(Color.decode("#007bff"));  //========================================================================================================================================================================================================================
      //  saveBtn.setForeground(Color.black);
     //   saveBtn.setBorderPainted(false);

        // Load saved language
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT M_lang FROM member WHERE M_id=?"
            );
            ps.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                langBox.setSelectedItem(
                    rs.getString("M_lang").equals("HI") ? "Hindi" : "English"
                );
            }
            rs.close(); ps.close();
        } catch (Exception ex) {}

        saveBtn.addActionListener(e -> {
            try {
                String lang = langBox.getSelectedItem().equals("Hindi") ? "HI" : "EN";

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE member SET M_lang=? WHERE M_id=?"
                );
                ps.setString(1, lang);
                ps.setInt(2, Integer.parseInt(memberId));
                ps.executeUpdate();
                ps.close();

                applyLanguage(lang);
                JOptionPane.showMessageDialog(lf, "Language updated!");
                lf.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        lf.add(lbl);
        lf.add(langBox);
        lf.add(saveBtn);
        lf.setVisible(true);
    }
    private void applyLanguage(String lang) {
        JOptionPane.showMessageDialog(
            null,
            lang.equals("HI")
                ? "भाषा बदल दी गई है। कृपया विंडो दोबारा खोलें।"
                : "Language changed. Please reopen the window."
        );
    }
    private void showSupportChat(Connection con) {

        JFrame frame = new JFrame("Support & Help");
        frame.setSize(520, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 247, 250));

        /* ================= TOP BAR ================= */
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, 520, 60);
        topBar.setBackground(Color.WHITE);

        JLabel title = new JLabel("Support & Help");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBounds(20, 15, 200, 30);

        JComboBox<String> typeBox =
                new JComboBox<>(new String[]{"Feedback", "Report"});
        typeBox.setBounds(360, 15, 120, 30);

        topBar.add(title);
        topBar.add(typeBox);
        frame.add(topBar);

        /* ================= CHAT AREA ================= */
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chatArea.setBackground(Color.WHITE);

        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBounds(15, 75, 490, 380);
        chatScroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        frame.add(chatScroll);

        /* ================= INPUT BAR ================= */
        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(0, 470, 520, 80);
        inputPanel.setBackground(Color.WHITE);

        JTextField messageField = new JTextField();
        messageField.setBounds(20, 20, 360, 40);
        messageField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton sendBtn = new JButton("Send");
        sendBtn.setBounds(390, 20, 90, 40);
        sendBtn.setFont(new Font("SansSerif", Font.BOLD, 13));

        inputPanel.add(messageField);
        inputPanel.add(sendBtn);
        frame.add(inputPanel);

        /* ================= LOAD CHAT ================= */
        Runnable loadChat = () -> {
            chatArea.setText("");
            try {
                PreparedStatement ps = con.prepareStatement(
                    "SELECT sender, message FROM support_chat WHERE m_id=? ORDER BY created_at"
                );
                ps.setInt(1, Integer.parseInt(memberId));
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getString("sender").equals("ADMIN")) {
                        chatArea.append("Admin: " + rs.getString("message") + "\n\n");
                    } else {
                        chatArea.append("You: " + rs.getString("message") + "\n\n");
                    }
                }
                rs.close();
                ps.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        loadChat.run();

        /* ================= SEND MESSAGE ================= */
        sendBtn.addActionListener(e -> {
            String msg = messageField.getText().trim();
            if (msg.isEmpty()) return;

            try {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO support_chat (m_id, sender, type, message) VALUES (?, 'MEMBER', ?, ?)"
                );
                ps.setInt(1, Integer.parseInt(memberId));
                ps.setString(2, typeBox.getSelectedItem().toString().toUpperCase());
                ps.setString(3, msg);
                ps.executeUpdate();
                ps.close();

                messageField.setText("");
                loadChat.run();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }



    }

