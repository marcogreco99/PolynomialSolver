Progetto Risolutore Sudoku, studente Marco Greco Matricola 200901

Il mio secondo progetto (risolutore di Sudoku) si basa sull’implementazione di due classi: 

- Sudoku 
- SudokuGui. 

La prima è concentrata sulla logica del gioco con un main al fine di test e la seconda sulla grafica. 

`                               `Classe Sudoku:

//Per semplicità chiamo celle le 81 celle e macrocelle quelle 3x3. 

Nella classe Sudoku ho dichiarato una matrice di interi (griglia) la quale sarà la nostra griglia 9x9 nelle cui celle verranno inseriti tutti i numeri da 1 a 9 nel rispetto dei vincoli del gioco. Una matrice di booleani (grigliaInput) utile per tenere conto di quali celle sono quelle preimpostate dall’utente all’inizio e una variabile per il numero della soluzione corrente (numSol) utile per la stampa.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.001.png)





Costruttori

Costruttori


La classe presenta due Costruttori: quello di default che inizializza a 0 tutte le celle della griglia 9x9 e a false tutte quelle della matrice di booleani; e quello di impostazioni che riceve come parametro una matrice Nx3 (dove N è il numero delle celle preimpostate) e si avvale del metodo imposta che riceve come parametri la riga i (imp[i][0]) la colonna j (imp[i][1]) e il relativo valore v (imp[i][2]) della cella preimpostata.


![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.003.png)






Metodo Imposta

Metodo Imposta



Il metodo imposta crea una nuova cella con riga e colonna <i,j> passati come parametri, e verifica se rispetta i vincoli del gioco (in tal caso solleva un’eccezione).

Il metodo così assegna (se i parametri sono corretti) alla posizione [i,j] il valore alla griglia e true alla grigliaInput per ricordare che si tratti di una cella preimpostata.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.005.png)




Classe Cella

Classe Cella



Per eleganza (anche se ci perdo un po’ in termini di efficienza) mi sono avvalso di una classe Cella identificata dalla riga i e colonna j. (Durante la stesura della relazione ho pensato che sarebbe stato ancora più elegante creare direttamente anziché una matrice (griglia) di interi una matrice di Celle in cui ogni Cella possedeva il valore v oltre che riga e colonna...)

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.007.png)



Metodo Colloca

Metodo Colloca


Uno dei metodi più importanti della classe è Colloca che si avvale del compito di collocare per tentativi le possibili scelte (da 1 a 9) in ogni cella del Sudoku; quindi utilizzando la tecnica del backtracking.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.009.png)

Ho limitato le soluzioni a 200 poiché altrimenti si potrebbe aspettare molto tempo se si passa una matrice di impostazioni molto povera di celle preimpostate. Come punto di scelta ho utilizzato la cella e le possibili scelte non sono altro che i valori da 1 a 9. 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.010.png)La tecnica del backtracking deve lavorare solo con le celle non preimpostate, pertanto ho inserito la condizione if(!grigliaInput[c.rig][c.col]) che verifica se siamo in presenza di una cella non preimpostata e verificando in tal caso l’assegnabilità del valore in essa.





Se si trattava di una cella preimpostata richiamo il colloca alla cella successiva se non siamo sull’ultima cella della griglia dove in tal caso devo stampare la soluzione. (Non ho messo l’else poiché ciò non può stare nel ciclo!!)

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.011.png)








Se il numero è assegnabile nella cella c (rispetta i vincoli) lo assegna chiamando il metodo assegna che imposta alla riga e colonna della cella il numero (valore) e se non siamo arrivati all’ultima cella della griglia richiamo il metodo colloca alla cella successiva, altrimenti scriviamo la soluzione.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.012.png)


Se si sono provati tutti i numeri e si ha sempre ottenuto assegnabile==false allora il metodo colloca ritorna e verrà eseguito deassegna che imposta la cella a 0.







Metodo Assegnabile

Metodo Assegnabile


Il metodo assegnabile riceve come parametri la cella e il valore da attribuire.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.014.png)








Verifico con il primo for la presenza di uno stesso numero all’interno della stessa riga, con il secondo sulla stessa colonna ed infine con un semplice calcolo di indici se è presente nella sottomatrice 3x3 (macrocella).


Controllo Riga

Controllo Riga![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.016.png)


Controllo Colonna

Controllo Colonna![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.018.png)


Controllo macrocella 3x3

Controllo macrocella 3x3![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.020.png)

` `Il calcolo consiste nel cercare di ottenere sempre lo 0 se ci troviamo in un indice riga/colonna tra 0 e 2, sempre 3 se riga/colonna è tra 3 e 5, ed invece 6 se riga/colonna è tra 6 e 8. I valori 0,3,6 sono gli indici di partenza di ogni riga/colonna della macrocella3x3: <0,0>,<0,3>,<0,6>,<3,0>,<3,3>,<3,6>,<6,0>,<6,3>,<6,6>.

Per esempio se devo verificare la cella <4,5> mi troverei a controllare la macrocella centrale ed in fatti otteniamo x=3 e y=3. I due cicli for scorrono in modo intuitivo fino a x/y +3 (escluso).

Se non si verifica nessuno di questi tre casi il valore è assegnabile e il metodo ritorna true.

Metodo scriviSoluzione

Metodo scriviSoluzione



Il metodo scriviSoluzione() oltre che stampare le soluzioni faccio una copia della griglia attuale(la soluzione) e la aggiungo alla listaSoluzioni. Se ci troviamo sugli indici pari a 2 o a 5, sulle colonne procedo a mettere il trattino verticale, mentre nelle righe quelli orizzontali tanti quanto è la lunghezza della griglia\*2+3 (i trattini orizzontali sono molto piccoli).

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.022.png)











Esempio di output di un Sudoku con due soluzioni:

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.023.png)![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.024.png)






`                           `Classe SudokuGui:

La classe SudokuGui possiede un main che si limita a creare una nuova finestra dell’applicazione e renderla visibile.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.025.png)




Classe FinestraApp

Classe FinestraApp



La classe relativa alla finestra estende JFrame, nella quale dichiaro un JTextField sol che corrisponde al riquadro nero dove segnala all’utente quale soluzione sta visionando; i bottoni risolvi,previous,reset,next,salva,carica, due label s e c per il testo salva e carica, il numero della soluzione attuale (soluzione) e un oggetto di tipo Sudoku. 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.027.png)






I JTextFields delle 81 celle li raggruppo in 9 blocchi di matrici 3x3 (macrocelle) i quali li salvo nella matrice complessiva che raffigurerà la nostra griglia Sudoku (una matrice di matrici). Quindi in poche parole creo una matrice multidimensionale (matriceSu) 3x3 che conterrà le 9 macrocelle 3x3. Ciò mi è stato molto utile nella rappresentazione grafica del Sudoku.

Il tutto è accompagnato dalla creazione di un ascoltatore (listener).


Costruttori

Costruttori


La classe FinestraApp contiene solo il costruttore di default. Al suo interno setto il titolo della finestra, la sua dimensione, la posizione e una volta creato gli aggiungo un pannello principale (pannello) con lo scopo di contenere i vari componenti: risolvi,previous,reset,next,salva,carica, title,sol e la griglia del sudoku. Si preoccupa quindi di creare tutto ciò che l’utente visiona all’apertura dell’applicazione.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.029.png)

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.030.png)

Il layout del pannello pannello lo setto a null, garantendomi di settare le dimensioni e posizioni (bounds) a mio piacimento dei vari componenti ai quali aggiungo successivamente l’ascoltatore listener.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.031.png)


Al pannello pannello aggiungo il pannello griglia con background di colorazione nera che farà da base alle 9 macrocelle3x3 del Sudoku (layout null per lo stesso motivo).

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.032.png)



All’interno del pannello griglia ho deciso di inserire 9 pannelli (per le macrocelle 3x3) con un layout GridLayout dove al suo interno ad ogni giro del for una volta creato aggiungo un jTextField che rappresenterà una cella. Ad ogni JTextField setto l’allineamento del testo Centrale, un font più grande e un bordo nero che evidenzia gli spazi sottili tra ogni cella. 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.033.png)












Il primo for scorre le righe i della griglia (matriceSu), il secondo le colonne j della griglia (matriceSu), il terzo le righe m della macrocella e il quarto le colonne n della macrocella.

La matrice 3x3 rappresenta la nostra macrocella alla quale verranno assegnati tutti i nove JTextField ed essa verrà assegnata a sua volta alla posizione i,j della griglia matriceSu.

La x e la y sono rispettivamente le coordinate della posizione del pannello della macrocella. La x aumenta di 205 ogni scorrimento di colonna ritornando a zero ad ogni scorrimento di riga e la y non può solo che aumentare ad ogni scorrimento di riga.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.034.png)![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.035.png)

Metodi stampaSoluzione e contaCellePreimpostate

Metodi stampaSoluzione e contaCellePreimpostate

In seguito ho effettuato l’implementazione di due metodi privati:

**stampaSoluzione** che passatogli un oggetto di tipo Sudoku e un intero che corrisponde al numero della soluzione setta il testo di ogni cella con il valore corrispondente prestando attenzione a quale soluzione si tratta. Per fare ciò utilizzo il metodo setText sulla cella di JTextField passandogli la stringa (Integer.toString) dell’intero presente nella cella in posizione [c+3\*i][m+3\*j] della matrice in posizione numeroSoluzione all’interno della listaSoluzioni. 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.037.png)



**contaCellePreimpostate**() con lo scopo di contare il numero di celle preimpostate in modo da poter fornire la grandezza delle righe della matrice imposta successivamente creata. Il metodo è banale poiché vi è solo un contatore che aumenta una volta incontrata una cella in cui il contenuto del JTextField non è vuoto (c’è un valore).


![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.038.png)









Classe AscoltatoreEventi

Classe AscoltatoreEventi


Ho creato la inner class AscoltatoreEventi ed ho implementato l’unico metodo dell’interfaccia ActionListener implementata dalla classe.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.040.png)

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.041.png)

- **Pressione Risolvi**

Se abbiamo premuto il bottone **risolvi**, si occupa di creare la matrice di impostazioni e di verificare la validità di ogni contenuto di un JTextField pronto a lanciare un’eccezione. Azzera soluzione (in caso di una nuova pressione futura), abilita i bottoni next e previous e inizializza la matrice imposta con tante righe quanto ritorna il metodo contaCellePreimpostate(). 

Ho creato un’etichetta “ciclo” in modo che se durante l’assegnamento del valore nella matrice di impostazione dovesse andare storto qualcosa (Se mi passa un formato sbagliato, come ad esempio una stringa=>NumberFormatException) fermo tutto il ciclo e al try e catch successivo mostrerà il messaggio di dialogo “Parametri Iniziali Errati !”. Non ho fatto uscire al primo catch il messaggio di dialogo poiché altrimenti essendo catturata nuovamente l’eccezione, il messaggio comparirebbe due volte. Quindi se tutto fila liscio crea un nuovo Sudoku invocando il costruttore di impostazioni e lo assegna a sud e invoca sullo stesso il metodo risolvi() della classe Sudoku. Stampa la prima soluzione, mostra la soluzione corrente e abilita il bottone next se vi è più di una soluzione. Il controllo dell’eccezione basato sul non rispetto dei vincoli è gestito nell’ultimo try-catch.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.042.png)

- ![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.043.png)**Pressione Previous e Next**

Se si è premuto il bottone previous se la soluzione precedente è la prima allora disabilito il bottone previous in modo da non permettere all’utente di accedere ad una soluzione negativa causando il sollevamento di un’eccezione. Successivamente in qualunque caso ci troviamo diminuisco soluzione, stampo la soluzione corrente, aggiorno il testo della soluzione corrente e abilito il bottone next nel caso l’utente avesse visionato prima tutte le soluzioni.

Una volta capito cosa succede se si preme il bottone previous è intuitivo capire la versione riguardo la pressione del bottone next: disabilitando il bottone next se la soluzione successiva è l’ultima (numSol-2 perché numSol parte da 1 ed è il numero delle soluzioni totali). 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.044.png)

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.045.png)







![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.046.png)

- **Pressione Resetta**

Alla pressione del bottone reset disabilita i bottoni next e previous, pulisce il testo della soluzione corrente, disabilita il bottone salva e imposta soluzione corrente (soluzione) a 0.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.047.png)





![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.048.png)

- **Pressione Salva e Carica**

Come ultimo controllo gestisco il salvataggio e il caricamento su/da file cercando di mantenere consistenza con la gestione salvataggio/caricamento del progetto sui polinomi. 

Indipendentemente da quale dei due bottoni premo creo un nuovo jFileChooser jfc al quale gli assegno un FileFilter consentendo di salvare/caricare solo file in formato txt (per semplicità, in generale file testo).

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.049.png)


Se stiamo salvando, racchiuso in un try-catch (può sorgere un’eccezione del tipo IOException se ad esempio un file è corrotto) creo un PrintWriter (Bufferizzato) al quale gli passo il Path del nomefile selezionato con il JFileChooser. Considerando solo le celle preimpostate, controllando quindi che in quella posizione nella grigliaInput risulti esserci true, scrivo sul file ordinatamente prima la priga, poi la colonna e poi il contenuto (valore). 

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.050.png)

Se stiamo in fase di caricamento è opportuno dapprima resettare il tutto invocando il metodo doClick() sul bottone reset per poi creare un BufferedReader e leggere quindi da file. Ho utilizzato un for infinito e leggo la linee di testo a tre a tre in cui la prima ci da il numero di riga, la seconda il numero di colonna e la terza il valore. La fine di un file su un BufferedReader lo si individua quando la linea letta è null, procedendo in tal caso al break del ciclo infinito.

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.051.png)

In base a quale delle tre righe ci troviamo ho realizzato uno switch dove nei casi 0 e 1 mi salvo riga e colonna e nella terza linea (indice 2) procedo direttamente a settare il valore sulla cella corrispondente presente nella matrice multidimensionale matriceSu di JTextField. Ricordo che riga e colonna vanno da 0 a 8 e in matriceSu abbiamo le macrocelle3x3 con indici che vanno quindi sempre da 0 a 2; pertanto per identificare la posizione della cella nella macrocella corrispondente ho dovuto sistemare gli indici con qualche calcolo intuitivo. Sempre chiudere i relativi PrintWriter e BufferedReader!

![](Aspose.Words.8bdf1e53-6962-42db-bf58-01b63a2f3fa8.052.png)

