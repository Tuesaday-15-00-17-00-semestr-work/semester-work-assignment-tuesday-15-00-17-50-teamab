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
        frame.setSize(400, 450); // Zvýšená výška pre väčšie tlačidlá
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Student Library");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(null);
        frame.setBackground(null);

        JLabel label1 = Main.title("Welcome " + this.name);
        frame.getContentPane().add(label1, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        panel.setLayout(new GridLayout(data.length, 1, 20, 20)); // Väčšie medzery medzi tlačidlami
        panel.setBackground(null);

        for (int i = 0; i < data.length; i++) {
            JButton button = new JButton(data[i]) {
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
                    g2.setColor(Color.BLACK); // Čierne okraje
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25); // Zaoblené okraje
                }
            };
            button.setFont(new Font("Times", Font.BOLD, 18));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.decode("#e74c3c"));
            button.setOpaque(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);

            // Nastavenie preferovanej veľkosti tlačidla
            button.setPreferredSize(new Dimension(300, 50)); // Šírka 300px a výška 50px

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
