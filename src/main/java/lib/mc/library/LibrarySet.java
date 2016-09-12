/*
 * 	Copyright (C) 2016 Eyeballcode
 *
 * 	This program is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 *
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 *
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * 	See LICENSE.MD for more details.
 */

package lib.mc.library;

import lib.mc.libraryutil.LibraryDownloader;
import lib.mc.util.Handler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LibrarySet {

    private ArrayList<LibraryObject> libraries = new ArrayList<>();

    /**
     * Constructs a Library Set.
     *
     * @param libraries The array of libraries from the JSON. It will try to determine the library type (Native, Forge, Jar)
     */
    public LibrarySet(JSONArray libraries) {
        for (Object o : libraries) {
            JSONObject library = (JSONObject) o;
            DefaultMCLibraryObject dummy = new DefaultMCLibraryObject(library.getString("name"), "");
            if (!library.has("name"))
                throw new IllegalArgumentException("Invalid JSON");
            HashMap<String, String> versionMap = new HashMap<>();
            if (library.has("natives")) {
                NativesRules nativesRules = new NativesRules(library.has("rules") ? library.getJSONArray("rules") : new JSONArray("[]"));
                ExtractRules extractRules = new ExtractRules(library.getJSONObject("extract"));
                this.libraries.add(new NativeMCLibraryObject(library.getString("name"), library.getString("sha1"), nativesRules, extractRules));
            } else if (!library.has("download") && (library.has("url") || library.has("serverreq") || library.has("clientreq") || library.has("checksum"))) {
                ForgeLibraryObject forgeLibraryObject = new ForgeLibraryObject(library.getString("name"), library.has("url"));
                if (versionMap.containsKey(dummy.parseName().getLibraryName())) {
                    String existingVersion = versionMap.get(dummy.parseName().getLibraryName());
                    int existing = Integer.parseInt(existingVersion.substring(0, existingVersion.indexOf(".")));
                    String cV = forgeLibraryObject.parseName().getVersion();
                    int current = Integer.parseInt(cV.substring(0, cV.indexOf(".")));
                    if (!(current > existing)) continue;
                }
                versionMap.remove(forgeLibraryObject.parseName().getLibraryName());
                versionMap.put(forgeLibraryObject.parseName().getLibraryName(), forgeLibraryObject.parseName().getVersion());

                this.libraries.add(forgeLibraryObject);
            } else {
                System.out.println(library);
                DefaultMCLibraryObject defaultMCLibraryObject = new DefaultMCLibraryObject(library.getString("name"), library.getJSONObject("downloads").getJSONObject("artifact").getString("sha1"));

                if (versionMap.containsKey(dummy.parseName().getLibraryName())) {
                    String existingVersion = versionMap.get(dummy.parseName().getLibraryName());
                    int existing = Integer.parseInt(existingVersion.substring(0, existingVersion.indexOf(".")));
                    String cV = defaultMCLibraryObject.parseName().getVersion();
                    int current = Integer.parseInt(cV.substring(0, cV.indexOf(".")));
                    if (!(current > existing)) continue;
                }
                versionMap.remove(defaultMCLibraryObject.parseName().getLibraryName());
                versionMap.put(defaultMCLibraryObject.parseName().getLibraryName(), defaultMCLibraryObject.parseName().getVersion());

                this.libraries.add(defaultMCLibraryObject);
            }
        }
    }

    /**
     * Removes a library from the list
     * @param object The library to remove
     */
    public void drop(LibraryObject object) {
        libraries.remove(object);
    }

    /**
     * Download all the libraries to a given folder
     *
     * @param to The folder to download to
     * @throws IOException If an IO operation failed
     */
    public void downloadAll(File to, Handler<LibraryObject> handler) throws IOException {
        for (LibraryObject libraryObject : libraries) {
            LibraryDownloader.downloadLibrary(libraryObject, to);
            handler.handle(libraryObject);
        }
    }

   public ArrayList<LibraryObject> getLibraries() {
        return libraries;
    }
}

