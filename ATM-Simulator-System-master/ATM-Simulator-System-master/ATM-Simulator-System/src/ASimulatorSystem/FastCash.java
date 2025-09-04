package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        add(background);

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(235, 400, 700, 35);
        background.add(l1);

        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        b1.setBounds(170, 499, 150, 35);
        b2.setBounds(390, 499, 150, 35);
        b3.setBounds(170, 543, 150, 35);
        b4.setBounds(390, 543, 150, 35);
        b5.setBounds(170, 588, 150, 35);
        b6.setBounds(390, 588, 150, 35);
        b7.setBounds(390, 633, 150, 35);

        background.add(b1);
        background.add(b2);
        background.add(b3);
        background.add(b4);
        background.add(b5);
        background.add(b6);
        background.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setLayout(null);
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton) ae.getSource()).getText().substring(3);
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }

            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            if (ae.getSource() == b7) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            } else {
                Date date = new Date();
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

                // Debug prints
                System.out.println("PIN: " + pin);
                System.out.println("Amount: " + amount);
                System.out.println("Date: " + formattedDate);

                // ✅ Corrected: use Connection object for PreparedStatement
                PreparedStatement ps = c.c.prepareStatement(
                    "INSERT INTO bank(pin, date, type, amount, mode) VALUES (?, ?, ?, ?, ?)"
                );
                ps.setString(1, pin);
                ps.setString(2, formattedDate);
                ps.setString(3, "Withdrawl");
                ps.setString(4, amount);
                ps.setString(5, "Withdrawl");
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}
