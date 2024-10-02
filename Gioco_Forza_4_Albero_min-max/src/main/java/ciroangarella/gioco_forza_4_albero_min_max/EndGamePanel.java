package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class EndGamePanel
 * @brief Gestisce la visualizzazione del pannello di fine gioco e aggiorna la classifica dei vincitori.
 * 
 * Il pannello visualizza il vincitore o un pareggio, salva lo stato della griglia e aggiorna
 * la classifica in un file.
 */
public class EndGamePanel extends JPanel {

    private Winner winner; ///< Oggetto per memorizzare i dati del vincitore.
    SaveHandler saveHandler = SaveHandler.getInstance(); ///< Gestore per il salvataggio dello stato della griglia.
  
    private static final int ROWS = 6; ///< Numero di righe nella griglia di gioco.
    private static final int COLS = 7; ///< Numero di colonne nella griglia di gioco.

    /**
     * Costruttore che crea il pannello di fine gioco per un giocatore vincitore.
     * 
     * @param player Il giocatore che ha vinto la partita.
     */
    public EndGamePanel(InterfacePlayer player) {
        
        //imposta tutta la griglia di gioco a vuota, per inizare una nuova partita
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                try {
                    saveHandler.setOwner(0, i, j); // Imposta il proprietario della cella a vuoto.
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        saveHandler.writeFile(); // Salva lo stato della griglia su file.
       
        winner = new Winner(player.getId()); // Crea un oggetto `Winner` con l'ID del giocatore.

        // Layout del pannello
        this.setLayout(new BorderLayout());

        // Pannello per mostrare il vincitore
        JPanel winnerPanel = new JPanel();
        winnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        winnerPanel.setBackground(Color.WHITE);
        winnerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel winnerLabel = new JLabel("WINNER: " + winner.getId());
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        winnerPanel.add(winnerLabel);

        this.add(winnerPanel, BorderLayout.NORTH); // Aggiungi il pannello in alto.

        // Pannello per visualizzare la classifica
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane rankedPanel = new JScrollPane(contentPanel);
        rankedPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rankedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lettura dei vincitori precedenti dal file
        List<Winner> rank = new ArrayList<>();
        readWinners(rank); // Metodo per leggere i vincitori da un file.

        // Verifica se il vincitore attuale esiste già nella classifica
        boolean isPlayerFound = false;
        int foundedPlayerIndex = 0;

        for (int i = 0; i < rank.size(); i++) {
            if (rank.get(i).getId().equals(winner.getId())) {
                isPlayerFound = true;
                foundedPlayerIndex = i;
                break;
            }
        }

        if (isPlayerFound) {
            rank.get(foundedPlayerIndex).addWin(); // Aumenta le vittorie del giocatore esistente.
        } else {
            winner.setFirstWin(); // Imposta la prima vittoria per il nuovo vincitore.
            rank.add(winner);
        }

        rank.sort(Comparator.comparingInt(Winner::getWin).reversed()); // Ordina la classifica per numero di vittorie.
        overwriteRankFile(rank); // Sovrascrive il file della classifica.

        displayRankedPlayer(rank, contentPanel); // Mostra i giocatori classificati nel pannello.

        this.add(rankedPanel, BorderLayout.CENTER); // Aggiungi il pannello centrale.
    }

    /**
     * Costruttore che crea il pannello di fine gioco per un pareggio.
     * 
     * @param mediator Mediatore per gestire la finestra.
     */
    public EndGamePanel(WindowMediator mediator) {
        
        //svuota la griglia
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                try {
                    saveHandler.setOwner(0, i, j); // Imposta il proprietario della cella a vuoto.
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
         
        saveHandler.writeFile(); // Salva lo stato della griglia su file.

        // Pannello per mostrare il pareggio
        JPanel tiePanel = new JPanel();
        tiePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tiePanel.setBackground(Color.WHITE);
        tiePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel tieLabel = new JLabel(" TIE ");
        tieLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tiePanel.add(tieLabel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane rankedPanel = new JScrollPane(contentPanel);
        rankedPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rankedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lettura dei vincitori precedenti dal file
        List<Winner> rank = new ArrayList<>();
        readWinners(rank);

        rank.sort(Comparator.comparingInt(Winner::getWin).reversed()); // Ordina la classifica per vittorie.

        displayRankedPlayer(rank, contentPanel); // Mostra i giocatori classificati.

        this.add(rankedPanel, BorderLayout.CENTER); // Aggiungi il pannello centrale.
    }

    /**
     * Legge i vincitori da un file e li aggiunge alla lista.
     * 
     * @param rank Lista in cui memorizzare i vincitori.
     */
    private void readWinners(List<Winner> rank) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/rank.json"));
            String readedLine;
            while ((readedLine = reader.readLine()) != null) {
                readedLine = readedLine.trim(); // Rimuove spazi vuoti.
                if (readedLine.startsWith("[") || readedLine.endsWith("]")) {
                    continue; // Ignora linee non rilevanti.
                }
                String id = parseId(readedLine);
                int win = parseWin(readedLine);
                Winner winner = new Winner(id);
                winner.setWin(win);
                rank.add(winner); // Aggiunge il vincitore alla lista.
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EndGamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EndGamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {
                Logger.getLogger(EndGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Sovrascrive il file  della classifica con una lista di vincitori.
     * 
     * @param winners Lista di vincitori da salvare nel file.
     */
    public static void overwriteRankFile(List<Winner> winners) {
        String filePath = "src/main/java/ciroangarella/gioco_forza_4_albero_min_max/img/rank.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("["); // Inizio dell'array JSON
            writer.newLine();
            
            // Itera attraverso la lista di vincitori
            for (int i = 0; i < winners.size(); i++) {
                Winner winner = winners.get(i);
                String json = "{\"id\": \"" + winner.getId() + "\", \"win\": " + winner.getWin() + "}";
                
                writer.write(json); // Scrivi l'oggetto JSON del vincitore
                
                // Aggiungi una virgola se non è l'ultimo elemento
                if (i < winners.size() - 1) {
                    writer.write(",");
                }
                writer.newLine();
            }
            
            writer.write("]"); // Fine dell'array JSON
        } catch (IOException ex) {
            Logger.getLogger(EndGamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Mostra i giocatori classificati in un pannello.
     * 
     * @param rank Lista di giocatori classificati.
     * @param rankedPanel Pannello in cui visualizzare i giocatori classificati.
     */
    void displayRankedPlayer(List<Winner> rank, JPanel rankedPanel) {
        int i = 1;
        // Mostra i vincitori nel pannello rankedPanel
        for (Winner winner : rank) {
            // Crea il JLabel per il vincitore
            JLabel winnerDisplay = new JLabel(i + ". " + winner.getId() + " win: " + winner.getWin());
            winnerDisplay.setFont(new Font("Serif", Font.PLAIN, 16)); // Font per i vincitori
            winnerDisplay.setHorizontalAlignment(SwingConstants.LEFT); // Allinea il testo a sinistra

            // Aggiungi il vincitore al pannello
            rankedPanel.add(winnerDisplay);
            i++;
        }
    }

    /**
     * Estrae l'ID di un giocatore da una stringa JSON.
     * 
     * @param line Linea da cui estrarre l'ID.
     * @return ID estratto dalla stringa.
     */
    private static String parseId(String line) {
        int startIndex = line.indexOf("\"id\":") + 8;
        int endIndex = line.indexOf(",", startIndex);
        return line.substring(startIndex - 1, endIndex - 1).trim();
    }

    /**
     * Estrae il numero di vittorie di un giocatore da una stringa JSON.
     * 
     * @param line Linea da cui estrarre il numero di vittorie.
     * @return Numero di vittorie estratto dalla stringa.
     */
    private static int parseWin(String line) {
        int startIndex = line.indexOf("\"win\":") + 6;
        int endIndex = line.indexOf("}", startIndex);
        return Integer.parseInt(line.substring(startIndex, endIndex).trim());
    }
}
