package svc.managers;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import svc.data.municipal.CourtDAO;
import svc.models.Court;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourtManagerTest {
    @InjectMocks
    CourtManager courtManager;

    @Mock
    CourtDAO courtDAO;

    @Test
    public void getsCourtById() {
        Long courtId = 14L;
        Court court = new Court();
        court.id = courtId;
        court.name = "Some court";
        when(courtDAO.getCourtById(courtId)).thenReturn(court);

        Court foundCourt = courtManager.getCourtById(courtId);

        assertEquals(foundCourt, court);
    }

    @Test
    public void getsCourtsByMuni() {
        Long municipalityId = 14L;
        Court court = new Court();
        court.id = 10L;
        court.name = "Some court";
        List<Court> courts = Lists.newArrayList(court);
        when(courtDAO.getCourtsByMunicipalityId(municipalityId)).thenReturn(courts);

        List<Court> foundCourts = courtManager.getCourtsByMunicipalityId(municipalityId);

        assertEquals(foundCourts, courts);
    }

    @Test
    public void getAllCourts() {
        Court court = new Court();
        court.id = 10L;
        court.name = "Some court";
        List<Court> courts = Lists.newArrayList(court);
        when(courtDAO.getAllCourts()).thenReturn(courts);

        List<Court> foundCourts = courtManager.getAllCourts();

        assertEquals(foundCourts, courts);
    }
}
