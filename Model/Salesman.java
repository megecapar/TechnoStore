package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Salesman {
    public Salesman(){}

    public String sellProduct(DBConnection conn,String name,int count) throws SQLException {

        ResultSet resultSet = conn.send_query("SELECT * FROM products WHERE name = '"+name+"'");
        if (!resultSet.isBeforeFirst() ) {  return "No Item called " + name; }
        if (count <= 0) return "Enter a positive number";

        else{
            int count2 = conn.send_query("SELECT count FROM products WHERE name = '"+ name +"'").getInt(1);

            if (count2 >= count){ //satılacak adet yeteri kadar varsa
                conn.send_update("UPDATE Products SET count = (SELECT count FROM Products WHERE name = '" + name + "') - " + count + " WHERE name = '" + name + "'");

                if (conn.send_query("SELECT count FROM products WHERE name = '"+ name +"'").getInt(1)==0) { // eğer 0sa sil
                    conn.send_update("DELETE FROM products WHERE name = '"+ name +"'");
                }
            }
            else{ return "Not Enough count to sell";}
        }
        return "Succesfuly sold " + count + " " + name;
    }

}
