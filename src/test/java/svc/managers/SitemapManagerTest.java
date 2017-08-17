package svc.managers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import svc.models.Sitemap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SitemapManagerTest {
    @InjectMocks
    SitemapManager courtManager;

    @SuppressWarnings("deprecation")
	@Test
    public void returnsASitemap() {
        assertThat(courtManager.generateSitemap(), is(Sitemap.class));
    	
    }
}
