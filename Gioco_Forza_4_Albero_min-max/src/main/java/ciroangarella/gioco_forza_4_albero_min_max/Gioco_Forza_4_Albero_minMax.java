package ciroangarella.gioco_forza_4_albero_min_max;

/**
 * @class Gioco_Forza_4_Albero_minMax
 * @brief Classe principale che avvia il gioco Forza 4 con l'albero MinMax.
 * 
 * Questa classe gestisce l'avvio del gioco, il caricamento dei dati salvati e la selezione della modalità di gioco.
 */
public class Gioco_Forza_4_Albero_minMax {

    /**
     * @brief Metodo principale che esegue il gioco.
     * 
     * Il metodo `main()` è il punto di ingresso del programma. 
     * Gestisce il recupero dei dati salvati, la creazione dei giocatori e l'avvio di una nuova partita.
     * 
     * @param args Argomenti della riga di comando.
     */
    public static void main(String[] args) {
        // Ottenere l'istanza Singleton di SaveHandler per gestire i salvataggi
        SaveHandler saveHandler = SaveHandler.getInstance();
       
        // Creare una nuova finestra dell'applicazione usando WindowMediator
        WindowMediator applicationWindow = new ApplicationWindow();
        
        // Se nessun giocatore ha ancora mosso, avvia il gioco dal pannello ID giocatore
        if (saveHandler.allOwnersAreZero()) {
            InterfacePlayer player = new Player(); ///< Creazione del giocatore
            applicationWindow.showPlayerIdPanel(player); ///< Mostra pannello per inserire l'ID del giocatore
        } else {
            // Recupera i dati salvati e carica il giocatore e il bot
            InterfacePlayer player = new Player(); ///< Creazione del giocatore
            player.setId(saveHandler.getName("playerId")); ///< Imposta l'ID del giocatore dal salvataggio
           
            InterfacePlayer bot = new Bot(); ///< Creazione del bot
            bot.setId(saveHandler.getName("strategy")); ///< Imposta la strategia del bot dal salvataggio
           
            // Stampa gli ID di giocatore e bot per conferma
            System.out.println(player.getId() + " " + bot.getId());
           
            Strategy strategy; ///< Variabile per la strategia del bot
           
            // Seleziona la strategia del bot in base ai dati salvati
            switch (bot.getId()) {
                case "DEFENSE":
                    strategy = new DefenseStrategy(player, bot);
                    bot.setId(bot.getId() + " BOT"); ///< Aggiorna l'ID del bot
                    break;
                case "ATTACK":
                    strategy = new AttackStrategy(player, bot);
                    bot.setId(bot.getId() + " BOT"); ///< Aggiorna l'ID del bot
                    break;
                case "CAREFUL":
                    strategy = new CarefulStrategy(player, bot);
                    bot.setId(bot.getId() + " BOT"); ///< Aggiorna l'ID del bot
                    break;
                default:
                    strategy = new AttackStrategy(player, bot); ///< Strategia di default
                    bot.setId(bot.getId() + " BOT"); ///< Aggiorna l'ID del bot
                    break;
            }
           
            // Avvia una nuova partita con il giocatore, il bot e la strategia selezionata
            applicationWindow.newGame(player, bot, strategy);
        }
    }
}
