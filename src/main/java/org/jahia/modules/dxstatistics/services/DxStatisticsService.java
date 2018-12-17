package org.jahia.modules.dxstatistics.services;

import org.jahia.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Date;

public class DxStatisticsService {
    private static final String SELECT_STATISTICS = "SELECT count(*) FROM JAHIA_DX_STATISTICS";
    private static final String SELECT_AGGREGATED_METRIC = "SELECT sum(metric) FROM JAHIA_DX_STATISTICS WHERE metric_type=''{0}'' " +
            "AND metric_timespan=''{1}'' AND metric_time > ''{2}'' AND metric_time < ''{3}''";
    private static final String SELECT_LAST_METRIC = "SELECT metric FROM JAHIA_DX_STATISTICS WHERE metric_type=''{0}'' " +
            "AND metric_timespan=''{1}'' order by metric_time DESC";
    private static final String INSERT_STATISTICS = "INSERT into JAHIA_DX_STATISTICS (metric_type, metric_timespan, metric_time, metric) values (''{0}'', ''{1}'', ''{2}'', {3})";

    private static final Logger logger = LoggerFactory.getLogger(DxStatisticsService.class);

    private static void executeInsert (String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getDatasource().getConnection();
            stmt = conn.createStatement();
            logger.debug("Executing query " + query);
            stmt.execute(query);



        } catch (Exception e) {
            logger.error("Unable to execute query", e);
            // we do not propagate this exception as it stops the discovery thread completely
        } finally {
            DatabaseUtils.closeQuietly(rs);
            DatabaseUtils.closeQuietly(stmt);
            DatabaseUtils.closeQuietly(conn);
        }
    }

    private static long executeUniqueSelect (String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getDatasource().getConnection();
            stmt = conn.createStatement();
            logger.debug("Executing query " + query);
            stmt.setFetchSize(1);

            rs = stmt.executeQuery(query);
            rs.next();
            long metric = rs.getLong(1);
            return metric;

        } catch (Exception e) {
            logger.error("Unable to execute query", e);
            // we do not propagate this exception as it stops the discovery thread completely
        } finally {
            DatabaseUtils.closeQuietly(rs);
            DatabaseUtils.closeQuietly(stmt);
            DatabaseUtils.closeQuietly(conn);
        }
        return 0;
    }

    public static long getLastMetric (String type, String timespan) {
        long metric = 0;

        String statement = MessageFormat.format(SELECT_LAST_METRIC, type, timespan);
        logger.info(statement);
        metric = executeUniqueSelect(statement);

        return metric;
    }

    public static void insertMetric (String type, String timespan, long metric) {
        long startTime = System.currentTimeMillis();

            Timestamp timestamp = new Timestamp(new Date().getTime());
            String insertStatement = MessageFormat.format(INSERT_STATISTICS, type, timespan, timestamp.toString(), metric);
            logger.info(insertStatement);
            executeInsert(insertStatement);

    }

    public static long getAggregatedMetric (String type, String timespan, Timestamp start, Timestamp end) {
        long metric = 0;

        String statement = MessageFormat.format(SELECT_AGGREGATED_METRIC, type, timespan, start.toString(), end.toString());
        logger.info(statement);
        metric = executeUniqueSelect(statement);

        return metric;
    }
}
