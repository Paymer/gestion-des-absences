package entite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entite.MessageErreur;

@RunWith(SpringRunner.class)
public class MessageErreurTest {
	
	private MessageErreur me;
	
	@Before
	public void init(){
		me = new MessageErreur();
		me.setId(1);
		me.setMessage("message test");
		me.setServiceOrigine("service test");
	}
	
	@Test
	public void notNull(){
		assertThat(me).isNotNull();
	}
	
	@Test
	public void get(){
		assertThat(me.getId()).isEqualTo(1);
		assertThat(me.getDate()).isNotNull();
		assertThat(me.getMessage()).isEqualTo("message test");
		assertThat(me.getServiceOrigine()).isEqualTo("service test");
	}
	
	@Test
	public void set(){
		me.setId(2);
		me.setMessage("test");
		me.setServiceOrigine("test2");
		assertThat(me.getId()).isEqualTo(2);
		assertThat(me.getMessage()).isEqualTo("test");
		assertThat(me.getServiceOrigine()).isEqualTo("test2");
	}
	
}
