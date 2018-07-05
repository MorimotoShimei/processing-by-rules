package drools.models.samples.SetDiscount;

import drools.models.samples.SetDiscount.Product;

// 注文パラメータ（ルールエンジンでセット商品のマッチングに使用）
public class OrderParameter {
    Product product;
    boolean done = false;

    public OrderParameter(Product product, boolean done) {
        this.product = product;
        this.done = done;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    public boolean getDone() {
        return this.done;
    }
}
