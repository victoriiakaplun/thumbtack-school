package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static net.thumbtack.school.database.jdbc.JdbcUtils.getConnection;

public class JdbcService {
    public static void insertTrainee(Trainee trainee) throws SQLException {
        String insertQuery = "INSERT INTO trainee VALUES(?,?,?,?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, trainee.getFirstName());
            statement.setString(3, trainee.getLastName());
            statement.setInt(4, trainee.getRating());
            statement.setNull(5, Types.INTEGER);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trainee.setId(generatedKeys.getInt((1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTrainee(Trainee trainee) throws SQLException {
        String updateQuery = "UPDATE trainee SET  firstname = ?, lastname = ?, rating = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(updateQuery)) {
            statement.setString(1, trainee.getFirstName());
            statement.setString(2, trainee.getLastName());
            statement.setInt(3, trainee.getRating());
            statement.setInt(4, trainee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        String findQuery = "SELECT * FROM trainee WHERE id = ?;";
        Trainee trainee = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, traineeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    trainee = new Trainee(traineeId,
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getInt("rating"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        String findQuery = "select * from trainee where id = ?;";
        Trainee trainee = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, traineeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    trainee = new Trainee(traineeId,
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        String findQuery = "select * from trainee;";
        List<Trainee> trainees = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Trainee trainee = new Trainee(resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("rating"));
                trainees.add(trainee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainees;
    }

    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        String findQuery = "select * from trainee;";
        List<Trainee> trainees = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Trainee trainee = new Trainee(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                trainees.add(trainee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainees;
    }

    public static void deleteTrainee(Trainee trainee) throws SQLException {
        String deleteQuery = "delete from trainee where id = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(deleteQuery)) {
            statement.setInt(1, trainee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTrainees() throws SQLException {
        String deleteQuery = "delete from trainee;";
        try (PreparedStatement statement = getConnection().prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSubject(Subject subject) throws SQLException {
        String insertQuery = "insert into subject(id, subject_name) values(?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, subject.getName());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subject.setId(generatedKeys.getInt((1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        String findQuery = "select * from subject where id = ? ;";
        Subject subject = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subject = new Subject(subjectId, resultSet.getString("subject_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        String findQuery = "select * from subject where id = ? ;";
        Subject subject = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subject = new Subject(subjectId, resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public static void deleteSubjects() throws SQLException {
        String deleteQuery = "delete from subject;";
        try (PreparedStatement statement = getConnection().prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSchool(School school) throws SQLException {
        String insertQuery = "insert into school (id, school_name, y) values(?,?,?);";
        try (PreparedStatement statement = getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, school.getName());
            statement.setInt(3, school.getYear());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    school.setId(generatedKeys.getInt((1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        String findQuery = "select school_name, y from school where id =?;";
        School school = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, schoolId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    school = new School(schoolId,
                            resultSet.getString("school_name"),
                            resultSet.getInt("y")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return school;
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        String findQuery = "select school_name, y from school where id = ?;";
        School school = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, schoolId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    school = new School(schoolId,
                            resultSet.getString(1),
                            resultSet.getInt(2)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return school;
    }

    public static void deleteSchools() throws SQLException {
        String deleteQuery = "delete from school;";
        try (PreparedStatement statement = getConnection().prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {
        String insertQuery = "insert into `group` values(?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, group.getName());
            statement.setString(3, group.getRoom());
            statement.setInt(4, school.getId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    group.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        String findQuery = "select * from school inner join `group`  on school_id =?;";
        School school = null;
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Group> groups = new ArrayList<>();
                while (resultSet.next()) {
                    school = new School(id, resultSet.getString(2), resultSet.getInt(3));
                    Group group = new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6));
                    if (!groups.contains(group)) {
                        groups.add(group);
                    }
                }
                if (school != null) {
                    school.setGroups(groups);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return school;
    }

    public static List<School> getSchoolsWithGroups() throws SQLException {
        String findQuery = "select * from school inner join `group` on school_id = school.id;";
        List<School> schools = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(findQuery); ResultSet resultSet = statement.executeQuery()) {
            List<Integer> schoolIds = new ArrayList<>();
            while (resultSet.next()) {
                int tmpId = resultSet.getInt(1);
                School school = new School(tmpId, resultSet.getString(2), resultSet.getInt(3));
                if (!schoolIds.contains(tmpId)) {
                    schools.add(school);
                    schoolIds.add(tmpId);
                }
                Group group = new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6));
                for (School s : schools) {
                    if (resultSet.getInt(7) == s.getId()) {
                        s.addGroup(group);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schools;
    }
}
