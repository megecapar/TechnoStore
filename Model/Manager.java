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
}
