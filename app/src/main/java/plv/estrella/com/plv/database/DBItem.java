package plv.estrella.com.plv.database;

import com.cristaliza.mvc.models.estrella.Item;
import com.orm.SugarRecord;

/**
 * Created by vasia on 26.05.2015.
 */
public class DBItem extends SugarRecord<DBItem> {

    private int itemNumber;

    private String itemId;
    private String name;
    private String icon;
    private String pdf;

    private Shop shop;

    public DBItem() {
    }

    public DBItem(final Item _item, int _number, Shop _shop){
        shop = _shop;
        itemNumber = _number;

        name = _item.getName();
        icon = _item.getIcon();
        pdf = _item.getPdf();
        itemId = _item.getId();
    }

    public Item getItem(){

        Item item = new Item();

        item.setId(itemId);
        item.setName(name);
        item.setIcon(icon);
        item.setPdf(pdf);

        return item;
    }

    public Shop getShop() {
        return shop;
    }

    public int getNumber(){
        return itemNumber;
    }

    public void setNumber(int _number){
        itemNumber = _number;
    }
}
