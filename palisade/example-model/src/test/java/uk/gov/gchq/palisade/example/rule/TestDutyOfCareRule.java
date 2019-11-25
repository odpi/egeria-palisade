package uk.gov.gchq.palisade.example.rule;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.util.EmployeeUtils;
import uk.gov.gchq.palisade.rule.Rule;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.*;

@RunWith(Theories.class)
public class TestDutyOfCareRule extends TestCommonRuleTheories {

    @DataPoint
    public static final DutyOfCareRule rule = new DutyOfCareRule();

    @Theory
    public void testUnchangedWithProfileAccess(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == PROFILE_ACCESS
        assumeThat(context.getPurpose(), is(Purpose.PROFILE_ACCESS.name()));
        // Given - Employee.Uid == User.Uid
        assumeThat(record.getUid(), is(user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, equalTo(record));
    }

    @Theory
    public void testUnchangedWithDutyOfCare(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == DIRECTORY_ACCESS
        assumeThat(context.getPurpose(), is(Purpose.DIRECTORY_ACCESS.name()));
        // Given - User.Uid in Employee.manager group
        assumeTrue(EmployeeUtils.isManager(record.getManager(), user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, is(record));
    }

    @Theory
    public void testEmergencyContactsRedacted(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - doesn't satisfy PROFILE_ACCESS rule
        assumeFalse(context.getPurpose().equals(Purpose.PROFILE_ACCESS.name()) && record.getUid().equals(user.getUserId()));
        // Given - doesn't satisfy DIRECTORY_ACCESS rule
        assumeFalse(context.getPurpose().equals(Purpose.DIRECTORY_ACCESS.name())
                && EmployeeUtils.isManager(record.getManager(), user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        Employee redactedRecord = new Employee(record);
        redactedRecord.setEmergencyContacts(null);
        // Then
        assertThat(recordWithRule, is(redactedRecord));
    }
}
