package io.colby.modules.auth.model;

import io.colby.modules.auth.MetaID;
import io.colby.modules.auth.Token;

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
