package ciroangarella.gioco_forza_4_albero_min_max;

/**
 * @class Winner
 * @brief Rappresenta un vincitore nel gioco.
 * 
 * Questa classe gestisce l'ID del vincitore e il numero di vittorie ottenute.
 * 
 * @author angalinux
 */
public class Winner {
    private String id;  ///< L'ID del vincitore
    private int win;    ///< Il numero di vittorie del vincitore

    /**
     * @brief Costruttore della classe Winner.
     * 
     * @param id L'ID del vincitore.
     */
    public Winner(String id){
        this.id = id;
        this.win = 0;  // Inizializza il numero di vittorie a zero
    }

    /**
     * @brief Ottiene l'ID del vincitore.
     * 
     * @return L'ID del vincitore.
     */
    public String getId() {
        return id;
    }
    
    /**
     * @brief Imposta l'ID del vincitore.
     * 
     * @param id L'ID da impostare.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * @brief Imposta il numero di vittorie.
     * 
     * @param win Il numero di vittorie da impostare.
     */
    public void setWin(int win){
        this.win = win;
    }
    
    /**
     * @brief Imposta il numero di vittorie a 1.
     */
    public void setFirstWin(){
        this.win = 1;
    }
    
    /**
     * @brief Ottiene il numero di vittorie.
     * 
     * @return Il numero di vittorie.
     */
    public int getWin() {
        return win;
    }
    
    /**
     * @brief Aggiunge una vittoria al totale delle vittorie.
     */
    public void addWin(){
        this.win++;
    }
}
