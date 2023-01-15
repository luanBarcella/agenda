package br.com.agenda.service.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RefreshScope
public class ConfigService {

    @Autowired
    private Environment env;

    public String get(final Key key) {
        return env.getProperty(key.getValue());
    }

    public Integer getInt(final Key key) {
        return env.getProperty(key.getValue(), Integer.class);
    }

    public Boolean getBoolean(final Key key) {
        return env.getProperty(key.getValue(), Boolean.class);
    }

    @Getter(AccessLevel.PROTECTED)
    @RequiredArgsConstructor
    public enum Key {

        URL_PADRAO_CIDADES("link-images.padrao.cidade"),
        URL_PADRAO_EVENTOS("link-images.padrao.evento"),
        URL_PADRAO_LOCAIS("link-images.padrao.local"),
        URL_PADRAO_PESSOAS("link-images.padrao.pessoa");

        private final String value;
    }
}

