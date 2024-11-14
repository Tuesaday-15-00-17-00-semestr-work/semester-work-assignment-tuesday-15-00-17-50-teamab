package org.example;

import javax.swing.*;
import java.awt.*;

public class ViewBooks implements io_operation {

    @Override
    public void oper(Database database, User user) {

        int rows = database.getAllBooks().size()+1;
        int height = rows*60 + 100;
        JFrame frame = Main.frame(1000, height);

        JLabel title = Main.title("View Books");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 7, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        panel.setBackground(null);

        String[] row1 = new String[] {"Name", "Author"};
        for (String s : row1) {
            JLabel label = label(s);
            panel.add(label);
        }

        for (Book b : database.getAllBooks()) {
            JLabel label1 = label(b.getName());
            JLabel label2 = label(b.getAuthor());
            panel.add(label1);
            panel.add(label2);
        }
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JLabel label(String text) {
        JLabel label = Main.label(text);
        label.setBackground(Color.white);
        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label.setOpaque(true);
        return label;
    }

}
