package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.ImageIcon;

/**
 * @class AttackStrategy
 * @brief Implementazione di una strategia d'attacco per il gioco Forza 4.
 *
 * La classe AttackStrategy implementa l'interfaccia Strategy. Utilizza
 * l'algoritmo Minimax per scegliere la mossa migliore per il bot
 * durante il gioco.
 */
public class AttackStrategy implements Strategy {

    private int COLS = 7;  ///< Numero di colonne nella griglia di gioco.
    private int ROWS = 6;  ///< Numero di righe nella griglia di gioco.
    private ImageIcon emptyIcon;  ///< Icona che rappresenta una cella vuota nella griglia.
    private InterfacePlayer bot;  ///< Riferimento al giocatore bot.
    private InterfacePlayer player;  ///< Riferimento al giocatore umano.

    /**
     * Costruttore della classe AttackStrategy.
     * 
     * @param player Giocatore umano.
     * @param bot Giocatore bot.
     */
    public AttackStrategy(InterfacePlayer player, InterfacePlayer bot) {
        emptyIcon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/empty.png");
        this.bot = bot;
        this.player = player;
    }

    /**
     * Metodo per scegliere la mossa migliore per il bot utilizzando l'algoritmo Minimax.
     * 
     * @param grid La griglia di gioco attuale.
     * @return L'indice della colonna in cui il bot posizionerà la sua pedina. Questo
     * indice verrà usato per accedere all'array che contiene i pulsanti sulla parte 
     * superiore dello schermo.
     */
    public int chooseMove(Token grid[][]) {
        int bestMove = 0;
        int bestScore = -100;
        int moveScore;

        // Loop per valutare ogni colonna disponibile
        for (int j = 0; j < COLS; j++) {
            // Verifica se la colonna è piena
            if (grid[0][j].getOwner() != 0) {
                continue;
            }

            GameGrid.placeToken(grid, j, bot);
            moveScore = minmax(6, false, grid, player, bot);
            undo(grid, j);

            // Aggiornamento della migliore mossa e del punteggio
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = j;
            }
        }

        return bestMove;
    }

    /**
     * Algoritmo Minimax per scegliere la migliore mossa in base alla profondità dell'albero.
     * 
     * @param depth Profondità massima della ricerca.(un numero elevato crea un calo di perfomance )
     * @param isBotTurn Indica se è il turno del bot.
     * @param grid La griglia di gioco attuale.
     * @param player Giocatore umano.
     * @param bot bot.
     * @return Il punteggio migliore trovato.
     */
    private int minmax(int depth, boolean isBotTurn, Token grid[][], InterfacePlayer player, InterfacePlayer bot) {

        // Caso base: profondità raggiunta o vittoria
        if (depth == 0) {
            return 0;
        }

        if (checkForWin(grid, player) == 1) {
            return -depth;
        } else if (checkForWin(grid, bot) == 1) {
            return depth;
        }

        //---------------------------------------------------------------------
        // Turno del bot
        if (isBotTurn) {
            int bestScore = -100;
            for (int j = 0; j < COLS; j++) {
                if (grid[0][j].getOwner() != 0) {
                    continue;
                }
                GameGrid.placeToken(grid, j, bot);
                bestScore = Math.max(bestScore, minmax(depth - 1, !isBotTurn, grid, player, bot));
                undo(grid, j);
            }
            return bestScore;
        } else {
            // Turno del giocatore
            int minScore = 100;
            for (int j = 0; j < COLS; j++) {
                if (grid[0][j].getOwner() != 0) {
                    continue;
                }
                GameGrid.placeToken(grid, j, player);
                minScore = Math.min(minScore, minmax(depth - 1, !isBotTurn, grid, player, bot));
                undo(grid, j);
            }
            return minScore;
        }
    }

    /**
     * Metodo per annullare una mossa precedentemente eseguita.
     * Metdo usato solo dal bot tramite lo strategy durante la simulazione
     * delle varie mosse
     * 
     * @param grid La griglia di gioco.
     * @param col La colonna da cui rimuovere il token.
     */
    private void undo(Token grid[][], int col) {
        for (int row = 0; row < ROWS; row++) {
            if (grid[row][col].getOwner() != 0) {
                grid[row][col].setOwner(0, emptyIcon);
                break;
            }
        }
    }

    /**
     * Metodo per controllare se il giocatore o il bot ha vinto la partita.
     * 
     * @param grid La griglia di gioco attuale.
     * @param player Il giocatore per cui verificare la vittoria.
     * @return 1 se il giocatore ha vinto, altrimenti 0.
     */
    public int checkForWin(Token grid[][], InterfacePlayer player) {
        // Controlla vittoria nelle righe
        for (int row = 0; row < ROWS; row++) {
            int rowWin = 0;
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col].getOwner() == player.getPlayerCode()) {
                    rowWin++;
                    if (rowWin == 4) {
                        return 1;
                    }
                } else {
                    rowWin = 0;
                }
            }
        }

        // Controlla vittoria nelle colonne
        for (int col = 0; col < COLS; col++) {
            int colWin = 0;
            for (int row = 0; row < ROWS; row++) {
                if (grid[row][col].getOwner() == player.getPlayerCode()) {
                    colWin++;
                    if (colWin == 4) {
                        return 1;
                    }
                } else {
                    colWin = 0;
                }
            }
        }

        // Controlla vittoria nelle diagonali ascendenti
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                int diagWin = 0;
                for (int i = 0; i < 4; i++) {
                    if (grid[row + i][col + i].getOwner() == player.getPlayerCode()) {
                        diagWin++;
                        if (diagWin == 4) {
                            return 1;
                        }
                    } else {
                        diagWin = 0;
                    }
                }
            }
        }

        // Controlla vittoria nelle diagonali discendenti
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                int diagWin = 0;
                for (int i = 0; i < 4; i++) {
                    if (grid[row - i][col + i].getOwner() == player.getPlayerCode()) {
                        diagWin++;
                        if (diagWin == 4) {
                            return 1;
                        }
                    } else {
                        diagWin = 0;
                    }
                }
            }
        }
        return 0;
    }
}
