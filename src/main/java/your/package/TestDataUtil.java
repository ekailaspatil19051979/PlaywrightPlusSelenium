package your.package;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class TestDataUtil {
    public static JSONObject getTestData(String filePath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(content);
    }
}
