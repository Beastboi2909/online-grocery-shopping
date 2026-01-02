package onlinegroceryshopping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class register {

    public void register1() {
        JFrame F = new JFrame("Member Registration");
        F.setLayout(null);
        F.setSize(480, 460);
        F.setLocationRelativeTo(null);
        F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      //  F.getContentPane().setBackground(Color.decode("#f8f9fa"));

        JLabel title = new JLabel("Create New Member Account");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(110, 10, 280, 30);
        F.add(title);

        JLabel J2 = new JLabel(" Name");
        J2.setBounds(30, 60, 200, 25);
        JLabel J3 = new JLabel("Email");
        J3.setBounds(30, 95, 200, 25);
        JLabel J4 = new JLabel("Password");
        J4.setBounds(30, 130, 200, 25);
        JLabel J5 = new JLabel("Address");
        J5.setBounds(30, 165, 200, 25);
        JLabel J6 = new JLabel("Street");
        J6.setBounds(30, 200, 200, 25);
        JLabel J7 = new JLabel("City");
        J7.setBounds(30, 235, 200, 25);
        JLabel J8 = new JLabel("Zip");
        J8.setBounds(30, 270, 200, 25);
        JLabel J9 = new JLabel("Phone Number");
        J9.setBounds(30, 305, 200, 25); 

        JTextField M_Name = new JTextField();
        M_Name.setBounds(130, 60, 250, 25);

        JTextField M_email = new JTextField();
        M_email.setBounds(130, 95, 250, 25);

        JPasswordField M_password = new JPasswordField();
        M_password.setBounds(130, 130, 250, 25);

        JTextField M_add = new JTextField();
        M_add.setBounds(130, 165, 250, 25);

        JTextField M_street = new JTextField();
        M_street.setBounds(130, 200, 250, 25);

        JTextField M_city = new JTextField();
        M_city.setBounds(130, 235, 250, 25);

        JTextField M_Zipcode = new JTextField();
        M_Zipcode.setBounds(130, 270, 250, 25);
        
        JTextField M_phone = new JTextField();
        M_phone.setBounds(130, 305, 250, 25);

        JButton registerbutton = new JButton("Register");
        registerbutton.setBounds(150, 350, 180, 35);
        registerbutton.setBackground(Color.decode("#035e7b"));
       // registerbutton.setForeground(Color.WHITE);
        registerbutton.setFocusPainted(false);

        registerbutton.addActionListener((ActionEvent e) -> {
            String name = M_Name.getText().trim();
            String email = M_email.getText().trim();
            String pass = new String(M_password.getPassword()).trim();
            String addr = M_add.getText().trim();
            String street = M_street.getText().trim();
            String city = M_city.getText().trim();
            String zip = M_Zipcode.getText().trim();
            String phone = M_phone.getText().trim(); 

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()
                    || addr.isEmpty() || street.isEmpty()
                    || city.isEmpty() || zip.isEmpty() || phone.isEmpty() ){
                JOptionPane.showMessageDialog(F, "Please enter all fields!");
                return;
            }
            
            if (!zip.equals("495677")) {
                JOptionPane.showMessageDialog(F, "Not available at your pincode");
                M_Zipcode.setText(""); // Clear invalid zip
                M_Zipcode.requestFocus();
                return;
            }

            Connection con = null;
            PreparedStatement pst = null;
            try {
                con = adminlogin.connect();   // static connect is fine for reuse [web:35]

                String sql = "INSERT INTO member (M_name, M_Email, M_pass, M_add, M_Street, M_City, M_zip, M_phone) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, email);
                pst.setString(3, pass);
                pst.setString(4, addr);
                pst.setString(5, street);
                pst.setString(6, city);
                pst.setString(7, zip);
                pst.setString(8, phone);

                pst.executeUpdate();          // standard Swing+JDBC pattern [web:31][web:35]
                JOptionPane.showMessageDialog(F, "Registration Successful!");
                F.dispose();
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(F,
                        "Member with this email or ID already exists!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(F,
                        "Error: " + ex.getMessage());
            } finally {
                try {
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        F.add(J2);
        F.add(J3);
        F.add(J4);
        F.add(J5);
        F.add(J6);
        F.add(J7);
        F.add(J8);
        F.add(J9);
        
        
        F.add(M_Name);
        F.add(M_email);
        F.add(M_password);
        F.add(M_add);
        F.add(M_street);
        F.add(M_city);
        F.add(M_Zipcode);
        F.add(M_phone);
        F.add(registerbutton);

        F.setVisible(true);
    }
}
