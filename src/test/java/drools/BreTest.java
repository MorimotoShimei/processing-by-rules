package drools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import drools.Bre;
import org.kie.api.runtime.KieSession;
import drools.models.samples.Measurement;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreTest {
    static final Logger LOG = LoggerFactory.getLogger(BreTest.class);

    static final String baseName = "kBase1";
    static final String sessionName = "ksession-samples-measurement";
    static Bre bre = null;
    static KieSession session = null;

    @Before
    public void setUp() throws Exception {
        bre = Bre.getInstance(baseName);
        session = bre.createSession(sessionName);
    }

    @After
    public void tearDown() throws Exception {
        session.dispose();
    }

    @Test
    public void testBre() {
        Set<String> check = new HashSet<String>();
        session.setGlobal("controlSet", check);

        Measurement mBlack= new Measurement("color", "black");
        session.insert(mBlack);
        session.fireAllRules();

        Measurement mGreen= new Measurement("color", "green");
        session.insert(mGreen);
        session.fireAllRules();

        Measurement mBlue= new Measurement("color", "blue");
        session.insert(mBlue);
        session.fireAllRules();

        Measurement mRed= new Measurement("roloc", "red");
        session.insert(mRed);
        session.fireAllRules();

        assertEquals("Size of object in Working Memory is 4", 4, session.getObjects().size());
        assertTrue("contains green", check.contains("green"));
        assertTrue("contains blue", check.contains("blue"));
        assertTrue("contains black", check.contains("black"));
        assertFalse("not contains red", check.contains("red"));
    }
}
