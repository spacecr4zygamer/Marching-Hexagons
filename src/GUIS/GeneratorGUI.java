package GUIS;

import DataTypes.Vector2;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.EdECKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;

public class GeneratorGUI extends JPanel {

    public JFrame parent;
    public JPanel explorer;

    private JLabel CaseCounter, CaseDisplay;
    private JTextField CaseChooser;
    private int CaseNumber = 0;
    private int connectionmode = 1;

    private JRadioButton LinesB, TrisB;

    public GeneratorGUI() {
        parent = new JFrame("Connection Array");
        parent.setLayout(new BorderLayout());
        parent.add(this, BorderLayout.CENTER);
        parent.setResizable(false);
        explorer = new JPanel();
        parent.add(explorer, BorderLayout.EAST);
        setPreferredSize(new Dimension(400, 400));
        explorer.setPreferredSize(new Dimension(200, 400));
        explorer.setBackground(Color.GRAY);
        setBackground(Color.BLACK);
        parent.add(CaseDisplay = new JLabel("The Current Case: {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}") {{
            setPreferredSize(new Dimension(30, 30));
        }}, BorderLayout.SOUTH);
        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
        explorer.setLayout(new GridLayout(0, 2));
        explorer.add(new JLabel("Corner Points:"));
        explorer.add(new JTextField("6") {{
            addActionListener(e -> setupPoints(Integer.parseInt(getText()), BetweenNumber));
        }});
        explorer.add(new JLabel("Between Points:"));
        explorer.add(new JTextField("1") {{
            addActionListener(e -> setupPoints(EdgeNumber, Integer.parseInt(getText())));
        }});
        explorer.add(new JLabel("Cases Amount:"));
        explorer.add(CaseCounter = new JLabel(""));

        explorer.add(new JLabel("Case:"));
        explorer.add(CaseChooser = new JTextField("0") {{
            addActionListener(e -> recalculateCornerToggles(CaseNumber = Integer.parseInt(getText())));
        }});
        explorer.add(new JButton("Last-") {{
            addActionListener(e -> {
                recalculateCornerToggles(--CaseNumber);
                CaseChooser.setText("" + CaseNumber);
            });
        }});
        explorer.add(new JButton("Next Case") {{
            addActionListener(e -> {
                recalculateCornerToggles(++CaseNumber);
                CaseChooser.setText("" + CaseNumber);
            });
        }});
        explorer.add(new JLabel("Number Offset:"));
        explorer.add(new JTextField("0") {{
            addActionListener(e -> {
                NumberOffset = Integer.parseInt(getText());
                GeneratorGUI.this.repaint();
            });
        }});
        explorer.add(LinesB = new JRadioButton("Lines", true) {{
            addActionListener(e -> {
                TrisB.setSelected(!TrisB.isSelected());
                connectionmode = TrisB.isSelected() ? 2 : 1;
                GeneratorGUI.this.repaint();
            });
        }});
        explorer.add(TrisB = new JRadioButton("Triangles", false) {{
            addActionListener(e -> {
                LinesB.setSelected(!LinesB.isSelected());
                connectionmode = LinesB.isSelected() ? 1 : 2;
                GeneratorGUI.this.repaint();
            });
        }});
        explorer.add(new JButton("Reset Case") {{
            addActionListener(e -> {
                CaseArray[CaseNumber].clear();
                updateConTogglesByStorage();
                CaseDisplay.setText("The Current Case: " + CaseArray[CaseNumber].toString());
            });
        }});
        explorer.add(new JButton("Export Data") {{
            addActionListener(e -> {
                System.out.println("Exporting");
                try {
                    exportData();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }});
        explorer.add(new JButton("Load File") {{
            addActionListener(e -> {
                System.out.println("Exporting");
                try {
                    loadfile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }});
        explorer.add(new JButton("Save File") {{
            addActionListener(e -> {
                System.out.println("Exporting");
                try {
                    savefile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }});
        setupPoints(EdgeNumber, BetweenNumber);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Vector2 mouselocation = new Vector2(e.getPoint()).sub(10, 10);
//                System.out.println(mouselocation);
                for (int i = 0; i < connectiontoggles.size(); i++) {
                    Vector2 connectionpoint = connectionpoints.get(i);
                    if (connectionpoint.sub(mouselocation).magnitude() <= 10) {
//                        System.out.println("Clicked on a Corner Point.");
                        //cornertoggles.set(i, !cornertoggles.get(i));
                        connectiontoggles.set(i, true);
                        addConnectionpoint(i);
                        repaint();
                    }
                }
            }
        });
    }

    private ArrayList<Integer>[] CaseArray;

    private int NumberOffset = 0;
    private int EdgeNumber = 6;
    private int BetweenNumber = 1;
    private long MaxCases = 0;

    private final ArrayList<Boolean> cornertoggles = new ArrayList<>();
    private final ArrayList<Vector2> cornerpoints = new ArrayList<>();
    private final ArrayList<Vector2> connectionpoints = new ArrayList<>();
    private final ArrayList<Boolean> connectiontoggles = new ArrayList<>();

    public void addConnectionpoint(int Index) {
        CaseArray[CaseNumber].add(Index);
        CaseDisplay.setText("The Current Case: " + CaseArray[CaseNumber].toString());
    }

    private byte[] turntoBytes(Integer num, int bytesc) {
        byte[] bytes = new byte[bytesc];
        for (int i = 0; i < bytesc; i++) {
            Integer portionnumber = num & (0b1111_1111 << (i * 8));
            bytes[i] = portionnumber.byteValue();
        }
        return bytes;
    }

    private void savefile() throws IOException, NullPointerException {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().contains(".mca")) {
                    return true;
                }
                return !f.isFile();
            }

            @Override
            public String getDescription() {
                return ".mca";
            }
        });
        jFileChooser.showDialog(null, "Save to");
        File selectedFile = jFileChooser.getSelectedFile();
        if (selectedFile == null) {
            throw new FileNotFoundException("This File does not Exist");
        }
        String absolutepath = selectedFile.getAbsolutePath();
        if (!absolutepath.contains(".")) {
            //System.out.println("adding onto");
            absolutepath = absolutepath.concat(".mca");
        }
        System.out.println(absolutepath);
        FileOutputStream fileOutputStream = new FileOutputStream(absolutepath);
        fileOutputStream.write(109);
        fileOutputStream.write(99);
        fileOutputStream.write(97);
        fileOutputStream.write(turntoBytes(EdgeNumber, 2));
        fileOutputStream.write(turntoBytes(BetweenNumber, 2));
        for (ArrayList<Integer> integers : CaseArray) {
            fileOutputStream.write(turntoBytes(integers.size(), 1));
            for (Integer integer : integers) {
                fileOutputStream.write(turntoBytes(integer, 2));
            }
        }
        fileOutputStream.close();
    }

    private int bitearr_to_int(byte[] arr) {
        int accumulated = 0;
        for (int i = arr.length-1; i >= 0; i--) {
            accumulated |= (arr[i] & 0xff) << 8*i;
        }
        return accumulated;
    }

    private void loadfile() throws IOException {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().contains(".mca")) {
                    return true;
                }
                return !f.isFile();
            }

            @Override
            public String getDescription() {
                return ".mca";
            }
        });
        jFileChooser.showDialog(null, "Load File");
        File selectedFile = jFileChooser.getSelectedFile();
        if (selectedFile == null) {
            throw new FileNotFoundException("This File does not Exist");
        }
        if (!selectedFile.exists()) {
            throw new FileNotFoundException("This File does not Exist");
        }
        FileInputStream fileInputStream = new FileInputStream(selectedFile.getAbsolutePath());
        if (new String(fileInputStream.readNBytes(3)).equals("mca")) {
            System.out.println("This is a true MCA file");
        }
        EdgeNumber = bitearr_to_int(fileInputStream.readNBytes(2));
        System.out.println(EdgeNumber);
        BetweenNumber = bitearr_to_int(fileInputStream.readNBytes(2));
        System.out.println(BetweenNumber);
        setupPoints(EdgeNumber,BetweenNumber);
        for (int i = 0; i < Math.pow(2,EdgeNumber); i++) {
            ArrayList<Integer> thiscase = CaseArray[i];
            int caseentries = bitearr_to_int(fileInputStream.readNBytes(1));
            System.out.println(caseentries);
            for (int j = 0; j < caseentries; j++) {
                int casepoint = bitearr_to_int(fileInputStream.readNBytes(2));
                thiscase.add(casepoint);
            }
        }
        //byte[] allbytes = fileInputStream.readAllBytes();
        fileInputStream.close();
    }

    public void updateConTogglesByStorage() {
        ArrayList<Integer> Connections = CaseArray[CaseNumber];
        for (int i = 0; i < connectiontoggles.size(); i++) {
            connectiontoggles.set(i, false);
        }
        for (Integer connection : Connections) {
            connectiontoggles.set(connection - NumberOffset, true);
        }
        repaint();
    }

    public void exportData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\ExportData.txt"));
        writer.write("{");
        writer.newLine();
        for (ArrayList<Integer> integers : CaseArray) {
            writer.write(integers.toString().replaceAll("\\[", " {").replaceAll("]", "},"));
            writer.newLine();
        }
        writer.write("}");
        writer.close();
    }

    public void recalculateCornerToggles(int caseNumber) {
        CaseNumber = (int) Math.max(Math.min(CaseNumber, MaxCases - 1), 0);
        CaseDisplay.setText("The Current Case: " + CaseArray[CaseNumber].toString());
        updateConTogglesByStorage();
//        System.out.println(caseNumber);
//        System.out.println(Integer.toBinaryString(caseNumber));
        for (int i = 0; i < EdgeNumber; i++) {
//            System.out.println(1 << i);
//            System.out.println(Integer.toBinaryString(1 << i));
            if ((CaseNumber & (1 << i)) == (1 << i)) {
//                System.out.println("should be true?");
                cornertoggles.set(i, true);
            } else {
                cornertoggles.set(i, false);
            }
        }

        repaint();
    }

    private void resetCaseArray() {
        CaseArray = new ArrayList[(int) MaxCases];
        for (int i = 0; i < (int) MaxCases; i++) {
            CaseArray[i] = new ArrayList<Integer>();
        }
        CaseDisplay.setText("The Current Case: " + CaseArray[CaseNumber].toString());
    }

    public void setupPoints(int Edges, int Betweenpoints) {
        MaxCases = (long) (Math.pow(2, Edges));

        CaseNumber = 0;
        EdgeNumber = Edges;
        BetweenNumber = Betweenpoints;
        cornertoggles.clear();
        cornerpoints.clear();
        connectionpoints.clear();
        connectiontoggles.clear();
        resetCaseArray();
        double anglebetween = 360d / (double) Edges;
        for (int i = 0; i < 360; i += anglebetween) {
            cornerpoints.add(Vector2.Transform(new Vector2(150, 0), i).add(200 - 10, 200 - 10));
            cornertoggles.add(false);
        }

        double stepsize = 1d / (Betweenpoints + 1);

        for (int i = 0; i < cornerpoints.size(); i++) {
            Vector2 a = cornerpoints.get(i), b = cornerpoints.get((i + 1) % cornerpoints.size());
            connectionpoints.add(a);
            connectiontoggles.add(false);
            for (int e = 1; e <= Betweenpoints; e++) {
                connectionpoints.add(a.lerp(b, stepsize * e));
                connectiontoggles.add(false);
            }
        }
        CaseCounter.setText("0-" + (long) (Math.pow(2, Edges) - 1));
        repaint();
    }

    private void drawTriangle(Graphics2D g2d, Vector2 a, Vector2 b, Vector2 c) {
        int[] xpoints = new int[]{(int) a.x + 10, (int) b.x + 10, (int) c.x + 10};
        int[] ypoints = new int[]{(int) a.y + 10, (int) b.y + 10, (int) c.y + 10};
        g2d.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2d);
        //g2d.setColor(Color.white);
        //System.out.println(new Vector2(getWidth(),getHeight()));
        g2d.drawOval(50, 50, 300, 300);
//        g2d.drawLine(getWidth()/2,0,getWidth()/2,getHeight());
        g2d.setColor(Color.RED);
        g2d.fillRect(getWidth() / 2, 0, 1, getHeight());
        g2d.fillRect(0, getHeight() / 2, getWidth(), 1);
        g2d.setColor(Color.BLUE);
        g2d.fillOval(getWidth() / 2 - 1, getHeight() / 2 - 1, 3, 3);
        g2d.setColor(Color.white);
//        g2d.drawLine(0,getHeight()/2,getWidth(),getHeight()/2);

        for (int i = 0; i < cornerpoints.size(); i++) {
            Vector2 cornerpoint = cornerpoints.get(i);
            Vector2 cornerpoint2 = cornerpoints.get((i + 1) % cornerpoints.size());
            g2d.drawLine((int) cornerpoint.x + 10,
                    (int) cornerpoint.y + 10,
                    (int) cornerpoint2.x + 10,
                    (int) cornerpoint2.y + 10);
        }

        for (int i = 0; i < connectionpoints.size(); i++) {
            Vector2 connectionpoint = connectionpoints.get(i);
            boolean connectiontoogle = connectiontoggles.get(i);
            //g2d.setColor(Color.white);
            /*g2d.drawLine((int) connectionpoint.x + 10,
                    (int) connectionpoint.y + 10,
                    (int) connectionpointother.x + 10,
                    (int) connectionpointother.y + 10);*/
            g2d.setColor(connectiontoogle ? Color.red : Color.white);
            g2d.fillOval((int) connectionpoint.x, (int) connectionpoint.y, 20, 20);
            g2d.setColor(Color.black);
            //g2d.drawString(""+i+":"+Math.round(cornerpoint.x)+"/"+Math.round(cornerpoint.y),(int) cornerpoint.x, (int) cornerpoint.y);
            //g2d.drawString("" + (i + numberoffset), (int) connectionpoint.x + 4, (int) connectionpoint.y + 14);
        }

        g2d.setColor(new Color(0, 0, 255, 150));
        if (connectionmode == 1) {
            // Lines
            g2d.setStroke(new BasicStroke(3));
            if (CaseArray[CaseNumber].size() >= 2) {
                for (int index = 0; index < CaseArray[CaseNumber].size() / 2; index++) {
                    System.out.println(index);
                    g2d.drawLine(
                            (int) connectionpoints.get(CaseArray[CaseNumber].get(index * 2)).x + 10,
                            (int) connectionpoints.get(CaseArray[CaseNumber].get(index * 2)).y + 10,
                            (int) connectionpoints.get(CaseArray[CaseNumber].get(index * 2 + 1)).x + 10,
                            (int) connectionpoints.get(CaseArray[CaseNumber].get(index * 2 + 1)).y + 10
                    );
                }
            }
        } else if (connectionmode == 2) {
            // Triangles
            if (CaseArray[CaseNumber].size() >= 3) {
                for (int index = 0; index < CaseArray[CaseNumber].size() / 3; index++) {
                    System.out.println(index);
                    drawTriangle(
                            g2d,
                            connectionpoints.get(CaseArray[CaseNumber].get(index * 3)),
                            connectionpoints.get(CaseArray[CaseNumber].get(index * 3 + 1)),
                            connectionpoints.get(CaseArray[CaseNumber].get(index * 3 + 2))
                    );
                }
            }
        }


        g2d.setStroke(new BasicStroke(1));
        // Draw the Corner Points
        for (int i = 0; i < cornerpoints.size(); i++) {
            Vector2 cornerpoint = cornerpoints.get(i);
            g2d.setColor(cornertoggles.get(i) ? Color.magenta : Color.green);
            g2d.fillOval((int) cornerpoint.x + 3, (int) cornerpoint.y + 3, 14, 14);
        }

        g2d.setColor(Color.black);
        for (int i = 0; i < cornerpoints.size(); i++) {
            Vector2 cornerpoint = cornerpoints.get(i);
            //g2d.fillOval((int) cornerpoint.x+5, (int) cornerpoint.y+5, 10, 10);
            g2d.drawString("" + (i + NumberOffset), (int) cornerpoint.x + 4, (int) cornerpoint.y + 14);
        }

        g2d.setColor(Color.CYAN);
        for (int i = 0; i < connectionpoints.size(); i++) {
            Vector2 connectionpoint = connectionpoints.get(i).sub(getWidth() / 2d - 5, getHeight() / 2d - 14).mul(1.2).add(getWidth() / 2d, getHeight() / 2d);
            g2d.drawString("" + (i + NumberOffset), (int) connectionpoint.x, (int) connectionpoint.y);
        }

        //repaint();
    }
}
