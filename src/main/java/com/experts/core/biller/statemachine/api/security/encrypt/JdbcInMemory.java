package com.experts.core.biller.statemachine.api.security.encrypt;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;


@Component
public class JdbcInMemory {
    static final String JDBC_DRIVER = "org.h2.Driver"; //org.h2.Driver
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    @PostConstruct
    public void createTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER );
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS USER_APP_KEY " +
                    "(app_id VARCHAR(255) not NULL, " +
                    " public_key VARCHAR(MAX), " +
                    " private_key VARCHAR(MAX)) ";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt!=null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn!= null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");

    }

    public void insertData (String appId, String publicKey, String privateKey) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            String sql = "SELECT APP_ID FROM USER_APP_KEY WHERE APP_ID='"+appId+"'";
            ResultSet resultSet = stmt.executeQuery(sql);
            if(!resultSet.isBeforeFirst())
            {
                stmt.executeUpdate("INSERT INTO USER_APP_KEY " + "VALUES ('"+appId+"', '"+publicKey+"', '"+privateKey+"')");
                resultSet.close();
            }
            else
            {
                stmt.executeUpdate("UPDATE USER_APP_KEY SET PUBLIC_KEY='"+publicKey+"', PRIVATE_KEY='"+privateKey+"' WHERE APP_ID='"+appId+"' ");
                resultSet.close();
            }
            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (stmt!=null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn!=null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Goodbye!");

    }


    public String getPrivateKeyForAppId (String appId) {
        Connection conn = null;
        Statement stmt = null;
        String privateKey = null;
        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
            System.out.println("Reading record from the table...");
            stmt = conn.createStatement();
            String sql = "SELECT PRIVATE_KEY FROM USER_APP_KEY WHERE APP_ID='"+appId+"'";
            ResultSet resultSet = stmt.executeQuery(sql);

            while( resultSet.next()){
                privateKey = resultSet.getString("PRIVATE_KEY");

            }
            resultSet.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (stmt!=null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn!=null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return privateKey;

    }
}
