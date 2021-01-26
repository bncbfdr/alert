package com.fuxin.alert.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fuxin.alert.web.rest.TestUtil;

public class PlatformAlertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlatformAlert.class);
        PlatformAlert platformAlert1 = new PlatformAlert();
        platformAlert1.setId(1L);
        PlatformAlert platformAlert2 = new PlatformAlert();
        platformAlert2.setId(platformAlert1.getId());
        assertThat(platformAlert1).isEqualTo(platformAlert2);
        platformAlert2.setId(2L);
        assertThat(platformAlert1).isNotEqualTo(platformAlert2);
        platformAlert1.setId(null);
        assertThat(platformAlert1).isNotEqualTo(platformAlert2);
    }
}
