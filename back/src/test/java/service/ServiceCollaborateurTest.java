package service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.service.ServiceCollaborateur;
import dev.service.ServiceDepartement;
import dev.service.ServiceUrls;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ ServiceDepartement.class, ServiceCollaborateur.class, ServiceUrls.class })
public class ServiceCollaborateurTest {
	
	
	@Autowired
	private ServiceCollaborateur sc;
	
	@Test
	public void notNull() {
		assertThat(sc).isNotNull();
	}
	
	@Test
	public void findAll(){
		assertThat(sc.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void findParMauvaisMatricule(){
		assertThat(sc.findCollaborateurParMatricule("bd740e65").isPresent()).isFalse();
	}
	
	@Test
	public void findParMatricule(){
		assertThat(sc.findCollaborateurParMatricule("bd540e65").isPresent()).isTrue();
	}
	
	@Test
	public void checkAuthInvalid(){
		assertThat(sc.checkAuth("invalid", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8").isPresent()).isFalse();
	}
	
	@Test
	public void checkAuth(){
		assertThat(sc.checkAuth("robertsross@sultraxin.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8").isPresent()).isTrue();
	}
	
}
