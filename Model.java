import java.util.ArrayList;                                           
import java.util.Random;

public class Model {
    
    private GUI gui;                                                     //MVC kontroll
    private int startX, startY;
    private Random random = new Random();                                
    public int lengdeTeller;

    private ArrayList<int[]> slangeKropp = new ArrayList<>();            //arraylist med slangen sin kropp


    public Model(GUI gui) {
        this.gui = gui;

        startX = random.nextInt(12);                                     //start posisjoner for slangehode
        startY = random.nextInt(12);
        gui.tegnSlangeHode(startX, startY);

        leggUtSkatter();
    }

    public void bevegelse(Retning retning) {                             //metode som blir kalt av tråd for å bevege seg
        try {
            slangeKropp.add(new int[]{startX, startY});
            int[] array = slangeKropp.get(0);
            gui.harBesokt(array[0], array[1]);                     
    
            if (Retning.NORD == retning) {
                startX++;
            } else if (Retning.SØR == retning) {
                startX--;
            } else if (Retning.ØST == retning) {
                startY--;
            } else if (Retning.VEST == retning) {
                startY++;
            }
    
            if (traffSkatt()) {
                if (gyldigRute(startX, startY)) {
                    if (gui.hentRuteTekst(startX, startY).equals("O")) {
                        gui.visTaperMelding();
                    } else {
                        leggUtEnSkatt();
                        gui.tegnSlangeHode(startX, startY);
                    }
                }
            } else {
                if (gyldigRute(startX, startY)) {
                    if (gui.hentRuteTekst(startX, startY).equals("O")) {
                        gui.visTaperMelding();
                    }
                    gui.tegnSlangeHode(startX, startY);
                    slangeKropp.remove(0);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds. Snake hit the wall or itself!");
            gui.visTaperMelding();
        }
    }

    public boolean gyldigRute(int x, int y){                                 //metode som sjekker om det er en gyldig rute
        if(x < 0 || x >= 12) return false;
        if(y < 0 || y >= 12) return false;
        return true;
    }

    public void leggUtSkatter() {                                             //metode som legger ut skatter i starten
        int x;
        int y;
        while (gui.hvorMangeSkatter() < 10) {
            x = random.nextInt(12);
            y = random.nextInt(12);
            gui.tegnInnSkatter(x, y);
        }
    }

    public void leggUtEnSkatt() {                                            //metode som legger ut en skatt
        int x = random.nextInt(12);
        int y = random.nextInt(12);
        gui.tegnInnSkatter(x, y);
    }

    public boolean traffSkatt() {                                            //metode som sjekker om slangen spiser en skatt
        if (gui.hentRuteTekst(startX, startY).equals("$")) {
            lengdeTeller++;
            gui.oppdaterLengde(lengdeTeller);
            return true;
        }
        return false;
    }
}