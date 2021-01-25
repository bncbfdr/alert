package com.fuxin.alert.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fuxin.alert.web.rest.TestUtil;

public class AlarmRuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmRule.class);
        AlarmRule alarmRule1 = new AlarmRule();
        alarmRule1.setId(1L);
        AlarmRule alarmRule2 = new AlarmRule();
        alarmRule2.setId(alarmRule1.getId());
        assertThat(alarmRule1).isEqualTo(alarmRule2);
        alarmRule2.setId(2L);
        assertThat(alarmRule1).isNotEqualTo(alarmRule2);
        alarmRule1.setId(null);
        assertThat(alarmRule1).isNotEqualTo(alarmRule2);
    }
}
