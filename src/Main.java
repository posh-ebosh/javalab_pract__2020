import jdbc.SimpleDataSource;
import models.Student;
import repositories.StudentsRepository;
import repositories.StudentsRepositoryJdbcImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/javalab_pract_2020";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sab0902";


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        //System.out.println(studentsRepository.findById(2L));


        for (Student s : studentsRepository.findAll()){
            System.out.println(s);
        }
        connection.close();

    }
}
