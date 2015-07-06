package plv.estrella.com.plv.models;

import com.cristaliza.mvc.models.estrella.Item;

import java.io.Serializable;

/**
 * Created by vasia on 22.05.2015.
 */
public class ItemSerializable implements Serializable {

    private transient Item mItem;

    public ItemSerializable(final Item _item){
        mItem = _item;
    }

    public Item getItem(){
        return mItem;
    }


}
