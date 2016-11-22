import com.modlauncher.api.FileUtil;
import lib.mc.library.ForgeLibraryObject;
import lib.mc.library.LibraryObject;
import lib.mc.library.LibrarySet;
import lib.mc.util.ChecksumUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;

public class GenerateJSON {

    public static void main(String[] args) throws Exception {

        File f = new File("/home/eyeballcode/eyeballcode.github.io/Forge-Libraries/forge-data.json");
        File f2 = new File("/home/eyeballcode/eyeballcode.github.io/Forge-Libraries/1.7.10");
        JSONObject o = new JSONObject();

        for (File x : f2.listFiles()) {
            JSONObject c = new JSONObject();
            String sha = ChecksumUtils.calcSHA1Sum(x);
            String url = "https://eyeballcode.github.io/Forge-Libraries/1.7.10/" + x.getName();
            c.put("sha1", sha).put("url", url);
            o.put(x.getName(), c);
        }
        System.out.println(o.toString(4));
    }

}
