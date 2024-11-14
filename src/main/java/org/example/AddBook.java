package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBook implements io_operation {

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(500, 600);

        JLabel title = Main.title("Add new book");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        panel.setBackground(null);

        JLabel label1 = Main.label("Book Name:");
        JLabel label2 = Main.label("Book Author:");


        JTextField name = Main.textfield();
        JTextField author = Main.textfield();

        JButton addbook = Main.button("Add Book");
        JButton cancel = Main.button("Cancel");

        panel.add(label1);
        panel.add(name);
        panel.add(label2);
        panel.add(author);
        panel.add(addbook);
        panel.add(cancel);

        addbook.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Book name cannot be empty!");
                    return;
                }
                if (author.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Book author cannot be empty!");
                    return;
                }



                Book book = new Book();
                if (database.getBook(name.getText().toString())>-1) {
                    JOptionPane.showMessageDialog(new JFrame(), "There is a book with this name!");
                    return;
                } else {
                    book.setName(name.getText().toString());
                    book.setAuthor(author.getText().toString());
                    //database.AddBook(book);
                    database.addBook(book);
                    JOptionPane.showMessageDialog(new JFrame(), "Book added successfully");
                    frame.dispose();
                }
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
