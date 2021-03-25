package poo.polinomi;

import javax.swing.JOptionPane;


public class VerificaPolinomio extends PolinomioAL {
	public static void main(String[]args) {
		Monomio m = new Monomio(3,2);
		Monomio m1 = new Monomio(2,4);
		Polinomio polinomio1 = new PolinomioAL();
		Polinomio polinomio2 = new PolinomioAL();
		polinomio1.add(m);
		polinomio1.add(m1);
		polinomio2=polinomio2.add(polinomio1);
		System.out.print(polinomio2.size());
		
		String NUM = "[1-9]+";
		String VAR = "([xX]|[xX][//^]"+NUM+")";
		String OPER = "[\\+-]";
		//String MONOMIO = "("+OPER+"?"+NUM+VAR+"?)";
		//String MONOMIO = "([-]?"+NUM+")?"+"([-]?"+VAR+"|"+NUM+VAR+")"+"?";
		String MONOMIO = "("+NUM+VAR+"?|"+VAR+")";
		//String POLINOMIO = MONOMIO+ "("+OPER+NUM+"("+VAR+"|"+NUM+VAR+")"+"?"+")"+"*";
		String POLINOMIO ="[-]?"+ MONOMIO+"("+OPER+MONOMIO+")*";
		String input;
		for(;;) {
			input = JOptionPane.showInputDialog("Inserisci un Poliniomio");
			if(input.matches(POLINOMIO)) {
				JOptionPane.showMessageDialog(null, "CORRETTO!");
			}
			else
				JOptionPane.showMessageDialog(null, "INSERIRE UN POLINOMIO VALIDO!");
		}
		
	}

}

