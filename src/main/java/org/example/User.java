package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class User {

    protected String name;
    protected String password;
    protected String nickname;
    protected io_operation[] operations;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password, String nickname) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    abstract public String toString();

    abstract public void menu(Database database, User user);

    public JFrame frame(String[] data, Database database, User user) {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(400, 350); //Tlačidlá
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Student Library");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(null);
        frame.setBackground(null);

        JLabel label1 = Main.title("Welcome  "+ this.name);
        frame.getContentPane().add(label1, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));//bottom 30
        panel.setLayout(new GridLayout(3 , 1, 25, 20));//rows 7
        panel.setBackground(null);

        for (int i=0;i< data.length;i++) {
            JButton button = new JButton(data[i]);
            button.setFont(new Font("Times", Font.BOLD, 17));
            button.setForeground(Color.white);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setBackground(Color.decode("#e74c3c"));
            button.setBorder(null);
            int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    operations[index].oper(database, user);
                    if (data[index].matches("Exit") || data[index].matches("Delete all data")) {
                        frame.dispose();
                    }
                }
            });
            panel.add(button);
        }
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        return frame;
    }

}
