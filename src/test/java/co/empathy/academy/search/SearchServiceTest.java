package co.empathy.academy.search;

import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.services.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private SearchEngine searchEngine;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    public void testSearch(){
        when(searchEngine.getClusterName()).thenReturn("docker-cluster");

        assertEquals("Query: aa ClusterName: docker-cluster", searchService.search("aa"));
    }
}
