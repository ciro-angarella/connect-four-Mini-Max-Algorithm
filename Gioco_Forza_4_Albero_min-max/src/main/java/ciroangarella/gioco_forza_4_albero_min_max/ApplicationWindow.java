package ciroangarella.gioco_forza_4_albero_min_max;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * @class ApplicationWindow
 * @brief Classe che rappresenta la finestra principale dell'applicazione Forza 4.
 *
 * Questa classe estende JFrame e implementa WindowMediator. Gestisce l'interfaccia utente
 * e il salvataggio dei dati al momento della chiusura della finestra.
 */
public class ApplicationWindow extends JFrame implements WindowMediator {

    /**
     * Campo per memorizzare l'istanza di SaveHandler, utilizzato per gestire il salvataggio dei dati.
     */
    SaveHandler saveHandler = SaveHandler.getInstance();

    /**
     * Costruttore della classe ApplicationWindow.
     * Imposta la finestra a schermo intero, non ridimensionabile e aggiunge un WindowListener
     * per gestire la chiusura della finestra e il salvataggio dei dati tramite il SaveHandler.
     */
    public ApplicationWindow() {
        setTitle("Forza 4 Game");
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        // Aggiunge un WindowListener per gestire la chiusura della finestra
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (saveHandler != null) {
                    saveHandler.getInstance().writeFile(); // Salva i dati alla chiusura
                }
                System.exit(0); // Termina l'applicazione
            }
        });
    }

    /**
     * Mostra il pannello per inserire o modificare l'ID del giocatore.
     * @param player Il giocatore di cui si vuole modificare l'ID.
     */
    @Override
    public void showPlayerIdPanel(InterfacePlayer player) {
        // Aggiorna il contenuto della finestra con PlayerIdPanel
        getContentPane().removeAll();
        add(new PlayerIdPanel(player, this)); // Passa il mediatore e l'istanza del giocatore
        revalidate();
        repaint();
    }

    /**
     * Mostra il pannello per selezionare le modalità di gioco.
     * @param player Il giocatore corrente.
     */
    @Override
    public void showGameModesPanel(InterfacePlayer player) {
        // Aggiorna il contenuto della finestra con GameModesPanel
        getContentPane().removeAll();
        add(new GameModesPanel(player, this)); // Passa il mediatore
        revalidate();
        repaint();
    }

    /**
     * Avvia una nuova partita tra un giocatore e un bot, con una specifica strategia.
     * @param player Il giocatore umano.
     * @param bot Il giocatore bot.
     * @param strategy La strategia che il bot utilizzerà durante la partita.
     */
    @Override
    public void newGame(InterfacePlayer player, InterfacePlayer bot, Strategy strategy) {
        // Aggiorna il contenuto della finestra con NewGameWindow
        getContentPane().removeAll();
        add(new NewGameWindow(player, bot, strategy, this));
        revalidate();
        repaint();
    }

    /**
     * Mostra il pannello di fine partita, con l'indicazione del vincitore.
     * @param winner Il giocatore vincitore.
     */
    @Override
    public void endGame(InterfacePlayer winner) {
        // Aggiorna il contenuto della finestra con EndGamePanel
        getContentPane().removeAll();
        add(new EndGamePanel(winner));
        revalidate();
        repaint();
    }

    /**
     * Mostra il pannello di fine partita in caso di pareggio.
     */
    public void endTieGame() {
        // Aggiorna il contenuto della finestra con EndGamePanel per il pareggio
        getContentPane().removeAll();
        add(new EndGamePanel(this));
        revalidate();
        repaint();
    }
}
