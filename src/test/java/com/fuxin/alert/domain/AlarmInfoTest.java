package com.fuxin.alert.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fuxin.alert.web.rest.TestUtil;

public class AlarmInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmInfo.class);
        AlarmInfo alarmInfo1 = new AlarmInfo();
        alarmInfo1.setId(1L);
        AlarmInfo alarmInfo2 = new AlarmInfo();
        alarmInfo2.setId(alarmInfo1.getId());
        assertThat(alarmInfo1).isEqualTo(alarmInfo2);
        alarmInfo2.setId(2L);
        assertThat(alarmInfo1).isNotEqualTo(alarmInfo2);
        alarmInfo1.setId(null);
        assertThat(alarmInfo1).isNotEqualTo(alarmInfo2);
    }
}
