package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @class GameModesPanel
 * @brief Pannello per la selezione della modalità di gioco in Forza 4.
 * 
 * Questa classe gestisce la selezione delle modalità di gioco ("DEFENSE", "ATTACK", "CAREFUL") 
 * e inizializza la partita con la strategia scelta. Il nome della startegia viene usato come id della classe bot.
 */
public class GameModesPanel extends JPanel {
    
    private int modeValue; ///< Valore della modalità di gioco selezionata.
    SaveHandler saveHandler = SaveHandler.getInstance(); ///< Gestore per il salvataggio delle impostazioni di gioco.

    /**
     * @brief Costruttore della classe GameModesPanel.
     * 
     * Inizializza il pannello con i pulsanti per la selezione della modalità di gioco
     * e associa un listener per gestire la selezione della modalità.
     * 
     * @param player Giocatore umano.
     * @param mediator Mediatore che gestisce la logica di transizione verso una nuova partita.
     */
    public GameModesPanel(InterfacePlayer player, WindowMediator mediator) {
        setLayout(new GridBagLayout());

        // Definisce i vincoli del layout per posizionare i pulsanti
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crea i pulsanti per le modalità di gioco
        JButton defenseButton = new JButton("DEFENSE");
        JButton attackButton = new JButton("ATTACK");
        JButton carefulButton = new JButton("CAREFUL");

        // Posiziona i pulsanti all'interno del pannello
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(defenseButton, gbc);

        gbc.gridx = 1;
        add(attackButton, gbc);

        gbc.gridx = 2;
        add(carefulButton, gbc);

        /**
         * @brief Listener per gestire la selezione della modalità di gioco.
         * 
         * Quando viene premuto un pulsante, si sceglie una strategia specifica 
         * e viene avviata una nuova partita con la strategia e il bot associati alla modalità scelta.
         */
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                InterfacePlayer bot = new Bot(); ///< Creazione del bot.
                Strategy strategy; ///< Strategia selezionata in base al comando.

                // Seleziona la strategia in base alla modalità scelta
                switch (command) {
                    case "DEFENSE":
                        strategy = new DefenseStrategy(player, bot);
                        bot.setId(command + " BOT");
                        break;
                    case "ATTACK":
                        strategy = new AttackStrategy(player, bot);
                        bot.setId(command + " BOT");
                        break;
                    case "CAREFUL":
                        strategy = new CarefulStrategy(player, bot);
                        bot.setId(command + " BOT");
                        break;
                    default:
                        strategy = new AttackStrategy(player, bot);
                        bot.setId(command + " BOT");
                        break;
                }

                // Visualizza la modalità di gioco selezionata e il valore della modalità
                System.out.println("Game mode selected: " + command + " modeValue: " + modeValue);

                // Salva il nome della strategia selezionata
                saveHandler.setName("strategy", command);

                // Avvia una nuova partita con la strategia selezionata
                mediator.newGame(player, bot, strategy);
            }
        };

        // Associa il listener ai pulsanti
        defenseButton.addActionListener(buttonListener);
        attackButton.addActionListener(buttonListener);
        carefulButton.addActionListener(buttonListener);
    }

    /**
     * @brief Restituisce il valore della modalità di gioco selezionata.
     * 
     * @return modeValue Il valore della modalità di gioco.
     */
    public int getModeValue() {
        return modeValue;
    }
}
