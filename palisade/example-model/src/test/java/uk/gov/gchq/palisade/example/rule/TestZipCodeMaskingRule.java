package uk.gov.gchq.palisade.example.rule;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Address;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.rule.Rule;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestZipCodeMaskingRule extends TestCommonRuleTheories {

    @DataPoint
    public static final ZipCodeMaskingRule rule = new ZipCodeMaskingRule();

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
    public void testUnchangedWithHealthScreening(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == HEALTH_SCREENING
        assumeThat(context.getPurpose(), is(Purpose.HEALTH_SCREENING.name()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then
        assertThat(recordWithRule, is(record));
    }

    @Theory
    public void testZipCodeMasked(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Purpose == SALARY_ANALYSIS
        assumeThat(context.getPurpose(), is(Purpose.SALARY_ANALYSIS.name()));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Given - Cannot determine the Address of the maskedRecord
        Employee maskedRecord = new Employee(record);
        Address maskedAddress = maskedRecord.getAddress();
        maskedAddress.setZipCode(recordWithRule.getAddress().getZipCode());
        maskedAddress.setStreetAddressNumber(null);
        maskedAddress.setStreetName(null);
        // Then
        assertThat(recordWithRule.getAddress().getZipCode(), not(equalTo(record.getAddress().getZipCode())));
        assertThat(recordWithRule, is(maskedRecord));
    }

    @Theory
    public void testZipCodeRedacted(Rule<Employee> rule, final Employee record, final User user, final Context context) {
        // Given - Doesn't satisfy EDIT rule
        assumeFalse(context.getPurpose().equals(Purpose.EDIT.name()) && record.getUid().equals(user.getUserId()));
        // Given - Purpose != HEALTH_SCREENING or SALARY_ANALYSIS
        assumeThat(context.getPurpose(), not(equalTo(Purpose.HEALTH_SCREENING.name())));
        assumeThat(context.getPurpose(), not(equalTo(Purpose.SALARY_ANALYSIS.name())));

        // When
        Employee recordWithRule = rule.apply(new Employee(record), user, context);

        // Then - Expected
        Employee redactedRecord = new Employee(record);
        redactedRecord.setAddress(null);
        // Then - Observed
        assertThat(recordWithRule, is(redactedRecord));
    }
}
