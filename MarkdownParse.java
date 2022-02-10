import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )                     
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            //first fix: avoid infinite loop
            if (closeParen == -1 || openParen == -1 || nextCloseBracket == -1 || nextOpenBracket == -1){
                break;
            }
            //second fix: avoid image
            if (nextOpenBracket != 0 && markdown.substring(nextOpenBracket-1, nextOpenBracket).equals("!")){
                currentIndex = closeParen + 1;
                continue;
            }
            //third fix: avoid words with only paren
            if (!markdown.substring(openParen - 1, openParen).equals("]")) {
                currentIndex = closeParen + 1;
                continue;
            }
    
            else {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;
            }
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}