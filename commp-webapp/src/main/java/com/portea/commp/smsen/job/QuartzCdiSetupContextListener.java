package com.portea.commp.smsen.job;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A context listener which sets the CDI aware Job factory on the Quartz scheduler.
 * In web.xml, this listener should be registered after declaration of QuartzInitializerListener
 */
public class QuartzCdiSetupContextListener implements ServletContextListener {

    private static Logger LOG = LoggerFactory.getLogger(QuartzCdiSetupContextListener.class);

    private static String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

    @Inject
    private JobFactory cdiJobFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final SchedulerFactory schedulerFactory =
                (SchedulerFactory) sce.getServletContext().getAttribute(QUARTZ_FACTORY_KEY);

        try {
            schedulerFactory.getScheduler().setJobFactory(cdiJobFactory);
            if (LOG.isDebugEnabled()) {
                LOG.debug("CDI Job Factory has been set for the Quartz scheduler");
            }
        } catch (SchedulerException e) {
            LOG.error("Unable to set the CDI Job Factory");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Got the instance of scheduler factory" + schedulerFactory);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nothing to do as when context is destroyed, the Quartz shutdown hook takes care
    }
}