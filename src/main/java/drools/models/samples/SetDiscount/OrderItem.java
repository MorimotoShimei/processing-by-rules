package drools.models.samples.SetDiscount;

import drools.models.samples.SetDiscount.Product;

// 注文明細
public class OrderItem {
    Product product;
    int qty = 1;

    public OrderItem(Product product) {
        this.product = product;
    }

    public OrderItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return this.qty;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getTotalPrice() {
        return qty * product.getPrice();
    }
}
