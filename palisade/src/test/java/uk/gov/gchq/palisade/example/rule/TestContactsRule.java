package uk.gov.gchq.palisade.example.rule;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.PhoneNumber;
import uk.gov.gchq.palisade.rule.Rule;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestContactsRule extends TestCommonRuleTheories {

    @DataPoint
    public static final ContactsRule rule = new ContactsRule();

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
    public void testContactsMasked(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == ""
        assumeThat(context.getPurpose(), isEmptyString());

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        Employee maskedRecord = new Employee(record);
        maskedRecord.setContactNumbers(recordWithRule.getContactNumbers());
        // Then
        assertThat(recordWithRule.getContactNumbers(), not(equalTo(record.getContactNumbers())));
        assertThat(recordWithRule, is(maskedRecord));
        for (PhoneNumber number : recordWithRule.getContactNumbers()) {
            assertThat(number.getType(), containsString("Work"));
        }
    }

    @Theory
    public void testContactsRedacted(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Doesn't satisfy EDIT rule
        assumeFalse(context.getPurpose().equals(Purpose.EDIT.name()) && record.getUid().equals(user.getUserId()));
        // Given - Purpose != ""
        assumeThat(context.getPurpose(), not(isEmptyString()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then - Expected
        Employee redactedRecord = new Employee(record);
        redactedRecord.setContactNumbers(null);
        // Then - Observed
        assertThat(recordWithRule, is(redactedRecord));
    }
}
