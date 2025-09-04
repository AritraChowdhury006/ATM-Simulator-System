package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4;
    JButton b1;
    String pin;

    MiniStatement(String pin) {
        this.pin = pin;

        setTitle("Mini Statement");
        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        l1 = new JLabel();
        l1.setVerticalAlignment(SwingConstants.TOP);
        JScrollPane scrollPane = new JScrollPane(l1);
        scrollPane.setBounds(20, 140, 350, 250);
        add(scrollPane);

        l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 20, 100, 20);
        add(l2);

        l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);

        l4 = new JLabel();
        l4.setBounds(20, 420, 300, 20);
        add(l4);

        b1 = new JButton("Exit");
        b1.setBounds(20, 500, 100, 25);
        b1.addActionListener(this);
        add(b1);

        try {
            Conn c = new Conn();
            PreparedStatement ps = c.c.prepareStatement("SELECT cardno FROM login WHERE pin = ?");
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String card = rs.getString("cardno");
                if (card != null && card.length() >= 16) {
                    l3.setText("Card Number: " + card.substring(0, 4) + "XXXXXXXX" + card.substring(12));
                } else {
                    l3.setText("Card Number: [Invalid]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Conn c1 = new Conn();
            PreparedStatement ps = c1.c.prepareStatement("SELECT * FROM bank WHERE pin = ?");
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();

            int balance = 0;
            StringBuilder sb = new StringBuilder("<html>");
            while (rs.next()) {
                sb.append(rs.getString("date"))
                  .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                  .append(rs.getString("mode"))
                  .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                  .append(rs.getString("amount"))
                  .append("<br><br>");

                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            sb.append("</html>");
            l1.setText(sb.toString());
            l4.setText("Your total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);
    }
}
