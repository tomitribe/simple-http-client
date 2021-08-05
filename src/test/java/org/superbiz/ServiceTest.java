/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.superbiz;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.simplehttp.SimpleHttpClient;

import java.net.URL;

/**
 * Arquillian will start the container, deploy all @Deployment bundles, then run all the @Test methods.
 *
 * A strong value-add for Arquillian is that the test is abstracted from the server.
 * It is possible to rerun the same test against multiple adapters or server configurations.
 *
 * A second value-add is it is possible to build WebArchives that are slim and trim and therefore
 * isolate the functionality being tested.  This also makes it easier to swap out one implementation
 * of a class for another allowing for easy mocking.
 *
 */
@RunWith(Arquillian.class)
public class ServiceTest extends Assert {

    /**
     * ShrinkWrap is used to create a war file on the fly.
     *
     * The API is quite expressive and can build any possible
     * flavor of war file.  It can quite easily return a rebuilt
     * war file as well.
     *
     * More than one @Deployment method is allowed.
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "service.war").addClasses(SimpleService.class);
    }

    @ArquillianResource
    private URL webappUrl;

    @Test
    public void testGET() throws Exception {
        SimpleHttpClient client = new SimpleHttpClient() {
            public Exception doRequest() {
                try {
                    setHostname(webappUrl.getHost());
                    setPort(webappUrl.getPort());

                    // Open connection
                    connect();

                    String[] request = new String[1];
                    request[0] =
                            "GET /service/test HTTP/1.1" + CRLF +
                            "Host: " + webappUrl.getHost() + ":" + webappUrl.getPort() + CRLF +
                            "sec-ch-ua: \"Chromium\";v=\"91\", \" Not;A Brand\";v=\"99\"" + CRLF +
                            "sec-ch-ua-mobile: ?0" + CRLF +
                            "Upgrade-Insecure-Requests: 1" + CRLF +
                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36" + CRLF +
                            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + CRLF +
                            "Sec-Fetch-Site: none" + CRLF +
                            "Sec-Fetch-Mode: navigate" + CRLF +
                            "Sec-Fetch-User: ?1" + CRLF +
                            "Sec-Fetch-Dest: document" + CRLF +
                            "Accept-Encoding: gzip, deflate" + CRLF +
                            "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8" + CRLF +
                            "Connection: close" + CRLF +
                            "" + CRLF +
                            "";

                    setRequest(request);
                    processRequest(); // blocks until response has been read

                    // Close the connection
                    disconnect();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            public boolean isResponseBodyOK() {
                // TODO: add assertions here
                return true;
            }
        };

        final Exception e = client.doRequest();

        if (e != null) {
            throw e;
        }

        Assert.assertTrue(client.isResponse200());
        Assert.assertTrue(client.isResponseBodyOK());
    }

    @Test
    public void testForbidden() throws Exception {
        SimpleHttpClient client = new SimpleHttpClient() {
            public Exception doRequest() {
                try {
                    setHostname(webappUrl.getHost());
                    setPort(webappUrl.getPort());

                    // Open connection
                    connect();

                    String[] request = new String[1];
                    request[0] =
                            "GET /service/test/forbidden HTTP/1.1" + CRLF +
                            "Host: " + webappUrl.getHost() + ":" + webappUrl.getPort() + CRLF +
                            "sec-ch-ua: \"Chromium\";v=\"91\", \" Not;A Brand\";v=\"99\"" + CRLF +
                            "sec-ch-ua-mobile: ?0" + CRLF +
                            "Upgrade-Insecure-Requests: 1" + CRLF +
                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36" + CRLF +
                            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + CRLF +
                            "Sec-Fetch-Site: none" + CRLF +
                            "Sec-Fetch-Mode: navigate" + CRLF +
                            "Sec-Fetch-User: ?1" + CRLF +
                            "Sec-Fetch-Dest: document" + CRLF +
                            "Accept-Encoding: gzip, deflate" + CRLF +
                            "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8" + CRLF +
                            "Connection: close" + CRLF +
                            "" + CRLF +
                            "";

                    setRequest(request);
                    processRequest(); // blocks until response has been read

                    // Close the connection
                    disconnect();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            public boolean isResponseBodyOK() {
                // TODO: add assertions here
                return true;
            }
        };

        final Exception e = client.doRequest();

        if (e != null) {
            throw e;
        }

        Assert.assertTrue(client.isResponse403());
    }

    @Test
    public void testPOST() throws Exception {
        SimpleHttpClient client = new SimpleHttpClient() {
            public Exception doRequest() {
                try {
                    setHostname(webappUrl.getHost());
                    setPort(webappUrl.getPort());

                    // Open connection
                    connect();

                    String[] request = new String[1];
                    request[0] =
                            "POST /service/test HTTP/1.1" + CRLF +
                            "Host: " + webappUrl.getHost() + ":" + webappUrl.getPort() + CRLF +
                            "sec-ch-ua: \"Chromium\";v=\"91\", \" Not;A Brand\";v=\"99\"" + CRLF +
                            "sec-ch-ua-mobile: ?0" + CRLF +
                            "Upgrade-Insecure-Requests: 1" + CRLF +
                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36" + CRLF +
                            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + CRLF +
                            "Sec-Fetch-Site: none" + CRLF +
                            "Sec-Fetch-Mode: navigate" + CRLF +
                            "Sec-Fetch-User: ?1" + CRLF +
                            "Sec-Fetch-Dest: document" + CRLF +
                            "Accept-Encoding: gzip, deflate" + CRLF +
                            "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8" + CRLF +
                            "Connection: close" + CRLF +
                            "Content-Length: 14" + CRLF +
                            "Content-Type: text/plain" + CRLF +
                            "" + CRLF +
                            "norm rocks" + CRLF +
                            "" + CRLF +
                            "";

                    setRequest(request);
                    processRequest(); // blocks until response has been read

                    // Close the connection
                    disconnect();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            public boolean isResponseBodyOK() {
                // TODO: add assertions here
                return true;
            }
        };

        final Exception e = client.doRequest();

        if (e != null) {
            throw e;
        }

        Assert.assertTrue(client.isResponse200());
        Assert.assertTrue(client.isResponseBodyOK());
    }
}
