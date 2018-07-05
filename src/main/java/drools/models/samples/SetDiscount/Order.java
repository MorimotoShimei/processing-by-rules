package drools.models.samples.SetDiscount;

import java.util.List;
import java.util.ArrayList;
import drools.models.samples.SetDiscount.OrderItem;

// 注文
public class Order {
    String orderNo;
    List<OrderItem> itemList = new ArrayList<OrderItem>(0);
    public double discountRatio = 0.0;

    public Order(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<OrderItem> getItemList() {
        return this.itemList;
    }

    public void setDiscountRatio(double ratio) {
        this.discountRatio = ratio;
    }

    public double getDiscountRaio() {
        return this.discountRatio;
    }

    public int getTotalPrice() {
        int acc = 0;
        for (int i = 0; i < itemList.size(); i++) {
            acc += itemList.get(i).getTotalPrice();
        }
        return (int) (acc * (1.0 - discountRatio));
    }
}
