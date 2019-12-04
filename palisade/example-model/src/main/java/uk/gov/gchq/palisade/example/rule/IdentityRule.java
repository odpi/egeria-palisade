package uk.gov.gchq.palisade.example.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.rule.Rule;

import static java.util.Objects.requireNonNull;

public class IdentityRule implements Rule<Employee> {
    public IdentityRule() {
    }

    @Override
    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);
        String purpose = context.getPurpose();

        if (purpose.isEmpty() || purpose.equals(Purpose.HEALTH_SCREENING.name())) {
            return record;
        } else if (purpose.equals(Purpose.COMPANY_DIRECTORY.name())) {
            return record;
        } else if (purpose.equals(Purpose.PROFILE_ACCESS.name())) {
            if (user.getUserId().equals(record.getUid())) {
                return record;
            } else {
                return null;
            }
        } else {
            return redactRecord(record);
        }
    }

    private Employee redactRecord(final Employee record) {
        record.setUid(null);
        record.setName(null);
        return record;
    }
}
