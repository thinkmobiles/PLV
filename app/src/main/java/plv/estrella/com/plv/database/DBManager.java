package plv.estrella.com.plv.database;

import com.cristaliza.mvc.models.estrella.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasia on 26.05.2015.
 */
public  class DBManager {

    // ITEM

    public static final List<DBItem> getDBItems(Shop _shop){
        final String id = Long.toString(_shop.getId());
        return DBItem.find(DBItem.class, "shop = ?", new String[]{id});
    }

    public static final void addItem(final Item _item, int _number, final Shop _shop){
        final List<DBItem> items = getDBItems(_shop);
        for(DBItem dbItem : items){
            if(dbItem.getItem().getId().equals(_item.getId()))
                return;
        }
        final DBItem dbItem = new DBItem(_item, _number, _shop);
        dbItem.save();
    }

    public static final void addItem(String _itemId, String _name, String _icon, String _pdf, String _ean, int _number, final Shop _shop){
        final List<DBItem> items = getDBItems(_shop);
        for(DBItem dbItem : items){
            if(dbItem.getId().equals(_itemId))
                return;
        }
        final DBItem dbItem = new DBItem(_itemId, _name, _icon, _pdf, _ean, _number, _shop);
        dbItem.save();
    }

    public static final void deleteItem(final long _itemId){
        final DBItem dbItem = DBItem.findById(DBItem.class, _itemId);
        dbItem.delete();
    }

    public static final void deleteAllItems(final Shop _shop){
        final List<DBItem> items = getDBItems(_shop);
        for (int i = 0; i < items.size(); i ++){
            deleteItem(items.get(i).getId());
        }
    }


    //  SHOP

    public static final List<Shop> getShops(){
        return Shop.listAll(Shop.class);
    }

    public static final Shop addShop(final String _shopName, int _shopType){
        final Shop shop = new Shop(_shopName, _shopType);
        shop.save();
        return shop;
    }

    public static final void deleteShop(final Shop _shop){
        if (_shop.getId() == null)
            return;
        final ArrayList<DBItem> items = (ArrayList<DBItem>) getDBItems(_shop);
        for (int i = 0; i < items.size(); i ++){
            deleteItem(items.get(i).getId());
        }
        _shop.delete();
    }

    public static final void deleteAllShop(int _type){
        final ArrayList<Shop> shops = (ArrayList<Shop>) getShops();
        if (shops == null)
            return;
        for  (int i = 0; i < shops.size(); i ++){
            if(shops.get(i).getType() == _type) {
                deleteShop(shops.get(i));
            }
        }
    }

}
