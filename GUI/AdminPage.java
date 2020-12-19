package GUI;

import Model.Admin;
import Model.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class AdminPage {


    public JFrame frame;
    public JTextField textField;
    public JTextField textField_1;
    public JTextField textField_2;
    public JTextField textField_3;
    public JTextField textField_4;
    public JTextField textField_5;
    public JTextField textField_6;
    public JTextField textField_7;
    public JTextField textField_8;
    public String name;
    /**
     * Create the application.
     */
    public AdminPage(String name) {
        this.name = name;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        GUI gui = new GUI();
        Admin admn = new Admin();

        frame = new JFrame();
        frame.setBounds(100, 100, 750, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane);

        JPanel addProduct = new JPanel();
        tabbedPane.addTab("Add Product", null, addProduct, null);
        addProduct.setLayout(null);

        JLabel lblNewLabel = new JLabel("Product Name");
        lblNewLabel.setBounds(40, 54, 98, 14);
        addProduct.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(40, 77, 96, 20);
        addProduct.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Product Quantity");
        lblNewLabel_1.setBounds(40, 124, 98, 14);
        addProduct.add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(40, 148, 96, 20);
        addProduct.add(textField_1);

        JLabel lblNewLabel_2 = new JLabel("Add Product");
        lblNewLabel_2.setBounds(171, 22, 79, 14);
        addProduct.add(lblNewLabel_2);

        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(e -> {

            DBConnection conn = null;
            String a = textField.getText();
            int b = Integer.parseInt(textField_1.getText());
            try {
                conn = new DBConnection(readConfig(null));
                admn.addProduct(conn,a,b);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            conn.close();

        });
        btnNewButton.setBounds(161, 189, 89, 23);
        addProduct.add(btnNewButton);

        JPanel checkProduct = new JPanel();
        tabbedPane.addTab("Check Product", null, checkProduct, null);
        checkProduct.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("Check Product");
        lblNewLabel_3.setBounds(42, 25, 100, 14);
        checkProduct.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Product Name (Leave Blank to Check All Products)");
        lblNewLabel_4.setBounds(42, 62, 320, 14);
        checkProduct.add(lblNewLabel_4);

        textField_2 = new JTextField();
        textField_2.setBounds(42, 98, 96, 20);
        checkProduct.add(textField_2);
        textField_2.setColumns(10);


        JTable table = new JTable();
        table.setModel(new DefaultTableModel(5,2));
        table.getModel().isCellEditable(1,1);
        table.setPreferredScrollableViewportSize(new Dimension(300, 80));
        table.setFillsViewportHeight(true);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(400,25,200,187);
        checkProduct.add(scrollPane);
        scrollPane.getViewport().add(table);

        JButton btnNewButton_1 = new JButton("Check");
        btnNewButton_1.addActionListener(e -> {
            table.revalidate();
            table.repaint();
            DBConnection conn = null;
            String a = textField_2.getText();
            String[][] x = new String[0][];
            try {
                conn = new DBConnection(readConfig(null));
                x = admn.checkProduct(conn,a);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for(int i=0; i < table.getRowCount(); i++) {
                for(int j=0; j < table.getColumnCount(); j++) {
                    table.setValueAt("",i,j);
                }
            }
            table.setModel(new DefaultTableModel(x.length,x[0].length));
            if (x[0].length==1){
                PopupMessage.error("No Item called " + a);
            }
            for(int i = 1; i<=x.length;i++){
                for(int j = 1; j <= x[0].length; j++) {
                    table.setValueAt(x[i-1][j-1], i-1, j-1);
                }
            }

            conn.close();
        });
        btnNewButton_1.setBounds(42, 160, 89, 23);
        checkProduct.add(btnNewButton_1);

        JPanel sellProduct = new JPanel();
        tabbedPane.addTab("Sell Product", null, sellProduct, null);
        sellProduct.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("Product Name");
        lblNewLabel_6.setBounds(28, 53, 98, 14);
        sellProduct.add(lblNewLabel_6);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(28, 76, 96, 20);
        sellProduct.add(textField_3);

        JLabel lblNewLabel_1_1 = new JLabel("Product Quantity");
        lblNewLabel_1_1.setBounds(28, 123, 98, 14);
        sellProduct.add(lblNewLabel_1_1);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(28, 147, 96, 20);
        sellProduct.add(textField_4);

        JLabel lblNewLabel_2_1 = new JLabel("Sell Product");
        lblNewLabel_2_1.setBounds(159, 21, 79, 14);
        sellProduct.add(lblNewLabel_2_1);

        JButton btnNewButton_2 = new JButton("Sell");
        btnNewButton_2.addActionListener(e -> {

            DBConnection conn = null;
            String a = textField_3.getText();
            int b = 0;
            String result = "";
            try{
            b = Integer.parseInt(textField_4.getText());}
            catch (Exception ignored){
            }
            try {
                conn = new DBConnection(readConfig(null));
                result = admn.sellProduct(conn,a,b);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!result.equals("")){
                if(result.charAt(0) == 'S') // Succesfulynin S'si
                    PopupMessage.info(result);
                else PopupMessage.error(result);
            }
            conn.close();
        });
        btnNewButton_2.setBounds(149, 188, 89, 23);
        sellProduct.add(btnNewButton_2);

        JPanel addUser = new JPanel();
        tabbedPane.addTab("Add User", null, addUser, null);
        addUser.setLayout(null);

        JLabel lblNewLabel_7 = new JLabel("User Name");
        lblNewLabel_7.setBounds(39, 22, 98, 14);
        addUser.add(lblNewLabel_7);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(39, 45, 96, 20);
        addUser.add(textField_5);

        JLabel lblNewLabel_1_2 = new JLabel("Password");
        lblNewLabel_1_2.setBounds(39, 76, 98, 14);
        addUser.add(lblNewLabel_1_2);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(39, 99, 96, 20);
        addUser.add(textField_6);

        JLabel lblNewLabel_2_2 = new JLabel("Add User");
        lblNewLabel_2_2.setBounds(170, 5, 79, 14);
        addUser.add(lblNewLabel_2_2);

        JLabel lblNewLabel_1_2_1 = new JLabel("Role (1 for Admin; 2 for Manager; 3 for Salesman");
        lblNewLabel_1_2_1.setBounds(39, 130, 400, 14);
        addUser.add(lblNewLabel_1_2_1);

        textField_7 = new JTextField();
        textField_7.setColumns(10);
        textField_7.setBounds(39, 153, 96, 20);
        addUser.add(textField_7);

        JButton btnNewButton_3 = new JButton("Add");
        btnNewButton_3.addActionListener(e -> {
            DBConnection conn = null;
            String a = textField_5.getText();
            String b = textField_6.getText();
            int c = Integer.parseInt(textField_7.getText());
            try {
                conn = new DBConnection(readConfig(null));
                admn.addUser(conn,a, b, c);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            conn.close();

        });

        btnNewButton_3.setBounds(160, 172, 89, 23);
        addUser.add(btnNewButton_3);


        JPanel deleteUser = new JPanel();
        tabbedPane.addTab("Delete User", null, deleteUser, null);
        deleteUser.setLayout(null);

        JLabel lblNewLabel_3_1 = new JLabel("Delete User");
        lblNewLabel_3_1.setBounds(147, 11, 100, 14);
        deleteUser.add(lblNewLabel_3_1);

        JLabel lblNewLabel_4_1 = new JLabel("Username");
        lblNewLabel_4_1.setBounds(42, 46, 70, 14);
        deleteUser.add(lblNewLabel_4_1);

        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(42, 82, 96, 20);
        deleteUser.add(textField_8);

        JButton btnNewButton_1_1 = new JButton("Delete");
        btnNewButton_1_1.addActionListener(e -> {
            DBConnection conn = null;
            String a = textField_8.getText();
            if (!a.equals(name)) {
                try {
                    conn = new DBConnection(readConfig(null));
                    admn.deleteUser(conn, a);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                conn.close();
            }
            else PopupMessage.error("Cannot Delete Self");

        });
        btnNewButton_1_1.setBounds(147, 144, 89, 23);
        deleteUser.add(btnNewButton_1_1);
    }
    public static String readConfig(DBConnection database_connection) {
        return "jdbc:sqlite:db1.db";
    }

}
