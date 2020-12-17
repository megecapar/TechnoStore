package GUI;

import Model.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GUI {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        DBConnection database_connection = null;
        instantiateSQLite();
        String url = readConfig(database_connection);
        try {
            database_connection = new DBConnection(url);
            processCommand(database_connection);
            //run1(database_connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (database_connection != null) {
                database_connection.close();
            }
        }
        GUI window = new GUI();
        window.frame.setVisible(true);
    }

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textField = new JTextField();
        textField.setBounds(68, 96, 179, 23);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(68, 153, 179, 20);
        frame.getContentPane().add(passwordField);

        JLabel lblNewLabel = new JLabel("Username:");
        lblNewLabel.setBounds(68, 71, 63, 14);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setBounds(68, 130, 63, 14);
        frame.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("OK");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ignored) {
                boolean pass = false;
                int role = 0;
                DBConnection conn = null;

                try {
                    conn = new DBConnection(readConfig(null));
                    pass = Login(conn,textField.getText(),passwordField.getPassword());
                    role = getRole(conn,textField.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (pass){
                    System.out.println("done");
                    if (role == 1){
                        System.out.println("Admin");
                        AdminPage windowx = new AdminPage(textField.getText());
                        windowx.frame.setVisible(true);
                        frame.setVisible(false);
                        conn.close();
                    }
                    else if (role == 2){
                        System.out.println("Manager");
                        ManagerPage windowx = new ManagerPage();
                        windowx.frame.setVisible(true);
                        frame.setVisible(false);
                        conn.close();
                    }
                    else if (role == 3){
                        System.out.println("Salesman");
                        SalesmanPage windowx = new SalesmanPage();
                        windowx.frame.setVisible(true);
                        frame.setVisible(false);
                        conn.close();
                    }
                    else {
                        System.out.println("error");
                    }
                }
                else System.out.println("notdone");
            }
        });
        btnNewButton.setBounds(180, 194, 89, 23);
        frame.getContentPane().add(btnNewButton);
    }


    public boolean Login(DBConnection conn, String userName , char[] pass) throws SQLException {
        ResultSet x = conn.send_query("SELECT username from Users where username='"+ userName +"'");
        if (x.isBeforeFirst()) {
            if (!x.getString(1).equals("")) {
                String y = conn.send_query("SELECT password from Users where username='" + userName + "'").getString(1);
                return (String.copyValueOf(pass).equals(y));
            }

        }
        return false;
    }
    public int getRole(DBConnection conn, String userName) throws SQLException {
        ResultSet x = conn.send_query("SELECT role from Users where username='"+ userName +"'");
        return x.getInt(1);
    }


    /**
     * Serverın adresi returnliycek
     *
     * @param database_connection mysqlde lazım olcak
     * @return databasein adresi
     */
    public static String readConfig(DBConnection database_connection) {
        return "jdbc:sqlite:db1.db";
    }

    /**
     * Adından belli zaten
     */
    public static void instantiateSQLite() {
        String dbLocation = "db1.db";
        String sqliteURL = "jdbc:sqlite:" + dbLocation;

        java.sql.Connection conn = null;
        try {
            conn = java.sql.DriverManager.getConnection(sqliteURL);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * eğer SQLite db'si yoksa bir tane oluşturuyor
     *
     * @param connection mainden aktif connectionu çekip burda kullanıyor
     * @throws SQLException
     */
    public static void processCommand(DBConnection connection) throws SQLException {
        Scanner scn = new Scanner(System.in);
        connection.send_update(
                "CREATE TABLE IF NOT EXISTS \"Users\" (\n" +
                        "\t\"userName\"\tvarchar(255) NOT NULL,\n" +
                        "\t\"password\"\tvarchar(255) NOT NULL,\n" +
                        "\t\"Role\"\tINT,\n" +
                        "\tPRIMARY KEY(\"userName\")\n" +
                        "CHECK ((Role > 0) AND (role < 4))" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS \"Products\" (\n" +
                        "\t\"name\"\tvarchar(255) NOT NULL,\n" +
                        "\t\"count\"\tint,\n" +
                        "\tPRIMARY KEY(\"name\")\n" +
                        ");"
        );
    }


    public static String ReturnPass(ResultSet resultSet) throws SQLException {
        if (!resultSet.isBeforeFirst()) {
            return null;
        } else {
            return resultSet.getString(1);
        }
    }
}