/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.palisade.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.client.ClientConfiguredServices;
import uk.gov.gchq.palisade.config.service.ConfigUtils;
import uk.gov.gchq.palisade.config.service.ConfigurationService;
import uk.gov.gchq.palisade.example.common.ExamplePolicies;
import uk.gov.gchq.palisade.example.common.ExampleUsers;
import uk.gov.gchq.palisade.example.util.ExampleFileUtil;
import uk.gov.gchq.palisade.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.palisade.policy.service.request.SetResourcePolicyRequest;
import uk.gov.gchq.palisade.user.service.UserService;
import uk.gov.gchq.palisade.user.service.request.AddUserRequest;
import uk.gov.gchq.palisade.util.StreamUtil;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Convenience class for the examples to configure the users and data access policies for the example.
 */
public final class ExampleConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleConfigurator.class);
    private final String file;

    public ExampleConfigurator(final ClientConfiguredServices services, final String file) {
        URI absoluteFileURI = ExampleFileUtil.convertToFileURI(file);
        this.file = absoluteFileURI.toString();
        initialiseExample(services);
    }

    /**
     * Establishes policies and details for the examples and writes these into the configuration service.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final InputStream stream = StreamUtil.openStream(ExampleConfigurator.class, ConfigUtils.retrieveConfigurationPath());
        ConfigurationService configService = JSONSerialiser.deserialise(stream, ConfigurationService.class);
        ClientConfiguredServices cs = new ClientConfiguredServices(configService);
        new ExampleConfigurator(cs, args[0]);
    }

    private void initialiseExample(final ClientConfiguredServices services) {
        // The user authorisation owner or sys admin needs to add the user
        final UserService userService = services.getUserService();

        String[] users = new String[]{"reggie mint", "tom tally", "sally counter", "harry hopeful", "jules keeper", "faith broker", "ivan padlock", "erin overview", "peter profile", "garry geeke", "polly tasker", "bob nitter", "lemmie stage", "stew faster", "callie quartile", "tessa tube", "tania tidie", "angela cummings", "grant able", "julie stitched", "robbie records", "nancy noah", "des signa", "sidney seeker"};
        Collection<CompletableFuture> requestsList = new ArrayList<>();
        for (String user : users) {
            final CompletableFuture<Boolean> addUser = userService.addUser(
                    new AddUserRequest().user(ExampleUsers.getUser(user))
            );
            requestsList.add(addUser);
        }

        // Using Custom Rule implementations
        final SetResourcePolicyRequest customPolicies = ExamplePolicies.getExamplePolicy(file);

        final CompletableFuture<Boolean> policyStatus = services.getPolicyService().setResourcePolicy(
                customPolicies
        );
        requestsList.add(policyStatus);

        CompletableFuture[] requestsArray = new CompletableFuture[requestsList.size()];
        requestsList.toArray(requestsArray);
        // Wait for the users and policies to be loaded
        CompletableFuture.allOf(requestsArray).join();
        LOGGER.info("The example users and data access policies have been initialised.");
    }
}
