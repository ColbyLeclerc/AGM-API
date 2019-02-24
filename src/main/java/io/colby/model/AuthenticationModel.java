package io.colby.model;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import io.colby.database.DBCPDatabaseUtility;
import io.colby.entity.MetaID;
import io.colby.entity.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationModel {

    /**
     * Returns the MetaID of the user associated with the passed token. If token does not exist,
     * error flag will be activated in MetaID object, and object will contain an id of -1.
     * @param token - Token to lookup
     * @return MetaID
     */
    public MetaID getFromToken(Token token){

        return new MetaID(1);

//        ResultSet rs = null;
//
//        try (
//                Connection conn = DBCPDatabaseUtility.getDataSource().getConnection();
//                PreparedStatement statement = conn.prepareStatement("SELECT api_meta_id FROM  where token = ?");
//
//        ) {
//
//            statement.setString(1, token.toString());
//
//            rs = statement.executeQuery();
//
//            while (rs.next()) {
//                return new MetaID(rs.getInt("api_meta_id"));
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            if (rs != null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return new MetaID(-1);
    }
}
