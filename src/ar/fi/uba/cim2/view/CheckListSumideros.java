package ar.fi.uba.cim2.view;

import ar.fi.uba.cim2.model.Application;
import ar.fi.uba.cim2.model.Fuente;
import ar.fi.uba.cim2.model.Tanques;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0 04/24/99
 */
public class CheckListSumideros extends JFrame {
	JFrame frame;
	public CheckListSumideros() {
		super("Listado de Sumideros");

		 JLabel label = new JLabel("<html><h1 style='font-size:18px;'>Haga Click en los Sumideros que desea activar</h1></html>");
		    TitledBorder titled = new TitledBorder("Importante");
		    label.setBorder(titled);
//		    add(label);
		
		final JList list = new JList(createData());
		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EmptyBorder(0, 4, 0, 0));
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = list.locationToIndex(e.getPoint());
				Fuente item = (Fuente) list.getModel()
						.getElementAt(index);
				item.setSelected(!item.isSelected());
				Rectangle rect = list.getCellBounds(index, index);
				list.repaint(rect);
			}
		});
		JScrollPane sp = new JScrollPane(list);

		final JTextArea textArea = new JTextArea(12, 10);
		JScrollPane textPanel = new JScrollPane(textArea);
		JButton printButton = new JButton("Mostrar Capacidad");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListModel model = list.getModel();
				int n = model.getSize();
				int total=0;
				for (int i = 0; i < n; i++) {
					Fuente item = (Fuente) model.getElementAt(i);
					if (item.isSelected()) {
						textArea.append(item.toString());
						textArea.append(", Consumo:"+item.getCapacidad()+" Lts");
						textArea.append(System.getProperty("line.separator"));
						total+=item.getCapacidad();
					}
				}
				textArea.append(System.getProperty("line.separator"));
				textArea.append("Consumo Total : "+total);
			}
		});
		JButton clearButton = new JButton("Utilizar Agua Tratada");
		 frame=this;
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Tanques tq=Application.getInstance().getTanques();
				 Application.getInstance().getEstadisticas().setTratada(tq.getTratada());
        		 tq.setTratada(0.0);
			String	initialText = "<html>\n"+ 
		  				   
	        		"<TABLE BGCOLOR=\"#1d1f21\" BORDER=\"1\" CELLPADDING=\"7\" CELLSPACING=\"0\">"+
	        		"<tr BGCOLOR=\"#cd6a51\"><th colspan=\"2\"><FONT COLOR=\"#ffffff\" SIZE=\"4\" FACE=\"ARIAL\">Tanques de Agua Tratada</FONT></th></tr>"+
	        		"<tr><td ><FONT COLOR=\"#dfc48c\" SIZE=\"4\">Capacidad del Tanque</FONT></td><td ALIGN=\"center\"><FONT COLOR=\"#dfc48c\" SIZE=\"4\" FACE=\"ARIAL\">"+ Application.getInstance().getTanques().getTratadaMax() +" Lts</FONT></td></tr>"+
	        		"<tr><td><FONT COLOR=\"#dfc48c\" SIZE=\"4\">Agua Tratada</FONT></td><td ALIGN=\"center\"><FONT COLOR=\"#dfc48c\" SIZE=\"4\" FACE=\"ARIAL\">"+ Application.getInstance().getTanques().getTratada() +" Lts</FONT></td></tr>"+
	        		"</table>";
     	    Application.getInstance().getFrame().getTheLabel2().setText(initialText);
     	   
     	   frame.dispose();
			}
		});
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(printButton);
		panel.add(clearButton);
		getContentPane().add(label, BorderLayout.NORTH);
		getContentPane().add(sp, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.EAST);
		getContentPane().add(textPanel, BorderLayout.SOUTH);
	}

	private Fuente[] createData() {
        Fuente[] items = new Fuente[10];
        items[0] = new Fuente("Casa 1",1000);
        items[1] = new Fuente("Casa 2",500);
        items[2] = new Fuente("Casa 3",500);
        items[3] = new Fuente("Casa 4",500);
        items[4] = new Fuente("Casa 5",100);
        items[5] = new Fuente("Casa 6",300);
        items[6] = new Fuente("Casa 7",500);
        items[7] = new Fuente("Casa 8",500);
        items[8] = new Fuente("Casa 9",500);
        items[9] = new Fuente("Casa 10",500);
		List<Fuente> fuentes= new ArrayList<Fuente>();
		for (int i = 0; i < items.length; i++) {
			fuentes.add( items[i]);
		}
		Application.getInstance().setFuentes(fuentes);
		return items;
	}





	public static void main(String args[]) {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (Exception evt) {}

		CheckListSumideros frame = new CheckListSumideros();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(700,450);
		frame.setVisible(true);
	}
}





