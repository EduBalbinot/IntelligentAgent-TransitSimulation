package TrabalhoAgentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;



public class pnPrincipal extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private ArrayList<Player> bots;
    private ArrayList<Boundary> walls;
    public AffineTransform transform;
    private String[][] map;

    static int yOffset = 25;
    static int stWidth = 12;
    static int stHeight =12;

    private JButton button;

    public pnPrincipal() {
        walls = new ArrayList<Boundary>();
        bots = new ArrayList<Player>();
        transform = new AffineTransform();
        
        Timer timer = new Timer(10, this);
        timer.start();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        button = new JButton("Button");
        add(button);
        button.addActionListener(this);
        add(Box.createVerticalGlue());

        map = new String[][] 
        {{"3", "3", "3", "1", "3", "3", "3", "3", "3", "3", "3", "1", "3", "3", "3", "3", "3", "3", "3", "1", "3", "3", "3", "3", "3", "3", "3", "1", "3", "3", "3", "3", "3", "3", "3", "1", "3", "3", "3", "3", "3", "3", "3", "1", "3", "3"},
        {"3", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
        {"3", "-", " ", " ", " ", " ", " ", "0", " ", "d", " ", " ", " ", " ", " ", "0", " ", "d", " ", " ", " ", " ", " ", "0", " ", "d", " ", " ", " ", " ", " ", "0", " ", "d", " ", " ", " ", " ", " ", "0", " ", "d", " ", " ", " ", "-"},
        {"1", "-", " ", " ", "r", "=", " ", "=", " ", "=", " ", " ", "r", "=", " ", "=", " ", "=", " ", " ", "r", "=", " ", "=", " ", "=", " ", " ", "r", "=", " ", "=", " ", "=", " ", " ", "r", "=", " ", "=", " ", "=", " ", " ", " ", "-"},
        {"3", "-", " ", " ", " ", " ", " ", "0", " ", " ", " ", "d", " ", " ", " ", "0", " ", " ", " ", "d", " ", " ", " ", "0", " ", " ", " ", "d", " ", " ", " ", "0", " ", " ", " ", "d", " ", " ", " ", "0", " ", " ", " ", "d", " ", "-"},
        {"3", "-", "r", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-"},
        {"3", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-"},
        {"3", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-"},
        {"3", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-"},
        {"3", "-", " ", "|", " ", "-", "-", "-", "-", "-", "s0", "|", "l", "-", "-", "-", "-", "-", "s0", "|", "l", "-", "-", "-", "-", "-", "s0", "|", "l", "-", "-", "-", "-", "-", "s0", "|", "l", "-", "-", "-", "-", "-", " ", "|", "l", "-"},
        {"3", "-", " ", "u", " ", " ", " ", "0", " ", "d", " ", "u", " ", "s3", " ", "0", " ", "d", " ", "u", " ", "s3", " ", "0", " ", "d", " ", "u", " ", "s3", " ", "0", " ", "d", " ", "u", " ", "s3", " ", "0", " ", " ", " ", " ", " ", "-"},
        {"1", "-", " ", " ", "r", "=", " ", "=", " ", "=", "l", " ", "r", "=", " ", "=", " ", "=", "l", " ", "r", "=", " ", "=", " ", "=", "l", " ", "r", "=", " ", "=", " ", "=", "l", " ", "r", "=", " ", "=", " ", "=", "l", " ", " ", "-"},
        {"3", "-", " ", " ", " ", " ", " ", "0", " ", "s1", " ", "d", " ", "u", " ", "0", " ", "s1", " ", "d", " ", "u", " ", "0", " ", "s1", " ", "d", " ", "u", " ", "0", " ", "s1", " ", "d", " ", "u", " ", "0", " ", " ", " ", "d", " ", "-"},
        {"3", "-", "r", "|", " ", "-", "-", "-", "-", "-", "r", "|", "s2", "-", "-", "-", "-", "-", "r", "|", "s2", "-", "-", "-", "-", "-", "r", "|", "s2", "-", "-", "-", "-", "-", "r", "|", "s2", "-", "-", "-", "-", "-", " ", "|", " ", "-"},
        {"3", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-"},
        {"3", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-", "-", "-", "-", "-", "0", "|", "0", "-"},
        {"3", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-", "-", "-", "-", "-", " ", " ", " ", "-"},
        {"3", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", " ", "-", "-", "-", "-", "-", " ", "|", "l", "-"},
        {"3", "-", " ", "u", " ", " ", " ", "0", " ", " ", " ", "u", " ", " ", " ", "0", " ", " ", " ", "u", " ", " ", " ", "0", " ", " ", " ", "u", " ", " ", " ", "0", " ", " ", " ", "u", " ", " ", " ", "0", " ", " ", " ", " ", " ", "-"},
        {"1", "-", " ", " ", " ", "=", " ", "=", " ", "=", "l", " ", " ", "=", " ", "=", " ", "=", "l", " ", " ", "=", " ", "=", " ", "=", "l", " ", " ", "=", " ", "=", " ", "=", "l", " ", " ", "=", " ", "=", " ", "=", "l", " ", " ", "-"},
        {"3", "-", " ", " ", " ", "u", " ", "0", " ", " ", " ", " ", " ", "u", " ", "0", " ", " ", " ", " ", " ", "u", " ", "0", " ", " ", " ", " ", " ", "u", " ", "0", " ", " ", " ", " ", " ", "u", " ", "0", " ", " ", " ", " ", " ", "-"},
        {"3", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}};

        createWalls(map);

        Timer t = new Timer(0, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Boundary b : getWalls('s')) {
                    if(b.getColor()==Color.RED){
                        if(b.isRedStage()){
                            b.setColor(Color.GREEN);
                            b.setRedStage(false);
                        } else b.setRedStage(true);
                    } else if(b.getColor()==Color.YELLOW)
                            b.setColor(Color.RED);
                            else if(b.getColor()==Color.GREEN)
                                b.setColor(Color.YELLOW);
                }
            }
        });

        t.setRepeats(true);
        t.setDelay(3000); //1 sec
        t.start();

        bots.add(new Player(this, stWidth*10 , yOffset + stHeight*7.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*10 , yOffset + stHeight*51.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*10 , yOffset + stHeight*29.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*40 , yOffset + stHeight*7.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*40 , yOffset + stHeight*51.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*40 , yOffset + stHeight*29.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*110 , yOffset + stHeight*7.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*110 , yOffset + stHeight*51.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*110 , yOffset + stHeight*29.5  , stWidth*2.5, stHeight*1.5, 1));
        bots.add(new Player(this, stWidth*80 , yOffset + stHeight*29.5  , stWidth*2.5, stHeight*1.5, 1)); 
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK); //background
        g2d.fillRect(0, yOffset, getWidth(), getHeight() - yOffset);

        for (Boundary wall : walls) { 
            wall.drawWall(g2d);
        }

        for (Player bot : bots) { 
            bot.drawBot(g2d);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {            
        }
         for (Player a: this.bots) {
            a.execute();
        }
    }

    private int totalBWidth=0;
    private int totalBHeight=0;
    public void createWalls(String[][] mp){
        int colWidth = 0;
        int rowHeight = 0;
        for (int row=1;row<mp.length;row++) {
        totalBHeight+=rowHeight;
        rowHeight=stWidth*Integer.parseInt(mp[row][0]);
           for (int col=1;col<mp[row].length;col++) {
            totalBWidth+=colWidth;
            colWidth=stHeight*Integer.parseInt(mp[0][col]);
                switch(mp[row][col]){
                    case " ":
                    break;
                    case "-":
                    walls.add(new Boundary("wall", totalBWidth, yOffset + totalBHeight, Color.GRAY, rowHeight, colWidth));
                    break;
                    case "=":
                    walls.add(new Boundary("lineH", totalBWidth, yOffset + totalBHeight, Color.YELLOW, rowHeight, colWidth));
                    break;
                    case "|":
                    walls.add(new Boundary("lineV", totalBWidth, yOffset + totalBHeight, Color.YELLOW, rowHeight, colWidth));
                    break;
                    case "s0": case "s1":case "s2":case "s3":
                    Color tLightColor = Color.WHITE;
                    boolean RedStage = false;
                    switch(mp[row][col].charAt(1)){
                        case '0':
                            tLightColor = Color.GREEN;
                        break;
                        case '1':
                            tLightColor = Color.YELLOW;
                        break;
                        case '2':
                            tLightColor = Color.RED;
                        break;
                        case '3':
                            tLightColor = Color.RED;
                            RedStage = true;
                        break;
                    };
                    walls.add(new Boundary(mp[row][col], totalBWidth, yOffset + totalBHeight, tLightColor, rowHeight, colWidth, RedStage));
                    break;
                    default:
                    walls.add(new Boundary(mp[row][col], totalBWidth, yOffset + totalBHeight, Color.DARK_GRAY, rowHeight, colWidth));
                    break;
                }
           }
        totalBWidth=0;
        colWidth = 0;
        }
    }

    public ArrayList<Boundary> getWalls(char f) {
        ArrayList<Boundary> filtro = new ArrayList<Boundary>();
        for (Boundary b : walls) {
            if (b.getType().charAt(0)==f)
                filtro.add(b);
        }
        return filtro;
    }

    public int getyOffset() {
        return yOffset;
    }

    public ArrayList<Player> getBots() {
        return bots;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Trânsito de Xaxim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pnPrincipal panel = new pnPrincipal();
        frame.add(panel);
        
        frame.add(panel);
        frame.setSize(800, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
