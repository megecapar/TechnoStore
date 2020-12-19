package GUI;

import Model.DBConnection;
import Model.Salesman;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SalesmanPage {


    public JFrame frame;
    public JTextField textField_3;
    public JTextField textField_4;


    /**
     * Create the application.
     */
    public SalesmanPage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Salesman salesman = new Salesman();

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        JPanel sellProduct = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane);

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
                result = salesman.sellProduct(conn,a,b);
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


    }
    public static String readConfig(DBConnection database_connection) {
        return "jdbc:sqlite:db1.db";
    }

}
