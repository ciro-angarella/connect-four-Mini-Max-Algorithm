package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.ImageIcon;

/**
 * @interface InterfacePlayer
 * @brief Interfaccia che rappresenta un giocatore nel gioco Forza 4.
 * 
 * L'interfaccia `InterfacePlayer` è utilizzata per rappresentare un giocatore, sia esso umano o bot, 
 * e permette di definire un'astrazione per interagire con i giocatori, rispettando il principio di inversione delle dipendenze (DIP) della SOLID.
 * 
 * @author angalinux
 */
public interface InterfacePlayer {
    
    /**
     * @brief Imposta l'ID del giocatore.
     * 
     * Questo metodo permette di assegnare un identificativo univoco al giocatore.
     * 
     * @param id Stringa che rappresenta l'ID del giocatore.
     */
    void setId(String id);
    
    /**
     * @brief Restituisce l'ID del giocatore.
     * 
     * Questo metodo restituisce l'ID assegnato al giocatore.
     * 
     * @return Stringa che rappresenta l'ID del giocatore.
     */
    String getId();
    
    /**
     * @brief Restituisce il codice del giocatore.
     * 
     * Il codice del giocatore viene utilizzato per rappresentarlo nella griglia di gioco.
     * Generalmente, il codice è un numero intero che identifica se il giocatore è il giocatore 1 o il giocatore 2.
     * 
     * @return Un numero intero che rappresenta il codice del giocatore.
     */
    int getPlayerCode();
    
    /**
     * @brief Restituisce l'icona del giocatore.
     * 
     * Questo metodo restituisce l'icona grafica associata al giocatore, utilizzata per rappresentarlo nella GUI del gioco.
     * 
     * @return Oggetto `ImageIcon` che rappresenta l'icona del giocatore.
     */
    ImageIcon getIcon();
}
