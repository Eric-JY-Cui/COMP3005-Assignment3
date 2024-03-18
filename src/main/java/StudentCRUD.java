import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    String url;
    String user;
    String password;

    /**
     * initialize variable url, user, and password. create the table 'students' if not exist
     */
    public StudentCRUD(){
        url = "jdbc:postgresql://localhost:5432/Assignment3";
        user = "postgres";
        password = "admin";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE Students (\n" +
                    "    student_id SERIAL PRIMARY KEY,\n" +
                    "    first_name TEXT NOT NULL,\n" +
                    "    last_name TEXT NOT NULL,\n" +
                    "    email TEXT NOT NULL UNIQUE,\n" +
                    "    enrollment_date DATE NOT NULL\n" +
                    ");";
            statement.executeUpdate(query);
            query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES\n" +
                    "('John', 'Doe', 'john.doe@example.com', '2023-09-01'),\n" +
                    "('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),\n" +
                    "('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');";
            statement.executeUpdate(query);

            connection.close();
        }
        catch (PSQLException e){}//create the table if it does not exist
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * print out all elements and their attribute values
     */
    public void getAllStudents(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                System.out.print(resultSet.getString("student_id")+"  ");
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

    /**
     * create a new element in students table with set attribute values
     * @param first_name the value of the attribute first_name
     * @param last_name the value of the attribute last_name
     * @param email the value of the attribute email
     * @param enrollment_date the value of the attribute enrollment_date
     */
    public void addStudent(String first_name, String last_name, String email, String enrollment_date){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (" +
                    "\'"+first_name+"\', \'" +last_name+"\', \'"+email+"\', \'"+enrollment_date+"\')";
            statement.executeUpdate(query);
            System.out.println("User added successfully");

            connection.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an element that have specified id attribute value with a new value in email attribute
     * @param student_id the id attribute value of the element
     * @param new_email the new value of the element in email attribute
     */
    public void updateStudentEmail(int student_id, String new_email){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "UPDATE students\n" +
                    "SET email = '"+new_email+"'\n" +
                    "WHERE student_id = "+student_id+";";
            statement.executeUpdate(query);
            System.out.println("User updated successfully");

            connection.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the element with specified student_id value
     * @param student_id the id value of the deleting element
     */
    public void deleteStudent(int student_id){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "DELETE FROM students WHERE student_id = "+student_id+";";
            statement.executeUpdate(query);
            System.out.println("User removed successfully");

            connection.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * clear the table and reset it with initial elements
     */
    public void reset(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "TRUNCATE TABLE students RESTART IDENTITY;";
            statement.executeUpdate(query);
            query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES\n" +
                    "('John', 'Doe', 'john.doe@example.com', '2023-09-01'),\n" +
                    "('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),\n" +
                    "('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');";
            statement.executeUpdate(query);
            System.out.println("Table cleared successfully");

            connection.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentCRUD studentCRUD = new StudentCRUD();
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input[] = scanner.nextLine().split(" ");
            switch (input[0]){
                case "get":
                    studentCRUD.getAllStudents();
                    break;
                case "add":
                    studentCRUD.addStudent(input[1],input[2],input[3],input[4]);
                    break;
                case "update":
                    studentCRUD.updateStudentEmail(Integer.parseInt(input[1]),input[2]);
                    break;
                case "delete":
                    studentCRUD.deleteStudent(Integer.parseInt(input[1]));
                    break;
                case "reset":
                    studentCRUD.reset();
                    break;
                default:
                    break;
            }
        }
    }
}
