package uk.gov.gchq.palisade.example.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.rule.Rule;

import static java.util.Objects.requireNonNull;

public class PersonalCharacteristicsRule implements Rule<Employee> {
    public PersonalCharacteristicsRule() {
    }

    @Override
    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);
        String purpose = context.getPurpose();

        if (purpose.equals(Purpose.PROFILE_ACCESS.name()) && user.getUserId().equals(record.getUid())) {
            return record;
        } else {
            return redactRecord(record);
        }
    }

    private Employee redactRecord(final Employee redactedRecord) {
        redactedRecord.setBankDetails(null);
        redactedRecord.setEmergencyContacts(null);
        redactedRecord.setTaxCode(null);
        return redactedRecord;
    }
}
