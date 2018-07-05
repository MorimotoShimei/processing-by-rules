package drools.models.samples.SetDiscount;

import drools.models.samples.SetDiscount.Product;

//商品
public class Product {
    String category;
    String name;
    int price;

    public Product(String category, String name, int price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [");
        if (category != null) {
            builder.append("category=").append(category).append(", ");
        }
        if (name != null) {
            builder.append("name=").append(name).append(", ");
        }
        builder.append("price=").append(price);
        builder.append("]");
        return builder.toString();
    }
}
