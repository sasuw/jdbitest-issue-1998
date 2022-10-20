package net.sasu.jdbitest;

import net.sasu.jdbitest.db.datasource.HikariCPDataSource;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * See https://github.com/jdbi/jdbi/issues/1998
 */
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbiPersisterTest {

    private DataSource postgresDataSource;
    private Jdbi jdbi;

    @BeforeAll
    public void initJdbi() throws SQLException {
        this.postgresDataSource = HikariCPDataSource.getDataSource();
        this.jdbi = Jdbi.create(postgresDataSource);

        try (Handle handle = jdbi.open()) {
            handle.execute("DROP TABLE IF EXISTS \"public\".inet_test");
        }
    }

    @AfterAll
    public void destroyTestTable(){
        try (Handle handle = jdbi.open()) {
            handle.execute("DROP TABLE IF EXISTS \"public\".inet_test");
        }
    }

    /**
     * This does not work
     */
    @Test
    public void inetTest(){
        try (Handle handle = jdbi.open()) {
            String createTableSql = """
                    CREATE  TABLE "public".inet_test (
                    	hostipv4             inet
                     );
                    """;
            handle.execute(createTableSql);

            String hostAddress = "8.8.8.8";

            InetAddress inetAddress = Inet4Address.getByName(hostAddress);

            int createdRows = handle.createUpdate("insert into inet_test(hostipv4) values (?)")
                    .bind(0, inetAddress)
                    .execute();

            final Inet4Address select_hostipv4_from_inet_test = handle.createQuery("select hostipv4 from inet_test")
                    .mapTo(Inet4Address.class)
                            .one();

            assertEquals(hostAddress, select_hostipv4_from_inet_test.getHostAddress());

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    /**
     * This works
     */
    @Test
    public void inetTest2(){
        try (Handle handle = jdbi.open()) {
            String createTableSql = """
                    CREATE  TABLE "public".inet_test (
                    	hostipv4             inet
                     );
                    """;
            handle.execute(createTableSql);

            String hostAddress = "8.8.8.8";

            InetAddress inetAddress = Inet4Address.getByName(hostAddress);

            //works if we do not user bound parameter
            int createdRows = handle.createUpdate("insert into inet_test(hostipv4) values ('" + hostAddress + "')")
                    .execute();

            //handle.createQuery("select hostipv4 from inet_test");

            final String select_hostipv4_from_inet_test = handle.createQuery("select hostipv4 from inet_test")
                    .mapTo(String.class)
                    .one();

            assertEquals(hostAddress, select_hostipv4_from_inet_test);

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
