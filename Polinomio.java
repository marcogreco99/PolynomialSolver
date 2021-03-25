package poo.polinomi;

interface Polinomio extends Iterable<Monomio>{
	int size();
	void add( Monomio m );
	Polinomio add( Polinomio p );
	Polinomio mul( Polinomio p );
	Polinomio mul( Monomio m );
	Polinomio derivata();
	double valore( double x );
}//Polinomio
