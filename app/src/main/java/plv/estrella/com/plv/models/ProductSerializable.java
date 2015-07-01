package plv.estrella.com.plv.models;

import com.cristaliza.mvc.models.estrella.Product;

import java.io.Serializable;

/**
 * Created by samson on 01.07.15.
 */
public class ProductSerializable implements Serializable {
    Product product;

    public ProductSerializable(){

    }

    public ProductSerializable(Product _product){
        product = _product;
    }

    public Product getProduct() {
        return product;
    }
}
