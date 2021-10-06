package io.ramzi.elevator.simulator.dao.entity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.ramzi.elevator.simulator.dao.repository.UserRequestRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserRequestEntityTest {

	private UserRequestEntity userRequestEntity;

	@Before
	public void setup() {

		long index = Long.valueOf("1");		
		long timestamp = Long.valueOf("2");		
		long building = Long.valueOf("3");
		long group = Long.valueOf("4");
		long elevator = Long.valueOf("5");
		long sens = Long.valueOf("6");
		userRequestEntity = new UserRequestEntity(index,timestamp, building, group, 1, elevator, sens);
	}

	@Test
	public void shouldBeTheSame() {		
		assertEquals("Timestamp is incorect : " ,2,userRequestEntity.getTimestamp());
	}
}
