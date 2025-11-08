package templatequarkus.template.infrastructure.scheduler;

import io.quarkus.scheduler.Scheduled;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScheduler {

    private static final Logger logger = Logger.getLogger(ApplicationScheduler.class);

    @Scheduled(every = "30s")
    public void schedule() {
        logger.info("schedule running...");
    }

}
