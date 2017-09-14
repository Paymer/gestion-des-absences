package entite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entite.Collaborateur;
import dev.entite.Collaborateur.Grade;
import dev.service.ServiceCollaborateur;
import dev.service.ServiceDepartement;
import dev.service.ServiceUrls;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ ServiceCollaborateur.class, ServiceDepartement.class, ServiceUrls.class })
public class CollaborateurTest {
	
	private Collaborateur c;
	@Autowired
	private ServiceDepartement sd;
	@Autowired
	private ServiceCollaborateur sc;
	
	@Before
	public void init(){
		c = new Collaborateur();
		c.setNom("andré");
		c.setPrenom("michel");
		c.setEmail("m.andre@free.org");
		c.setPassword("pwd");
		c.setGrade(Grade.EMPLOYE);
		c.setDepartement(sd.findAll().get(0));
		c.setMatricule("abc123");
		c.setManager(null);
	}
	
	@Test
	public void notNull(){
		assertThat(c).isNotNull();
	}
	
	@Test
	public void get(){
		assertThat(c.getNom()).isEqualTo("andré");
		assertThat(c.getPrenom()).isEqualTo("michel");
		assertThat(c.getEmail()).isEqualTo("m.andre@free.org");
		assertThat(c.getPassword()).isEqualTo("pwd");
		assertThat(c.getGrade()).isEqualTo(Grade.EMPLOYE);
		assertThat(c.getDepartement()).isEqualTo(sd.findAll().get(0));
		assertThat(c.getMatricule()).isEqualTo("abc123");
		assertThat(c.getManager().isPresent()).isFalse();
	}
	
	@Test
	public void set(){
		c.setNom("andra");
		c.setPrenom("michela");
		c.setEmail("m.andre@free.orga");
		c.setPassword("pwda");
		c.setGrade(Grade.MANAGER);
		c.setDepartement(sd.findAll().get(1));
		c.setMatricule("abc123a");
		c.setManager(sc.findAll().get(0));
		assertThat(c.getNom()).isEqualTo("andra");
		assertThat(c.getPrenom()).isEqualTo("michela");
		assertThat(c.getEmail()).isEqualTo("m.andre@free.orga");
		assertThat(c.getPassword()).isEqualTo("pwda");
		assertThat(c.getGrade()).isEqualTo(Grade.MANAGER);
		assertThat(c.getDepartement()).isEqualTo(sd.findAll().get(1));
		assertThat(c.getMatricule()).isEqualTo("abc123a");
		assertThat(c.getManager().isPresent()).isTrue();
	}
	
}
