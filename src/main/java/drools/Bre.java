
package drools;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bre {
    private static Bre bre = null;
    private static KieServices kieServices = null;
    private static KieContainer kieContainer = null;
    private static KieBase kieBase = null;
    static final Logger LOG = LoggerFactory.getLogger(Bre.class);

    // constractor
    private Bre() {
    }

    // getter
    public static synchronized Bre getInstance(String kBaseName) throws Exception {
        if (kBaseName.isEmpty()) {
            throw new Exception("kBaseName is empty.");
        }

        if (bre == null) {
            bre = new Bre();
            kieServices = KieServices.Factory.get();
            kieContainer = kieServices.getKieClasspathContainer();
            Results verifyResults = kieContainer.verify();
            for (Message m : verifyResults.getMessages()) {
                LOG.info("{}", m);
            }

            LOG.info(String.format("Creating kieBase: %s", kBaseName));
            kieBase = kieContainer.getKieBase(kBaseName);

            LOG.info("There should be rules: ");
            for (KiePackage kp : kieBase.getKiePackages()) {
                for (Rule rule : kp.getRules()) {
                    LOG.info("kp " + kp + " rule " + rule.getName());
                }
            }
        }
        return bre;
    }

    public KieSession createSession(String kSessionName) throws Exception {
        if (kSessionName.isEmpty()) {
            throw new Exception("kSessionName is empty.");
        }

        LOG.info(String.format("Creating session: %s", kSessionName));
        return kieContainer.newKieSession(kSessionName);
    }
}
