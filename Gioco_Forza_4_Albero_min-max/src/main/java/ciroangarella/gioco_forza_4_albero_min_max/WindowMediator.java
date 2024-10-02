package ciroangarella.gioco_forza_4_albero_min_max;

/**
 * @interface WindowMediator
 * @brief Interfaccia per il pattern Mediator.
 * 
 * Questa interfaccia definisce i metodi che un mediatore deve implementare
 * per gestire le interazioni tra i componenti della UI nel gioco.
 */
public interface WindowMediator {

    /**
     * @brief Mostra il pannello per l'inserimento dell'ID del giocatore.
     * 
     * @param player L'oggetto del giocatore che deve inserire l'ID.
     */
    void showPlayerIdPanel(InterfacePlayer player);

    /**
     * @brief Mostra il pannello per la selezione della modalità di gioco.
     * 
     * @param player L'oggetto del giocatore per cui viene mostrata la modalità.
     */
    void showGameModesPanel(InterfacePlayer player);

    /**
     * @brief Inizia una nuova partita.
     * 
     * @param player L'oggetto del giocatore.
     * @param bot L'oggetto del bot avversario.
     * @param strategy La strategia da utilizzare nella partita.
     */
    void newGame(InterfacePlayer player, InterfacePlayer bot, Strategy strategy);

    /**
     * @brief Termina la partita e dichiara il vincitore.
     * 
     * @param winner L'oggetto del giocatore vincitore.
     */
    void endGame(InterfacePlayer winner);

    /**
     * @brief Termina la partita in caso di pareggio.
     */
    void endTieGame();
}
