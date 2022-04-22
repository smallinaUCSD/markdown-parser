import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
public class MarkdownParseTest {
    @Test
    public void addition() {
        assertEquals(2, 1 + 1);
    }


    @Test
    public void testGetLinks() throws IOException{
        Path fileName = Path.of("test.md");
        String content = Files.readString(fileName);
        ArrayList<String> test = MarkdownParse.getLinks(content);
        assertEquals(List.of("https://something.com","some-thing.html"), test);
    }
}


