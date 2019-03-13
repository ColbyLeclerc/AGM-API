package io.colby.modules.auth.model;



import io.colby.modules.auth.MetaID;

public class AuthorizationModel {

    public boolean userHaveAccessToEnclosure(MetaID metaId, int enclosureId){

        if (enclosureId < 1){
            return false;
        }

        return true;
    }

    public boolean userHaveAccessToPlant (MetaID metaId, int enclosureId){

        if (enclosureId < 1){
            return false;
        }

        return true;
    }

}
