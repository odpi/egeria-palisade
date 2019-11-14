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

package uk.gov.gchq.palisade.example.common;

import uk.gov.gchq.palisade.User;

public final class ExampleUsers {

    private ExampleUsers() {
    }

    public static User getUser(final String userId) {
        switch (userId) {
            case "reggie mint":
                return getReggieMint();
            case "tom tally":
                return getTomTally();
            case "sally counter":
                return getSallyCounter();
            case "harry hopeful":
                return getHarryHopeful();
            case "jules keeper":
                return getJulesKeeper();
            case "faith broker":
                return getFaithBroker();
            case "ivan padlock":
                return getIvorPadlock();
            case "erin overview":
                return getErinOverview();
            case "peter profile":
                return getPeterProfile();
            case "garry geeke":
                return getGaryGeeke();
            case "polly tasker":
                return getPollyTasker();
            case "bob nitter":
                return getBobNitter();
            case "lemmie stage":
                return getLemmieStage();
            case "stew faster":
                return getStewFaster();
            case "callie quartile":
                return getCallieQuartile();
            case "tessa tube":
                return getTessaTube();
            case "tania tidie":
                return getTaniaTidie();
            case "angela cummings":
                return getAngelaCummings();
            case "grant able":
                return getGrantAble();
            case "julie stitched":
                return getJulieStitched();
            case "robbie records":
                return getRobbieRecords();
            case "nancy noah":
                return getNancyNoah();
            case "des signa":
                return getDesSigna();
            case "sidney seeker":
                return getSidneySeeker();
            default:
                throw new RuntimeException(userId + ": user does not exist");
        }
    }

    private static void assignUserToFinanceGroup(final User user) {
        user.roles(Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.GlossaryBasicUser.name());
    }

    private static void assignUserToGovernanceGroup(final User user) {
        user.roles(Role.CMAdmin.name(),
                Role.CMImporter.name(),
                Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.GlossaryAuthor.name(),
                Role.GlossaryBasicUser.name(),
                Role.GlossaryAssigner.name(),
                Role.MDWUser.name(),
                Role.GlossaryUser.name(),
                Role.RulesAuthor.name(),
                Role.RulesManager.name());
    }

    private static void assignUserToITOpsGroup(final User user) {
        user.roles(Role.CMAdmin.name(),
                Role.CMImporter.name(),
                Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.SuiteAdmin.name(),
                Role.SorcererAdmin.name(),
                Role.GlossaryAdmin.name(),
                Role.MDWAdministrator.name(),
                Role.MDWUser.name(),
                Role.RulesAdministrator.name());
    }

    private static void assignUserToITProjectGroup(final User user) {
        user.roles(Role.CMAdmin.name(),
                Role.CMImporter.name(),
                Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.DataStageAdmin.name(),
                Role.DataStageUser.name(),
                Role.SorcererDataAdmin.name(),
                Role.SorcererUser.name(),
                Role.MDWUser.name(),
                Role.MDWAdministrator.name());
    }

    private static void assignUserToManufacturingGroup(final User user) {
        user.roles(Role.SuiteUser.name(),
                Role.GlossaryBasicUser.name());
    }

    private static void assignUserToClinicalTrialGroup(final User user) {
        user.roles(Role.CMImporter.name(),
                Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.SorcererDataAdmin.name(),
                Role.SorcererUser.name(),
                Role.GlossaryBasicUser.name(),
                Role.GlossaryAssigner.name(),
                Role.MDWUser.name(),
                Role.RulesAuthor.name(),
                Role.RulesUser.name()
                );
    }

    private static void assignUserToExternalConsultantsGroup(final User user) {
        user.roles(Role.CMUser.name(),
                Role.SuiteUser.name(),
                Role.SorcererUser.name(),
                Role.GlossaryBasicUser.name(),
                Role.MDWUser.name(),
                Role.RulesAuthor.name()
        );
    }

    public static User getReggieMint() {
        final User user = new ExampleUser()
                .name("Reggie Mint")
                .email("reggie.mint@cocopharmaceutical.com")
                .dn("uid=reggiemint,ou=People,dc=example,dc=org")
                .userId("reggiemint")
//                .auths("public", "private")
                ;
        assignUserToFinanceGroup(user);
        return (user);
    }

    public static User getTomTally() {
        final User user = new ExampleUser()
                .name("Tom Tally")
                .email("tom.tally@cocopharmaceutical.com")
                .dn("uid=tomtally,ou=People,dc=example,dc=org")
                .userId("tomtally")
//                .auths("public", "private")
                ;
        assignUserToFinanceGroup(user);
        return (user);
    }

    public static User getSallyCounter() {
        final User user = new ExampleUser()
                .name("Sally Counter")
                .email("sally.counter@cocopharmaceutical.com")
                .dn("uid=sallycounter,ou=People,dc=example,dc=org")
                .userId("sallycounter")
//                .auths("public", "private")
                ;
        assignUserToFinanceGroup(user);
        return (user);
    }

    public static User getHarryHopeful() {
        final User user = new ExampleUser()
                .name("Harry Hopeful")
                .email("harry.hopeful@cocopharmaceutical.com")
                .dn("uid=harryhopeful,ou=People,dc=example,dc=org")
                .userId("harryhopeful")
//                .auths("public", "private")
                ;
        assignUserToFinanceGroup(user);
        return (user);
    }

    public static User getJulesKeeper() {
        final User user = new ExampleUser()
                .name("Jules Keeper")
                .email("jules.keeper@cocopharmaceutical.com")
                .dn("uid=juleskeeper,ou=People,dc=example,dc=org")
                .userId("juleskeeper")
//                .auths("public", "private")
                ;
        assignUserToGovernanceGroup(user);
        return (user);
    }

    public static User getFaithBroker() {
        final User user = new ExampleUser()
                .name("Faith Broker")
                .email("faith.broker@cocopharmaceutical.com")
                .dn("uid=faithbroker,ou=People,dc=example,dc=org")
                .userId("faithbroker")
//                .auths("public", "private")
                ;
        assignUserToGovernanceGroup(user);
        return (user);
    }

    public static User getIvorPadlock() {
        final User user = new ExampleUser()
                .name("Ivor Padlock")
                .email("ivor.padlock@cocopharmaceutical.com")
                .dn("uid=ivorpadlock,ou=People,dc=example,dc=org")
                .userId("ivorpadlock")
//                .auths("public", "private")
                ;
        assignUserToGovernanceGroup(user);
        return (user);
    }

    public static User getErinOverview() {
        final User user = new ExampleUser()
                .name("Erin Overview")
                .email("erin.overview@cocopharmaceutical.com")
                .dn("uid=erinoverview,ou=People,dc=example,dc=org")
                .userId("erinoverview")
//                .auths("public", "private")
                ;
        assignUserToITOpsGroup(user);
        return (user);
    }

    public static User getPeterProfile() {
        final User user = new ExampleUser()
                .name("Peter Profile")
                .email("peter.profile@cocopharmaceutical.com")
                .dn("uid=peterprofile,ou=People,dc=example,dc=org")
                .userId("peterprofile")
//                .auths("public", "private")
                ;
        assignUserToITOpsGroup(user);
        return (user);
    }

    public static User getGaryGeeke() {
        final User user = new ExampleUser()
                .name("Gary Geeke")
                .email("gary.geeke@cocopharmaceutical.com")
                .dn("uid=garygeeke,ou=People,dc=example,dc=org")
                .userId("garygeeke")
//                .auths("public", "private")
                ;
        assignUserToITOpsGroup(user);
        return (user);
    }

    public static User getPollyTasker() {
        final User user = new ExampleUser()
                .name("Polly Tasker")
                .email("polly.tasker@cocopharmaceutical.com")
                .dn("uid=pollytasker,ou=People,dc=example,dc=org")
                .userId("pollytasker")
//                .auths("public", "private")
                ;
        assignUserToITProjectGroup(user);
        return (user);
    }

    public static User getBobNitter() {
        final User user = new ExampleUser()
                .name("Bob Nitter")
                .email("bob.nitter@cocopharmaceutical.com")
                .dn("uid=bobnitter,ou=People,dc=example,dc=org")
                .userId("bobnitter")
//                .auths("public", "private")
                ;
        assignUserToITProjectGroup(user);
        return (user);
    }

    public static User getLemmieStage() {
        final User user = new ExampleUser()
                .name("Lemmie Stage")
                .email("lemmie.stage@cocopharmaceutical.com")
                .dn("uid=lemmiestage,ou=People,dc=example,dc=org")
                .userId("lemmiestage")
//                .auths("public", "private")
                ;
        assignUserToITProjectGroup(user);
        return (user);
    }

    public static User getStewFaster() {
        final User user = new ExampleUser()
                .name("Stew Faster")
                .email("stew.faster@cocopharmaceutical.com")
                .dn("uid=stewfaster,ou=People,dc=example,dc=org")
                .userId("stewfaster")
//                .auths("public", "private")
                ;
        assignUserToManufacturingGroup(user);
        return (user);
    }

    public static User getCallieQuartile() {
        final User user = new ExampleUser()
                .name("Callie Quartile")
                .email("callie.quartile@cocopharmaceutical.com")
                .dn("uid=calliequartile,ou=People,dc=example,dc=org")
                .userId("calliequartile")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getTessaTube() {
        final User user = new ExampleUser()
                .name("Tessa Tube")
                .email("tessa.tube@cocopharmaceutical.com")
                .dn("uid=tessatube,ou=People,dc=example,dc=org")
                .userId("tessatube")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getTaniaTidie() {
        final User user = new ExampleUser()
                .name("Tania Tidie")
                .email("tania.tidie@cocopharmaceutical.com")
                .dn("uid=taniatidie,ou=People,dc=example,dc=org")
                .userId("taniatidie")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getAngelaCummings() {
        final User user = new ExampleUser()
                .name("Angela Cummings")
                .email("angela.cummings@hh-care.org")
                .dn("uid=angelacummings,ou=People,dc=example,dc=org")
                .userId("angelacummings")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getGrantAble() {
        final User user = new ExampleUser()
                .name("Grant Able")
                .email("grant.able@hh-care.org")
                .dn("uid=grantable,ou=People,dc=example,dc=org")
                .userId("grantable")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getJulieStitched() {
        final User user = new ExampleUser()
                .name("Julie Stitched")
                .email("julie.stitched@hh-care.org")
                .dn("uid=juliestitched,ou=People,dc=example,dc=org")
                .userId("juliestitched")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getRobbieRecords() {
        final User user = new ExampleUser()
                .name("Robbie Records")
                .email("robert.records@hh-care.org")
                .dn("uid=robbierecords,ou=People,dc=example,dc=org")
                .userId("robbierecords")
//                .auths("public", "private")
                ;
        assignUserToClinicalTrialGroup(user);
        return (user);
    }

    public static User getNancyNoah() {
        final User user = new ExampleUser()
                .name("Nancy Noah")
                .email("nancy.noah@pretend.ibm.com")
                .dn("uid=nancynoah,ou=People,dc=example,dc=org")
                .userId("nancynoah")
//                .auths("public", "private")
                ;
        assignUserToExternalConsultantsGroup(user);
        return (user);
    }

    public static User getDesSigna() {
        final User user = new ExampleUser()
                .name("Des Signa")
                .email("des.signa@pretend.ibm.com")
                .dn("uid=dessigna,ou=People,dc=example,dc=org")
                .userId("dessigna")
//                .auths("public", "private")
                ;
        assignUserToExternalConsultantsGroup(user);
        return (user);
    }

    public static User getSidneySeeker() {
        final User user = new ExampleUser()
                .name("Sidney Seeker")
                .email("sidney@seeker-specialists.com")
                .dn("uid=sidneyseeker,ou=People,dc=example,dc=org")
                .userId("sidneyseeker")
//                .auths("public", "private")
                ;
        assignUserToExternalConsultantsGroup(user);
        return (user);
    }
}
