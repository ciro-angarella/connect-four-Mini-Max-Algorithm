package ciroangarella.gioco_forza_4_albero_min_max;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * @class NewGameWindow
 * @brief Classe che rappresenta la finestra di una nuova partita nel gioco Forza 4.
 * 
 * La classe `NewGameWindow` è responsabile della creazione e gestione della finestra di gioco
 * dove i giocatori (umano e bot) interagiscono con la griglia di gioco per svolgere una partita.
 * Questa finestra contiene la griglia di gioco e gestisce l'interazione tra i giocatori.
 */
public class NewGameWindow extends JPanel {

    /**
     * @brief La griglia di gioco.
     * 
     * Oggetto della classe `GameGrid` che rappresenta la griglia su cui i giocatori piazzano i token.
     */
    private GameGrid grid;
    
    /**
     * @brief Oggetto per gestire il salvataggio dello stato di gioco.
     * 
     * Viene utilizzata l'istanza singleton di `SaveHandler` per gestire il salvataggio dello stato della partita.
     */
    SaveHandler saveHandler = SaveHandler.getInstance();

    /**
     * @brief Costruttore della classe NewGameWindow.
     * 
     * Inizializza una nuova finestra di gioco con i giocatori, la strategia e il mediator.
     * Imposta il layout della finestra e aggiunge la griglia di gioco al centro.
     * 
     * @param player Giocatore umano che partecipa alla partita, implementa l'interfaccia `InterfacePlayer`.
     * @param bot Bot che partecipa alla partita, implementa l'interfaccia `InterfacePlayer`.
     * @param strategy Strategia di gioco utilizzata dal bot, implementa l'interfaccia `Strategy`.
     * @param mediator Oggetto di tipo `WindowMediator` per coordinare le operazioni tra le varie componenti della GUI.
     */
    public NewGameWindow(InterfacePlayer player, InterfacePlayer bot, Strategy strategy, WindowMediator mediator) {
        
        // Imposta il layout del pannello principale come BorderLayout
        setLayout(new BorderLayout());
       
        // Crea e inizializza la griglia di gioco con i giocatori e la strategia specificata
        grid = new GameGrid(player, bot, strategy, mediator);

        // Aggiungi la griglia di gioco al pannello principale al centro
        add(grid, BorderLayout.CENTER);
    }
}
