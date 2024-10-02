package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.ImageIcon;

/**
 * @class DefenseStrategy
 * @brief Implementa una strategia difensiva per il bot nel gioco Forza 4.
 *
 * La classe `DefenseStrategy` cerca di interrompere le sequenze di pedine del giocatore
 * per prevenire una vittoria. Analizza sia le colonne che le righe per trovare la migliore
 * mossa difensiva possibile.
 */
public class DefenseStrategy implements Strategy {
    private static final int ROWS = 6; ///< Numero di righe nella griglia di gioco.
    private static final int COLS = 7; ///< Numero di colonne nella griglia di gioco.
    
    private ImageIcon emptyIcon;  ///< Icona che rappresenta una cella vuota nella griglia.
    private InterfacePlayer bot;  ///< Riferimento al bot che utilizza questa strategia.
    private InterfacePlayer player;  ///< Riferimento al giocatore avversario.

    /**
     * Costruttore della classe DefenseStrategy.
     * 
     * Inizializza la strategia con i giocatori e l'icona della cella vuota.
     * 
     * @param player Il giocatore umano.
     * @param bot Il bot che utilizza questa strategia difensiva.
     */
    public DefenseStrategy(InterfacePlayer player, InterfacePlayer bot) {
        emptyIcon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min/max/img/empty.png");
        this.bot = bot;
        this.player = player;
    }

    /**
     * Sceglie la mossa migliore per il bot basata su una strategia difensiva.
     * 
     * Analizza sia le colonne che le righe per trovare il maggior numero di pedine consecutive
     * del giocatore avversario e cerca di interromperne la sequenza.
     * 
     * @param grid La griglia di gioco, rappresentata come una matrice di oggetti `Token`.
     * @return L'indice della colonna in cui posizionare il token del bot.
     */
    @Override
    public int chooseMove(Token grid[][]) {
        int maxTokens = 0; // Massimo numero di token consecutivi trovati finora per il giocatore
        int bestMove = -1; // Migliore mossa trovata finora (indice della colonna)

        // Controlla le colonne per la sequenza più lunga di pedine del giocatore
        for (int j = 0; j < COLS; j++) {
            // Vede se la colonna è piena
            if (grid[0][j].getOwner() != 0) {
                continue; // Salta la colonna se è piena
            }
            
            int currentNumTokens = 0; // Numero attuale di token consecutivi per il giocatore nella colonna corrente
            
            for (int i = 0; i < ROWS; i++) {
                if (grid[i][j].getOwner() == player.getPlayerCode()) {
                    currentNumTokens++;
                } else {
                    currentNumTokens = 0; // Resetta il conteggio se trova un token diverso
                }
                
                if (currentNumTokens > maxTokens) {
                    maxTokens = currentNumTokens;
                    bestMove = j;
                }
            }
        }

        // Controlla le righe per interrompere la sequenza più lunga del giocatore
        for (int i = 0; i < ROWS; i++) {
            int currentNumTokens = 0; // Numero attuale di token consecutivi per il giocatore nella riga corrente
            int tempBestMove = -1; // Colonna temporanea per interrompere la sequenza
            
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j].getOwner() == player.getPlayerCode()) {
                    currentNumTokens++;
                } else {
                    if (currentNumTokens > maxTokens) {
                        maxTokens = currentNumTokens;
                        
                        // Controlla se può mettere una pedina prima della sequenza
                        if (j - currentNumTokens - 1 >= 0 && grid[i][j - currentNumTokens - 1].getOwner() == 0) {
                            tempBestMove = j - currentNumTokens - 1;
                        }
                        
                        // Controlla se può mettere una pedina dopo la sequenza
                        if (j < COLS && grid[i][j].getOwner() == 0) {
                            tempBestMove = j;
                        }
                    }
                    currentNumTokens = 0; // Resetta il conteggio se trova un token diverso
                }
            }

            // Se la sequenza più lunga è trovata alla fine della riga
            if (currentNumTokens > maxTokens) {
                maxTokens = currentNumTokens;
                
                // Controlla se può mettere una pedina alla fine della riga
                if (COLS - currentNumTokens - 1 >= 0 && grid[i][COLS - currentNumTokens - 1].getOwner() == 0) {
                    tempBestMove = COLS - currentNumTokens - 1;
                }
            }

            // Aggiorna la migliore mossa se la colonna non è piena
            if (tempBestMove != -1 && grid[0][tempBestMove].getOwner() == 0) {
                bestMove = tempBestMove;
            }
        }

        return bestMove; // Restituisce l'indice della colonna migliore trovata
    }
}
