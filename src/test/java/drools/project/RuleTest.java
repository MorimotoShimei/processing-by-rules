/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package drools.project;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
// import java.util.concurrent.TimeUnit;

// import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
// import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
// import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
// import org.kie.api.runtime.KieSessionConfiguration;
// import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import drools.models.Measurement;;

public class RuleTest {
    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        LOG.info("Creating kieBase");
        KieBase kieBase = kContainer.getKieBase("kBase1");

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        // KieSession session = kieBase.newKieSession();
        KieSession session = kContainer.newKieSession("ksession-rules");

        LOG.info("Populating globals");
        Set<String> check = new HashSet<String>();
        session.setGlobal("controlSet", check);

        LOG.info("Now running data");

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

        LOG.info("Final checks");

        assertEquals("Size of object in Working Memory is 4", 4, session.getObjects().size());
        // assertTrue("contains red", check.contains("red"));
        LOG.info(check.toString());
        assertTrue("contains green", check.contains("green"));
        assertTrue("contains blue", check.contains("blue"));
        assertTrue("contains black", check.contains("black"));
        assertFalse("not contains red", check.contains("red"));
    }
}
