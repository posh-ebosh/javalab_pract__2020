package repositories;

import models.Mentor;
import models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id = ";
    private static final String SQL_SELECT_BY_AGE = "select * from student where age = ";

    private static final String SQL_SELECT_ALL_WITH_MENTORS = "select student.id, student.first_name, student.last_name, student.age,\n" +
            " student.group_number, m.id as mentor_id, m.first_name fn_mentor, m.last_name ln_mentor\n" +
            " from student left join mentor m on student.id = m.student_id";

    private Connection connection;

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAllByAge(int age) {
        Statement statement = null;
        ResultSet result = null;
        List<Student> studentsFindAllByAge = new ArrayList<>();

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_AGE + age);
            while (result.next()) {
                studentsFindAllByAge.add(new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                ));
                //result.next();
            }
            return studentsFindAllByAge;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }


    @Override
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet result = null;
        List<Student> studentsFindAll = new ArrayList<>();
        List<Mentor> mentors = new ArrayList<>();
        Student student;
        Student student1;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_ALL_WITH_MENTORS);

            while (result.next()) {
                boolean b = true;
                for(int i = 0; i<studentsFindAll.size(); i++) {
                    if (studentsFindAll.get(i).getId() == result.getInt("id")) {
                        b = false;

                        studentsFindAll.get(i).addMentor(new Mentor(
                                result.getInt("mentor_id"),
                                result.getString("fn_mentor"),
                                result.getString("ln_mentor")
                        ));
                        break;
                    }
                }
                student = new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number"),
                        new ArrayList<>()
                );
                if (b) {
                    studentsFindAll.add(student);
                    if (result.getInt("mentor_id") != 0) {
                        studentsFindAll.get(studentsFindAll.size()-1).addMentor(new Mentor(
                                result.getInt("mentor_id"),
                                result.getString("fn_mentor"),
                                result.getString("ln_mentor")

                        ));
                    }
                }


            }
            return studentsFindAll;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }

    @Override
    public Student findById(Long id) {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_ID + id);
            if (result.next()) {
                return new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                );
            } else return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }


    @Override
    public void save(Student entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO student(first_name, last_name, age, group_number) VALUES (" +
                    "\'" + entity.getFirstName() + "\', " +
                    "\'" + entity.getLastName() + "\', " +
                    "" + entity.getAge() + ", " +
                    "" + entity.getGroupNumber() +
                    ")");

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);

        }

    }



    @Override
    public void update(Student entity) {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();

            statement.execute("UPDATE student SET" +
                    " first_name = \'" + entity.getFirstName() +
                    "\', last_name = \'" + entity.getLastName() +
                    "\', age = " + entity.getAge() +
                    ", group_number = " + entity.getGroupNumber() +
                    " WHERE id = " + entity.getId() + "");

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }
}

