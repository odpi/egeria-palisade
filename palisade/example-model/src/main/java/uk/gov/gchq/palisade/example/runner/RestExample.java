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

package uk.gov.gchq.palisade.example.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.client.ClientUtil;
import uk.gov.gchq.palisade.example.client.ExampleSimpleClient;
import uk.gov.gchq.palisade.example.common.ExampleUsers;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.service.PalisadeService;

import java.util.stream.Stream;


public class RestExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExample.class);

    public static void main(final String... args) throws Exception {
        if (args.length < 1) {
            System.out.printf("Usage: %s file\n", RestExample.class.getTypeName());
            System.out.println("\nfile\tfile containing serialised Employee instances to read");
            System.exit(1);
        }

        String sourceFile = args[0];
        LOGGER.info("Going to request {} from Palisade", sourceFile);
        new RestExample().run(sourceFile);
    }

    public void run(final String sourceFile) throws Exception {
        PalisadeService palisade = ClientUtil.getPalisadeClientEntryPoint();

        final ExampleSimpleClient client = new ExampleSimpleClient(palisade);

        final User reggieMint = ExampleUsers.getReggieMint();
        final User tomTally = ExampleUsers.getTomTally();
        final User sallyCounter = ExampleUsers.getSallyCounter();

        LOGGER.info("");
        LOGGER.info("reggieMint [ " + reggieMint.toString() + " } is reading the Employee file with a purpose of SALARY...");
        final Stream<Employee> reggieMintResults = client.read(sourceFile, reggieMint.getUserId().getId(), Purpose.SALARY_ANALYSIS.name());
        LOGGER.info("reggieMint got back: ");
        reggieMintResults.map(Object::toString).forEach(LOGGER::info);

        LOGGER.info("");
        LOGGER.info("reggieMint [ " + reggieMint.toString() + " } is reading the Employee file with a purpose of DUTY_OF_CARE...");
        final Stream<Employee> reggieMintResults2 = client.read(sourceFile, reggieMint.getUserId().getId(), Purpose.DUTY_OF_CARE.name());
        LOGGER.info("reggieMint got back: ");
        reggieMintResults2.map(Object::toString).forEach(LOGGER::info);

        LOGGER.info("");
        LOGGER.info("tomTally [ " + tomTally.toString() + " } is reading the Employee file with a purpose of DUTY_OF_CARE...");
        final Stream<Employee> tomTallyResults1 = client.read(sourceFile, tomTally.getUserId().getId(), Purpose.DUTY_OF_CARE.name());
        LOGGER.info("tomTally got back: ");
        tomTallyResults1.map(Object::toString).forEach(LOGGER::info);

        LOGGER.info("");
        LOGGER.info("tomTally [ " + tomTally.toString() + " } is reading the Employee file with a purpose that is empty...");
        final Stream<Employee> tomTallyResults2 = client.read(sourceFile, tomTally.getUserId().getId(), "");
        LOGGER.info("tomTally got back: ");
        tomTallyResults2.map(Object::toString).forEach(LOGGER::info);

        LOGGER.info("");
        LOGGER.info("sallyCounter [ " + sallyCounter.toString() + " } is reading the Employee file with a purpose that is empty...");
        final Stream<Employee> sallyCounterResults1 = client.read(sourceFile, sallyCounter.getUserId().getId(), "");
        LOGGER.info("sallyCounter got back: ");
        sallyCounterResults1.map(Object::toString).forEach(LOGGER::info);
    }
}
