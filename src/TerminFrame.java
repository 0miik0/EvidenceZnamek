import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TerminFrame extends JFrame{
    private JTextArea txtArea;
    private JPanel panelMain;
    private JTable table;
    private JButton prumerBtn;
    private JButton NPBtn;
    private List<TerminZkouseni> seznamTerminu = new ArrayList<>();

    public TerminFrame(){
        setContentPane(panelMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Termíny zkoušení");
        setSize(500,500);

        seznamTerminu.add(new TerminZkouseni("A", 1, LocalDate.of(2020, 10, 1)));
        seznamTerminu.add(new TerminZkouseni("B", 2, LocalDate.of(2020, 11, 2)));
        seznamTerminu.add(new TerminZkouseni("C", 3, LocalDate.of(2020, 12, 3)));

        prumerBtn.addActionListener(e -> getPrumer());
        NPBtn.addActionListener(e -> terminZnamkaJedna());
        initMenu();
        display();
        renderTable();
    }
    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenuItem nastavZnamkyItem = new JMenuItem("Nastav znamky...");
        menuBar.add(nastavZnamkyItem);
        nastavZnamkyItem.addActionListener(e -> nastavZnamky());

        JMenuItem novyTerminItem = new JMenuItem("Vytvořit termín");
        menuBar.add(novyTerminItem);
        novyTerminItem.addActionListener(e -> novyTermin());

        JMenuItem jeVetsiItem = new JMenuItem("Je lepší než...");
        menuBar.add(jeVetsiItem);
        jeVetsiItem.addActionListener(e -> jeLepsi());
    }
    public void getPrumer(){
        int sum = 0;
        for(TerminZkouseni terminZkouseni : seznamTerminu){
            sum += terminZkouseni.getZnamka();
        }
        int prumer = sum / seznamTerminu.size();

        JOptionPane.showMessageDialog(this,"Průměr známek je: " + prumer);
    }
    public void novyTermin(){
        JTextField temaTxt = new JTextField();
        JTextField znamkaTxt = new JTextField();
        JTextField datumTxt = new JTextField();

        Object[] fields ={
            "Téma: ", temaTxt,
            "Znamka: ", znamkaTxt,
            "Datum: ", datumTxt,
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Vložte informace: ", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String tema = temaTxt.getText();
            int znamka = Integer.parseInt(znamkaTxt.getText());
            LocalDate datum = LocalDate.parse(datumTxt.getText());
            seznamTerminu.add(new TerminZkouseni(tema, znamka, datum));
            txtArea.append(tema + " " + znamka + " " + datum + "\n");
        }
    }
    public void terminZnamkaJedna(){
        JTextField temaTxt = new JTextField();
        JTextField znamkaTxt = new JTextField();
        JTextField datumTxt = new JTextField();

        znamkaTxt.setText(String.valueOf(1));

        datumTxt.setText(String.valueOf(LocalDate.now()));
        znamkaTxt.setEditable(false);
        datumTxt.setEditable(false);

        Object[] fields ={
                "Téma: ", temaTxt,
                "Znamka: ", znamkaTxt,
                "Datum: ", datumTxt,
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Vložte informace: ", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String tema = temaTxt.getText();
            int znamka = Integer.parseInt(znamkaTxt.getText());
            LocalDate datum = LocalDate.parse(datumTxt.getText());
            seznamTerminu.add(new TerminZkouseni(tema, znamka, datum));
            for(TerminZkouseni termin : seznamTerminu){
                txtArea.append(termin.getTema() + " " + termin.getZnamka() + " " + termin.getDatum() + "\n");
            }
        }
    }
    public void nastavZnamky(){
        JTextField znamkaTxt = new JTextField();

        Object[] fields ={
                "Nastav všechny známky na: ", znamkaTxt,
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Nastav známky", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            int novaZnamka = Integer.parseInt(znamkaTxt.getText());
            for(TerminZkouseni termin : seznamTerminu){
                termin.setZnamka(novaZnamka);
                display();
            }
        }
    }
    public void jeLepsi(){
        JTextField znamkaTxt = new JTextField();

        Object[] fields ={
                "Nastav limit: ", znamkaTxt,
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Zadejte limit: ", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            int zadanyLimit = Integer.parseInt(znamkaTxt.getText());
            for(TerminZkouseni termin : seznamTerminu){
                if(termin.getZnamka() < zadanyLimit){
                    JOptionPane.showMessageDialog(this, "Známka je lepší než zadaný limit.");
                }else{
                    JOptionPane.showMessageDialog(this, "Známka není lepší než zadaný limit.");
                }
            }
        }

    }
    public void display(){
        for(TerminZkouseni zkouseni : seznamTerminu){
            txtArea.append(zkouseni.getTema() + " " + zkouseni.getZnamka() + " " + zkouseni.getDatum() + "\n");
        }
    }
    class Tabulka extends AbstractTableModel{
        @Override
        public int getRowCount() {
            return seznamTerminu.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            TerminZkouseni termin = seznamTerminu.get(rowIndex);
            return switch(columnIndex){
                case 0 -> termin.getTema();
                case 1 -> termin.getZnamka();
                case 2 -> termin.getDatum();
                default -> null;
            };
        }
    }
    public void renderTable(){
        table.setModel(new Tabulka());
    }
    public static void main(String[] args) {
        TerminFrame frame = new TerminFrame();
        frame.setVisible(true);
    }
}
