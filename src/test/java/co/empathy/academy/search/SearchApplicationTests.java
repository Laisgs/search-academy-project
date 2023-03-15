package co.empathy.academy.search;

import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.components.SearchEngineImpl;
import co.empathy.academy.search.services.SearchService;
import co.empathy.academy.search.services.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

@SpringBootTest
class SearchApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void SearchEngine(){

		SearchEngine se = new SearchEngineImpl();

		//assertEquals("docker-cluster", se.getClusterName());
	}

}
