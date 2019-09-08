import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;
import java.util.Random;
import java.util.Vector;


class MainWindow extends JFrame{
    MainWindow(){

        super("Pareto"); //set title name
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Set close settings



        Object[] ColomnName = new String[]{
                "Router brand",
                "Counts of ethernet ports",
                "Speed of ethernet ports",
                "Counts of usb ports",
                "Weight in gramm",
                "Price in rub"
        };

        Object[][] data = {
                {"D-link", new Integer(6) ,new Integer(150),new Integer(2),new Integer(250),new Integer(450)},
                {"TP-link",new Integer(3),new Integer(125),new Integer(1),new Integer(300),new Integer(333)},
                {"Zyxel",new Integer(4),new Integer(125),new Integer(2),new Integer(425),new Integer(375)},
                {"Samsung",new Integer(3),new Integer(120),new Integer(1),new Integer(250),new Integer(400)},
                {"Toshiba",new Integer(2),new Integer(100),new Integer(2),new Integer(300),new Integer(175)},
                {"Yota",new Integer(2),new Integer(100),new Integer(2),new Integer(350),new Integer(195)},
                {"Asus",new Integer(6),new Integer(175),new Integer(2),new Integer(233),new Integer(450)},
                {"Xiaomi",new Integer(3),new Integer(125),new Integer(1),new Integer(325),new Integer(350)},
                {"Tenda",new Integer(1),new Integer(400),new Integer(1),new Integer(150),new Integer(275)},
                {"Netgear",new Integer(4),new Integer(125),new Integer(3),new Integer(370),new Integer(370)}
        };

        DefaultTableModel tableModel = new DefaultTableModel();//create table model
        tableModel.setColumnIdentifiers(ColomnName);
        for (int i=0; i < data.length; i++) //fill model table
            tableModel.addRow(data[i]);
        JTable table = new JTable(tableModel);//create table on table model and add it to component JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        JTextArea textArea = new JTextArea("",30,30); //create textArea and add it to component JScrollPane
        JScrollPane scrollPane1 = new JScrollPane(textArea);

        JButton button1 = new JButton("Regenerate new value");//button creation
        JButton button2 = new JButton("Calculate");

        JPanel panel = new JPanel(); //create panel and add on it components
        setContentPane(panel);
        panel.setLayout(null);
        panel.setLayout(new FlowLayout());
        panel.add(scrollPane);
        panel.add(scrollPane1);
        panel.add(button2);
        panel.add(button1);

        button1.setActionCommand("button 1 was pressed");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                for (int i = 0; i < 10; i++){
                    table.getModel().setValueAt(rand.nextInt(6)+1,i,1);
                    table.getModel().setValueAt((rand.nextInt(40)+5)*10,i,2);
                    table.getModel().setValueAt(rand.nextInt(4),i,3);
                    table.getModel().setValueAt((rand.nextInt(50)+10)*10,i,4);
                    table.getModel().setValueAt((rand.nextInt(30)+10)*10,i,5);
                }
            }
        });

        button2.setActionCommand("button 2 was pressed");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(null);
                Vector<String> vectP = new Vector(); //information for table
                Vector<String> vectQ = new Vector();
                for (int k = 0; k < 10; k++) {
                    for (int i = k; i < 10; i++) {
                        if ((k == 9 ))
                            break;
                        if  ((i == k) && (k < 9))
                            i++;
                        if (((Integer) table.getModel().getValueAt(k, 1) >= (Integer)table.getModel().getValueAt(i, 1)) && ((Integer)table.getModel().getValueAt(k, 2) >= (Integer)table.getModel().getValueAt(i, 2)) &&
                        ((Integer)table.getModel().getValueAt(k, 3) >= (Integer)table.getModel().getValueAt(i, 3)) && ((Integer)table.getModel().getValueAt(k, 4) <= (Integer)table.getModel().getValueAt(i, 4)) &&
                        ((Integer)table.getModel().getValueAt(k, 5) <= (Integer)table.getModel().getValueAt(i, 5))){
                            textArea.append(table.getModel().getValueAt(k, 0) + " dominate " + table.getModel().getValueAt(i, 0) + "\n");
                            if (!vectP.contains(table.getModel().getValueAt(k, 0)))
                            vectP.add((String)table.getModel().getValueAt(k, 0));
                        }
                        if (((Integer)table.getModel().getValueAt(i, 1) >=  (Integer)table.getModel().getValueAt(k, 1)) && ((Integer)table.getModel().getValueAt(i, 2) >= (Integer)table.getModel().getValueAt(k, 2)) &&
                            ((Integer)table.getModel().getValueAt(i, 3) >= (Integer)table.getModel().getValueAt(k, 3)) && ((Integer)table.getModel().getValueAt(i, 4) <=  (Integer)table.getModel().getValueAt(k, 4)) &&
                            ((Integer)table.getModel().getValueAt(i, 5) <= (Integer)table.getModel().getValueAt(k, 5))){
                                textArea.append(table.getModel().getValueAt(i, 0) + " dominate " + table.getModel().getValueAt(k, 0) + "\n");
                                if (!vectP.contains(table.getModel().getValueAt(i, 0)))
                                    vectP.add((String)table.getModel().getValueAt(i, 0));
                            }
                        else textArea.append(table.getModel().getValueAt(k,0) + " incomparable " + table.getModel().getValueAt(i,0) + "\n");
                    }
                }
                for (int i = 0; i < 10; i++)
                if (!vectP.contains(table.getModel().getValueAt(i, 0))) {
                    vectQ.add((String)table.getModel().getValueAt(i,0));
                }
                textArea.append("P = " + vectP + "\n" + "Q = " + vectQ);

            }
        });


        pack(); //auto layout components
    }
}
