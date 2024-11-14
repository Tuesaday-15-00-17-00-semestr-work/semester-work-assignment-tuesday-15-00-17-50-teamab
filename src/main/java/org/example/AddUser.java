package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.Main.*;

public class AddUser implements io_operation {

    @Override
    public void oper(Database database, User user) {
        {

            JFrame frame = frame(500, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2, 15, 15));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
            panel.setBackground(null);

            JLabel title = label("Create new account");
            title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            title.setFont(new Font("Times", Font.BOLD, 21));
            title.setForeground(Color.decode("#ec7063"));
            frame.getContentPane().add(title, BorderLayout.NORTH);

            JLabel label0 = label("Name:");
            JLabel label1 = label("Nickname:");
            JLabel label2 = label("Password:");
            JTextField name = textfield();
            JTextField nickname = textfield();
            JTextField password = textfield();
            JRadioButton admin = radioButton("Admin");
            JRadioButton normaluser = radioButton("Normal User");
            JButton createacc = button("Create Account");
            JButton cancel = button("Cancel");

            admin.addActionListener(e -> {
                if (normaluser.isSelected()) {
                    normaluser.setSelected(false);
                }
            });
            normaluser.addActionListener(e -> {
                if (admin.isSelected()) {
                    admin.setSelected(false);
                }
            });

            panel.add(label0);
            panel.add(name);
            panel.add(label1);
            panel.add(nickname);
            panel.add(label2);
            panel.add(password);
            panel.add(admin);
            panel.add(normaluser);
            panel.add(createacc);
            panel.add(cancel);

            createacc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (database.userExists(name.getText().toString())) {
                        JOptionPane.showMessageDialog(new JFrame(), "Username exists!\nTry another one");
                        return;
                    }
                    if (name.getText().toString().matches("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Name cannot be empty!");
                        return;
                    }
                    if (nickname.getText().toString().matches("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Nickname cannot be empty!");
                        return;
                    }
                    if (password.getText().toString().matches("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Password cannot be empty!");
                        return;
                    }
                    if (!admin.isSelected() && !normaluser.isSelected()) {
                        JOptionPane.showMessageDialog(new JFrame(), "You must choose account type!");
                        return;
                    }
                    User user;
                    if (admin.isSelected()) {
                        user = new Admin(name.getText().toString(),
                                password.getText().toString(), nickname.getText().toString());
                    } else {
                        user = new NormalUser(name.getText().toString(),
                                password.getText().toString(), nickname.getText().toString());
                    }
                    frame.dispose();
                    //database.AddUser(user); - pred zmenou
                    database.addUser(user);
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
}