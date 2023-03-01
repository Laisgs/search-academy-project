package co.empathy.academy.search;

import co.empathy.academy.search.services.SearchService;
import co.empathy.academy.search.services.SearchServiceDummy;
import co.empathy.academy.search.services.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

@SpringBootTest
class SearchApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void SearchEngine(){
		SearchService ss = new SearchServiceImpl();

		assertEquals("Query: aa ClusterName: docker-cluster", ss.search("aa"));
	}

}
