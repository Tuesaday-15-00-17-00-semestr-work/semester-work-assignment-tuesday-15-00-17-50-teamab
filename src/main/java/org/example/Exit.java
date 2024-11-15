package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit implements io_operation {

    Database database;

    @Override
    public void oper(Database database, User user) {
        JFrame frame = Main.frame(500, 300);

        this.database = new Database();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        panel.setBackground(null);

        JLabel title = Main.label("Welcome to student Library");
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Times", Font.BOLD, 21));
        title.setForeground(Color.decode("#cb4335"));
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JLabel label1 = Main.label("Nickname:");
        JLabel label2 = Main.label("Password:");
        JTextField nickname = Main.textfield();
        JTextField password = Main.textfield();
        JButton login = Main.button("Login");
        JButton newUser = Main.button("New User");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nickname cannot be empty!");
                    return;
                }
                if (password.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Password cannot be empty!");
                    return;
                }
                login(nickname.getText().toString(), password.getText().toString(), frame);
            }
        });
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newuser();
                frame.dispose();
            }
        });

        panel.add(label1);
        panel.add(nickname);
        panel.add(label2);
        panel.add(password);
        panel.add(login);
        panel.add(newUser);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void login(String nickname, String password, JFrame frame) {
        int n = database.login(nickname, password);
        if (n != -1) {
            User user = database.getUser(n);
            user.menu(database, user);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "User doesn't exist");
        }
    }

    private void newuser() {

        JFrame frame = Main.frame(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        panel.setBackground(null);

        JLabel title = Main.label("Create new account");
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Times", Font.BOLD, 21));
        title.setForeground(Color.decode("#ec7063"));
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JLabel label0 = Main.label("Name:");
        JLabel label1 = Main.label("Nickname:");
        JLabel label2 = Main.label("Password:");
        JTextField name = Main.textfield();
        JTextField nickname = Main.textfield();
        JTextField password = Main.textfield();
        JRadioButton admin = Main.radioButton("Admin");
        JRadioButton normaluser = Main.radioButton("Normal User");
        JButton createacc = Main.button("Create Account");
        JButton cancel = Main.button("Cancel");

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
                //database.AddUser(user);
                database.addUser(user);
                user.menu(database, user);
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
