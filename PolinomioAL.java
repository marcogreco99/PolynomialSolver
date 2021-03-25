package poo.polinomi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PolinomioAL extends PolinomioAstratto {
	private ArrayList<Monomio> lista = new ArrayList<>();
	
	public PolinomioAL(Monomio... m){ //Posso aggiungere più monomi
		for(Monomio mon:m)
			this.add(mon); //Chiamo la add implementata sotto che fa una lista.add(m)
	}
	
	public PolinomioAL(Polinomio p) {
		for(Monomio m:p) this.add(m); //Chiamo la add implementata sotto che fa una lista.add(m)
	}

	@Override
	public Iterator<Monomio> iterator() {
		return lista.iterator();
	}

	@Override
	public void add(Monomio m) {
		if(m.getCoeff()==0) return;
		boolean trovato = false;
		for(Monomio mon:lista)
			if(m.equals(mon)) {
				lista.remove(mon);
				Monomio sma=mon.add(m);
				if(sma.getCoeff()!=0)
					lista.add(sma);
				trovato=true;
				break;
			}
		if(!trovato) lista.add(m);
		Collections.sort(lista);
	}

	@Override
	protected Polinomio create() {
		return new PolinomioAL();
	}
	
	@Override
	public int size() { return lista.size();}
	
	
	
}