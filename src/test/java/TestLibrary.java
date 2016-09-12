import lib.mc.library.*;
import lib.mc.libraryutil.LibraryDownloader;
import lib.mc.util.Handler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;

public class TestLibrary {

    public static void main(String[] args) throws IOException {
        File dir = new File("libraries");
        dir.mkdir();
        JSONArray libs = new JSONObject(new JSONTokener(TestLibrary.class.getResourceAsStream("test_json_lib.json"))).getJSONArray("libraries");
        LibrarySet set = new LibrarySet(libs);
        set.downloadAll(dir, new Handler<LibraryObject>());
    }

}
