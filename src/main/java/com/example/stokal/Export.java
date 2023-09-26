package com.example.stokal;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Arrays;

public class Export {
    final String jdbcUrl = "jdbc:sqlserver://DESKTOP-35TGP8J\\SQLEXPRESS01;authenticationScheme=NTLM;databaseName=myDB;portNumber=1433;integratedSecurity=false;encrypt=true;trustServerCertificate=true";
    private final String username = "sa";
    private final String password = "1234";
    private int numberOfRecord = 0;



    public void exportData(String path,String tableName) throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(path));
        DBFField[] sourcefield = new DBFField[reader.getFieldCount()];

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT * FROM "+ tableName;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                for (int i = 0; i < reader.getFieldCount(); i++) {
                    DBFField field = reader.getField(i);
                    sourcefield[i] = field;
                }
             if(reader.getRecordCount() == 0) {
                 DBFWriter writer = new DBFWriter(new FileOutputStream(path));
                 System.out.println("Yazıyor");
                 writer.setFields(sourcefield);
                 int k = 0;
                 while (resultSet.next()) {
                     Object[] rowData = new Object[sourcefield.length];

                     for (int i = 0; i < sourcefield.length; i++) {
                         Object value = resultSet.getObject(i + 1);
                         if (value == null) {
                             rowData[i] = ""; // Null değerleri boş bir karakterle değiştir
                         } else {
                             rowData[i] = value;
                             System.out.println(Arrays.toString(rowData));
                         }
                     }
                     writer.addRecord(rowData);
                     k++;
                 }
                 this.numberOfRecord = k;
                 writer.close();
                 System.out.println("Data transfer completed successfully.");
             }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public int getNumberOfRecord(){
        return numberOfRecord;
    }
}
