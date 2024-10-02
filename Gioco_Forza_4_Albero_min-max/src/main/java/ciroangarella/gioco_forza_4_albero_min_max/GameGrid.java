package ciroangarella.gioco_forza_4_albero_min_max;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @class GameGrid
 * @brief Gestisce la griglia di gioco per il Forza 4.
 * 
 * La classe rappresenta la griglia del gioco Forza 4 e include le funzionalità
 * per posizionare i gettoni, controllare le vittorie e aggiornare lo stato della partita.
 */
public class GameGrid extends JPanel {

    private static final int ROWS = 6;   ///< Numero di righe della griglia di gioco.
    private static final int COLS = 7;   ///< Numero di colonne della griglia di gioco.
    private static final int CELL_SIZE = 40;  ///< Dimensione delle celle della griglia.

    private Token[][] grid; ///< Matrice della griglia che memorizza lo stato dei gettoni.
    private ImageIcon emptyIcon; ///< Icona per una cella vuota.
    private InterfacePlayer player; ///< Riferimento al giocatore umano.
    private InterfacePlayer bot; ///< Riferimento al bot.
    private Strategy strategy; ///< Strategia utilizzata dal bot.
    private int tie; ///< Contatore delle mosse per controllare i pareggi.
    private WindowMediator mediator; ///< Mediatore per gestire la fine della partita.
    private SaveHandler saveHandler = SaveHandler.getInstance(); ///< Gestore del salvataggio della partita.

    /**
     * @brief Costruttore che inizializza la griglia di gioco.
     * 
     * @param player Giocatore umano.
     * @param bot Giocatore bot.
     * @param strategy Strategia per il bot.
     * @param mediator Mediatore per la gestione della finestra e del fine partita.
     */
    public GameGrid(InterfacePlayer player, InterfacePlayer bot, Strategy strategy, WindowMediator mediator) {
        this.player = player;
        this.bot = bot;
        this.strategy = strategy;
        this.mediator = mediator;  // Usa il mediator passato al costruttore
        this.tie = 0; // Conta il numero di mosse

        setLayout(new BorderLayout()); 
        grid = new Token[ROWS][COLS];
        emptyIcon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/empty.png");

        // Inizializza i pannelli
        initializeControlPanel();
        initializeGrid();
    }

    /**
     * @brief Inizializza la griglia di gioco e popola ogni cella con un token vuoto.
     * 
     * Questo metodo inizializza il pannello della griglia di gioco, impostando 
     * le celle vuote e aggiornando la griglia salvata.
     */
    private void initializeGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));

        // Riempie la griglia con token vuoti
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Token emptyToken = new Token();
                emptyToken.setBackground(Color.blue);
                emptyToken.setOwner(0, emptyIcon);
                emptyToken.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                grid[row][col] = emptyToken;
                gridPanel.add(grid[row][col]);
            }
        }

        // Recupera e aggiorna la griglia dal salvataggio
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                try {
                    int owner = saveHandler.getOwner(row, col);
                    if (owner == 1) {
                        tie++;
                        grid[row][col].setOwner(1, player.getIcon());
                    } else if (owner == 2) {
                        tie++;
                        grid[row][col].setOwner(2, bot.getIcon());
                    } else {
                        grid[row][col].setOwner(0, emptyIcon);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * @brief Inizializza il pannello di controllo con i pulsanti per far cadere i gettoni.
     * 
     * Questo metodo crea una fila di pulsanti, uno per ogni colonna, che permettono
     * ai giocatori di inserire i gettoni nella griglia.
     */
    private void initializeControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(1, COLS));

        // Aggiunge i pulsanti per far cadere i gettoni
        for (int col = 0; col < COLS; col++) {
            JButton dropButton = new JButton("↓");
            dropButton.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
            dropButton.addActionListener(new ButtonListener(col));
            controlPanel.add(dropButton);
        }

        add(controlPanel, BorderLayout.NORTH);
    }

    /**
     * @class ButtonListener
     * @brief Listener per i pulsanti di inserimento dei gettoni.
     * 
     * Classe interna che gestisce l'azione di inserire un gettone nella colonna corrispondente
     * quando un pulsante viene premuto.
     */
    private class ButtonListener implements ActionListener {
        private int column; ///< La colonna in cui verrà inserito il gettone.

        /**
         * @brief Costruttore del ButtonListener.
         * 
         * @param column La colonna associata al pulsante.
         */
        public ButtonListener(int column) {
            this.column = column;
        }

        /**
         * @brief Azione eseguita quando si preme un pulsante per inserire un gettone.
         * 
         * @param e Evento che rappresenta l'azione eseguita.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (grid[0][column].getOwner() != 0) {
                    return;
            }else{
                        
                    
                placeToken(grid, column, player);
                tie++;
                if (checkForWin(grid, player) == 1) {
                    System.out.println("Player " + player.getId() + " wins");
                    mediator.endGame(player);  // Usa il mediator passato
                } else if (tie == 42) {
                    System.out.println("Tie");
                    mediator.endTieGame();  // Usa il mediator passato
                } else {
                    int botMove = strategy.chooseMove(grid);
                    placeToken(grid, botMove, bot);
                    tie++;
                    if (checkForWin(grid, bot) == 1) {
                        System.out.println("bot " + bot.getId() + " wins");
                        mediator.endGame(bot);  // Usa il mediator passato
                    } else if (tie == 42) {
                        System.out.println("Tie");
                        mediator.endTieGame();  // Usa il mediator passato
                    }
                }

                // Aggiorna il salvataggio se non ci sono vincitori
                if(checkForWin(grid, bot) == 0 && checkForWin(grid, player) == 0 && tie != ROWS*COLS){
                    for (int i = 0; i < ROWS; i++) {
                        for (int j = 0; j < COLS; j++) {
                            try {
                                saveHandler.setOwner(grid[i][j].getOwner(), i, j);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @brief Posiziona un gettone nella colonna specificata.
     * 
     * Cerca la prima cella disponibile nella colonna e assegna ad essa il token
     * del giocatore corrente.
     * 
     * @param grid La griglia di gioco.
     * @param column La colonna in cui inserire il gettone.
     * @param currentPlayer Il giocatore corrente che sta inserendo il gettone.
     */
    public static void placeToken(Token[][] grid, int column, InterfacePlayer currentPlayer) {
        if (grid[0][column].getOwner() != 0) {
            return;
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][column].getOwner() == 0) {
                grid[row][column].setOwner(currentPlayer.getPlayerCode(), currentPlayer.getIcon());
                break;
            }
        }
    }

    /**
     * @brief Controlla se un giocatore ha vinto la partita.
     * 
     * Verifica la presenza di una combinazione vincente di 4 gettoni allineati orizzontalmente,
     * verticalmente o diagonalmente per il giocatore specificato.
     * 
     * @param grid La griglia di gioco.
     * @param player Il giocatore di cui verificare la vittoria.
     * @return 1 se il giocatore ha vinto, 0 altrimenti.
     */
    public static int checkForWin(Token grid[][], InterfacePlayer player) {
        // Controlla vittoria nelle righe
        for (int row = 0; row < ROWS; row++) {
            int rowWin = 0;
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col].getOwner() == player.getPlayerCode()) {
                    rowWin++;
                    if (rowWin == 4) {
                        System.out.println("Player " + player.getPlayerCode() + " wins by row!");
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
                        System.out.println("Player " + player.getPlayerCode() + " wins by column!");
                        return 1;
                    }
                } else {
                    colWin = 0;
                }
            }
        }

        // Controlla vittoria nelle diagonali ascendenti (dal basso a sinistra verso l'alto a destra)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                boolean win = true;
                for (int i = 0; i < 4; i++) {
                    if (grid[row - i][col + i].getOwner() != player.getPlayerCode()) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    System.out.println("Player " + player.getPlayerCode() + " wins by ascending diagonal!");
                    return 1;
                }
            }
        }

        // Controlla vittoria nelle diagonali discendenti (dal basso a destra verso l'alto a sinistra)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                boolean win = true;
                for (int i = 0; i < 4; i++) {
                    if (grid[row + i][col + i].getOwner() != player.getPlayerCode()) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    System.out.println("Player " + player.getPlayerCode() + " wins by descending diagonal!");
                    return 1;
                }
            }
        }
        
        return 0; // Nessuna vittoria trovata
    }
}
