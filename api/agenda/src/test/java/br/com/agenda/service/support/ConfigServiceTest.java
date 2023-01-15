package br.com.agenda.service.support;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import br.com.agenda.utils.RandomCollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {

    @InjectMocks
    private ConfigService config;

    @Mock
    private Environment env;

    private ConfigService.Key key;

    @Before
    public void setup() {
        key = RandomCollectionUtils.pickRandom(ConfigService.Key.values());
    }

    @Test
    public void getAsString() {

        final String value = UUID.randomUUID().toString();
        when(env.getProperty(any())).thenReturn(value);

        assertEquals(value, config.get(key));

        verify(env).getProperty(key.getValue());
    }

    @Test
    public void getAsInt() {

        final Integer value = nextInt();
        when(env.getProperty(any(), eq(Integer.class))).thenReturn(value);

        assertEquals(value, config.getInt(key));

        verify(env).getProperty(key.getValue(), Integer.class);
    }

    @Test
    public void getAsBoolean() {

        final Boolean value = nextBoolean();
        when(env.getProperty(any(), eq(Boolean.class))).thenReturn(value);

        assertEquals(value, config.getBoolean(key));

        verify(env).getProperty(key.getValue(), Boolean.class);
    }
}

