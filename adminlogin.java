package onlinegroceryshopping;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class adminlogin {  
    
    private JLabel item1; 

    // FIXED: Added proper main method
    public static void main(String[] args) {
        new adminlogin().main_server();
    }

    public void main_server() {
        JFrame f = new JFrame("ONLINE GROCERY SHOPPING");
        f.setLayout(null);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
       
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(Color.decode("#f8f9fa"));
        
       
       

        item1 = new JLabel("Welcome to Grocery Shopping");
        item1.setFont(new Font("Arial", Font.BOLD, 40));  
        item1.setBounds(200, 150, 700, 80);
        item1.setForeground(Color.BLACK);
        f.add(item1);

        
        JButton b1 = new JButton("ADMIN LOGIN");
        b1.setFont(new Font("Arial", Font.PLAIN, 25));  
        b1.setForeground(Color.decode("#FFFFFF"));
        b1.setBackground(Color.decode("#035e7b"));
        b1.setOpaque(true);
        b1.setBorderPainted(false);
        b1.setFocusPainted(false);
        b1.setBounds(150, 400, 250, 60);
        b1.addActionListener(e -> {
            admin_login();
            f.dispose();
        });
        f.add(b1);

        JButton b2 = new JButton("USER LOGIN");
        b2.setFont(new Font("Arial", Font.PLAIN, 25)); 
        b2.setForeground(Color.decode("#FFFFFF"));
        b2.setBackground(Color.decode("#035e7b"));
        b2.setOpaque(true);
        b2.setBorderPainted(false);
        b2.setFocusPainted(false);
        b2.setBounds(700, 400, 250, 60);
        b2.addActionListener(e -> {
            Member();
            f.dispose();
        });
        f.add(b2);

        f.setVisible(true);
    }

    public void admin_login() {
        JFrame f1 = new JFrame("Admin Login");
        f1.setLayout(null);
        f1.setSize(400, 250);
        f1.setLocationRelativeTo(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 20, 100, 30);
        f1.add(l1);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 70, 100, 30);
        f1.add(l2);

        JTextField F_user1 = new JTextField();
        F_user1.setBounds(130, 20, 220, 30);
        f1.add(F_user1);

        JPasswordField F_pass1 = new JPasswordField();
        F_pass1.setBounds(130, 70, 220, 30);
        f1.add(F_pass1);

        JButton login_but = new JButton("Login");
        login_but.setBounds(150, 130, 100, 35);
        f1.add(login_but);

        login_but.addActionListener(e -> {
            String username = F_user1.getText().trim();
            String password = new String(F_pass1.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(f1, "Username or Password cannot be empty!");
                return;
            }

            Connection connection = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            try {
                connection = connect();
                if (connection == null) {
                    JOptionPane.showMessageDialog(f1, "Database connection failed!");
                    return;
                }

                pst = connection.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();

                if (rs.next()) {
                    String admin_id = rs.getString("admin_id");
                    JOptionPane.showMessageDialog(f1, "Login Successful!");
                    
                    adminwork c1 = new adminwork();
                    c1.admin_main_work(admin_id, connection);
                    f1.dispose();
                } else {
                    JOptionPane.showMessageDialog(f1, "Invalid Credentials!");
                    F_user1.setText("");
                    F_pass1.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f1, "Login Error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                // Proper cleanup
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        f1.setVisible(true);
    }

    public void Member() {
        JFrame F = new JFrame("Member Options");
        F.setLayout(null);
        F.setSize(450, 350);
        F.setLocationRelativeTo(null);
        F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        F.getContentPane().setBackground(Color.decode("#f8f9fa"));

        JLabel title = new JLabel("New Member ? Choose Option");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(80, 30, 300, 30);
        F.add(title);

        JButton reg_but = new JButton(" Register New Account");
        reg_but.setFont(new Font("Arial", Font.PLAIN, 16));
        reg_but.setBackground(Color.decode("#28a745"));
        reg_but.setForeground(Color.WHITE);
        reg_but.setOpaque(true);
        reg_but.setBorderPainted(false);
        reg_but.setFocusPainted(false);
        reg_but.setBounds(100, 120, 250, 45);
        F.add(reg_but);

        JButton login_but = new JButton(" Existing Login");
        login_but.setFont(new Font("Arial", Font.PLAIN, 16));
        login_but.setBackground(Color.decode("#035e7b"));
        login_but.setForeground(Color.WHITE);
        login_but.setOpaque(true);
        login_but.setBorderPainted(false);
        login_but.setFocusPainted(false);
        login_but.setBounds(100, 200, 250, 45);
        F.add(login_but);

        reg_but.addActionListener(e -> {
            F.dispose();
            register R1 = new register();
            R1.register1();
        });

        login_but.addActionListener(e -> {
            F.dispose();
            member();
        });

        F.setVisible(true);
    }

    public void member() {
        JFrame f1 = new JFrame("Member Login");
        f1.setLayout(null);
        f1.setSize(450, 220);
        f1.setLocationRelativeTo(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.getContentPane().setBackground(Color.decode("#f8f9fa"));
        
        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 25, 100, 30);
        f1.add(l1);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 70, 100, 30);
        f1.add(l2);

        JTextField F_user = new JTextField();
        F_user.setBounds(140, 25, 250, 30);
        f1.add(F_user);

        JPasswordField F_pass = new JPasswordField();
        F_pass.setBounds(140, 70, 250, 30);
        f1.add(F_pass);

        JButton login_but = new JButton("Login");
        login_but.setBounds(170, 130, 100, 35);
        login_but.setBackground(Color.decode("#035e7b"));
        login_but.setForeground(Color.BLACK);
        f1.add(login_but);
        
        login_but.addActionListener(e -> {
            String username = F_user.getText().trim();
            String password = new String(F_pass.getPassword()).trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(f1, "Please enter username!");
                return;
            } 
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(f1, "Please enter password!");
                return;
            }

            Connection connection = null;
            Statement smt = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            
            try {
                connection = connect();
                if (connection == null) {
                    JOptionPane.showMessageDialog(f1, "Database connection failed!");
                    return;
                }
                
                smt = connection.createStatement();
                smt.execute("USE grocery");
                
                // FIXED: SQL Injection vulnerability - use PreparedStatement
                pst = connection.prepareStatement("SELECT * FROM member WHERE M_name = ? AND M_pass = ?");
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    String M_id = rs.getString("M_id");
                    Member M = new Member();
                    M.Member_menu(M_id, smt, connection);  // FIXED: Added connection parameter
                    f1.dispose();
                } else {
                    JOptionPane.showMessageDialog(f1, "Wrong Username / Password!");
                    F_user.setText("");
                    F_pass.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f1, "Login Error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (smt != null) smt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        f1.setVisible(true);
    }

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/grocery?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                "root", "tarun1234");
        } catch (Exception ex) {
            System.err.println("Database connection failed: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
