import java.awt.*;
import java.awt.event.*;                                             //Imports for GUI
import javax.swing.*;

public class GUI {
    private JLabel[][] matrise = new JLabel[12][12];                 //matrise for å ha rutenettet tilgjengelig     
    private Controller controller;  
    JFrame vinduet;
    private int lengdeTeller = 0;
    JLabel lengdeBoks;


    public GUI(Controller controller) {                              //Konstruktør som popper opp vinduet med snake
        this.controller = controller;

        vinduet = new JFrame("SNAKE");
        vinduet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel helePANEL = new JPanel(new BorderLayout());
        helePANEL.add(lagOverlerret(), BorderLayout.NORTH);
        helePANEL.add(lagUnderlerret(), BorderLayout.SOUTH);

        vinduet.add(helePANEL);
        vinduet.addKeyListener(new PilTaster());                     //Legger til piltaster
        vinduet.setFocusable(true);                                  // Får piltaster til å fungere
        vinduet.pack();
        vinduet.setVisible(true);
    }

    private JPanel lagOverlerret() {                                //metode for toppLerretet (knapper, lengde og avslutt knapp)
        JPanel overPanel = new JPanel(new GridLayout(1,3));

        JPanel lengdePanel = new JPanel(new BorderLayout());
        lengdeBoks = new JLabel("Lengde: " + lengdeTeller, SwingConstants.CENTER);
        lengdePanel.add(lengdeBoks);

        JPanel knappPanel = new JPanel(new BorderLayout());
        JButton nordKnapp = new JButton("Nord");
        nordKnapp.addActionListener(new NordKnapper());
        JButton sørKnapp = new JButton("Sør");
        sørKnapp.addActionListener(new SorKnapper());
        JButton østKnapp = new JButton("Øst");
        østKnapp.addActionListener(new OstKnapper());
        JButton vestKnapp = new JButton("Vest");
        vestKnapp.addActionListener(new VestKnapper());
        knappPanel.add(nordKnapp, BorderLayout.NORTH);
        knappPanel.add(sørKnapp, BorderLayout.SOUTH);
        knappPanel.add(østKnapp, BorderLayout.EAST);
        knappPanel.add(vestKnapp, BorderLayout.WEST);

        JPanel sluttPanel = new JPanel(new BorderLayout());
        JButton sluttKnapp = new JButton("Avslutt");
        sluttKnapp.addActionListener(new Avslutt());
        sluttPanel.add(sluttKnapp, SwingConstants.CENTER);

        overPanel.add(lengdePanel);
        overPanel.add(knappPanel);
        overPanel.add(sluttPanel);

        overPanel.setSize(50,50);

        return overPanel;
    }

    public JPanel lagUnderlerret() {                            //Metode for underLerret med rutenett og selve snake
        JPanel underPanel = new JPanel(new GridLayout(12,12));

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                JLabel nyTekstBoks = new JLabel(" ", SwingConstants.CENTER);
                nyTekstBoks.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                nyTekstBoks.setPreferredSize(new Dimension(40,40));
                underPanel.add(nyTekstBoks, SwingConstants.CENTER);
                matrise[i][j] = nyTekstBoks;
            }
        }
        return underPanel;
    }

    public void tegnSlangeHode(int rad, int bredde) {      
        JLabel hode = matrise[rad][bredde];
        hode.setText("O");
        hode.setForeground(Color.GREEN);
    }

    public void harBesokt(int rad, int bredde) {           
        JLabel hode = matrise[rad][bredde];
        hode.setText(" ");
    }

    public void tegnInnSkatter(int rad, int bredde) {    
        JLabel hode = matrise[rad][bredde];
        hode.setText("$");
        hode.setForeground(Color.RED); 
    }

    public int hvorMangeSkatter() {                         //teller hvor mange skatter det er i brettet
        int teller = 0;
        for (JLabel[] label : matrise) {
            for (JLabel tekstBoks : label) {
                if (tekstBoks.getText().equals("$")) {
                    teller++;
                }
            }
        }
        return teller;
    }

    public String hentRuteTekst(int rad, int bredde) {      //henter innhold i rutenett
        return matrise[rad][bredde].getText();
    }

    public void visTaperMelding() {                         //popup av en tapermelding
        JOptionPane.showMessageDialog(vinduet, "DU TAPTE! PRØV PÅ NYTT", "GAME OVER", JOptionPane.CLOSED_OPTION);
    }

    public void oppdaterLengde(int lengde) {                //oppdaterer lengde-teller
        lengdeTeller = lengde;
        lengdeBoks.setText("Lengde: " + lengdeTeller);
    }

    class PilTaster implements KeyListener {        //klasse for å bruke taster i snake

        @Override
        public void keyReleased(KeyEvent e) {
            int nokkel = e.getKeyCode();
            if (nokkel == KeyEvent.VK_LEFT) {
                controller.settRetning(Retning.VEST);
            }
            else if (nokkel == KeyEvent.VK_RIGHT) {
                controller.settRetning(Retning.ØST);
            }
            if (nokkel == KeyEvent.VK_UP) {
                controller.settRetning(Retning.NORD);
            }
            if (nokkel == KeyEvent.VK_DOWN) {
                controller.settRetning(Retning.SØR);
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}
    }

    class NordKnapper implements ActionListener {           //Knapper for hver retning

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.settRetning(Retning.NORD);
        }
    }

    class SorKnapper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.settRetning(Retning.SØR);
        }
    }

    class OstKnapper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.settRetning(Retning.ØST);
        }
    }

    class VestKnapper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.settRetning(Retning.VEST);
        }
    }

    class Avslutt implements ActionListener {           //knapp som avslutter programmet

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }
}
