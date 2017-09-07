package back.TestAbsence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.controller.ControllerAbsence;
import dev.entite.Absence;
import dev.service.ServiceAbsence;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Absence.class, ControllerAbsence.class, ServiceAbsence.class })
public class TestAbsence {


	@Autowired
	private ServiceAbsence sa;
	
	@Test
	public void ajoutAbsence() {
		
	}

}
