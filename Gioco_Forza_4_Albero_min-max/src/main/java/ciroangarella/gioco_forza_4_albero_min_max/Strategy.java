package ciroangarella.gioco_forza_4_albero_min_max;

/**
 * @interface Strategy
 * @brief Interfaccia per definire diverse strategie di gioco.
 * 
 * Questa interfaccia è implementata da diverse classi che definiscono
 * strategie specifiche per il gioco, come attacco, difesa o cautela.
 */
public interface Strategy {
  
    /**
     * @brief Sceglie il movimento da eseguire sulla griglia di gioco.
     * 
     * @param grid La matrice di token che rappresenta la griglia di gioco.
     * @return La colonna in cui effettuare il movimento.
     */
    public int chooseMove(Token grid[][]);
}
