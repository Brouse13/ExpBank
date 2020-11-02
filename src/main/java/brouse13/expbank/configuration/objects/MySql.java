package brouse13.expbank.configuration.objects;

import java.sql.*;

public class MySql{

    private Connection connection;
    private String host,database,username,password;
    int port;
    String error;

    public MySql(String host, String database, int port, String username, String password) throws SQLException {
        this.host = host;
        this.database = database;
        this.port = port;
        this.username = username;
        this.password = password;
        connect();
    }

    /**
     * Connect to a database
     * @throws SQLException If its trying to create two identical connections
     */
    public void connect()throws SQLException{
        try {
            synchronized (this) {
                if(connection != null && !connection.isClosed()) {
                    throw new SQLException("You can't have two identical connections opened");
                }

                Class.forName("com.mysql.jdbc.Driver");
                try {
                    connection = (DriverManager.getConnection(
                            "jdbc:mysql://"+this.host+ ":"+ this.port + "/"+ this.database, this.username, this.password));
                }catch (Exception exception) {
                    error = "Credentials";
                    throw new SQLException("Database doesn't exist or credentials are wrong");
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close database connection
     * @throws SQLException If exist an SqlException
     */
    public void close_connection() throws SQLException {
        connection.close();
    }

    /**
     * Query an SQL
     * @param sql Query to execute
     * @return Return query data
     */
    public ResultSet querySql(String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet results = statement.executeQuery();
            results.next();
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Update a table
     * @param sql Update to execute
     */
    public void updateSql(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            int results = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if connection is opened
     * @return Return if ceonnection is opened
     * @throws SQLException If exist an SQLException
     */
    public boolean isConnectionOpen()  throws SQLException{
        return connection != null && !connection.isClosed();
    }

    public boolean existError() {
        return error != null;
    }
}

