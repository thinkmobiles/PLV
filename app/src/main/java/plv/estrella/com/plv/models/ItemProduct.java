package plv.estrella.com.plv.models;

import java.io.Serializable;

/**
 * Created by samson on 22.05.2015.
 */
public class ItemProduct implements Serializable {
    private String mDescription;
    private int res;

    public ItemProduct(String _desc, int _id){
        this.mDescription = _desc;
        this.res = _id;
    }

    public String getmDescription() {
        return this.mDescription;
    }

    public int getRes() {
        return this.res;
    }
}
