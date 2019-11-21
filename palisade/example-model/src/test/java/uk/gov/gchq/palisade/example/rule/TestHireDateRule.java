package uk.gov.gchq.palisade.example.rule;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.rule.Rule;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestHireDateRule extends TestCommonRuleTheories {

    @DataPoint
    public static final HireDateRule rule = new HireDateRule();

    @Theory
    public void testUnchangedWithProfileAccess(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == DIRECTORY_ACCESS
        assumeThat(context.getPurpose(), is(Purpose.PROFILE_ACCESS.name()));
        // Given - Employee.Uid == User.Uid
        assumeThat(record.getUid(), is(user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, equalTo(record));
    }

    @Theory
    public void testUnchangedWithHealthScreening(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == HEALTH_SCREENING
        assumeThat(context.getPurpose(), is(Purpose.HEALTH_SCREENING.name()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, is(record));
    }

    @Theory
    public void testHireDateRedacted(Rule<Employee> rule, final Employee record, final User user, Context context) {
        // Given - doesn't satisfy DIRECTORY_ACCESS rule
        assumeFalse(context.getPurpose().equals(Purpose.PROFILE_ACCESS.name()) && record.getUid().equals(user.getUserId()));
        // Given - Purpose != HEALTH_SCREENING
        assumeThat(context.getPurpose(), not(equalTo(Purpose.HEALTH_SCREENING.name())));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        Employee redactedRecord = new Employee(record);
        redactedRecord.setHireDate(null);
        // Then
        assertThat(recordWithRule, is(redactedRecord));
    }
}
