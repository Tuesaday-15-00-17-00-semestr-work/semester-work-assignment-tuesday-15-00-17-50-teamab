package org.example;

import javax.swing.*;

public class Admin extends User {

    public Admin(String name) {
        super(name);
        this.operations = new io_operation[] {
                new ViewBooks(),
                new AddBook(),
                new DeleteBook(),
                new DeleteAllData(),
                new AddUser(),
                new Exit()
        };
    }

    public Admin(String name, String password, String nickname) {
        super(name, password, nickname);
        this.operations = new io_operation[] {
                new ViewBooks(),
                new AddBook(),
                new DeleteBook(),
                new DeleteAllData(),
                new AddUser(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, User user) {
        String[] data = new String[6];
        data[0] = "View Books";
        data[1] = "Add Book";
        data[2] = "Delete Book";
        data[3] = "Delete all data";
        data[4] = "Add User";
        data[5] = "Exit";

        JFrame frame = this.frame(data, database, user);
        frame.setVisible(true);
    }

    public String toString() {
        return name+"<N/>"+ password +"<N/>"+ nickname +"<N/>"+"Admin";
    }

}
