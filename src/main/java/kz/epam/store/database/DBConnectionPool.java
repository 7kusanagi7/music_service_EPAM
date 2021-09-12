package kz.epam.store.database;

import kz.epam.store.database.util.DBConnector;
import kz.epam.store.database.util.DBResourceManager;
import kz.epam.store.exception.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(DBConnectionPool.class);

    private static final int POOL_CAPACITY = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_CAPACITY));
    private static final int POOL_INITIAL_SIZE = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_INITIAL_SIZE));
    private static final int TIMEOUT_SECONDS = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_TIMEOUT));

    private BlockingQueue<Connection> pool;
    private AtomicInteger connectionCount;
    private Lock lock;

    public static DBConnectionPool getInstance() {
        return DBConnectionPoolHolder.INSTANCE;
    }

    private DBConnectionPool() {
        try {
            pool = new ArrayBlockingQueue<>(POOL_CAPACITY);
            connectionCount = new AtomicInteger();
            lock = new ReentrantLock();
            for (int i = 0; i < POOL_INITIAL_SIZE; i++) {
                pool.offer(DBConnector.getConnection());
                connectionCount.incrementAndGet();
            }
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public PoolConnection getConnection() throws DBException {
        Connection connection;
        try {
            connection = pool.poll(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            lock.lock();
            if (connection == null && connectionCount.get() < POOL_CAPACITY) {
                connection = DBConnector.getConnection();
                pool.offer(connection);
                connectionCount.incrementAndGet();
            }

            if (connection == null) {
                throw new DBException("Too many connections.");
            }
        } catch (InterruptedException | SQLException e) {
            throw new DBException(e);
        } finally {
            lock.unlock();
        }
        return new PoolConnection(connection);
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    public void close() {
        pool.forEach(this::closeConnection);
    }

    public class PoolConnection implements AutoCloseable {
        private final Connection connection;

        private PoolConnection(Connection connection) {
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        @Override
        public void close() {
            try {
                if (!connection.isClosed()) {
                    pool.offer(connection);
                } else {
                    pool.remove(connection);
                }
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }

    private static class DBConnectionPoolHolder {
        private static final DBConnectionPool INSTANCE = new DBConnectionPool();
    }
}
