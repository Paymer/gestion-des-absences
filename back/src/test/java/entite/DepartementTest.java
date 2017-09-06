package entite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entite.Departement;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ Departement.class})
public class DepartementTest {
	
	private Departement d;
	
	@Before
	public void init(){
		d = new Departement();
		d.setLibelle("TEST");
	}
	
	@Test
	public void getLibelle(){
		assertThat(d.getLibelle()).isEqualTo("TEST");
	}
	
	@Test
	public void setLibelle(){
		d.setLibelle("test2");
		assertThat(d.getLibelle()).isEqualTo("test2");
	}
	
}
