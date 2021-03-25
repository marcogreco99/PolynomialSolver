package poo.polinomi;
import java.util.*;

public class PolinomioLL extends PolinomioAstratto{
	private LinkedList<Monomio> lista=new LinkedList<Monomio>();
	
	public PolinomioLL(){};
	public PolinomioLL( Polinomio p ){
		for( Monomio m: p ) add(m);
	}
	
	@Override
	protected PolinomioLL create(){ //covarianza tipo di ritorno
		return new PolinomioLL(); 
	}//create
	
	public Iterator<Monomio> iterator(){ 
		return lista.iterator(); 
	}//iterator
	
	public int size(){ return lista.size(); }
	
	public void add( Monomio m ){
		//si mantiene la lista continuamente ordinata per gradi decrescenti
		if( m.getCoeff()==0 ) return;
		ListIterator<Monomio> lit=lista.listIterator();
		boolean flag=false; //true quando m e' sistemato
		while( lit.hasNext() && !flag ){
			Monomio m1=lit.next();
			if( m.equals(m1) ){//monomi simili
				Monomio m2=m.add(m1);
				if( m2.getCoeff()!=0 ){
					lit.set( m2 );
				}
				else lit.remove();
				flag=true;
			}
			else if( m1.compareTo(m)>0 ){
				lit.previous();
				lit.add(m);
				flag=true;
			}
		}//while
		if( !flag ) lit.add( m );
	}//add
}//PolinomioLL
