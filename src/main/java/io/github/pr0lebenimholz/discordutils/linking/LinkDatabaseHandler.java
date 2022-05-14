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
import java.util.Iterator;
import java.util.List;

/**
 * Handles the databases located on the server under `/[world]/discordutils/PendingLinks.csv` and
 * `/[world]/discordutils/ConfirmedLinks.csv`
 *
 * @author
 */
public class LinkDatabaseHandler {
    private List<List<String>> registerCache = new ArrayList<>();
    private final String FILEPATH = "";
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
    public void addEntry(String uuid,String snowflake) {
       if(uuid != null && snowflake != null){
           String rank = "ftb";
           String[] user = {uuid, snowflake,rank};
           ensureCache();
           //event call get start rank
           try {
               writeToFile(user);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }

           updateCache();
       }
    }


    public List<String> getLinkedByID(String checkID){
        ensureCache();
        for (List<String> player : registerCache){
            for (String id : player){
                if (id == checkID){
                    return player;
                }
            }
        }
        return null;
    }

    public void removeEntry(String UUID){
        //todo
        int index = getIndexByUUID(UUID);
        if (index != -1){
            registerCache.remove(index);
            saveToFile();
            updateCache();
        }
    }

    /*
     *  returns index in registerCache if not found returns -1
     */
    private int getIndexByUUID(String UUID){

        int index = -1;
        for (int i = 0; i<registerCache.size();i++){
            List<String> buffer = registerCache.get(i);
            if (buffer.contains(UUID)){
                index = i;
            }
        }
        return index;
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
    public boolean isLinked(String checkID){
        ensureCache();
        for (List<String> player : registerCache){
            for (String id : player){
                if (id == checkID){
                    return true;
                }
            }
        }
        return false;
    }

    /*
     *  ensures that cache is loaded and not empty unless csv is as well
     */
    private void ensureCache(){
        try {
            if (!isCSVempty()){
              updateCache();
            }else {
                if (!registerCache.isEmpty()){
                    updateCache();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isCSVempty() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
        if (br.readLine() == null) {
            return true;
        }
        return false;
    }

    /*
     *  appends a new line to the csv
     */
    private void writeToFile(String[] user) throws IOException {
        if (user != null){
            if (!isLinked(user[0]) && !isLinked(user[1])) {
                FileWriter fw = new FileWriter("FILEPATH", true);
                fw.append(String.join(",",user));
                fw.append("\n");
                fw.flush();
                fw.close();
                updateCache();
            }
        }
    }

    /*
     *  saves the whole List registerCache into the given CSV
     */
    private void saveToFile(){

    }

    /*
     *  reads the csv file and loads it into registerCache as List<List<String>> in form uuid | snowflake | rank
     */
    private void updateCache (){
        try(BufferedReader br = new BufferedReader(new FileReader("FILEPATH"))) {
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
