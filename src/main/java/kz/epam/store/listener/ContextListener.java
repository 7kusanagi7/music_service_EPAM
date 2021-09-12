package kz.epam.store.listener;

import kz.epam.store.database.DBConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        DBConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        DBConnectionPool.getInstance().close();
    }
}
