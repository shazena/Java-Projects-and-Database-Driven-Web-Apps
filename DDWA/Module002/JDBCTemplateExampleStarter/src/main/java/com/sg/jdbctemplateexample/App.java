/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.jdbctemplateexample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author kylerudy
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbc;

    private static Scanner sc;

    public static void main(String args[]) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sc = new Scanner(System.in);

        do {
            System.out.println("To-Do List");
            System.out.println("1. Display List");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Exit");

            System.out.println("Enter an option:");
            String option = sc.nextLine();
            try {
                switch (option) {
                    case "1":
                        displayList();
                        break;
                    case "2":
                        addItem();
                        break;
                    case "3":
                        updateItem();
                        break;
                    case "4":
                        removeItem();
                        break;
                    case "5":
                        System.out.println("Exiting");
                        System.exit(0);
                    default:
                        System.out.println("I don't understand");
                }
            } catch (Exception ex) {
                System.out.println("Error communicating with database");
                System.out.println(ex.getMessage());
                System.exit(0);
            }

        } while (true);
    }

    private void displayList() throws SQLException {
        //DAO
        String query = "SELECT * FROM todo";
        List<ToDo> todos = jdbc.query(query, new ToDoMapper());

        //VIEW
        for (ToDo td : todos) {
            System.out.printf("%s: %s -- %s -- %s\n",
                    td.getId(),
                    td.getTodo(),
                    td.getNote(),
                    td.isFinished());
        }
        System.out.println("");
    }

    private void addItem() throws SQLException {
        //VIEW
        System.out.println("Add Item");
        System.out.println("What is the task?");
        String task = sc.nextLine();
        System.out.println("Any additional notes?");
        String note = sc.nextLine();

        ToDo todo = new ToDo();
        todo.setTodo(task);
        todo.setNote(note);

        String query = "INSERT INTO todo(todo, note) VALUES(?,?)";
        jdbc.update(query, todo.todo, todo.note);
        System.out.println("Add Complete");
    }

    private void updateItem() throws SQLException {
        //VIEW
        System.out.println("Update Item");
        System.out.println("Which item do you want to update?");
        String itemId = sc.nextLine();
        
        //DAO
        String query = "SELECT * FROM todo WHERE id = ?";
        ToDo item = jdbc.queryForObject(query, new ToDoMapper(), itemId);

        //VIEW
        System.out.println("1. ToDo - " + item.getTodo());
        System.out.println("2. Note - " + item.getNote());
        System.out.println("3. Finished - " + item.isFinished());
        System.out.println("What would you like to change?");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                System.out.println("Enter now ToDo: ");
                String todo = sc.nextLine();
                item.setTodo(todo);
                break;
            case "2":
                System.out.println("Enter a new Note:");
                String note = sc.nextLine();
                item.setNote(note);
                break;
            case "3":
                System.out.println("Toggling Finished to " + !item.isFinished());
                item.setFinished(!item.isFinished());
                break;
            default:
                System.out.println("No change made");
                return;
        }
        
        
        String updateQuery = "UPDATE todo "
                + "SET todo = ?, "
                + "note = ?, "
                + "finished = ? "
                + "WHERE id = ?";
        jdbc.update(updateQuery,
                item.getTodo(),
                item.getNote(),
                item.isFinished(),
                item.getId());
        System.out.println("Update Complete");

    }

    private void removeItem() throws SQLException {
        //VIEW
        System.out.println("Remove Item");
        System.out.println("Which item would you like to remove?");
        String itemId = sc.nextLine();
        
        //DAO (DELETE)
        String query = "DELETE FROM todo WHERE id = ?";
        jdbc.update(query, itemId);
        System.out.println("Remove Complete");
    }

    private static final class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));//name of column
            td.setTodo(rs.getString("todo"));//name of column
            td.setNote(rs.getString("note"));//name of column
            td.setFinished(rs.getBoolean("finished"));//name of column
            return td;
        }
    }

}
