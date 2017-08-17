package svc.managers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import svc.data.municipal.CourtDAO;
import svc.models.Court;
import svc.models.Sitemap;
import svc.security.HashUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class SitemapManagerTest {
    @InjectMocks
    SitemapManager sitemapManager;
    
    @Mock
    CourtDAO courtDAO;
    
    @Mock
	HashUtil hashUtil;

    @SuppressWarnings("deprecation")
	@Test
    public void returnsASitemap() {
    	when(courtDAO.getAllCourts()).thenReturn(new ArrayList<Court>());
    	when(hashUtil.encode(eq(Court.class),anyLong())).thenReturn("");
        assertThat(sitemapManager.generate(), is(Sitemap.class));
    	
    }
}
