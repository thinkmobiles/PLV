package plv.estrella.com.plv.database;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by vasia on 26.05.2015.
 */
public class Shop extends SugarRecord<Shop> implements Serializable {

    private String name;
    private int type;

    public Shop() {
    }

    public Shop(String _name, int _type) {
        name = _name;
        type = _type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
