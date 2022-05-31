import static org.junit.Assert.*;
import org.junit.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MarkdownParseTest {

    @Test
    public void testFile1() throws IOException {
        String contents= Files.readString(Path.of("Test4.md"));
        List<String> expect = List.of();
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }
    @Test
    public void testFile2() throws IOException {
        String contents= Files.readString(Path.of("Test5.md"));
        List<String> expect = List.of("https://spec.commonmark.org/url");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }
}