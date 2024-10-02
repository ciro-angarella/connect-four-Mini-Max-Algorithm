package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.*;
import java.awt.*;

/**
 * @class Token
 * @brief Rappresenta un token nella griglia di gioco.
 * 
 * Questa classe estende JPanel e gestisce lo stato di un token,
 * inclusa l'assegnazione del giocatore e la visualizzazione dell'icona.
 */
public class Token extends JPanel {
    private int player; // 0 per vuoto, 1 per giocatore 1, 2 per giocatore 2
    private JLabel label;

    /**
     * @brief Costruttore di Token.
     * 
     * Inizializza il token come vuoto e crea un JLabel per visualizzare
     * l'icona associata al token.
     */
    public Token() {
        super();
        this.player = 0;
        this.label = new JLabel();
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setBackground(Color.WHITE);
    }

    /**
     * @brief Ottiene l'ID del proprietario del token.
     * 
     * @return Un intero che rappresenta il giocatore associato al token:
     * 0 se vuoto, 1 per il giocatore 1, 2 per il giocatore 2.
     */
    public int getOwner() {
        return player;
    }

    /**
     * @brief Imposta il proprietario del token e l'icona visualizzata.
     * 
     * @param player L'ID del giocatore che possiede il token.
     * @param icon L'icona da visualizzare per il token.
     */
    public void setOwner(int player, ImageIcon icon) {
        this.player = player;
        label.setIcon(icon);
    }

    /**
     * @brief Ottiene l'icona associata al token.
     * 
     * @return L'icona del token.
     */
    public ImageIcon getImageIcon() {
        return (ImageIcon) label.getIcon();
    }
}
