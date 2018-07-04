package com.jpctrade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.glassfish.grizzly.http.server.HttpServer;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import static org.junit.Assert.assertEquals;

public class MainTest {
    private static HttpServer server = null;
    private static WebClient webClient = null;
    private static HtmlPage page = null;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();

        // create the client
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.waitForBackgroundJavaScript(10000);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);

        page = webClient.getPage(Main.STATIC_BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testSaticRoot() {
        assertEquals("omikuji", page.getTitleText());
        assertEquals("static page", page.querySelector("body").querySelector("h1").asText());
    }

}
