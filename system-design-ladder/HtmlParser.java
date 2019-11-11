import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    /*
     * @param content: content source code
     * @return: a list of links
     */
    public List<String> parseUrls(String content) {
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("href[\\s]*=[\\s]*[\"|']([^\"|']+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            if (!matcher.group(1).startsWith("#"))
                result.add(matcher.group(1));
        }
        return result;
    }
}