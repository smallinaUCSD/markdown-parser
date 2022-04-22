//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.*;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        // initialize arraylist to store contents
        ArrayList<String> toReturn = new ArrayList<>();
        
        // regular expression for urls 
        String url = "\\b((?:https?|ftp|file):" + "//[-a-zA-Z0-9+&@#/%?=" + "~_|!:, .;]*[-a-zA-Z0-9+" + "&@#/%=~_|])";
        
        Pattern link = Pattern.compile(url,Pattern.CASE_INSENSITIVE);
        Matcher match = link.matcher(markdown);
        
        while (match.find()) {
            // adds the substring "url" to the arrayList
            toReturn.add(markdown.substring(
                match.start(0), match.end(0)));
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
