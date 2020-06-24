package net.thumbtack.school.concert;

import net.thumbtack.school.concert.data.DataBase;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDataBaseService {
    private final Server server = new Server();


    @Rule
    public final TemporaryFolder TEMP_FOLDER = new TemporaryFolder();

    @Before
    public void setUp() throws IOException, BaseServerException {
        server.startServer(null);
    }

    @Test
    public void testSerializeDeserializeDataBase() throws IOException, BaseServerException {
        DataBase dataBaseToWrite = DataBase.getDataBase();
        File file = TEMP_FOLDER.newFile("test.txt");
        server.stopServer("test.txt");
        assertTrue(file.exists());
        server.startServer("test.txt");
        assertEquals(dataBaseToWrite, DataBase.getDataBase());
    }

    @After
    public void afterTest() throws IOException {
        server.stopServer(null);
    }
}
