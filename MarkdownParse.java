import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.net.URL;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while (currentIndex < markdown.length()) {

            int openBracket = markdown.indexOf("[", currentIndex);

            // quit if no [, found
            if (openBracket == -1)
                break;

            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            int exclamation = markdown.indexOf("!", currentIndex);
            
            // add to list ONLY if ALL 4 [,],(,), are found
            if (openBracket != -1 && closeBracket != -1 && openParen != -1 && closeParen != -1) {
                String url = markdown.substring(openParen + 1, closeParen);
                // validate if URL is NOT null and is either valid image or URL, ![ for an image
                if (url != null && (((exclamation != -1 && exclamation < openBracket) && isValidImage(url)) 
                || isValidURL(url)))
                    toReturn.add(url);
            }
            
            // point currentIndex to next openBracket
            if ((closeParen != -1) && (closeParen > openBracket)) 
                currentIndex = closeParen + 1;
            else if ((closeBracket != -1) && (closeBracket > openBracket))
                currentIndex = closeBracket + 1;
            else
                currentIndex++;
        }
        return toReturn;
    }

    static boolean isValidURL(String url) {
        String validURL = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";
        return url.matches(validURL);
    }

    static boolean isValidImage(String url) {
        Image image;
        try {
            if (isValidURL(url)) {
                image = ImageIO.read(new URL(url));
            } else
                image = ImageIO.read(new File(url));
        } catch (Exception e) {
            return false;
        }
        return (image != null);
    }

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
        System.out.println(links);
    }
}