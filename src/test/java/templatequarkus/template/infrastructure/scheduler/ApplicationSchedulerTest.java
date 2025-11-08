package templatequarkus.template.infrastructure.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationSchedulerTest {

    @Test
    void shouldInvokeSchedule() {
        final ApplicationScheduler scheduler = spy(new ApplicationScheduler());

        scheduler.schedule();

        verify(scheduler, times(1)).schedule();
        verifyNoMoreInteractions(scheduler);
    }
}

