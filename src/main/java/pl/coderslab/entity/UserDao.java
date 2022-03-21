package pl.coderslab.entity;

import pl.coderslab.dbUtil.DbUtil;
import pl.coderslab.model.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(email, username, password) VALUES (?, ?, ?);";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?;";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users;";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?;";

    public User create(User user) {
        long id = 0;
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStatement = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStatement.setString(1, user.getEmail());
            preStatement.setString(2, user.getUserName());
            preStatement.setString(3, user.getPassword());
            preStatement.executeUpdate();
            ResultSet rs = preStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                System.out.println("Inserted user ID is: " + id);
            }
            user.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User read(int id) {
        User user = new User();
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStatement = conn.prepareStatement(READ_USER_QUERY);
            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {
                user.setId(Long.parseLong(rs.getString("id")));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStatement = conn.prepareStatement(UPDATE_USER_QUERY);
            preStatement.setString(1, user.getEmail());
            preStatement.setString(2, user.getUserName());
            preStatement.setString(3, user.getPassword());
            preStatement.setString(4, String.valueOf(user.getId()));
            preStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] addToArry(User[] array) {
        array = Arrays.copyOf(array, array.length + 1);
        return array;
    }

    public User[] findAll() {
        int counter = 0;
        User user = null;
        User[] usersArray = new User[0];
        try (Connection conn = DbUtil.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_USERS_QUERY);
            while (rs.next()) {
                usersArray = addToArry(usersArray);
                user = new User();
                user.setId(Long.parseLong(rs.getString("id")));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                usersArray[counter] = user;
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersArray;
    }

    public void delete(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStatement = conn.prepareStatement(DELETE_USER_QUERY);
            preStatement.setString(1, String.valueOf(id));
            preStatement.executeUpdate();
            System.out.println("row with id " + id + " was deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
