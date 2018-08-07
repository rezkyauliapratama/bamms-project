package rezkyaulia.com.bamms_project.util;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Rezky Aulia Pratama on 3/7/18.
 */
@Singleton
public class ParameterConstant {

    @Inject
    public ParameterConstant() {
    }

    public final String SCHEDULE_YEARLY = "CAT_SCDL_YEARLY";
    public final String SCHEDULE_MONTHLY = "CAT_SCDL_MONTHLY";
    public final String SCHEDULE_WEEKLY = "CAT_SCDL_WEEKLY";
    public final String SCHEDULE_DAILY = "CAT_SCDL_DAILY";
    public final String SCHEDULE_ONCE = "CAT_SCDL_ONCE";

    public final String ACTIVITY_TYPE = "ID_ACTIVITY_TYPE";
    public final String CAT_BAD_HABIT = "CAT_BAD_HABIT";
    public final String CAT_GOOD_HABIT = "CAT_GOOD_HABIT";
}
