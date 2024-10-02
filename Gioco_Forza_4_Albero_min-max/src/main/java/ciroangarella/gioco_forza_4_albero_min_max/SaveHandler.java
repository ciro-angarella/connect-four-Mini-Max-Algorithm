package ciroangarella.gioco_forza_4_albero_min_max;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class SaveHandler
 * @brief Classe per gestire il salvataggio e il recupero dei dati di gioco.
 * 
 * Questa classe implementa il pattern Singleton per garantire che ci sia
 * una sola istanza di `SaveHandler` durante l'esecuzione del programma.
 */
public class SaveHandler {

    // L'istanza Singleton
    private static SaveHandler instance;

    private String filePath; ///< Percorso del file di salvataggio
    private JSONObject save; ///< Oggetto JSON che contiene i dati di salvataggio
    
    /**
     * @brief Costruttore privato per prevenire l'istanziamento dall'esterno.
     * 
     * Inizializza il percorso del file di salvataggio e carica i dati dal file
     * JSON nel campo `save`.
     */
    private SaveHandler(){
        save = new JSONObject();
        filePath = "src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/save.json";
        StringBuilder content = new StringBuilder();
        
        // Legge il file e lo salva in `save`
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.save = new JSONObject(content.toString());
    }

    /**
     * @brief Metodo pubblico per ottenere l'istanza del Singleton.
     * 
     * @return L'istanza di `SaveHandler`.
     */
    public static synchronized SaveHandler getInstance() {
        if (instance == null) {
            instance = new SaveHandler();
        }
        return instance;
    }

    /**
     * @brief Ottiene il valore dell'owner in una specifica cella della griglia.
     * 
     * @param row Riga della cella da controllare.
     * @param col Colonna della cella da controllare.
     * @return Il valore dell'owner nella cella specificata.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    public int getOwner(int row, int col) throws IOException {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Row and col must be non-negative");
        }

        JSONArray jsonArray = this.save.getJSONArray("grid");

        // Trova l'oggetto specifico e restituisce il valore dell'owner
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("row") == row && jsonObject.getInt("col") == col) {
                return jsonObject.getInt("owner");
            }
        }
        return -1; // Ritorna -1 se l'owner non viene trovato
    }

    /**
     * @brief Imposta il valore dell'owner in una specifica cella della griglia.
     * 
     * @param owner Il nuovo valore dell'owner.
     * @param row Riga della cella da aggiornare.
     * @param col Colonna della cella da aggiornare.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    public void setOwner(int owner, int row, int col) throws IOException {
        JSONArray jsonArray = this.save.getJSONArray("grid");
        
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Row and col must be non-negative");
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("row") == row && jsonObject.getInt("col") == col) {
                this.save.getJSONArray("grid").getJSONObject(i).put("owner", owner);
                return;
            }
        }
    }
    
    /**
     * @brief Scrive i dati di salvataggio nel file JSON.
     * 
     * Questo metodo salva lo stato attuale del gioco nel file specificato da `filePath`.
     */
    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(this.save.toString(4));
            System.out.println(this.save.toString(4));
        } catch (IOException ex) { 
           ex.printStackTrace();
        }
    }
    
    /**
     * @brief Controlla se tutti gli owner nelle celle della griglia sono zero.
     * 
     * @return `true` se tutti gli owner sono zero, altrimenti `false`.
     */
    public boolean allOwnersAreZero() {
        JSONArray jsonArray = this.save.getJSONArray("grid");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("owner") != 0) {
                return false; // Restituisce false se trova un owner diverso da 0
            }
        }
        return true; // Restituisce true se tutti gli owner sono 0
    }

    /**
     * @brief Imposta un nome associato a un ID nel file di salvataggio.
     * 
     * @param id L'ID associato al nome.
     * @param name Il nome da salvare.
     */
    public void setName(String id, String name){
        save.put(id, name);
    }
    
    /**
     * @brief Recupera un nome associato a un ID dal file di salvataggio.
     * 
     * @param id L'ID di cui recuperare il nome.
     * @return Il nome associato all'ID, o `null` se non esiste.
     */
    public String getName(String id){
        return this.save.optString(id);
    }
}
