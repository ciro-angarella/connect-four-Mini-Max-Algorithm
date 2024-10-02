package ciroangarella.gioco_forza_4_albero_min_max;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * @class CarefulStrategy
 * @brief Implementa una strategia di gioco bilanciata tra attacco e difesa per il bot nel gioco Forza 4.
 *
 * La classe `CarefulStrategy` è una strategia che combina in modo casuale le strategie di attacco e difesa.
 * Utilizza una logica probabilistica per alternare tra una mossa offensiva e una difensiva.
 */
public class CarefulStrategy implements Strategy {

    private int COLS = 7;  ///< Numero di colonne nella griglia di gioco.
    private int ROWS = 6;  ///< Numero di righe nella griglia di gioco.
    private ImageIcon emptyIcon;  ///< Icona che rappresenta una cella vuota nella griglia.
    private InterfacePlayer bot;  ///< Riferimento al bot che utilizza questa strategia.
    private InterfacePlayer player;  ///< Riferimento al giocatore avversario.

    private Strategy attackStrategy;  ///< Strategia di attacco utilizzata dal bot.
    private Strategy defenseStrategy;  ///< Strategia di difesa utilizzata dal bot.
    private Random random;  ///< Oggetto Random per determinare casualmente quale strategia usare.

    /**
     * Costruttore della classe CarefulStrategy.
     * 
     * Inizializza le strategie di attacco e difesa e crea un oggetto `Random` per decidere casualmente quale strategia usare.
     * 
     * @param player Il giocatore umano.
     * @param bot Il bot che utilizza questa strategia.
     */
    public CarefulStrategy(InterfacePlayer player, InterfacePlayer bot) {
        emptyIcon = new ImageIcon("src/main/java/ciroangarella/gioco_forza_4_albero_min/max/img/empty.png");
        this.bot = bot;
        this.player = player;
        
        attackStrategy = new AttackStrategy(player, bot);
        defenseStrategy = new DefenseStrategy(player, bot);
         
        random = new Random();
    }
    
    /**
     * Sceglie la mossa successiva in base a una probabilità casuale.
     * 
     * Il 50% delle volte sceglie una mossa offensiva `AttackStrategy`,
     * e nel restante 50% delle volte sceglie una `DefenseStrategy`.
     * 
     * @param grid La griglia di gioco, rappresentata come una matrice di oggetti `Token`.
     * @return L'indice della colonna in cui posizionare il token del bot.
     */
    @Override
    public int chooseMove(Token grid[][]) {
        int rand = random.nextInt(100);  // Genera un numero casuale tra 0 e 99.
        
        if (rand >= 0 && rand < 50) {
            return attackStrategy.chooseMove(grid);  // Usa la strategia di attacco.
        } else {
            return defenseStrategy.chooseMove(grid);  // Usa la strategia di difesa.
        }
    }
}
