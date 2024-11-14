package org.example;

import javax.swing.*;

public class NormalUser extends User {

    public NormalUser(String name) {
        super(name);
        this.operations = new io_operation[] {
                new ViewBooks(),
                new AddBook(),
                new Exit()
        };
    }

    public NormalUser(String name, String password, String nickname) {
        super(name, password, nickname);
        this.operations = new io_operation[] {
                new ViewBooks(),
                new AddBook(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, User user) {

        String[] data = new String[3];
        data[0] = "View Books";
        data[1] = "Add Book";
        data[2] = "Exit";

        JFrame frame = this.frame(data, database, user);
        frame.setVisible(true);
    }

    public String toString() {
        return name+"<N/>"+ password +"<N/>"+ nickname +"<N/>"+"Normal";
    }

}
