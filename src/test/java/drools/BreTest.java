package drools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import drools.Bre;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import drools.models.samples.Measurement;
import drools.models.samples.SetDiscount.Product;
import drools.models.samples.SetDiscount.OrderItem;
import drools.models.samples.SetDiscount.Order;
import drools.models.samples.SetDiscount.OrderParameter;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreTest {
    static final Logger LOG = LoggerFactory.getLogger(BreTest.class);

    static final String baseName = "kBase1";
    static final String measureSessionName = "ksession-samples-measurement";
    static final String setDiscountSessionName = "ksession-samples-setdiscount";
    static Bre bre = null;
    static KieSession measureSession = null;
    static KieSession setDiscountSession = null;

    @Before
    public void setUp() throws Exception {
        bre = Bre.getInstance(baseName);
        measureSession = bre.createSession(measureSessionName);
        setDiscountSession = bre.createSession(setDiscountSessionName);
    }

    @After
    public void tearDown() throws Exception {
        measureSession.dispose();
        setDiscountSession.dispose();
    }

    @Test
    public void testMeasure() {
        Set<String> check = new HashSet<String>();
        measureSession.setGlobal("controlMeasureSet", check);

        Measurement mBlack = new Measurement("color", "black");
        measureSession.insert(mBlack);
        measureSession.fireAllRules();

        Measurement mGreen = new Measurement("color", "green");
        measureSession.insert(mGreen);
        measureSession.fireAllRules();

        Measurement mBlue = new Measurement("color", "blue");
        measureSession.insert(mBlue);
        measureSession.fireAllRules();

        Measurement mRed = new Measurement("roloc", "red");
        measureSession.insert(mRed);
        measureSession.fireAllRules();

        assertEquals("Size of object in Working Memory is 4", 4, measureSession.getObjects().size());
        assertTrue("contains green", check.contains("green"));
        assertTrue("contains blue", check.contains("blue"));
        assertTrue("contains black", check.contains("black"));
        assertFalse("not contains red", check.contains("red"));
    }

    @Test
    public void testSetDiscount() {
        final OrderItem[] inputData = {
            new OrderItem(new Product("A", "商品1", 4000), 1),
            new OrderItem(new Product("B", "商品2", 3000), 4),
            new OrderItem(new Product("C", "商品3", 3500), 4),
            new OrderItem(new Product("A", "商品4", 4500), 2),
            new OrderItem(new Product("D", "商品5", 4500), 2)
        };

        final Order order = new Order("order:001");

        Set<String> check = new HashSet<String>();
        setDiscountSession.setGlobal("controlSetDiscountSet", check);
        setDiscountSession.insert(order);

        for (OrderItem item : inputData) {
            for (int i = 0; i < item.getQty(); i++) {
                setDiscountSession.insert(new OrderParameter(item.getProduct()));
            }
        }

        Agenda agenda = setDiscountSession.getAgenda();
        agenda.getAgendaGroup("注文").setFocus();
        agenda.getAgendaGroup("注文明細").setFocus();

        setDiscountSession.fireAllRules();

        System.out.println(String.format("合計金額 = %s, 割引率 = %s", order.getTotalPrice(), order.getDiscountRatio()));

        order.getItemList().forEach(it -> {
            System.out.println(String.format(
                    "内訳 : <%s> %s x %s = %s",
                it.getQty(),
                it.getProduct().getCategory(),
                it.getProduct().getName(),
                it.getTotalPrice()));
        });
    }
}
