package main.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private String url;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    private static final String deleteTable = "DROP TABLE IF EXISTS TEST";
    private static final String createTable = "CREATE TABLE TEST (FIELD INTEGER NOT NULL)";
    private static final String insertFields = "INSERT INTO TEST (FIELD) VALUES (?)";
    private static final String selectFields = "SELECT * FROM TEST";

    private void createDB() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            //Сначала удаляем таблицу TEST
            statement.executeUpdate(deleteTable);
            //Затем создаем пустую таблицу TEST
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void insertFields(long N) throws SQLException {
        try {
            createDB();
            Connection connection = DriverManager.getConnection(url, user, password);
            //Вставляем fields
            PreparedStatement preparedStatement = connection.prepareStatement(insertFields);

            for (int i = 1; i <= N; i++) {
                preparedStatement.setLong(1, i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<Integer> getFields() throws SQLException {
        List<Integer> fields = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, user, password);
        //Получаем значения fields
        PreparedStatement preparedStatement = connection.prepareStatement(selectFields);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int n = resultSet.getInt("field");
            fields.add(n);
        }
        return fields;
    }

}
