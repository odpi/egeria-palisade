package uk.gov.gchq.palisade.example.rule;

import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.ExampleUsers;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Manager;
import uk.gov.gchq.palisade.rule.Rule;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class TestCommonRuleTheories {
    // Implements a set of common tests applicable to any rule
    // Mostly, this covers behaviour of passing null to everything
    // An extending class must provide its Rule as a @DataPoint

    private static final Random random = new Random(1);
    private static final Context profileAccessContext = new Context().purpose(Purpose.PROFILE_ACCESS.name());

    @DataPoints
    public static final Employee[] records = new Employee[] {
            Employee.generate(random),
            Employee.generate(random),
    };
    @DataPoints
    public static final User[] users = new User[] {
            ExampleUsers.getUser("reggie mint"),
            ExampleUsers.getUser("tom tally")
    };
    @DataPoints
    public static final Context[] contexts = new Context[] {
            new Context().purpose(Purpose.SALARY_ANALYSIS.name()),
            new Context().purpose(Purpose.HEALTH_SCREENING.name()),
            new Context().purpose(Purpose.PROFILE_ACCESS.name()),
            new Context().purpose(Purpose.DIRECTORY_ACCESS.name()),
            new Context().purpose("")
    };

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    private static HashSet<Manager> traverseManagers(Manager[] managers) {
        HashSet<Manager> traversal = new HashSet<Manager>();
        if (managers != null) {
            for (Manager manager : managers) {
                traversal.add(manager);
                traversal.addAll(traverseManagers(manager.getManager()));
            }
        }
        return traversal;
    }

    @BeforeClass
    public static void setUp() {
        // At least one Employee has a Uid matching for at least one User
        records[0].setUid(users[0].getUserId());

        // At least one Employee has managers containing at least one User
        Iterator<Manager> allManagers  = traverseManagers(records[1].getManager()).iterator();
        assertTrue(allManagers.hasNext());
        allManagers.next().setUid(users[1].getUserId());
    }

    @Theory
    public void testNullEmployee(Rule<Employee> rule, final User user, final Context context) {
        // Given

        // When
        Employee redactedRecord = rule.apply(null, user, context);

        // Then
        assertThat(redactedRecord, is(nullValue()));
    }

    @Theory
    public void testNullUser(Rule<Employee> rule, final Employee record, final Context context) throws NullPointerException {
        // Given - Context profileViewContext

        // Then (expected)
        thrown.expect(NullPointerException.class);

        // When
        rule.apply(new Employee(record), null, context);
    }

    @Theory
    public void testContextWithoutPurpose(Rule<Employee> rule, final Employee record, final User user) throws NullPointerException {
        // Given - Employee record, User user
        Context purposelessContext = new Context(); // .purpose() assignment never called

        // Then (expected)
        thrown.expect(NullPointerException.class);

        // When
        rule.apply(new Employee(record), user, purposelessContext);
    }

    @Theory
    public void testNullContext(Rule<Employee> rule, final Employee record, final User user) throws NullPointerException {
        // Given - Employee record, User user

        // Then (expected)
        thrown.expect(NullPointerException.class);

        // When
        rule.apply(new Employee(record), user, null);
    }
}
