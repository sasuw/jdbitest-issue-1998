package net.sasu.jdbitest.db.datasource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        //enableP6Spy();

        config.setJdbcUrl("jdbc:postgresql://localhost/jdbitest");
        config.setUsername("jdbiuser");
        config.setPassword("jdbiuser");
        config.addDataSourceProperty("ssl", "false");

        ds = new HikariDataSource(config);
    }

    public static DataSource getDataSource() throws SQLException {
        return ds;
    }

    private HikariCPDataSource(){}

    private static void enableP6Spy(){
        config.setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
        config.setJdbcUrl("jdbc:p6spy:postgresql://localhost/jdbitest");
    }
}
