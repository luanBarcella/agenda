package br.com.agenda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgendaApplicationTests {

	@Test
	@DisplayName("Teste Application Agenda.")
	public void assertConfig() {
		new AgendaApplication().main(new String[] { "--spring.profiles.active=test" });
	}

}
