package org.example;
import java.sql.*;

public class Main {
    String url = "jdbc:postgresql://localhost:5432/Assignment3";
    String user = "postgres";
    String password = "admin";


    public void getAllStudents(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                System.out.print(resultSet.getString("author_id")+"  ");
                System.out.print(resultSet.getString("first_name")+"  ");
                System.out.print(resultSet.getString("last_name")+"  ");
                System.out.print(resultSet.getString("email")+"  ");
                System.out.println(resultSet.getString("enrollment_date"));
            }
            connection.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}