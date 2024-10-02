package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @class Player
 * @brief Classe che rappresenta il giocatore umano nel gioco Forza 4.
 * 
 * La classe `Player` implementa l'interfaccia `InterfacePlayer` e ha la responsabilità
 * di memorizzare e gestire l'ID del giocatore, il codice del giocatore e l'icona
 * che lo rappresenta nella griglia di gioco.
 */
public class Player implements InterfacePlayer {

    /**
     * @brief ID del giocatore.
     * 
     * Stringa che memorizza l'identificatore unico del giocatore.
     */
    private String playerId;
    
    /**
     * @brief Codice del giocatore.
     * 
     * Valore intero che identifica il giocatore come "giocatore 1". Il codice serve per distinguere
     * i giocatori e per il controllo delle vincite.
     */
    private int playerCode = 1;
    
    /**
     * @brief Icona del giocatore.
     * 
     * Icona grafica associata al giocatore, che viene visualizzata sulla griglia di gioco.
     */
    private ImageIcon icon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/token_1.png");

    /**
     * @brief Imposta l'ID del giocatore.
     * 
     * @param id Stringa che rappresenta l'ID del giocatore.
     */
    @Override
    public void setId(String id) {
        playerId = id;
    }

    /**
     * @brief Restituisce l'ID del giocatore.
     * 
     * @return Stringa che rappresenta l'ID del giocatore.
     */
    @Override
    public String getId() {
        return playerId;
    }

    /**
     * @brief Restituisce il codice del giocatore.
     * 
     * @return Intero che rappresenta il codice del giocatore (1).
     */
    @Override
    public int getPlayerCode() {
        return playerCode;
    }

    /**
     * @brief Restituisce l'icona del giocatore.
     * 
     * @return Oggetto `ImageIcon` che rappresenta l'icona grafica del giocatore.
     */
    @Override
    public ImageIcon getIcon() {
        return icon;
    }
}
