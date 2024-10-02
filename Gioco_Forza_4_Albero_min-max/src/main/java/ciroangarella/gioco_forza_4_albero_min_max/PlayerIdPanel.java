package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class PlayerIdPanel
 * @brief Classe che rappresenta un pannello per inserire l'ID del giocatore.
 * 
 * Questa classe gestisce l'interfaccia utente per l'inserimento del nome del giocatore,
 * mostrando un campo di testo e un pulsante per confermare l'input. L'ID del giocatore viene
 * salvato tramite il `SaveHandler` e passato al `WindowMediator` per l'avvio di una nuova partita.
 */
public class PlayerIdPanel extends JPanel {
    
    /**
     * @brief Variabile che memorizza temporaneamente il nome inserito dall'utente.
     */
    private String name; 
    
    /**
     * @brief Dichiarazione del listener per il pulsante di conferma dell'input del nome del giocatore.
     */
    private ActionListener enterNameListener; 
    
    /**
     * @brief Riferimento all'istanza Singleton di `SaveHandler` per la gestione del salvataggio.
     */
    SaveHandler saveHandler = SaveHandler.getInstance();
   
    /**
     * @brief Costruttore della classe `PlayerIdPanel`.
     * 
     * Inizializza il pannello con un layout e gestisce l'inserimento dell'ID del giocatore.
     * 
     * @param player Oggetto `InterfacePlayer` per memorizzare l'ID del giocatore.
     * @param mediator Oggetto `WindowMediator` per la gestione delle transizioni tra le finestre dell'applicazione.
     */
    public PlayerIdPanel(InterfacePlayer player, WindowMediator mediator) {
        
        // Imposta il layout del pannello
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Crea e configura i componenti del pannello
        JLabel label = new JLabel("Inserisci il tuo nome:");
        JButton enterButton = new JButton("Invio");
        JTextField nameField = new JTextField(30);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        
        // Inizializza il listener per l'inserimento del nome
        enterNameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText();
                if (!name.isEmpty()) {
                    player.setId(name); // Imposta l'ID del giocatore
                    System.out.println("ID del giocatore: " + player.getId());
                    mediator.showGameModesPanel(player); // Mostra il pannello delle modalità di gioco
                    saveHandler.setName("playerId", name); // Salva l'ID del giocatore
                }
            }
        };
        
        // Assegna il listener per l'invio dell'input
        nameField.addActionListener(enterNameListener);
        enterButton.addActionListener(enterNameListener);
        
        // Posizionamento dei componenti nel layout del pannello
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(nameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(enterButton, gbc);
    }
}
