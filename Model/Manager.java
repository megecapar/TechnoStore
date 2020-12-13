package Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Manager {
    public Manager(){}

    public void addProduct(DBConnection conn,String name, int count) throws SQLException {
        try {
            conn.send_update2("INSERT INTO Products (name,count)" + "VALUES('"+name+"',"+count+");");
        }
        catch (SQLException e){
            if (e.getErrorCode()==19) { //primary key hatası
                conn.send_update("UPDATE Products SET count = (SELECT count FROM Products WHERE name = '" + name + "') + " + count + " WHERE name = '" + name + "'");
            }
            else{
                printErrors(e);
            }
        }

    }

    public String[][] checkProduct(DBConnection conn,String name) throws SQLException {

        if (name.equals("")){

            ResultSet resultSet = conn.send_query("SELECT * FROM products");
            ResultSet count = conn.send_query("SELECT Count(*) from products");
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int column = rsmd.getColumnCount();
            int row = count.getInt(1);
            if (row==1){
                String[][] result = new String[1][2];
                ResultSet rs2 = conn.send_query("SELECT * FROM products");
                result[0][0] = rs2.getString(1);
                result[0][1] = rs2.getString(2);
                return result;
            }
            else if (row == 0){
                return new String[1][1];
            }
            String[][] result = new String[row][column];
            for(int i = 1;i<=row;i++){
                ResultSet rs2 = conn.send_query("SELECT * FROM products where ROWID = " + i );
                for(int j = 1; j <= column;j++){
                    result[i-1][j-1] = rs2.getString(j);
                    System.out.println(result[i-1][j-1]);
                }
            }
            return result;

        }
        ResultSet resultSet = conn.send_query("SELECT * FROM products WHERE name = '"+name+"'");
        if (!resultSet.isBeforeFirst() ) {  System.out.println("No Item called " + name); }
        else {
            String[][] result = new String[1][2];
            result[0][0] = resultSet.getString(1);
            result[0][1] = resultSet.getString(2);
            return result;
        }

        return new String[1][1];
    }

    private void printErrors(SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

}
