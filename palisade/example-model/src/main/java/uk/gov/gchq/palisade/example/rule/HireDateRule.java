package uk.gov.gchq.palisade.example.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.rule.Rule;

import static java.util.Objects.requireNonNull;

public class HireDateRule implements Rule<Employee> {
    public HireDateRule() {
    }

    @Override
    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);
        String purpose = context.getPurpose();

        if (purpose.equals(Purpose.HEALTH_SCREENING.name()) || purpose.equals(Purpose.SALARY_ANALYSIS.name())) {
            return record;
        } else if (purpose.equals(Purpose.PROFILE_ACCESS.name())) {
            if (user.getUserId().equals(record.getUid())) {
                return record;
            } else {
                return null;
            }
        }
        return redactRecord(record);
    }

    private Employee redactRecord(final Employee record) {
        record.setHireDate(null);
        return record;
    }
}
