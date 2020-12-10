package Model;

import java.sql.*;


public class DBConnection{

    private Connection conn = null;


    public DBConnection(String url) throws SQLException {
        conn = DriverManager.getConnection(url);
    }

    public void close() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * A method to send select statement to the underlying DBMS (e.g., "select * from Table1")
     * @param query_statement A query to run on the underlying DBMS
     * @return Resultset the query result.
     */
    public ResultSet send_query(String query_statement) {
        // Feel free to make them Class attributes
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query_statement);
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return rs;

    }
    public int send_update(String query_statement) throws SQLException {
        // Feel free to make them Class attributes
        Statement stmt = null;
        int rs = 0;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeUpdate(query_statement);
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != 0) {
                rs = 0;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
        return rs;

    }
    public int send_update2(String query_statement) throws SQLException {
        // Feel free to make them Class attributes
        Statement stmt = null;
        int rs = 0;


            stmt = conn.createStatement();
            rs = stmt.executeUpdate(query_statement);

        return rs;

    }
}
