package service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.service.ServiceDepartement;
import dev.service.ServiceUrls;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ ServiceDepartement.class, ServiceUrls.class })
public class ServiceDepartementTest {
	
	@Autowired
	private ServiceDepartement sd;
	
	@Test
	public void notNull(){
		assertThat(sd).isNotNull();
	}
	
	@Test
	public void findAll(){
		assertThat(sd.findAll().size()).isGreaterThan(0);
	}
	
	@Test
	public void findDepartementInvalid(){
		assertThat(sd.findDepartementParLibelle("DSBB").isPresent()).isFalse();
	}
	
	@Test
	public void findDepartement(){
		assertThat(sd.findDepartementParLibelle("DSI").isPresent()).isTrue();
	}
	
}
