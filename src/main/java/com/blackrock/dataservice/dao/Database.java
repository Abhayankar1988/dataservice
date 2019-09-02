package com.blackrock.dataservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            ex.printStackTrace();
        }

        return connection;
    }

}
