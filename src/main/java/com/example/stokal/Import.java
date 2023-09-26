package com.example.stokal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import java.io.FileNotFoundException;


public class Import {
    private static final String jdbcUrl = "jdbc:sqlserver://DESKTOP-35TGP8J\\SQLEXPRESS01;authenticationScheme=NTLM;databaseName = myDB;portNumber = 1433; integratedSecurity=false;encrypt=true; trustServerCertificate=true";
    private static final String username = "sa";
    private static final String password = "1234";
    private int numberOfRecord;
    public void insertdata(String path,String tableName) throws FileNotFoundException {
        String createQuery = "";
        String questionmark = "";
        DBFReader reader = new DBFReader(new FileInputStream(path));
        Object[] fields = new Object[reader.getFieldCount()];
        Object field;


        for (int i = 0; i < reader.getFieldCount() ; i++) {
            field = reader.getField(i).getName();
            fields[i] = field;
        }


        for (int i = 0; i < reader.getFieldCount() ; i++) {


            if (i < (reader.getFieldCount() - 1) ){
                createQuery += fields[i].toString()  + ",";
            }
            else createQuery += fields[i].toString();


            if (i>=1) {
                questionmark += "?,";
            }
        }

        questionmark += "?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String insertDataSQL = "INSERT INTO "+ tableName  + "(" +
                    createQuery + ") VALUES (" + questionmark +")";


            // Veri ekleme sorgusunu çalıştırma
            try (PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL)) {
                Object[] data;
                int k = 0;
                while ((data = reader.nextRecord()) != null){
                    for (int i = 0; i < reader.getFieldCount();i++) {
                        insertDataStatement.setObject(i+1,data[i]);
                    }
                    insertDataStatement.executeUpdate();
                    k++;
                }
                this.numberOfRecord = k;
                System.out.println("Data inserted successfully.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBFField[] sourcefield = new DBFField[reader.getFieldCount()];
        for (int i = 0; i < reader.getFieldCount(); i++) {
            DBFField wfield = reader.getField(i);
            sourcefield[i] = wfield;
        }
        DBFWriter writer = new DBFWriter(new FileOutputStream(path));
        writer.setFields(sourcefield);
        writer.close();
    }
    public int getNumberOfRecord(){
        return numberOfRecord;
    }
}
