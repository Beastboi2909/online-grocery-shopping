package onlinegroceryshopping;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;


import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import net.proteanit.sql.DbUtils;

public class adminwork {
	 private java.util.List<Object[]> productData = new java.util.ArrayList<>();
  
    private JTextField searchFieldReference;
    private Connection con1;
    
  
    // ================= ADMIN MAIN WINDOW ========================================================================================================================================
   public void admin_main_work(String admin_id, Connection con1) {
	   
	   this.con1 = con1;

       JFrame f = new JFrame(" Admin Panel");
       f.setLayout(null);
       f.setSize(320, 650);
       f.setLocationRelativeTo(null);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.getContentPane().setBackground(Color.decode("#f8f9fa"));
       
       JLabel welcomeLabel = new JLabel("Welcome, Tarun !");
       welcomeLabel.setBounds(10, 20, 200, 35);
       welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
       welcomeLabel.setForeground(new Color(0, 102, 204));
       welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
       f.add(welcomeLabel);

       JButton add_but = new JButton("Add Product");
       add_but.setBounds(40, 110, 240, 45);
       add_but.addActionListener(e -> add_course(con1));
       add_but.setForeground(Color.GRAY);
       add_but.setFont(new Font("Arial", Font.BOLD, 16));

       JButton inv_but = new JButton("Inventory & stores");
       inv_but.setBounds(40, 165, 240, 45);
       inv_but.addActionListener(e -> display_course(con1));
       inv_but.setForeground(Color.GRAY);
       inv_but.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton ord_but = new JButton("Orders");
        ord_but.setBounds(40, 220, 240, 45);   // change y for below previous
        ord_but.setForeground(Color.GRAY);
        ord_but.setFont(new Font("Arial", Font.BOLD, 16));
        ord_but.addActionListener(e -> showOrderManagement(con1));

        
        JButton cus_but = new JButton("Customer");
        cus_but.setBounds(40, 275, 240, 45);
        cus_but.setForeground(Color.GRAY);
        cus_but.setFont(new Font("Arial", Font.BOLD, 16));
        cus_but.addActionListener(e -> display_customers(con1)); 
        
        JButton pay_but = new JButton("Payments");
        pay_but.setBounds(40, 330, 240, 45);  
        pay_but.setForeground(Color.GRAY);
        pay_but.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton rep_but = new JButton("Reports");
        rep_but.setBounds(40, 385, 240, 45); 
        rep_but.setForeground(Color.GRAY);
        rep_but.setFont(new Font("Arial", Font.BOLD, 16));
        rep_but.addActionListener(e -> showReportsDashboard(con1));

        
        JButton set_but = new JButton("Settings");
        set_but.setBounds(40, 440, 240, 45);  
        set_but.setForeground(Color.GRAY);
        set_but.setFont(new Font("Arial", Font.BOLD, 16));
        set_but.addActionListener(e -> showSettingsDialog(con1, admin_id));
        
     // Short Bell Alerts Button
     // Complete Circular Black Bell Alert Button
        JButton alertBtn = new JButton() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
                                   java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.gray);
                g2.setStroke(new java.awt.BasicStroke(2.0f));
                g2.drawOval(1, 1, 28, 28);
                
                // Black circle background
                g2.setColor(Color.white);
                g2.fillOval(2, 2, 26, 26);
                
                // White bell icon
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int x = (30 - fm.stringWidth("🔔")) / 2;
                int y = (30 + fm.getAscent()) / 2;
                g2.drawString("🔔", x, y);
                
                g2.dispose();
            }
        };

        alertBtn.setOpaque(false);
        alertBtn.setContentAreaFilled(false);
        alertBtn.setBorderPainted(false);
        alertBtn.setFocusPainted(false);
        alertBtn.setPreferredSize(new java.awt.Dimension(30, 30));
        alertBtn.setBounds(215, 20, 30, 30);
        alertBtn.setToolTipText("Alerts");
  //      alertBtn.addActionListener(e -> showAdminAlerts(con1));
        f.add(alertBtn);


        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 10));
        logoutBtn.setForeground(Color.white);
        logoutBtn.setBackground(Color.decode("#dc3545"));
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBounds(90, 530, 80, 30);
        
        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(f, "Logged out successfully!");
            f.dispose();
        });
  


        f.add(add_but);
        f.add(ord_but);
        f.add(inv_but);
        f.add(cus_but);
        f.add(pay_but);
        f.add(rep_but);
        f.add(set_but);
        f.add(logoutBtn); 

        f.setVisible(true);
    }
   

    // ================= ADD PRODUCT ========================================================================================================================================
    public void add_course(Connection con1) {

        JFrame f2 = new JFrame("Add Product");
        f2.setLayout(null);
        f2.setSize(400, 500);
        f2.setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Category");
        JLabel l2 = new JLabel("Brand");
        JLabel l3 = new JLabel("Name");
        JLabel l4 = new JLabel("Price");
        JLabel l5 = new JLabel("Expiry Date");
        JLabel l6 = new JLabel("Quantity");

        l1.setBounds(30, 20, 100, 30);
        l2.setBounds(30, 70, 100, 30);
        l3.setBounds(30, 120, 100, 30);
        l4.setBounds(30, 170, 100, 30);
        l5.setBounds(30, 220, 100, 30);
        l6.setBounds(30, 270, 100, 30);  // -------------------

        String[] categories = {
                "Atta, Rice & Cereals", "Baby care", "Bakery and Biscuit",
                "Breakfast & Instant Food", "Chicken, meat & fish", "Cleaning essentials",
                "Home & office", "Fruits & Vegetables", "Masala, oil and more","personal care",
                "Personal care", "Snacks","Sweet", "Tea, Coffee & Drinks"
        };

        String[] brands = {
               
                "Amul", "Aashirvaad", "Badshah Masala", "Balaji", "Bikaji", "Bournvita",
                "Britannia", "Brooke Bond", "Cadbury", "Catch", "Colin", "Dabur", "Daawat",
                "Devbhog", "Dhara", "Everest", "Fortune", "Haldiram", "Harpic", "Himalaya",
                "India Gate", "Johnson's Baby", "Kellogg's", "Kitkat", "Kurkure", "Lay's",
                "Lipton", "Lizol", "Lotus", "Mamaearth", "Marie", "MDH", "Milton",
                "Mother Dairy", "MTR", "Nescafe", "Nestle", "Nivea", "Oreo", "Parle-G",
                "Patanjali", "Quaker", "Saffola", "Sunfeast", "Tata", "others"
        };

        JComboBox<String> cbCat = new JComboBox<>(categories);
        JComboBox<String> cbBra = new JComboBox<>(brands);
        


        JTextField txtName = new JTextField();
        JTextField txtPrice = new JTextField();
        JTextField txtQuantity = new JTextField("1");  // New quantity field
        
       
        txtQuantity.setHorizontalAlignment(JTextField.CENTER);
        txtQuantity.setFont(txtQuantity.getFont().deriveFont(16f));

        JButton minusBtn = new JButton("-");
        minusBtn.setBounds(140, 270, 30, 35);
      
        minusBtn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        minusBtn.setFocusPainted(false);
        minusBtn.setForeground(java.awt.Color.BLACK);
        minusBtn.setBackground(java.awt.Color.WHITE);
        minusBtn.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
      

        JButton plusBtn = new JButton("+");
        plusBtn.setBounds(250, 270, 30, 35);
        plusBtn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
      
        plusBtn.setFocusPainted(false);
        plusBtn.setBackground(java.awt.Color.WHITE);
        plusBtn.setForeground(java.awt.Color.BLACK);
        plusBtn.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
     
        plusBtn.setContentAreaFilled(false);

        DatePickerSettings dps = new DatePickerSettings();
        dps.setFormatForDatesCommonEra("dd/MM/yyyy");
        DatePicker expPicker = new DatePicker(dps);

        cbCat.setBounds(140, 20, 200, 30);
        cbBra.setBounds(140, 70, 200, 30);
        txtName.setBounds(140, 120, 200, 30);
        txtPrice.setBounds(140, 170, 200, 30);
        expPicker.setBounds(140, 220, 200, 30);
        txtQuantity.setBounds(175, 270, 60, 35);
        
    
        class QuantityManager {
            private int quantity = 1;
            
            public void increment() {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
            
            public void decrement() {
                if (quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
            
            public int getQuantity() {
                try {
                    return Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException e) {
                    return 1;
                }
            }
            
            public void reset() {
                quantity = 1;
                txtQuantity.setText("1");
            }
        }
        
        final QuantityManager qtyManager = new QuantityManager();

        
        minusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                qtyManager.decrement();
            }
        });
        
        plusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                qtyManager.increment();
            }
        });
        
        txtQuantity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int qty = qtyManager.getQuantity();
                if (qty < 1) qty = 1;
                txtQuantity.setText(String.valueOf(qty));
            }
        });

       



        JButton addBtn = new JButton("Add");
        JButton resetBtn = new JButton("Reset");
        addBtn.setBounds(50, 350, 120, 30);
        resetBtn.setBounds(220, 350, 120, 30);

        // RESET
        resetBtn.addActionListener(e -> {
            cbCat.setSelectedIndex(0);
            cbBra.setSelectedIndex(0);
            txtName.setText("");
            txtPrice.setText("");
            expPicker.clear();
            txtQuantity.setText("1"); 
            qtyManager.reset();  
        });

        // ADD PRODUCT
        addBtn.addActionListener(e -> {
            try {
                String sql = "INSERT INTO course (cacat, cabra, caname, capr, caexp, quantity) VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement pst = con1.prepareStatement(sql);
                pst.setString(1, cbCat.getSelectedItem().toString());
                pst.setString(2, cbBra.getSelectedItem().toString());
                pst.setString(3, txtName.getText());
                pst.setDouble(4, Double.parseDouble(txtPrice.getText()));
                pst.setString(5,
                        expPicker.getDate() == null ? "" : expPicker.getDate().toString());
                pst.setInt(6, Integer.parseInt(txtQuantity.getText()));  // Add quantity
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Product Added Successfully!");
                f2.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        f2.add(l1); f2.add(l2); f2.add(l3); f2.add(l4); f2.add(l5); f2.add(l6);
        f2.add(cbCat); f2.add(cbBra); f2.add(txtName); f2.add(txtPrice); f2.add(expPicker); f2.add(txtQuantity);
        f2.add(minusBtn); f2.add(plusBtn); f2.add(addBtn); f2.add(resetBtn);

        f2.setVisible(true);
    }
 
   

    // ================= DISPLAY PRODUCTS =========================================================================================================================================================
    
    
    public void display_course(Connection con1) {
        JFrame f = new JFrame("Inventory & Stores ");
        f.setSize(1200, 600);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // Search Panel
        JLabel searchLabel = new JLabel("Search Products:");
        searchLabel.setBounds(20, 20, 120, 30);
        
        JTextField searchField = new JTextField();
        searchField.setBounds(150, 20, 200, 30);
        searchFieldReference = searchField;  
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(370, 20, 100, 30);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(490, 20, 120, 30);
        
        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(630, 20, 130, 30);
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(780, 20, 130, 30);
      //  deleteBtn.setBackground(java.awt.Color.RED);
       // deleteBtn.setForeground(java.awt.Color.WHITE);

        // Table
        JTable table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 70, 1150, 450);

        // Load initial data
        loadTableData(table, con1, "");

        // ===== SEARCH FUNCTIONALITY =====
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            loadTableData(table, con1, searchText);
        });
        
        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            loadTableData(table, con1, searchText);
        });
        
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadTableData(table, con1, "");
        });
        
        // ===== UPDATE SELECTED ROW =====
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = productData.get(selectedRow);
                product_upd(con1, rowData);
            } else {
                JOptionPane.showMessageDialog(f, "Please select a product to update!");
            }
        });
        
        // ===== DELETE SELECTED ROW =====
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = productData.get(selectedRow);
                deleteProduct(con1, rowData, f, table);
            } else {
                JOptionPane.showMessageDialog(f, "Please select a product to delete!");
            }
        });

        // Add all components
        f.add(searchLabel); f.add(searchField); f.add(searchBtn);
        f.add(refreshBtn); f.add(updateBtn); f.add(deleteBtn); f.add(sp);
        f.setVisible(true);
    }

    // ================= LOAD TABLE DATA =================
    private void loadTableData(JTable table, Connection con1, String searchText) {
        try {
            productData.clear();  // Clear previous data
            
            String sql = "SELECT cacat, cabra, caname, capr, caexp, quantity FROM course";
            if (!searchText.isEmpty()) {
                sql += " WHERE cacat LIKE ? OR cabra LIKE ? OR caname LIKE ?";
            }
            
            PreparedStatement pst = con1.prepareStatement(sql);
            if (!searchText.isEmpty()) {
                String likePattern = "%" + searchText + "%";
                pst.setString(1, likePattern);
                pst.setString(2, likePattern);
                pst.setString(3, likePattern);
            }
            
            ResultSet rs = pst.executeQuery();
            
            // Store raw data before creating table
            while (rs.next()) {
                Object[] row = {
                    rs.getString("cacat"),
                    rs.getString("cabra"),
                    rs.getString("caname"),
                    rs.getDouble("capr"),
                    rs.getString("caexp"),
                    rs.getInt("quantity")
                };
                productData.add(row);
            }
            
            // Create table from stored data
            Object[][] dataArray = productData.toArray(new Object[0][]);
            String[] columnNames = {"Category", "Brand", "Name", "Price", "Expiry", "Quantity"};
            
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(dataArray, columnNames);
            table.setModel(model);
            table.setAutoCreateRowSorter(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading  " + e.getMessage());
        }
    }

 ///==========================Customers================================================================================================================================================================   
    public void display_customers(Connection con1) {
        JFrame f = new JFrame("Customer ");
        f.setSize(1100, 650);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // Search Panel
        JLabel searchLabel = new JLabel("Search Customers:");
        searchLabel.setBounds(20, 20, 120, 30);
        
        JTextField searchField = new JTextField();
        searchField.setBounds(150, 20, 200, 30);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(370, 20, 100, 30);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(490, 20, 120, 30);
        
        JButton viewBtn = new JButton("View Details");
        viewBtn.setBounds(630, 20, 130, 30);

       
        String[] customerColumns = {"Member ID", "Name"};
        DefaultTableModel customerModel = new DefaultTableModel(customerColumns, 0);
        JTable customerTable = new JTable(customerModel);
        customerTable.setRowHeight(30);
        JScrollPane customerScroll = new JScrollPane(customerTable);
        customerScroll.setBounds(20, 70, 350, 500);

       
        String[] detailsColumns = {"Email", "Phone", "Address", "Street"};
        DefaultTableModel detailsModel = new DefaultTableModel(detailsColumns, 0);
        JTable detailsTable = new JTable(detailsModel);
        detailsTable.setEnabled(false); // Read-only
        JScrollPane detailsScroll = new JScrollPane(detailsTable);
        detailsScroll.setBounds(400, 70, 670, 500);

       
        loadMemberData(customerModel, con1, "");

        // ===== SEARCH MEMBERS =====
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            loadMemberData(customerModel, con1, searchText);
            detailsModel.setRowCount(0); // Clear details
        });
        
        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            loadMemberData(customerModel, con1, searchText);
            detailsModel.setRowCount(0);
        });
        
        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadMemberData(customerModel, con1, "");
            detailsModel.setRowCount(0);
        });
        
        // ===== VIEW MEMBER DETAILS =====
        viewBtn.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                String memberId = customerModel.getValueAt(selectedRow, 0).toString();
                loadMemberDetails(detailsModel, con1, memberId);
            } else {
                JOptionPane.showMessageDialog(f, "Please select a member to view details!");
            }
        });

        // Double-click on customer table to view details
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String memberId = customerModel.getValueAt(customerTable.getSelectedRow(), 0).toString();
                    loadMemberDetails(detailsModel, con1, memberId);
                }
            }
        });

        f.add(searchLabel); f.add(searchField); f.add(searchBtn);
        f.add(refreshBtn); f.add(viewBtn); f.add(customerScroll); f.add(detailsScroll);
        f.setVisible(true);
    }

    //  LOAD MEMBER LIST (ID & Name)
    private void loadMemberData(DefaultTableModel model, Connection con1, String searchText) {
        try {
            model.setRowCount(0); // Clear table
            
            PreparedStatement pst;
            if (searchText == null || searchText.trim().isEmpty()) {
                // Load ALL members
                String sql = "SELECT M_id, M_name FROM member";
                pst = con1.prepareStatement(sql);
            } else {
                // Search by ID or Name
                try {
                    int id = Integer.parseInt(searchText.trim());
                    String sql = "SELECT M_id, M_name FROM member WHERE M_id = ?";
                    pst = con1.prepareStatement(sql);
                    pst.setInt(1, id);
                } catch (NumberFormatException e) {
                    // Search by name
                    String sql = "SELECT M_id, M_name FROM member WHERE M_name LIKE ?";
                    pst = con1.prepareStatement(sql);
                    pst.setString(1, "%" + searchText.trim() + "%");
                }
            }
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("M_id"),
                    rs.getString("M_name")
                });
            }
            
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No members found matching: " + searchText);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading members: " + e.getMessage());
        }
    }

    // LOAD MEMBER FULL DETAILS
    private void loadMemberDetails(DefaultTableModel model, Connection con1, String memberId) {
        try {
            model.setRowCount(0); // Clear details
            
            // Validate memberId
            if (memberId == null || memberId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Member ID!");
                return;
            }
            
            int id = Integer.parseInt(memberId.trim());
            String sql = "SELECT M_email, M_phone, M_street, M_city FROM member WHERE M_id = ?";
            PreparedStatement pst = con1.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("M_email"),
                    rs.getString("M_phone"),
                    rs.getString("M_street"),
                    rs.getString("M_city")
                });
            } else {
                JOptionPane.showMessageDialog(null, "Member ID " + id + " not found!");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading member details: " + e.getMessage());
        }
    }
    

    // ================= UPDATE PRODUCT ==========================================================================================================================================================================
    public void product_upd(Connection con1, Object[] rowData) {
        JFrame f = new JFrame("Update Product");
        f.setSize(450, 550);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // Extract current data
        String currentCategory = (String) rowData[0];
        String currentBrand = (String) rowData[1];
        String currentName = (String) rowData[2];
        double currentPrice = (Double) rowData[3];
        String currentExpiry = (String) rowData[4];
        int currentQuantity = (Integer) rowData[5];

        // Labels
        JLabel l1 = new JLabel("Category"); l1.setBounds(30, 30, 100, 30);
        JLabel l2 = new JLabel("Brand"); l2.setBounds(30, 80, 100, 30);
        JLabel l3 = new JLabel("Name"); l3.setBounds(30, 130, 100, 30);
        JLabel l4 = new JLabel("Price"); l4.setBounds(30, 180, 100, 30);
        JLabel l5 = new JLabel("Expiry Date"); l5.setBounds(30, 230, 100, 30);
        JLabel l6 = new JLabel("Quantity"); l6.setBounds(30, 280, 100, 30);

        // Categories and Brands
        String[] categories = {
            "Atta, Rice & Cereals", "Baby care", "Bakery and Biscuit",
            "Breakfast & Instant Food", "Chicken, meat & fish", "Cleaning essentials",
            "Home & office", "Fruits & Vegetables", "Masala, oil and more",
            "Personal care", "Snacks", "Sweet", "Tea, Coffee & Drinks"
        };
        String[] brands = {
        		 "Amul", "Aashirvaad", "Badshah Masala", "Balaji", "Bikaji", "Bournvita",
                 "Britannia", "Brooke Bond", "Cadbury", "Catch", "Colin", "Dabur", "Daawat",
                 "Devbhog", "Dhara", "Everest", "Fortune", "Haldiram", "Harpic", "Himalaya",
                 "India Gate", "Johnson's Baby", "Kellogg's", "Kitkat", "Kurkure", "Lay's",
                 "Lipton", "Lizol", "Lotus", "Mamaearth", "Marie", "MDH", "Milton",
                 "Mother Dairy", "MTR", "Nescafe", "Nestle", "Nivea", "Oreo", "Parle-G",
                 "Patanjali", "Quaker", "Saffola", "Sunfeast", "Tata", "others"
        };

        JComboBox<String> cbCat = new JComboBox<>(categories); cbCat.setBounds(150, 30, 250, 30);
        JComboBox<String> cbBra = new JComboBox<>(brands); cbBra.setBounds(150, 80, 250, 30);
        JTextField txtName = new JTextField(currentName); txtName.setBounds(150, 130, 250, 30);
        JTextField txtPrice = new JTextField(String.valueOf(currentPrice)); txtPrice.setBounds(150, 180, 250, 30);
        JTextField txtQuantity = new JTextField(String.valueOf(currentQuantity)); txtQuantity.setBounds(150, 280, 250, 30);
        
        DatePickerSettings dps = new DatePickerSettings();
        dps.setFormatForDatesCommonEra("dd/MM/yyyy");
        DatePicker expPicker = new DatePicker(dps);
        expPicker.setBounds(150, 230, 250, 30);

        // Set current selections
        for (int i = 0; i < cbCat.getItemCount(); i++) {
            if (cbCat.getItemAt(i).equals(currentCategory)) {
                cbCat.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cbBra.getItemCount(); i++) {
            if (cbBra.getItemAt(i).equals(currentBrand)) {
                cbBra.setSelectedIndex(i);
                break;
            }
        }

        JButton updateBtn = new JButton("UPDATE PRODUCT");
        updateBtn.setBounds(100, 350, 150, 40);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(270, 350, 100, 40);

        // Update Action
        updateBtn.addActionListener(e -> {
            try {
                String sql = "UPDATE course SET cacat=?, cabra=?, caname=?, capr=?, caexp=?, quantity=? WHERE caname=?";
                PreparedStatement pst = con1.prepareStatement(sql);
                pst.setString(1, cbCat.getSelectedItem().toString());
                pst.setString(2, cbBra.getSelectedItem().toString());
                pst.setString(3, txtName.getText());
                pst.setDouble(4, Double.parseDouble(txtPrice.getText()));
                pst.setString(5, expPicker.getDate() == null ? "" : expPicker.getDate().toString());
                pst.setInt(6, Integer.parseInt(txtQuantity.getText()));
                pst.setString(7, currentName);
                
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(f, "Product Updated Successfully!");
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(f, "No product found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> f.dispose());

        f.add(l1); f.add(l2); f.add(l3); f.add(l4); f.add(l5); f.add(l6);
        f.add(cbCat); f.add(cbBra); f.add(txtName); f.add(txtPrice); 
        f.add(expPicker); f.add(txtQuantity); f.add(updateBtn); f.add(cancelBtn);
        f.setVisible(true);
    }
    
    

    // ================= DELETE PRODUCT =================
    private void deleteProduct(Connection con1, Object[] rowData, JFrame parentFrame, JTable table) {
        String productName = (String) rowData[2];
        
        int confirm = JOptionPane.showConfirmDialog(
            parentFrame, 
            "Are you sure you want to delete:\n" + productName + "?\n\nThis cannot be undone!", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM course WHERE caname = ?";
                PreparedStatement pst = con1.prepareStatement(sql);
                pst.setString(1, productName);
                
                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(parentFrame, productName + " Product Deleted!");
                    
                    // Refresh table maintaining search
                    String currentSearch = (searchFieldReference != null) ? searchFieldReference.getText().trim() : "";
                    loadTableData(table, con1, currentSearch);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Product not found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Delete Error: " + ex.getMessage());
            }
        }
    }

//============================================================================================================================================================================================================
    private void showOrderManagement(Connection con) {

        JFrame orderFrame = new JFrame("Order Management");
        orderFrame.setLayout(null);
        orderFrame.setSize(1100, 700);
        orderFrame.setLocationRelativeTo(null);
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "Order ID", "Member ID", "Items", "Qty Total",
            "Total Price", "Order Date", "Status", "Update"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // Status + Update button only
            }
        };

        JTable orderTable = new JTable(model);
        orderTable.setRowHeight(25);
        orderTable.getTableHeader().setBackground(Color.decode("#e3e7af"));
        orderTable.getTableHeader().setForeground(Color.decode("#002ec2"));

        JScrollPane scroll = new JScrollPane(orderTable);
        scroll.setBounds(20, 80, 1050, 500);
        orderFrame.add(scroll);

        loadOrdersTable(model, con);

        // Status dropdown
        JComboBox<String> statusCombo = new JComboBox<>(
            new String[]{"Confirmed", "Cancelled", "Preparing", "Out for Delivery", "Delivered"}
        );
        orderTable.getColumnModel().getColumn(6)
                  .setCellEditor(new DefaultCellEditor(statusCombo));

        // Update button column
        orderTable.getColumnModel().getColumn(7)
                  .setCellRenderer(new ButtonRenderer());
        orderTable.getColumnModel().getColumn(7)
                  .setCellEditor(new UpdateOrderEditor(con, model));

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setBounds(900, 30, 100, 35);
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            loadOrdersTable(model, con);
        });

        orderFrame.add(refreshBtn);
        orderFrame.setVisible(true);
    }

   
        private void loadOrdersTable(DefaultTableModel model, Connection con) {

            String sql =
                "SELECT o.order_id, o.m_id, " +
                "GROUP_CONCAT(oi.product_name SEPARATOR ', ') AS items, " +
                "SUM(oi.quantity) AS qty_total, o.total_amount, " +
                "DATE_FORMAT(o.order_date, '%Y-%m-%d') AS order_date, o.status " +
                "FROM orders o " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "GROUP BY o.order_id, o.m_id, o.total_amount, o.order_date, o.status " +
                "ORDER BY o.order_id DESC";

            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getInt("m_id"),
                        rs.getString("items"),
                        rs.getInt("qty_total"),
                        "₹" + String.format("%.2f", rs.getDouble("total_amount")),
                        rs.getString("order_date"),
                        rs.getString("status"),
                        "Update"
                    });
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        class ButtonRenderer extends JButton implements TableCellRenderer {
        	private static final long serialVersionUID = 1L;
            public ButtonRenderer() {
                setOpaque(true);
                setFocusPainted(false);
               // setBackground(Color.decode("#28a745"));
                setBackground(UIManager.getColor("Button.background")); 
                setForeground(Color.black);
                setBorder(UIManager.getBorder("Button.border"));
            }

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                setText("Update");
                return this;
            }
        }
        class UpdateOrderEditor extends DefaultCellEditor {
        	 private static final long serialVersionUID = 1L; 
            private JButton button;
            private Connection con;
            private DefaultTableModel model;
            private int row;

            public UpdateOrderEditor(Connection con, DefaultTableModel model) {
                super(new JCheckBox()); // ✅ FIXED
                this.con = con;
                this.model = model;

                button = new JButton("Update");
                button.setBackground(UIManager.getColor("Button.background"));
                button.setForeground(Color.BLACK);
                button.setFocusPainted(false);


                button.addActionListener(e -> updateOrder());
            }

            @Override
            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected,
                    int row, int column) {

                this.row = row;
                return button;
            }

            private void updateOrder() {

                int orderId = (int) model.getValueAt(row, 0);
                String newStatus = model.getValueAt(row, 6).toString();

                try (PreparedStatement ps =
                         con.prepareStatement("UPDATE orders SET status=? WHERE order_id=?")) {

                    ps.setString(1, newStatus);
                    ps.setInt(2, orderId);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(
                        null, "Order #" + orderId + " updated to " + newStatus);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

                fireEditingStopped();
            }

            @Override
            public Object getCellEditorValue() {
                return "Update";
            }
        }



   

    
    
    
  //============================================================================================================================================================================================================   
public void showSettingsDialog(Connection con1, String admin_id) {
 JDialog settingsDialog = new JDialog();
 settingsDialog.setTitle("Settings");
 settingsDialog.setSize(400, 400);
 settingsDialog.setLayout(null);
 settingsDialog.setLocationRelativeTo(null);
 settingsDialog.setModal(true);
 
 // Current theme tracking
 final boolean[] isDarkMode = {false}; // Track current theme
 
 // Labels
 JLabel themeLabel = new JLabel("Theme");
 themeLabel.setBounds(30, 30, 100, 30);
 themeLabel.setFont(new Font("Arial", Font.BOLD, 14));
 
 JToggleButton themeToggle = new JToggleButton("Light Mode");
 themeToggle.setBounds(190, 30, 150, 35);
 themeToggle.setFont(new Font("Arial", Font.BOLD, 12));
 
 JLabel passwordLabel = new JLabel("Change Password");
 passwordLabel.setBounds(30, 90, 150, 30);
 passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
 
 JButton passwordBtn = new JButton("Change");
 passwordBtn.setBounds(190, 90, 150, 35);
 passwordBtn.setFont(new Font("Arial", Font.BOLD, 12));
 
 JLabel languageLabel = new JLabel("Default Language");
 languageLabel.setBounds(30, 150, 150, 30);
 languageLabel.setFont(new Font("Arial", Font.BOLD, 14));
 
 String[] languages = {"English", "Hindi"};
 JComboBox<String> languageCombo = new JComboBox<>(languages);
 languageCombo.setBounds(190, 150, 150, 35);
 
 JLabel storeLabel = new JLabel("Store Name");
 storeLabel.setBounds(30, 210, 150, 30);
 storeLabel.setFont(new Font("Arial", Font.BOLD, 14));
 
 JTextField storeNameField = new JTextField("Grocery Store");
 storeNameField.setBounds(190, 210, 150, 35);
 
 JButton saveBtn = new JButton("Save Settings");
 saveBtn.setBounds(80, 280, 120, 40);
 saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
 saveBtn.setBackground(new Color(34, 139, 34));
 saveBtn.setForeground(Color.WHITE);
 
 JButton cancelBtn = new JButton("Cancel");
 cancelBtn.setBounds(220, 280, 100, 40);
 cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
 
 // Theme Toggle Logic
 themeToggle.addActionListener(e -> {
     isDarkMode[0] = themeToggle.isSelected();
     if (isDarkMode[0]) {
         themeToggle.setText("Dark Mode");
         applyDarkTheme(settingsDialog);
     } else {
         themeToggle.setText("Light Mode");
         applyLightTheme(settingsDialog);
     }
 });
 
 // Password button (placeholder)
 passwordBtn.addActionListener(e -> {
     JOptionPane.showMessageDialog(settingsDialog, "Password change functionality to be implemented");
 });
 
 // Save button
 saveBtn.addActionListener(e -> {
     JOptionPane.showMessageDialog(settingsDialog, 
         "Settings Saved!" );
     settingsDialog.dispose();
 });
 
 // Cancel button
 cancelBtn.addActionListener(e -> settingsDialog.dispose());
 
 // Add all components
 settingsDialog.add(themeLabel); settingsDialog.add(themeToggle);
 settingsDialog.add(passwordLabel); settingsDialog.add(passwordBtn);
 settingsDialog.add(languageLabel); settingsDialog.add(languageCombo);
 settingsDialog.add(storeLabel); settingsDialog.add(storeNameField);
 settingsDialog.add(saveBtn); settingsDialog.add(cancelBtn);
 
 // Apply initial light theme
 applyLightTheme(settingsDialog);
 
 settingsDialog.setVisible(true);
}

//Light Theme Method
private void applyLightTheme(JDialog dialog) {
 dialog.getContentPane().setBackground(Color.WHITE);
 
 Component[] components = dialog.getContentPane().getComponents();
 for (Component comp : components) {
     if (comp instanceof JLabel) {
         ((JLabel) comp).setForeground(Color.BLACK);
     } else if (comp instanceof JButton) {
         ((JButton) comp).setBackground(new Color(240, 240, 240));
         ((JButton) comp).setForeground(Color.BLACK);
         ((JButton) comp).setBorderPainted(true);
     } else if (comp instanceof JComboBox) {
         ((JComboBox<?>) comp).setBackground(Color.WHITE);
         ((JComboBox<?>) comp).setForeground(Color.BLACK);
     } else if (comp instanceof JTextField) {
         ((JTextField) comp).setBackground(Color.WHITE);
         ((JTextField) comp).setForeground(Color.BLACK);
     }
 }
}

//Dark Theme Method
private void applyDarkTheme(JDialog dialog) {
 dialog.getContentPane().setBackground(new Color(30, 30, 30));
 
 Component[] components = dialog.getContentPane().getComponents();
 for (Component comp : components) {
     if (comp instanceof JLabel) {
         ((JLabel) comp).setForeground(new Color(220, 220, 220));
     } else if (comp instanceof JButton) {
         ((JButton) comp).setBackground(new Color(50, 50, 50));
         ((JButton) comp).setForeground(new Color(220, 220, 220));
         ((JButton) comp).setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
     } else if (comp instanceof JComboBox) {
         ((JComboBox<?>) comp).setBackground(new Color(50, 50, 50));
         ((JComboBox<?>) comp).setForeground(new Color(220, 220, 220));
     } else if (comp instanceof JTextField) {
         ((JTextField) comp).setBackground(new Color(50, 50, 50));
         ((JTextField) comp).setForeground(new Color(220, 220, 220));
         ((JTextField) comp).setCaretColor(Color.WHITE);
         ((JTextField) comp).setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
     }
 }
 }
private void showReportsDashboard(Connection con) {

    JFrame frame = new JFrame("Reports Dashboard");
    frame.setSize(950, 600);
    frame.setLayout(null);
    frame.setLocationRelativeTo(null);
    frame.getContentPane().setBackground(new Color(245, 247, 250));

    JLabel title = new JLabel("Admin Reports & Support");
    title.setFont(new Font("SansSerif", Font.BOLD, 22));
    title.setBounds(30, 20, 400, 30);
    frame.add(title);

    /* LEFT MENU */
    JPanel menu = new JPanel(null);
    menu.setBounds(20, 70, 200, 470);
    menu.setBackground(Color.WHITE);
    frame.add(menu);

    JButton todayBtn = new JButton("Today's Sales");
    JButton monthBtn = new JButton("Monthly Sales");
    JButton yearBtn = new JButton("Yearly Sales");
    JButton supportBtn = new JButton("Member Support");

    JButton[] btns = { todayBtn, monthBtn, yearBtn, supportBtn };

    int y = 30;
    for (JButton b : btns) {
        b.setBounds(20, y, 160, 35);
        b.setFont(new Font("SansSerif", Font.PLAIN, 13));
        menu.add(b);
        y += 55;
    }

    /* CONTENT */
    JPanel content = new JPanel(null);
    content.setBounds(240, 70, 660, 470);
    content.setBackground(Color.WHITE);
    frame.add(content);

    todayBtn.addActionListener(e -> {
        content.removeAll();
        showSales(con, content, "TODAY");
    });

    monthBtn.addActionListener(e -> {
        content.removeAll();
        showSales(con, content, "MONTH");
    });

    yearBtn.addActionListener(e -> {
        content.removeAll();
        showSales(con, content, "YEAR");
    });

    supportBtn.addActionListener(e -> {
        content.removeAll();
        showSupportList(con, content);
    });

    frame.setVisible(true);
}
private void showSales(Connection con, JPanel panel, String type) {

    JLabel title = new JLabel(type + " Sales Report");
    title.setFont(new Font("SansSerif", Font.BOLD, 18));
    title.setBounds(20, 20, 300, 30);
    panel.add(title);

    String[] cols = {"Order ID", "Date", "Amount"};
    DefaultTableModel model = new DefaultTableModel(cols, 0);
    JTable table = new JTable(model);
    table.setRowHeight(26);

    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(20, 70, 600, 300);
    panel.add(scroll);

    if (con == null) {
        JOptionPane.showMessageDialog(panel, "Database connection not available");
        return;
    }

    String sql;
    if (type.equals("TODAY"))
        sql = "SELECT order_id, order_date, total_amount FROM orders WHERE DATE(order_date)=CURDATE()";
    else if (type.equals("MONTH"))
        sql = "SELECT order_id, order_date, total_amount FROM orders WHERE MONTH(order_date)=MONTH(CURDATE())";
    else
        sql = "SELECT order_id, order_date, total_amount FROM orders WHERE YEAR(order_date)=YEAR(CURDATE())";

    try (Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        double total = 0;
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt(1),
                rs.getDate(2),
                "₹" + rs.getDouble(3)
            });
            total += rs.getDouble(3);
        }

        JLabel totalLbl = new JLabel("Total: ₹" + total);
        totalLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLbl.setBounds(20, 390, 300, 30);
        panel.add(totalLbl);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(panel, "Error loading sales");
    }

    panel.revalidate();
    panel.repaint();
}
private void showSupportList(Connection con, JPanel panel) {

    JLabel title = new JLabel("Member Feedback & Reports");
    title.setFont(new Font("SansSerif", Font.BOLD, 18));
    title.setBounds(20, 20, 400, 30);
    panel.add(title);

    String[] cols = {"Member ID", "Sender", "Type", "Message"};
    DefaultTableModel model = new DefaultTableModel(cols, 0);
    JTable table = new JTable(model);
    table.setRowHeight(26);

    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(20, 70, 600, 300);
    panel.add(scroll);

    if (con == null) {
        JOptionPane.showMessageDialog(panel, "Database connection not available");
        return;
    }

    try {
        PreparedStatement ps = con.prepareStatement(
            "SELECT m_id, sender, type, message FROM support_chat ORDER BY chat_id DESC"
        );
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("m_id"),
                rs.getString("sender"),
                rs.getString("type"),
                rs.getString("message")
            });
        }

        rs.close();
        ps.close();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(panel, "Unable to load feedback");
    }

    JButton openBtn = new JButton("Open Chat");
    openBtn.setBounds(20, 390, 120, 35);
    openBtn.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(panel, "Select a row");
            return;
        }
        int memberId = Integer.parseInt(model.getValueAt(row, 0).toString());
        showAdminChat(con, memberId);
    });

    panel.add(openBtn);
    panel.revalidate();
    panel.repaint();
}
private void showAdminChat(Connection con, int memberId) {

    if (con == null) {
        JOptionPane.showMessageDialog(null, "DB Connection is NULL");
        return;
    }

    JFrame f = new JFrame("Support Chat - Member " + memberId);
    f.setSize(500, 550);
    f.setLayout(null);
    f.setLocationRelativeTo(null);

    JTextArea chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setLineWrap(true);
    chatArea.setWrapStyleWord(true);
    chatArea.setFont(new Font("SansSerif", Font.PLAIN, 13));

    JScrollPane scroll = new JScrollPane(chatArea);
    scroll.setBounds(20, 20, 440, 360);
    f.add(scroll);

    JTextField msgField = new JTextField();
    msgField.setBounds(20, 400, 330, 35);
    f.add(msgField);

    JButton sendBtn = new JButton("Send");
    sendBtn.setBounds(360, 400, 100, 35);
    f.add(sendBtn);

    // LOAD CHAT
    Runnable loadChat = () -> {
        chatArea.setText("");
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT sender, message FROM support_chat WHERE m_id=? ORDER BY chat_id")) {

            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String message = rs.getString("message");
                    chatArea.append(sender + ": " + message + "\n\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };

    loadChat.run();

    // SEND MESSAGE
    sendBtn.addActionListener(e -> {
        String msg = msgField.getText().trim();
        if (msg.isEmpty()) return;

        System.out.println("ADMIN SEND -> " + msg);

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO support_chat (m_id, sender, type, message) VALUES (?, ?, ?, ?)")) {

            ps.setInt(1, memberId);
            ps.setString(2, "ADMIN");
            ps.setString(3, "FEEDBACK");
            ps.setString(4, msg);
            ps.executeUpdate();

            msgField.setText("");
            loadChat.run();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f,
                "Failed to send message:\n" + ex.getMessage());
        }
    });

    f.setVisible(true);
}


}


