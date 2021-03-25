package poo.polinomi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**@author Marco Greco */

public class Applicazione {


	public static void main(String[]args) {
		JFrame f = new FinestraApplicazione();
		f.setVisible(true);
	}

}


class FinestraApplicazione extends JFrame {
	int countSelezionati;
	ArrayList<Polinomio> listaPolinomi=new ArrayList<>();
	ArrayList<JCheckBox> listaCheck=new ArrayList<>();
	ArrayList<JMenuItem> comandiPolinomi = new ArrayList<>();
	ArrayList<Integer> first = new ArrayList<>(10); //Mi ricordo il primo check che spunto per la differenza
	JTextArea risultato;
	String input;
	JPanel poliPanel,risPanel;
	JScrollPane sp;
	JMenuItem inserisci,elimina,salvaconnome,
	          carica,esci,somma,differenza,
	          prodotto,derivata,valore,about,version;
	JMenu menucomandi,menuFile,menuhelp;
	String NUM = "[1-9]+";
	String VAR = "([xX]|[xX][//^]"+NUM+")";
	String OPER = "[\\+\\-]";
	String MONOMIO = "("+NUM+VAR+"?|"+VAR+")";
	String POLINOMIO ="[\\+\\-]?"+ MONOMIO+"("+OPER+MONOMIO+")*";
	String DBL = "([\\-]?[0-9]+|[0-9]+[.][0-9]+)";

	AscoltatoreEventi ascoltatore = new AscoltatoreEventi();
	
	public FinestraApplicazione() {
		setTitle("RISOLUTORE POLINOMI");
		setSize(700,500);
		setLocation(570,300);
		setResizable(false);
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		
		
        addWindowListener( new WindowAdapter() {
	        public void windowClosing(WindowEvent e){
	       	 uscita();
	        }
	     } );
		
		
		//Pannello dedicato ai Polinomi
		poliPanel = new JPanel();
		JLabel titoloPolinomio=new JLabel("POLINOMI:");
		poliPanel.add(titoloPolinomio);
		poliPanel.setLayout(new BoxLayout(poliPanel, BoxLayout.Y_AXIS));
		poliPanel.setBackground(Color.YELLOW);
		sp = new JScrollPane(poliPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp,BorderLayout.EAST);
		
		
		//Gestione Menù
		JMenuBar m = new JMenuBar();
		setJMenuBar(m);

		//Pannello dedicato al risultato
		risPanel = new JPanel();
		risPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		risPanel.setBackground(Color.ORANGE);
		add(risPanel,BorderLayout.CENTER);
		risultato = new JTextArea(10,30);
		//Inserisco una JScrollPane alla textarea in modo da non farla espadere all'aggiunta di un testo, per eleganza (grafica) elimino le scrollbar
		JScrollPane sp2=new JScrollPane(risultato,ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(350,200));
		risultato.setFont(new Font("Italic", Font.BOLD, 15));
		risultato.append("\n                               CHE FARE ?"
				+ "\n\n -Inserisci Polinomi (menù comandi)\n\n -Seleziona uno o due Polinomi---->\n\n -Scegli un'operazione dal menù comandi");
		risultato.setEditable(false);
		risPanel.add(sp2);

		//IN FILE
		menuFile = new JMenu("File"); m.add(menuFile);
		salvaconnome = new JMenuItem("Salva con nome"); menuFile.add(salvaconnome);
		carica = new JMenuItem("Carica"); menuFile.add(carica);
		menuFile.addSeparator();
		esci = new JMenuItem("Esci"); menuFile.add(esci);
		salvaconnome.addActionListener(ascoltatore);
		carica.addActionListener(ascoltatore);
		esci.addActionListener(ascoltatore);

		//IN COMANDI
		menucomandi = new JMenu("Comandi"); m.add(menucomandi);
		inserisci = new JMenuItem("Inserisci Polinomi");menucomandi.add(inserisci);
		elimina= new JMenuItem("Elimina Polinomio/i");menucomandi.add(elimina);
		menucomandi.addSeparator();
		somma= new JMenuItem("Somma");menucomandi.add(somma);
		comandiPolinomi.add(somma);
		differenza = new JMenuItem("Differenza");menucomandi.add(differenza);
		comandiPolinomi.add(differenza);
		prodotto = new JMenuItem("Prodotto");menucomandi.add(prodotto);
		comandiPolinomi.add(prodotto);
		menucomandi.addSeparator();
		derivata = new JMenuItem("Derivata");menucomandi.add(derivata);
		comandiPolinomi.add(derivata);
		valore = new JMenuItem("Valore");menucomandi.add(valore);
		comandiPolinomi.add(valore);
		inserisci.addActionListener(ascoltatore);
		elimina.addActionListener(ascoltatore);
		for(JMenuItem jmi:comandiPolinomi) {
			jmi.addActionListener(ascoltatore);
		}
		
		//ABOUT
		menuhelp = new JMenu("Help"); m.add(menuhelp);
		about=new JMenuItem("About");menuhelp.add(about);
		about.addActionListener(ascoltatore);
		version=new JMenuItem("Versione 1.0");menuhelp.add(version);//Scopo decorativo
		version.setEnabled(false);
		
		nascondiComandi();
	}//costruttore


	//Se gli passo true attiva i comandi relativi a più polinomi e disattiva quelli per un solo polinomio, false viceversa.
	private void comandiPolinomio(boolean visibili) {
		if(visibili) {
			somma.setEnabled(true);
			differenza.setEnabled(true);
			prodotto.setEnabled(true);
			derivata.setEnabled(false);
			valore.setEnabled(false);
		}
		else {
			somma.setEnabled(false);
			differenza.setEnabled(false);
			prodotto.setEnabled(false);
			derivata.setEnabled(true);
			valore.setEnabled(true);
		}
	}

	//Nascondi tutti i comandi
	private void nascondiComandi() {
		somma.setEnabled(false);
		differenza.setEnabled(false);
		prodotto.setEnabled(false);
		derivata.setEnabled(false);
		valore.setEnabled(false);
	}




	private Monomio creaMonomio(String monomio) {
		StringTokenizer st = new StringTokenizer(monomio,"xX^",true);
		String token;
		int coef=1;
		int grad=0;
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			if(token.equalsIgnoreCase("x")) { 
				if(st.hasMoreTokens()) {
					st.nextToken();
					grad=Integer.parseInt(st.nextToken());
				}
				else {grad=1;break;}
			}
			else {
				if(st.hasMoreTokens()) {
					st.nextToken();
					if(st.hasMoreTokens()) {
						st.nextToken(); //^
						coef=Integer.parseInt(token);grad=Integer.parseInt(st.nextToken());break;
					}
					else { coef=Integer.parseInt(token);grad=1;}
				}
				else coef=Integer.parseInt(token);
			}
		}//while
		Monomio m = new Monomio(coef,grad);
		return m;
	}//creaMonomio
	
	
	
	private void creaPolinomio(String input) {
		StringTokenizer st= new StringTokenizer(input,"+-",true);
		String segno;
		Monomio monomio;
		String token;
		Polinomio poli=new PolinomioAL();
		while(st.hasMoreTokens()) {
			token =st.nextToken();
			if(token.equals("-")||token.equals("+")) {
				segno =token;
				token = st.nextToken();
				monomio = creaMonomio(token);
				if(segno.equals("-")) poli.add(monomio.mul(-1));
				else poli.add(monomio);
			}
			else {
				monomio = creaMonomio(token);
				poli.add(monomio);
			}

		}
		JCheckBox polinomioCheck = new JCheckBox(poli.toString());
		polinomioCheck.addActionListener(ascoltatore);
		polinomioCheck.setBackground(null);
		listaCheck.add(polinomioCheck);
		poliPanel.add(polinomioCheck);
		polinomioCheck.setSize(100,20);
		poliPanel.validate();
		validate();
		listaPolinomi.add(poli);
		
	}//creaPolinomio
	
	public void uscita() {
		int i=JOptionPane.showConfirmDialog(risPanel, "Vuoi salvare il contenuto prima di uscire ?");
    	if(i==JOptionPane.NO_OPTION) System.exit(0);
    	if(i==JOptionPane.YES_OPTION) salvaconnome.doClick();
	}//uscita


	private class AscoltatoreEventi implements ActionListener{
		int premuto;
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==inserisci) {
				for(;;) {
					input = JOptionPane.showInputDialog("Inserisci un Poliniomio");
					if(input==null) break; //pressione annulla
					if(input.matches(POLINOMIO)) {
						JOptionPane.showMessageDialog(null, "INSERITO CON SUCCESSO !");
						creaPolinomio(input);
					}
					else
						JOptionPane.showMessageDialog(null, "INSERIRE UN POLINOMIO VALIDO!");
				}
			}//e==inserisci

			for(JCheckBox c:listaCheck) {
				if(e.getSource()==c) {
					if(c.isSelected()) { first.add(listaCheck.indexOf((c)));countSelezionati++;}
					else {first.remove(first.indexOf(listaCheck.indexOf((c)))); countSelezionati--;}
					if(countSelezionati>=2) comandiPolinomio(true);
					else if(countSelezionati==1) comandiPolinomio(false);
					else nascondiComandi();
				}
			}

			if(e.getSource()==elimina) {
				boolean eliminabile=false;
				//Inserisco i jcheckbox da eliminare in una collezione e i relativi polinomi per ovviare al ConcurrentModificationException
				ArrayList<JCheckBox> checkToDelete = new ArrayList<>();
				ArrayList<Polinomio> poliToDelete = new ArrayList<>();
				for(JCheckBox c:listaCheck)
					if(c.isSelected()) {
						eliminabile = true;
						c.setVisible(false);
						//listaPolinomi.remove(listaCheck.indexOf(c));
						//listaCheck.remove(c);
						poliToDelete.add(listaPolinomi.get(listaCheck.indexOf(c)));
						checkToDelete.add(c);
						countSelezionati--;
					}
				for(JCheckBox j:checkToDelete) listaCheck.remove(j);
				for(Polinomio p:poliToDelete) listaPolinomi.remove(p);
				if(!eliminabile) JOptionPane.showMessageDialog(null, "SELEZIONA ALMENO UN POLINOMIO DA ELIMINARE!");
				nascondiComandi();
			}//e=elimina
			
			if(e.getSource()==about) {
				JFrame ab = new FinestraAbout();
				ab.setVisible(true);
			}
			
			
			if(e.getSource()==salvaconnome || e.getSource()==carica) {
				String nomefile=null;
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("File TXT", "txt");
				jfc.setFileFilter(filtro);
				int value;
				if(e.getSource()==carica) value = jfc.showOpenDialog(null);
				else value = jfc.showSaveDialog(null);
				if(value==JFileChooser.APPROVE_OPTION) {
					nomefile=jfc.getSelectedFile().getAbsolutePath();
					if(e.getSource()==salvaconnome) {
						
						PrintWriter pw;
						try {
							pw = new PrintWriter(new BufferedWriter(new FileWriter(nomefile)));
							for(Polinomio p:listaPolinomi) {
								pw.println(p.toString());
							}
							pw.close();
							JOptionPane.showMessageDialog(null,"Hai salvato il tuo operato sul file: " + nomefile);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Qualcosa è andato storto..");
							e1.printStackTrace();
						}
					}
					else {
						listaPolinomi.clear();
						for(JCheckBox jc:listaCheck) {
							jc.setVisible(false);
						}
						listaCheck.clear();
						countSelezionati=0;
						nascondiComandi();
						try {
							BufferedReader br = new BufferedReader(new FileReader(nomefile));
							String linea=null;
							for(;;) {
								linea=br.readLine();
								if(linea==null) break;
								creaPolinomio(linea);
							}
							br.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Qualcosa è andato storto.. Il file potrebbe essere corrotto");
							e1.printStackTrace();
						}
					}
				}
			}
			
            if(e.getSource()==esci ) {
            	uscita();
			}

			for(JMenuItem jmi:comandiPolinomi) {
				if(e.getSource()==jmi) {
					if(countSelezionati>=3) JOptionPane.showMessageDialog(null,"DAI NON ESAGERARE, SCEGLI SOLO DUE POLINOMI...");
					else {
						boolean sommato=false;//Utile per il prodotto
						Polinomio ris = new PolinomioAL();
						for(JCheckBox jc:listaCheck) {
							if(jc.isSelected()) {
								if(e.getSource()==somma) {
									ris=ris.add(listaPolinomi.get(listaCheck.indexOf(jc))); //perchè ritorna un polinomio
								}
								else if(e.getSource()==differenza) {
									if(listaCheck.indexOf(jc)==first.get(0)){
										ris=ris.add(listaPolinomi.get(first.get(0))); //perchè ritorna un polinomio
									}
									else{
										for(Monomio m:listaPolinomi.get(listaCheck.indexOf(jc))) {
											m=m.mul(-1); //perchè ritorna un monomio
											ris.add(m);
										}
									}
								}
								else if(e.getSource()==prodotto) {
									if(!sommato) {
										ris=ris.add(listaPolinomi.get(listaCheck.indexOf(jc)));
										sommato=true;
									}
									else ris=ris.mul(listaPolinomi.get(listaCheck.indexOf(jc)));
								}
								else if(e.getSource()==derivata) {
									ris=ris.add(listaPolinomi.get(listaCheck.indexOf(jc))).derivata();
								}
								else if(e.getSource()==valore) {
									risultato.setText(null);
									risultato.setFont(new Font("Italic", Font.BOLD, 25));
									for(;;) {
										String x= JOptionPane.showInputDialog("Inserisci il valore:");
										if(x.matches(DBL)) {
											risultato.append(" In "+String.format("%5.2f", Double.parseDouble(x))+" il Polinomio vale:"
													+ "\n\n       "+(listaPolinomi.get(listaCheck.indexOf(jc)).valore(Double.parseDouble(x))));
											break;
										}
										else JOptionPane.showMessageDialog(risPanel, "Inserisci un Valore valido per favore..");
									}
								}

								jc.setSelected(false);
								if(countSelezionati!=0) {
									countSelezionati--;
								}
							}
							premuto=comandiPolinomi.indexOf(jmi);
						}
						
						
						sommato=false;
						first.clear();//Azzero l'arraylist dove ricordo l'indice del primo check selezionato
						
						
						if(!(e.getSource()==valore)) {
							risultato.setText(null);
							risultato.setFont(new Font("Italic", Font.BOLD, 25));
							switch(premuto) {
							case 0: risultato.append("La somma è pari a:\n\n  "+ris.toString());break;
							case 1: risultato.append("La differenza è pari a:\n\n  "+ris.toString());break;
							case 2: risultato.append("Il prodotto è pari a:\n\n  "+ris.toString());break;
							case 3: risultato.append("La derivata è pari a:\n\n  "+ris.toString());break;
							}
							int i;
							do {
								i=JOptionPane.showConfirmDialog(poliPanel,"\n Vuoi inserirlo tra i polinomi ?","Scegli..",JOptionPane.YES_NO_OPTION);
								if(i==JOptionPane.YES_OPTION) {
									JCheckBox nuovo = new JCheckBox(ris.toString());
									poliPanel.add(nuovo);
									nuovo.setSize(100,20);
									poliPanel.validate();
									validate();
									derivata.validate();
									listaPolinomi.add(ris);
									listaCheck.add(nuovo);
									nuovo.setBackground(null);
									nuovo.addActionListener(this);

								}
								else if(i==JOptionPane.CLOSED_OPTION) JOptionPane.showMessageDialog(null, "Dai dimmi cosa fare..");
							}while(!(i==JOptionPane.YES_OPTION || i==JOptionPane.NO_OPTION));
						}

					}
					nascondiComandi();
				}//jmi

			}

		}//actionPerformed

	}//AscoltatoreEventi
	
	class FinestraAbout extends JFrame{
		public FinestraAbout() {
			setTitle("Help");
			setSize(430,280);
			setLocation(710,300);
			setResizable(false);
			JPanel Pabout = new JPanel();
			add(Pabout);
			JTextArea txtA = new JTextArea();
			txtA.setBackground(Color.LIGHT_GRAY);
			txtA.setFont(new Font("Italic",Font.BOLD, 15));
			txtA.setPreferredSize(new Dimension(420,260));
			Pabout.add(txtA);
			txtA.setEditable(false);
			txtA.append("Questo programma svolge le operazioni base tra polinomi:\n"
					+ "quali somma,differenza,moltiplicazione,derivata \ne ricerca del suo valore passatogli un numero (double). \n" 
					+ "\n\nProgramma sviluppato da Marco Greco, matricola 200901.\n\nUnical \nIngegneria Informatica ");
			
		}
	}//FinestraAbout

}//FinestraApplicazione


