package uk.gov.gchq.palisade.example.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.Purpose;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.WorkLocation;
import uk.gov.gchq.palisade.rule.Rule;

import static java.util.Objects.requireNonNull;

public class WorkLocationRule implements Rule<Employee>{
    public WorkLocationRule(){
    }

    @Override
    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);
        String purpose = context.getPurpose();

        if (purpose.equals(Purpose.HEALTH_SCREENING.name())) {
            return record;
        } else if (purpose.equals(Purpose.PROFILE_ACCESS.name())) {
            if (user.getUserId().equals(record.getUid())) {
                return record;
            } else {
                return null;
            }
        } else if (purpose.equals(Purpose.SALARY_ANALYSIS.name()) || purpose.equals(Purpose.COMPANY_DIRECTORY.name())) {
            return redactWorkLocationAddress(record);
        }  else {
            return redactWorkLocation(record);
        }
    }

    private Employee redactWorkLocationAddress(final Employee redactedRecord) {
        WorkLocation location = redactedRecord.getWorkLocation();
        location.setAddress(null);

        redactedRecord.setWorkLocation(location);
        return redactedRecord;
    }

    private Employee redactWorkLocation(final Employee redactedRecord) {
        redactedRecord.setWorkLocation(null);
        return redactedRecord;
    }
}
