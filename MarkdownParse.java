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

			// add to list ONLY if ALL 4 [,],(,), are found
			if (openBracket != -1 && closeBracket != -1 && openParen != -1 && closeParen != -1) {
				String url = markdown.substring(openParen + 1, closeParen);
				// validate if url is not null and valid link or image
				if (url != null && (isValidLink(url) || isValidImage(url)))
					toReturn.add(url);
			}
			if (closeParen != -1) // if link/image is specified
				currentIndex = closeParen + 1;
			else // if no link/image is specified
				currentIndex = closeBracket + 1;
		}
		return toReturn;
	}

	static boolean isValidLink(String url) {
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
			if (url.contains("http")) {
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