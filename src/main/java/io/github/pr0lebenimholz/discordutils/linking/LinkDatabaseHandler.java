package io.github.pr0lebenimholz.discordutils.linking;

// TODO: 21.03.22 WIP, maybe merge those 2 DBs
// TODO: 21.03.22 author missing. schwitze?
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles the databases located on the server under `/[world]/discordutils/PendingLinks.csv` and
 * `/[world]/discordutils/ConfirmedLinks.csv`
 *
 * @author
 */
public class LinkDatabaseHandler {
    private List<List<String>> registerCache = new ArrayList<>();
    /*
     * ./world/@modid/PendingLinks.csv
     * ---
     * @mc,@dc
     * @dc,@mc
     * ---
     * 1st entry is requested by @mc, needs to be confirmed by @dc
     * 2nd entry is requested by @dc, needs to be confirmed by @mc
     *
     * ===
     *
     * ./world/@modid/ConfirmedLinks.csv
     * ---
     * @mc,@dc
     * @dc,@mc
     * ---
     * the order of @mc and @dc is inherited from PendingLinks.csv, but doesn't matter anymore
     *
     * ===
     *
     * because both the discord name (Clyde#1234) and minecraft name can be changed,
     * the discord id (=snowflake) and minecraft id (=uuid) are used
     */

    public LinkDatabaseHandler(){
        updateCache();
    }

    /*
     *  Adds a new Row to csv using provided data and set default rank to ftb
     */
    public void addEntry(String uuid,String snowflake) throws IOException {
        String rank = "ftb";
        String[] user = {uuid, snowflake,rank};
        //event call get start rank
        writeToFile(user);
        updateCache();
    }

    public void removeEntry(String UUID){
        //todo
    }

    /*
     *  returns all linked players as List<List<String>> in form uuid | snowflake | rank
     */
    public List getPlayerList(){
        ensureCache();
        return registerCache;
    }

    /*
     *  returns true if given id is contained
     */
    public boolean isLinked(String id){
        ensureCache();
        if (registerCache.contains(id)){
            return true;
        }
        return false;
    }

    /*
     *  ensures that cache is loaded and not empty
     */
    private void ensureCache(){
        //todo check if csv is empty and thus registerCache should be empty as well and no update is necessary
        if (registerCache.isEmpty()) {
            updateCache();
        }
    }

    /*
     *  appends a new line to the csv
     */
    private void writeToFile(String[] user) throws IOException {
        //todo check duplicates
        FileWriter fw = new FileWriter("PlayerReg", true);
        fw.append(String.join(",",user));
        fw.append("\n");
        fw.flush();
        fw.close();
        updateCache();
    }

    /*
     *  reads the csv file and loads it into registerCache as List<List<String>> in form uuid | snowflake | rank
     */
    private void updateCache (){
        try(BufferedReader br = new BufferedReader(new FileReader("PlayerReg"))) {
        String line;
        List<List<String>> bufferCache = new ArrayList<>();
        while ((line=br.readLine()) != null){
            String[] values = line.split(",");
            bufferCache.add(Arrays.asList(values));
        }
        registerCache = bufferCache;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
