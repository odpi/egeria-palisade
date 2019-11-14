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
    public void testUnchangedWithEdit(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == EDIT
        assumeThat(context.getPurpose(), is(Purpose.EDIT.name()));
        // Given - Employee.Uid == User.Uid
        assumeThat(record.getUid(), is(user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, equalTo(record));
    }

    @Theory
    public void testUnchangedWithDutyOfCare(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == DUTY_OF_CARE
        assumeThat(context.getPurpose(), is(Purpose.DUTY_OF_CARE.name()));
        // Given - User.Uid in Employee.manager group
        assumeTrue(EmployeeUtils.isManager(record.getManager(), user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, is(record));
    }

    @Theory
    public void testEmergencyContactsRedacted(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - doesn't satisfy EDIT rule
        assumeFalse(context.getPurpose().equals(Purpose.EDIT.name()) && record.getUid().equals(user.getUserId()));
        // Given - doesn't satisfy DUTY_OF_CARE rule
        assumeFalse(context.getPurpose().equals(Purpose.DUTY_OF_CARE.name())
                && EmployeeUtils.isManager(record.getManager(), user.getUserId()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        Employee redactedRecord = new Employee(record);
        redactedRecord.setEmergencyContacts(null);
        // Then
        assertThat(recordWithRule, is(redactedRecord));
    }
}
