package io.colby.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class DBCPDatabaseUtility {

    private static BasicDataSource dataSource;

    public static BasicDataSource getDataSource() {

        if (dataSource == null) {

            BasicDataSource ds = new BasicDataSource();

            //TODO change DB
            ds.setUrl("jdbc:sqlserver://;databaseName=");
            ds.setUsername("");
            ds.setPassword("");

//            ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            ds.setInitialSize(3);
            ds.setMaxTotal(25);
            ds.setMinIdle(0);
            ds.setMaxIdle(8);
            ds.setMaxOpenPreparedStatements(100);


            dataSource = ds;
        }

        return dataSource;
    }

    public static void shutdown() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
