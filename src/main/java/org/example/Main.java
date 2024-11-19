package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main {

    static Scanner s;
    static Database database;

    public static void main(String[] args) {

        database = new Database();

        JFrame frame = frame(500, 300); // Tlačidlá
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        panel.setBackground(null);

        // Nadpis
        JLabel title = label("Welcome to Student Library");
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Times", Font.BOLD, 21));
        title.setForeground(Color.decode("#cb4335"));
        frame.getContentPane().add(title, BorderLayout.NORTH);

        // Tlacidla
        JLabel label1 = label("Nickname:");
        JLabel label2 = label("Password:");
        JTextField nickname = textfield();
        JTextField password = textfield();
        JButton login = button("Login");
        JButton newUser = button("New User");

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

    private static void login(String nickname, String password, JFrame frame) {
        int n = database.login(nickname, password);
        if (n != -1) {
            User user = database.getUser(n);
            user.menu(database, user);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "User does not exist");
        }
    }

    private static void newuser() {

        JFrame frame = frame(500, 400);
        frame.setResizable(false);

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

    //Nazov vyskakovacieho okna
    public static JFrame frame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Student Library");
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.white);
        frame.getContentPane().setBackground(Color.white);
        frame.setResizable(false);
        return frame;
    }

    //Text vedľa volitelného poľa
    public static JLabel label(String text) {
        JLabel label1 = new JLabel(text);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Times", Font.BOLD, 17));
        label1.setForeground(Color.black);
        return label1;
    }

    //Text napisany do volitelneho pola
    public static JTextField textfield() {
        JTextField textfield1 = new JTextField();
        textfield1.setFont(new Font("Times", Font.BOLD, 17));
        textfield1.setForeground(Color.black);
        textfield1.setHorizontalAlignment(SwingConstants.CENTER);
        return textfield1;
    }

    // Spodné tlačidlá (Login screen)
    public static JButton button(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Zaoblené rohy
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // Farba okrajov
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25); // Okraje
            }
        };
        button.setFont(new Font("Times", Font.BOLD, 17));
        button.setForeground(Color.white);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBackground(Color.decode("#e74c3c"));
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
    //Tlacidlo zvolenia moznosti (okruhle)
    public static JRadioButton radioButton(String text) {
        JRadioButton btn = new JRadioButton();
        btn.setForeground(Color.black);
        btn.setText(text);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setFont(new Font("Times", Font.BOLD, 17));
        btn.setBackground(null);
        return btn;
    }

    //Nadpis v prihlasenom menu
    public static JLabel title(String text) {
        JLabel title = Main.label(text);
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Times", Font.BOLD, 21));
        title.setForeground(Color.decode("#f1948a"));
        return title;
    }

}
