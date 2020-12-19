package GUI;

import Model.DBConnection;
import Model.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class ManagerPage {


    public JFrame frame;
    public JTextField textField;
    public JTextField textField_1;
    public JTextField textField_2;

    /**
     * Create the application.
     */
    public ManagerPage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Manager mngr = new Manager();

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
                mngr.addProduct(conn,a,b);
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
                x = mngr.checkProduct(conn,a);
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


    }
    public static String readConfig(DBConnection database_connection) {
        return "jdbc:sqlite:db1.db";
    }

}
