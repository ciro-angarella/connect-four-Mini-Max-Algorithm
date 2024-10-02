package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.ImageIcon;

/**
 * @class Bot
 * @brief Classe che rappresenta un bot giocatore nel gioco Forza 4.
 *
 * La classe Bot implementa l'interfaccia InterfacePlayer. Ogni bot ha un ID (nome della strategia scelta), un codice giocatore,
 * e un'icona che rappresenta il suo token nel gioco.
 * Questa classe è utilizzata per implementare lee informazioni di un giocatore controllato dalla CPU.
 */
public class Bot implements InterfacePlayer {
  
    private String playerId;  ///< ID univoco del bot.
    private int playerCode = 2;  ///< Codice identificativo del bot (2).
    private ImageIcon icon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/token_2.png");  ///< Icona del token del bot.
            
    /**
     * Imposta l'ID del bot.
     * 
     * @param id L'ID univoco del bot.
     */
    @Override
    public void setId(String id){
        playerId = id;
    }

    /**
     * Restituisce l'ID del bot.
     * 
     * @return L'ID del bot.
     */
    @Override
    public String getId(){
        return playerId;
    }
    
    /**
     * Restituisce il codice identificativo del giocatore bot.
     * 
     * @return Il codice giocatore (2 per il bot).
     */
    @Override
    public int getPlayerCode(){
        return playerCode;
    }

    /**
     * Restituisce l'icona del token associato al bot.
     * 
     * @return L'oggetto ImageIcon che rappresenta l'icona del token del bot.
     */
    @Override
    public ImageIcon getIcon(){
        return icon;
    }
}
