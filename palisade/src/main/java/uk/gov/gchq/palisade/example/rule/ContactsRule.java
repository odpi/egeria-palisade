package uk.gov.gchq.palisade.example.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.PhoneNumber;
import uk.gov.gchq.palisade.rule.Rule;

import static java.util.Objects.requireNonNull;

public class ContactsRule implements Rule<Employee> {

    public ContactsRule() {
    }

    @Override
    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);
        final String purpose = context.getPurpose();

        if (purpose.isEmpty()) {
            return maskRecord(record);
        } else if (purpose.equals(Purpose.EDIT.name()) && user.getUserId().equals(record.getUid())) {
            return record;
        }
        return redactRecord(record);
    }

    private Employee redactRecord(final Employee record) {
        record.setContactNumbers(null);
        return record;
    }

    private Employee maskRecord(final Employee record) {
        PhoneNumber[] workNumbers = new PhoneNumber[2];
        for (PhoneNumber number : record.getContactNumbers()) {
            String numberType = number.getType();
            if (numberType.equals("Work")) {
                workNumbers[0] = number;
            } else if (numberType.equals("Work Mobile")) {
                workNumbers[1] = number;
            }
        }
        record.setContactNumbers(workNumbers);
        return record;
    }
}
