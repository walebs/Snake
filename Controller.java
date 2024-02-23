public class Controller {
    
    private GUI gui;
    private Model model;                            //Instansvariabler, og MVC kontroll
    private Retning retning = Retning.SØR;
    private Thread traad;

    public Controller() {
        gui = new GUI(this);
        model = new Model(gui);

        traad = new Thread(new Teller());
        traad.start();
    }

    class Teller implements Runnable {              //Runnable for tråd

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                    return;
                }
                bevegelse();
            }
        }
    }

    private void bevegelse() {
        model.bevegelse(retning);
    }

    public void settRetning(Retning retning) {
        this.retning = retning;
    }

    public void gameOver() {                    //Pop up av en tapermelding om du taper
        traad.interrupt();
        gui.visTaperMelding();
        System.exit(0);
    }

    public static void main(String[] args) {    //main
        Controller c = new Controller();
    }
}
