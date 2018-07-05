package drools.models.samples.SetDiscount;

import java.util.List;
import java.util.ArrayList;
import drools.models.samples.SetDiscount.Product;

// セット商品
public class SetProduct extends Product {
    private List<Product> productList = new ArrayList<Product>(0);

    public SetProduct(String category, String name, int price, List<Product> products) {
        super(category, name, price);
        products.forEach(s -> productList.add(s));
    }

    public String getName() {
        List<String> names = new ArrayList<String>(0);
        productList.forEach(it -> {
            names.add(it.getName());
        });

        return super.getName() + "(" + String.join(",", names) + ")";
    }
}
