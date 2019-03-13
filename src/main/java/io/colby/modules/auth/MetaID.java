package io.colby.modules.auth;

public class MetaID {

    private final int metaID;

    public MetaID (int metaID){
        this.metaID = metaID;
    }

    public int getInt(){
        return this.metaID;
    }

    /**
     * Returns false if MetaID does not exist
     * @return false if MetaID does not exist, true otherwise
     */
    public boolean doesExist(){
        return metaID > 0;
    }
}
