/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import astarwar.Astar;
import astarwar.Dijkstra;
import astarwar.Kartta;
import astarwar.Solmu;
import java.io.FileNotFoundException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author pxkorpel
 */
public class AstarwarTest {

    Astar A;
    Kartta kartta;

    public AstarwarTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws FileNotFoundException {
        Kartta kartta = new Kartta();
        kartta.luetiedostotaulukkoon("k");
        kartta.arvotakartta(kartta.kartta);
        Astar A = new Astar(kartta);
        Dijkstra D = new Dijkstra(kartta);

    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void arvioitumatkamaaliin() {
        boolean totta = false;
        if (A.arvioitumatkamaaliin(3, 4) > 0) {
            totta = true;
        }
        assertTrue(totta);
    }

    @Test
    public void matkatähänasti() {
    }

    @Test
    public void testMatkatähänasti() {
        boolean totta = false;
        if (A.matkatähänasti() > 0) {
            totta = true;
        }
        assertTrue(totta);
    }

    /**
     * Test of päivitänaapurit method, of class Astar.
     */
    @Test
    public void testPäivitänaapurit() {
        int numero = A.solmujono.size();
        A.päivitänaapurit();
        int numero2 = A.solmujono.size();
        boolean totta = false;
        if (numero2 > numero) {
            totta = true;
        }
        assertTrue(totta);
    }

    /**
     * Test of asetasolmulleF method, of class Astar.
     */
    @Test
    public void testAsetasolmulleF() {

        Solmu solmu = new Solmu();
        A.asetasolmulleF(solmu);
        boolean totta = false;
        if (solmu.F > 0) {
            totta = true;
        }
        assertTrue(totta);
    }

    /**
     * Test of onseinä method, of class Astar.
     */
    @Test
    public void testOnseinä() {
        Solmu solmu = new Solmu();
        boolean totta = false;
        if (!A.onseinä(solmu) && solmu.omaarvo < 1000) {
            totta = true;
        }
        assertTrue(totta);
    }

    /**
     * Test of parasvaihtoehto method, of class Astar.
     */
    @Test
    public void testParasvaihtoehto() {
    }

    /**
     * Test of onlistassa method, of class Astar.
     */
    @Test
    public void testOnlistassa() {
    }

    /**
     * Test of solmujonoon method, of class Astar.
     */
    @Test
    public void testSolmujonoon() {
    }

    /**
     * Test of lisäänaapurinaapureihin method, of class Astar.
     */
    @Test
    public void testLisäänaapurinaapureihin() {
    }

    /**
     * Test of käysolmu method, of class Astar.
     */
    @Test
    public void testKäysolmu() {
    }

    /**
     * Test of onkokäyty method, of class Astar.
     */
    @Test
    public void testOnkokäyty() {
    }

    /**
     * Test of kelpaakolistaan method, of class Astar.
     */
    @Test
    public void testKelpaakolistaan() {
    }

    /**
     * Test of kuljereitti method, of class Astar.
     */
    @Test
    public void testKuljereitti() {
    }

    /**
     * Test of ollaankomaalissa method, of class Astar.
     */
    @Test
    public void testOllaankomaalissa() {
    }

    /**
     * Test of luetiedostotaulukkoon method, of class Kartta.
     */
    @Test
    public void testLuetiedostotaulukkoon() throws Exception {
    }

    /**
     * Test of tulostakartta method, of class Kartta.
     */
    @Test
    public void testTulostakartta() throws Exception {
    }

    /**
     * Test of arvotareitti method, of class Kartta.
     */
    @Test
    public void testArvotareitti() throws Exception {
    }

    /**
     * Test of uusikartta method, of class Kartta.
     */
    @Test
    public void testUusikartta() {
    }

    /**
     * Test of piirräreitti method, of class Kartta.
     */
    @Test
    public void testPiirräreitti() throws Exception {
    }

    /**
     * Test of piirräreittisolmusta method, of class Kartta.
     */
    @Test
    public void testPiirräreittisolmusta() throws Exception {
    }

    /**
     * Test of käysolmuttakaperin method, of class Kartta.
     */
    @Test
    public void testKäysolmuttakaperin() {
    }

    /**
     * Test of arvotakartta method, of class Kartta.
     */
    @Test
    public void testArvotakartta() {
    }

    /**
     * Test of onkoseinä method, of class Kartta.
     */
    @Test
    public void testOnkoseinä() {
    }

    /**
     * Test of onkomaali method, of class Kartta.
     */
    @Test
    public void testOnkomaali() {
    }

    /**
     * Test of annaalkupistey method, of class Kartta.
     */
    @Test
    public void testAnnaalkupistey() {
    }

    /**
     * Test of annaalkupistex method, of class Kartta.
     */
    @Test
    public void testAnnaalkupistex() {
    }

    /**
     * Test of annaannaloppupistey method, of class Kartta.
     */
    @Test
    public void testAnnaannaloppupistey() {
    }

    /**
     * Test of annaloppupistex method, of class Kartta.
     */
    @Test
    public void testAnnaloppupistex() {
    }

    /**
     * Test of arvotasolmu method, of class Kartta.
     */
    @Test
    public void testArvotasolmu() {
    }

    /**
     * Test of annavanhempi method, of class Solmu.
     */
    @Test
    public void testAnnavanhempi() {
    }

    /**
     * Test of compareTo method, of class Solmu.
     */
    @Test
    public void testCompareTo() {
    }

    /**
     * Test of toString method, of class Solmu.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of summaamatkat method, of class Solmu.
     */
    @Test
    public void testSummaamatkat() {
    }

    /**
     * Test of equals method, of class Koordinaatti.
     */
    @Test
    public void testEquals() {
    }

    /**
     * Test of hashCode method, of class Koordinaatti.
     */
    @Test
    public void testHashCode() {
    }
    
    /**
     * Test of päivitänaapurienetäisyydetjapalautalähin method, of class Dijkstra.
     */
    @Test
    public void testPäivitänaapurienetäisyydetjapalautalähin() {
       
    }

    /**
     * Test of palautalähinnaapuri method, of class Dijkstra.
     */
    @Test
    public void testPalautalähinnaapuri() {
       
    }

    

    /**
     * Test of vertaareittejä method, of class Dijkstra.
     */
    @Test
    public void testVertaareittejä() {
        
    }

    /**
     * Test of annaetäisyys method, of class Dijkstra.
     */
    @Test
    public void testAnnaetäisyys() {
       
    }

    /**
     * Test of haesolmunarvo method, of class Dijkstra.
     */
    @Test
    public void testHaesolmunarvo() {
        
    }

    /**
     * Test of päivitäetäisyysvanhemmasta method, of class Dijkstra.
     */
    @Test
    public void testPäivitäetäisyysvanhemmasta() {
       
    }

    /**
     * Test of laskereitinpituus method, of class Dijkstra.
     */
    @Test
    public void testLaskereitinpituus() {
        
    }

    /**
     * Test of päivitäomaarvo method, of class Dijkstra.
     */
    @Test
    public void testPäivitäomaarvo() {
        
    }

    /**
     * Test of päivitäarvo method, of class Dijkstra.
     */
    @Test
    public void testPäivitäarvo() {
        
    }

    /**
     * Test of päivitämatkanykyisestäsolmusta method, of class Dijkstra.
     */
    @Test
    public void testPäivitämatkanykyisestäsolmusta() {
        
    }

    

    /**
     * Test of onkoseinätaikäyty method, of class Dijkstra.
     */
    @Test
    public void testOnkoseinätaikäyty() {
        
    }

    

    
}
