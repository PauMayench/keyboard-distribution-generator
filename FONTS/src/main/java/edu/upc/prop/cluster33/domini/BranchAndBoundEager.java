package edu.upc.prop.cluster33.domini;

import java.util.*;

/**
 * Classe que implementa l'algoritme Branch and Bound Eager per a la generació de layouts de teclats.
 */
public class BranchAndBoundEager extends Algorisme {

    /**
     * Matriu de distàncies utilitzada en l'algoritme per a determinar la distància entre tecles.
     */
    private int[][] matriuDistancies;
    /**
     * Matriu de proximitat utilitzada en l'algoritme per a determinar la proximitat entre tecles.
     */
    private int[][] matriuProximitat;

    /**
     * Retorna el nom de l'algorisme.
     *
     * @return El nom de l'algorisme.
     */
    public String getNom(){
        return "Branch and Bound Eager";
    }

    /**
     * Inicialitza les matrius de distàncies i proximitat a 0.
     */
    public  void init(){
        matriuDistancies = new int[0][0];
        matriuProximitat = new int[0][0];
    }

    /**
     * Genera i retorna un layout de teclat optimitzat basat en les freqüències de les tecles i les dimensions donades.
     *
     * @param frequencies Objecte que conté les freqüències dels caràcters.
     * @param columnes    Nombre de columnes del teclat.
     * @param files       Nombre de files del teclat.
     * @return Un array bidimensional de caràcters que representa el layout del teclat.
     */
    @Override
    public char[][] generarLayout(Frequencies frequencies, int columnes, int files) {
        final int midaMatriuCritica = 21;

        //inicialitzem
        Integer[] caractersPocFrequents = inicialitzarMatriuProximitat(frequencies);
        Integer[] teclesLlunyanes = inicialitzarMatriuDistancies(columnes,files);
        PriorityQueue<SolucioParcial> priorityQueue = new PriorityQueue<>();

        //solucio inicial
        ArrayList<Integer> solucioInicial = new ArrayList<>(matriuProximitat.length);
        for (int i = 0; i < matriuProximitat.length; i++) solucioInicial.add(-1); // solucio Buida ha d'estar inicialitzada a -1, no tenim cap caracter assignat
        final int nCaractersAfegir = matriuProximitat.length - midaMatriuCritica;
        if(nCaractersAfegir > 0) {
            for (int i = 0; i < nCaractersAfegir; i++)
                solucioInicial.set(teclesLlunyanes[matriuProximitat.length - i - 1], caractersPocFrequents[i]);
        }

        SolucioParcial sp = new SolucioParcial(solucioInicial); // creem la solucio inicial amb 0 elements, o els elements necessaris per a puguer compllir amb l'eficiencia i ficats a les posicions mes llunyanes
        priorityQueue.add(sp);

        boolean solucioTrobada = false;
        while(!solucioTrobada){
            //agafem el primer element i eliminem la resta
            sp = priorityQueue.poll();
            priorityQueue.clear();

            if(sp == null) break;
            ArrayList<Integer> charsDisponibles = sp.getCharsDisponibles();

            for(Integer character : charsDisponibles){
                SolucioParcial sp2 = sp.creaSolucioParcialAmb(character);
                if (sp2.solucionat()){
                    solucioTrobada = true;
                    sp = sp2;
                    break;
                }
                sp2.calcularCotaGilmoreLawler();
                priorityQueue.add(sp2);
            }

        }

        if (sp == null) return new char[0][0]; // aixo no hauria de passar mai, pero retorna un layout buit
        SolucioParcial solucioFinal = sp;
        return creaLayoutAmbSolucio(solucioFinal, columnes, files, frequencies);
    }

    /**
     * Inicialitza i omple la matriu de distàncies entre tecles.
     *
     * @param columnes Nombre de columnes del teclat.
     * @param files    Nombre de files del teclat.
     * @return Array d'enters amb els índexs de les tecles ordenades per distància.
     */
    private Integer[] inicialitzarMatriuDistancies(int columnes,int files){
        int nElements = columnes * files;
        int[][] matriuDist = new int[nElements][nElements];
        int[] teclesLlunyanes = new int[matriuProximitat.length];

        for (int i = 0; i < nElements; i++) {
            int xi = i / columnes;
            int yi = i % columnes;

            for (int j = 0; j < nElements; j++) {
                int xj = j / columnes;
                int yj = j % columnes;

                int dx = xj - xi;
                int dy = yj - yi;
                int dist = (int) Math.sqrt(dx * dx + dy * dy)*100;
                matriuDist[i][j] = dist;
                if(i < matriuProximitat.length && j < matriuProximitat.length) {
                    teclesLlunyanes[i] += dist;
                    teclesLlunyanes[j] += dist;
                }

            }
        }

        matriuDistancies = matriuDist;
        return indexsArrayOrdenat(teclesLlunyanes);
    }

    /**
     * Inicialitza i omple la matriu de proximitat entre caràcters basant-se en les freqüències.
     *
     * @param frequencies Objecte que conté les freqüències dels caràcters.
     * @return Array d'enters amb els índexs dels caràcters ordenats per freqüència.
     */
    private Integer[] inicialitzarMatriuProximitat(Frequencies frequencies){
        TreeMap<String,Integer> freq = frequencies.getLlistaFrequencies();
        Alfabet alfabet = frequencies.getAlfabet();
        int alfabetSize = alfabet.getMida();
        int[] caractersPocFrequents = new int[alfabetSize];
        int[][] matriuProx = new int[alfabetSize][alfabetSize];

        Random random = new Random(4);
        // Ho fiquem tot a un valor random entre [0,1,2,3,4], aquest valor es insignificant, pero l'eficiencia es dispara
        for (int i = 0; i < alfabetSize; i++) {
            for (int j = 0; j < alfabetSize; j++) {
                matriuProx[i][j] = random.nextInt(15);
            }
        }


        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            String paraula = entry.getKey();
            int paraulaFreq = entry.getValue();

            // per cada parell de caracters en la paraula sumem la frequencia
            for (int i = 0; i < paraula.length() - 1; i++) {
                char caracterEsquerra = paraula.charAt(i);
                char caracterDreta = paraula.charAt(i + 1);

                int indexEsquerra = alfabet.getIndex(caracterEsquerra);
                int indexDreta = alfabet.getIndex(caracterDreta);


                if (indexEsquerra >= 0 && indexDreta >= 0) {
                    matriuProx[indexEsquerra][indexDreta] += paraulaFreq*10;
                    matriuProx[indexDreta][indexEsquerra] += paraulaFreq*10;
                    caractersPocFrequents[indexEsquerra] += paraulaFreq;
                    caractersPocFrequents[indexDreta] += paraulaFreq;
                }
            }
        }
        matriuProximitat = matriuProx;
        return indexsArrayOrdenat(caractersPocFrequents);
    }

    /**
     * Ordena un array i retorna un nou array amb els índexs dels elements originals ordenats.
     *
     * @param array Array d'enters a ordenar.
     * @return Array d'enters amb els índexs dels elements ordenats.
     */
    public Integer[] indexsArrayOrdenat(int[] array) {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(array[i1], array[i2]);
            }
        });

        return indexes;
    }
    /**
     * Omple el layout amb la solució trobada.
     *
     * @param solucioFinal Objecte de la classe SolucioParcial que conté la solució final.
     * @param columnes     Nombre de columnes del teclat.
     * @param files        Nombre de files del teclat.
     * @param frequencies  Objecte que conté les freqüències dels caràcters.
     * @return Un array bidimensional de caràcters que representa el layout del teclat.
     */
    private char[][] creaLayoutAmbSolucio(SolucioParcial solucioFinal, int columnes, int files, Frequencies frequencies) {
        Alfabet alfabet = frequencies.getAlfabet();
        ArrayList<Integer> solucio = solucioFinal.getSolucio();

        char[][] layout = new char[files][columnes];
        for (int tecla = 0; tecla < solucio.size(); tecla++){
            int caracterIndex = solucio.get(tecla);
            layout[tecla/columnes][tecla%columnes] = alfabet.getCharAtIndex(caracterIndex);
        }
        return layout;
    }
    /**
     * Calcula el cost de les arestes entre un caràcter i la resta dels caràcters de la solució.
     *
     * @param caracter1 Índex del primer caràcter.
     * @param tecla1    Índex de la tecla del primer caràcter.
     * @param sol       ArrayList amb la solució parcial actual.
     * @return El cost total de les arestes.
     */
    private int costArestes(int caracter1, int tecla1, ArrayList<Integer> sol ){
        int cost = 0;
        for (int i = 0; i < sol.size(); ++i){
            int caracter2 = sol.get(i);
            if (caracter2 != -1 && caracter1 != caracter2)
                cost += matriuProximitat[caracter1][caracter2]  * matriuDistancies[tecla1][i];
        }
        return cost;
    }


    /**
     * Classe interna que representa una solució parcial (un layout mig ple).
     */
    private class SolucioParcial implements Comparable<SolucioParcial> {
        /**
         * Representa la solució parcial. És un ArrayList on els elements són els índexs dels caràcters
         * i si la posició està buida, un -1.
         */
        final private ArrayList<Integer> solucio; // la solucio parcial, es un arraylist on els elements son els indexs dels caracters i si la posicio esta buida un -1
        /**
         * Resultat després de calcular la cota Gilmore-Lawler.
         */
        private int cotaGilmoreLawler;  // el resultat despres de calcular la cota
        /**
         * El factor F1 de la cota de Gilmore-Lawler, així no l'hem de recalcular cada vegada
         * i el podem mantenir amb un simple càlcul.
         */
        private int f1; //el factor f1 de la cota de gilmore lawler, aixi no l'hem de recalcular cada vegada i el poodem mantenir amb un simple calcul
        /**
         * ArrayList amb els caràcters que queden per assignar.
         */
        final private ArrayList<Integer> charsPerAssignar;  //arraylist on els elements son els caracters que queden per assignar
        /**
         * ArrayList amb les tecles buides que queden per assignar.
         */
        final private ArrayList<Integer> teclesPerAssignar; // arraylist on els elements son les tecles  buides


        /**
         * Classe interna que representa una solució parcial (un layout mig ple).
         */
        public SolucioParcial(ArrayList<Integer> solucio) {

            this.solucio = new ArrayList<>(solucio);
            // calculem f1
            this.f1 = calculaF1();
            this.cotaGilmoreLawler = -1; //encara no la calculem

            charsPerAssignar = new ArrayList<Integer>();
            teclesPerAssignar = new ArrayList<Integer>();
            //omplim charsPerAssignar
            for (int character = 0; character < solucio.size(); ++character) {
                if (!solucio.contains(character)) {
                    charsPerAssignar.add(character);
                }
            }

            //omplim teclesPerAssignar
            for (int j = 0; j < solucio.size(); j++) {
                if (solucio.get(j) == -1) {
                    teclesPerAssignar.add(j);
                }
            }
        }

        /**
         * Constructor amb tots els paràmetres determinats.
         *
         * @param solucio Array d'enters que representa la solució parcial actual.
         * @param f1 Valor del factor F1 per a la solució parcial.
         * @param charsPerAssignar ArrayList d'enters amb els caràcters pendents d'assignar.
         * @param teclesPerAssignar ArrayList d'enters amb les tecles buides pendents d'assignar.
         */
        private SolucioParcial(ArrayList<Integer> solucio, int f1, ArrayList<Integer> charsPerAssignar, ArrayList<Integer> teclesPerAssignar) {
            this.solucio = solucio;
            this.f1 = f1;
            this.cotaGilmoreLawler = -1; //encara no la calculem
            this.charsPerAssignar = charsPerAssignar;
            this.teclesPerAssignar = teclesPerAssignar;

        }


        /**
         * Crea una nova solució parcial afegint un caràcter a la solució actual.
         *
         * @param character El caràcter a afegir a la solució.
         * @return Una nova instància de SolucioParcial amb el caràcter afegit.
         */
        public SolucioParcial creaSolucioParcialAmb(int character) {

            ArrayList<Integer> novaSolucio = new ArrayList<>(this.solucio);
            int tecla = -1;
            for (int i = 0; i < solucio.size(); i++) {
                if (solucio.get(i) == -1) {
                    tecla = i;
                    break;
                }
            }

            novaSolucio.set(tecla, character);

            ArrayList<Integer> nousCharsPerAssignar = new ArrayList<>(this.charsPerAssignar);
            ArrayList<Integer> novesTeclesPerAssignar = new ArrayList<>(this.teclesPerAssignar);

            nousCharsPerAssignar.remove(Integer.valueOf(character));
            novesTeclesPerAssignar.remove(Integer.valueOf(tecla));

            int nouF1 = f1 + costArestes(character, tecla, novaSolucio);

            return new SolucioParcial(novaSolucio, nouF1, nousCharsPerAssignar, novesTeclesPerAssignar);


        }

        /**
         * Retorna la llista de caràcters disponibles per assignar a la solució parcial.
         *
         * @return ArrayList d'enters amb els caràcters pendents d'assignar.
         */
        public ArrayList<Integer> getCharsDisponibles() {
            return this.charsPerAssignar;
        }

        /**
         * Determina si la solució parcial és completa, i si ho és, finalitza l'assignació.
         *
         * @return True si la solució parcial només necessita una assignació final.
         */
        public boolean solucionat() {
            if (charsPerAssignar.size() <= 1 ) {
                int tecla = -1;
                for (int i = 0; i < solucio.size(); i++) {
                    if (solucio.get(i) == -1) {
                        tecla = i;
                        break;
                    }
                }
                solucio.set(tecla, charsPerAssignar.get(0));
                return true;
            }
            return false;
        }

        /**
         * Retorna l'ArrayList que representa la solució parcial actual.
         *
         * @return ArrayList d'enters amb la solució parcial.
         */
        public ArrayList<Integer> getSolucio(){
            return solucio;
        }

        /**
         * Defineix l'ordre de la cua de prioritat basant-se en la cota Gilmore-Lawler.
         *
         * @param other Una altra instància de SolucioParcial a comparar.
         * @return Un valor negatiu, zero o positiu segons aquesta solució sigui menor, igual o major que l'objecte especificat.
         */
        @Override
        public int compareTo(SolucioParcial other) {
            return Integer.compare(this.cotaGilmoreLawler, other.cotaGilmoreLawler);
        }


        /**
         * Calcula el factor F1 per a la solució parcial actual.
         *
         * @return El valor del factor F1.
         */
        private int calculaF1() {
            ArrayList<Integer> list = new ArrayList<>(solucio);
            int cost = 0;
            for (int i = 0; i < solucio.size(); ++i) {
                int caracter = solucio.get(i);
                if (caracter != -1) {
                    list.set(i, -1);
                    cost += costArestes(caracter, i, list);
                }
            }
            return cost;
        }

        /**
         * Calcula la cota Gilmore-Lawler i l'emmagatzema a l'atribut cotaGilmoreLawler.
         */
        public void calcularCotaGilmoreLawler() {
            int[][] c1 = computarC1();
            int[][] c2 = computarC2();
            int[][] matriuC1C2 = suma(c1, c2);

            HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm();
            boolean[][] matSolucioOptima = hungarianAlgorithm.resoldre(matriuC1C2);
            int f2f3 = calculaF2F3(matriuC1C2, matSolucioOptima);

            cotaGilmoreLawler = f1 + f2f3;
        }

        /**
         * Retorna la matriu C1 utilitzada per l'algorisme que troba la cota Gilmore-Lawler.
         *
         * @return Una matriu quadrada que representa els costos associats amb els caràcters i les tecles assignades.
         */
        private int[][] computarC1() {
            int m = charsPerAssignar.size();
            int[][] matriuC1 = new int[m][m];
            for (int indexChar = 0; indexChar < m; indexChar++) {
                for (int indexTecla = 0; indexTecla < m; indexTecla++) {
                    int caracter = charsPerAssignar.get(indexChar);
                    int tecla = teclesPerAssignar.get(indexTecla);
                    matriuC1[indexChar][indexTecla] = costArestes(caracter, tecla, solucio);
                }
            }
            return matriuC1;
        }

        /**
         * Retorna la matriu C2 utilitzada per l'algorisme que troba la cota Gilmore-Lawler.
         *
         * @return Una matriu quadrada que representa els costos estimats per als caràcters i les tecles no assignats.
         */
        private int[][] computarC2() {
            int m = charsPerAssignar.size();
            int[][] matriuC2 = new int[m][m];
            for (int indexChar = 0; indexChar < m; ++indexChar) {
                for (int indexTecla = 0; indexTecla < m; ++indexTecla) {
                    int caracter = charsPerAssignar.get(indexChar);
                    int tecla = teclesPerAssignar.get(indexTecla);
                    int[] vectorT = vectorT(caracter);
                    int[] vectorD = vectorD(tecla);
                    Arrays.sort(vectorT);
                    Arrays.sort(vectorD);
                    matriuC2[indexChar][indexTecla] = dotDReversed(vectorT, vectorD);
                }
            }
            return matriuC2;
        }

        /**
         * Retorna el vector T per a un caràcter donat, comparant-lo amb els caràcters encara no assignats.
         * S'utilitza per a calcular la matriu C2.
         *
         * @param caracter El caràcter per al qual es calcula el vector T.
         * @return Un array d'enters que representa el vector T.
         */
        private int[] vectorT(int caracter) {
            int[] t = new int[charsPerAssignar.size() - 1];
            int i = 0;
            for (int charPerAssignar : charsPerAssignar) {
                if (charPerAssignar != caracter) {
                    t[i] = matriuProximitat[charPerAssignar][caracter];
                    ++i;
                }
            }
            return t;
        }

        /**
         * Retorna el vector D per a una tecla donada, comparant-la amb les tecles encara no assignades.
         * S'utilitza per a calcular la matriu C2.
         *
         * @param tecla La tecla per a la qual es calcula el vector D.
         * @return Un array d'enters que representa el vector D.
         */
        private int[] vectorD(int tecla) {
            int[] d = new int[teclesPerAssignar.size() - 1];
            int i = 0;
            for (int teclaPerAssignar : teclesPerAssignar) {
                if (teclaPerAssignar != tecla) {
                    d[i] = matriuDistancies[teclaPerAssignar][tecla];
                    ++i;
                }
            }
            return d;
        }

        /**
         * Retorna el producte escalar de dos vectors, però tenint en compte que el segon vector està girat.
         * És a dir, retorna el producte escalar de v1 amb v2 invertit.
         *
         * @param vectorT El primer vector.
         * @param vectorD El segon vector, el qual es girarà per al càlcul.
         * @return El resultat del producte escalar amb el segon vector invertit.
         */
        private int dotDReversed(int[] vectorT, int[] vectorD) {
            int m = vectorT.length;
            int resultat = 0;
            for (int i = 0; i < m; ++i) {
                resultat += vectorT[i] * vectorD[m - i - 1];
            }
            return resultat;
        }

        /**
         * Retorna la suma de dues matrius quadrades de la mateixa mida.
         *
         * @param c1 La primera matriu a sumar.
         * @param c2 La segona matriu a sumar.
         * @return Una matriu que és la suma de c1 i c2.
         */
        private int[][] suma(int[][] c1, int[][] c2) {
            int m = c1.length;
            int[][] sum = new int[m][m];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    sum[i][j] = c1[i][j] + c2[i][j];
                }
            }

            return sum;
        }
        /**
         * Calcula els factors F2 i F3 per a la matriu i la solució òptima donades.
         *
         * @param mat Matriu que conté els costos.
         * @param solucioOptima Matriu de booleans que indica l'assignació òptima.
         * @return La suma dels costos associats amb l'assignació òptima.
         */
        public int calculaF2F3(int[][] mat, boolean[][] solucioOptima) {
            int f2f3 = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    if (solucioOptima[i][j]) {
                        f2f3 += mat[i][j];
                    }
                }
            }
            return f2f3;
        }
        /**
         * Classe interna que implementa l'algorisme Hongarès per resoldre problemes d'assignació lineal.
         */
        private class HungarianAlgorithm {
            /**
             * Resol el problema d'assignació lineal (LAP) i retorna l'assignació òptima com una matriu de booleans.
             *
             * @param mat La matriu de costos del problema d'assignació.
             * @return Una matriu de booleans on cada posició òptima està marcada amb true.
             */
            public boolean[][] resoldre(int[][] mat) {
                int[][] matriu = deepCopyMatriu(mat);
                int mida = matriu.length;
                restaMinimPerFila(matriu);
                restaMinimPerColumna(matriu);
                int[] linies = minLiniesCobrirZeros(matriu);
                while (linies.length < mida) {
                    int minNoCobert = minNoCobert(matriu, linies);
                    sumaCoberts(matriu, linies, minNoCobert);
                    int minim = minimMat(matriu);
                    restaMatriu(matriu, minim);
                    linies = minLiniesCobrirZeros(matriu);
                }

                return trobaAssignacioZeroFilaColumna(matriu); // nomes te 0 a les posicions que volem
            }

            /**
             * Retorna una còpia profunda de la matriu proporcionada.
             *
             * @param original La matriu original que es vol copiar.
             * @return Una nova matriu que és una còpia de l'original.
             */
            private int[][] deepCopyMatriu(int[][] original) {
                int[][] copy = new int[original.length][];
                for (int i = 0; i < original.length; i++) {
                    copy[i] = Arrays.copyOf(original[i], original[i].length); // Copy each row
                }
                return copy;
            }

            /**
             * Resta el valor mínim de cada fila a tots els elements d'aquella fila.
             *
             * @param matriu La matriu sobre la qual es realitzarà l'operació.
             */
            private void restaMinimPerFila(int[][] matriu) {

                for (int i = 0; i < matriu.length; i++) {
                    int minim = matriu[i][0]; // trobem el minim
                    for (int j = 0; j < matriu[i].length; j++) {
                        if (matriu[i][j] < minim) {
                            minim = matriu[i][j];
                        }
                    }
                    for (int j = 0; j < matriu[i].length; j++)  // restem el minim a cada element de la fila
                        matriu[i][j] = matriu[i][j] - minim;

                }
            }

            /**
             * Resta el valor mínim de cada columna a tots els elements d'aquella columna.
             *
             * @param matriu La matriu sobre la qual es realitzarà l'operació.
             */
            private void restaMinimPerColumna(int[][] matriu) {
                for (int j = 0; j < matriu.length; j++) {
                    int minim = matriu[0][j];
                    for (int[] ints : matriu) { // trobem el minim
                        if (ints[j] < minim)
                            minim = ints[j];
                    }
                    for (int i = 0; i < matriu.length; i++)  //restem el minim a cada element de la columna
                        matriu[i][j] -= minim;

                }
            }

            /**
             * Retorna el valor mínim no cobert per les línies donades.
             *
             * @param mat    La matriu sobre la qual es realitza la cerca.
             * @param linies Un array d'enters que representa les línies que cobreixen certs elements de la matriu.
             * @return El valor mínim no cobert.
             */
            private int minNoCobert(int[][] mat, int[] linies) {
                boolean[] isFilaCoberta = new boolean[mat.length];
                boolean[] isColumnaCoberta = new boolean[mat[0].length];

                for (int linia : linies) { //marquem les files i columnes
                    if (linia < mat.length) {
                        isFilaCoberta[linia] = true; //es una fila
                    } else if (linia < 2 * mat.length) {
                        isColumnaCoberta[linia - mat.length] = true; // es una columna
                    }
                }

                int min = Integer.MAX_VALUE;
                for (int i = 0; i < mat.length; i++) { //mirem nomes els que no estan coberts
                    for (int j = 0; j < mat[i].length; j++) {
                        if (!isFilaCoberta[i] && !isColumnaCoberta[j]) {
                            if (mat[i][j] < min) min = mat[i][j];
                        }
                    }
                }
                return min;
            }

            /**
             * Suma un nombre especificat a tots els elements coberts per les línies donades.
             *
             * @param mat          La matriu sobre la qual es realitza l'operació.
             * @param linies       Un array d'enters que representa les línies que cobreixen certs elements de la matriu.
             * @param minNoCobert  El valor a sumar als elements coberts.
             */
            private void sumaCoberts(int[][] mat, int[] linies, int minNoCobert) {
                boolean[] isFilaCoberta = new boolean[mat.length];
                boolean[] isColumnaCoberta = new boolean[mat[0].length];

                for (int linia : linies) { //marquem les files i columnes
                    if (linia < mat.length) {
                        isFilaCoberta[linia] = true; //es una fila
                    } else if (linia < 2 * mat.length) {
                        isColumnaCoberta[linia - mat.length] = true; // es una columna
                    }
                }


                for (int i = 0; i < mat.length; i++) { //sumem nomes els elements que estan coberts
                    for (int j = 0; j < mat[i].length; j++) {
                        if (isFilaCoberta[i] || isColumnaCoberta[j]) {
                            mat[i][j] += minNoCobert;
                            if (isFilaCoberta[i] && isColumnaCoberta[j]) { //pot ser que estigui cobert deus vegades, per tant hem de sumar el valor dues vegades
                                mat[i][j] += minNoCobert;
                            }
                        }
                    }
                }
            }

            /**
             * Retorna l'element mínim de la matriu.
             *
             * @param mat La matriu de la qual es vol trobar l'element mínim.
             * @return El valor mínim trobat dins de la matriu.
             */
            private int minimMat(int[][] mat) {

                int min = Integer.MAX_VALUE;
                for (int i = 0; i < mat.length; i++)
                    for (int j = 0; j < mat[i].length; j++)
                        if (mat[i][j] < min) min = mat[i][j];

                return min;
            }

            /**
             * Resta un valor donat a tots els elements de la matriu.
             *
             * @param mat La matriu a la qual se li restarà el valor.
             * @param val El valor a restar de cada element de la matriu.
             */
            private void restaMatriu(int[][] mat, int val) {
                for (int i = 0; i < mat.length; i++) {
                    for (int j = 0; j < mat[i].length; j++)
                        mat[i][j] -= val;
                }
            }

            /**
             * Retorna el mínim nombre de línies necessàries per cobrir tots els zeros de la matriu.
             *
             * @param mat La matriu sobre la qual es realitza l'operació.
             * @return Un array d'enters que representa les línies necessàries per cobrir tots els zeros.
             */
            private int[] minLiniesCobrirZeros(int[][] mat) {
                boolean[][] assignacioFiles = new boolean[mat.length][mat[0].length];
                boolean[] filesSeleccionades = assignacioCompletaDeFiles(mat, assignacioFiles);
                boolean[] filesMarcades = filesMarcadesDeSeleccionades(filesSeleccionades);
                boolean[] columnesMarcades = new boolean[mat[0].length];

                while (!totsZerosCoberts(mat, filesMarcades, columnesMarcades)) {
                    marcarColumnesAmb0AFilaMarcada(mat, filesMarcades, columnesMarcades);
                    marcarFilesAmbAssignacioAColumnaMarcada(mat, filesMarcades, columnesMarcades, assignacioFiles);
                }
                return liniesSolucio(filesMarcades, columnesMarcades);
            }

            /**
             * Determina si tots els zeros de la matriu estan coberts per les files marcades i les columnes no marcades.
             *
             * @param mat              La matriu sobre la qual es realitza la comprovació.
             * @param filesMarcades    Un array de booleans que indica les files marcades.
             * @param columnesMarcades Un array de booleans que indica les columnes marcades.
             * @return True si tots els zeros estan coberts adequadament, false en cas contrari.
             */
            public boolean totsZerosCoberts(int[][] mat, boolean[] filesMarcades, boolean[] columnesMarcades) {
                for (int i = 0; i < mat.length; i++) {
                    for (int j = 0; j < mat[i].length; j++) {
                        if (mat[i][j] == 0 && filesMarcades[i] && !columnesMarcades[j]) {
                            return false;
                        }
                    }
                }
                return true;
            }

            /**
             * Crea una matriu de booleans que representa l'assignació de zeros en cada fila i columna.
             *
             * @param mat La matriu sobre la qual es realitza l'assignació.
             * @return Una matriu de booleans on cada posició amb un zero assignat és marcada com a true.
             */
            private boolean[][] trobaAssignacioZeroFilaColumna(int[][] mat) {
                boolean[][] selectedMat = new boolean[mat.length][mat[0].length];
                return selectedMat;
            }

            /**
             * Inverteix la selecció de files, marcant les files no seleccionades.
             *
             * @param filesSeleccionades Un array de booleans que indica les files seleccionades.
             * @return Un array de booleans amb les files no seleccionades marcades.
             */
            private boolean[] filesMarcadesDeSeleccionades(boolean[] filesSeleccionades) {
                boolean[] filesMarcades = new boolean[filesSeleccionades.length];
                for (int i = 0; i < filesSeleccionades.length; ++i)
                    filesMarcades[i] = !filesSeleccionades[i];
                return filesMarcades;
            }

            /**
             * Marca les columnes no marcades que contenen un zero en alguna de les files marcades.
             *
             * @param mat              La matriu sobre la qual es realitza l'operació.
             * @param filesMarcades    Un array de booleans que indica les files marcades.
             * @param columnesMarcades Un array de booleans que indica les columnes marcades.
             * @return True si s'ha marcat alguna columna addicional, false en cas contrari.
             */
            private boolean marcarColumnesAmb0AFilaMarcada(int[][] mat, boolean[] filesMarcades, boolean[] columnesMarcades) {
                boolean haCanviat = false;
                for (int i = 0; i < mat.length; i++) {
                    if (filesMarcades[i]) {
                        for (int j = 0; j < mat[0].length; j++) {
                            if (mat[i][j] == 0 && !columnesMarcades[j]) {
                                columnesMarcades[j] = true;
                                haCanviat = true;
                            }
                        }
                    }
                }
                return haCanviat;
            }

            /**
             * Marca les files no marcades que contenen un zero en alguna de les columnes marcades.
             *
             * @param mat              La matriu sobre la qual es realitza l'operació.
             * @param filesMarcades    Un array de booleans que indica les files marcades.
             * @param columnesMarcades Un array de booleans que indica les columnes marcades.
             * @param assignacioFiles  Matriu de booleans que indica l'assignació actual de zeros a files i columnes.
             * @return True si s'ha marcat alguna fila addicional, false en cas contrari.
             */
            private boolean marcarFilesAmbAssignacioAColumnaMarcada(int[][] mat, boolean[] filesMarcades, boolean[] columnesMarcades, boolean[][] assignacioFiles) {
                boolean haCanviat = false;
                for (int j = 0; j < mat[0].length; j++) {
                    if (columnesMarcades[j]) {
                        for (int i = 0; i < mat.length; i++) {
                            if (assignacioFiles[i][j] && !filesMarcades[i]) {
                                filesMarcades[i] = true;
                                haCanviat = true;
                            }
                        }
                    }
                }

                return haCanviat;
            }

            /**
             * Transforma la selecció de files i columnes a línies per a l'algorisme Hongarès.
             *
             * @param filesMarcades    Un array de booleans que indica les files marcades.
             * @param columnesMarcades Un array de booleans que indica les columnes marcades.
             * @return Un array d'enters que representa les línies seleccionades.
             */
            private int[] liniesSolucio(boolean[] filesMarcades, boolean[] columnesMarcades) {
                int mida = filesMarcades.length;
                int[] linies = new int[mida * 2];

                int indexL = 0;
                for (int i = 0; i < mida; i++) {
                    if (!filesMarcades[i]) {
                        linies[indexL] = i;
                        indexL++;
                    }
                }

                for (int j = 0; j < mida; j++) {
                    if (columnesMarcades[j]) {
                        linies[indexL] = j + mida;
                        indexL++;
                    }
                }

                return Arrays.copyOf(linies, indexL);

            }

            /**
             * Retorna una assignació completa de files basada en la matriu donada.
             *
             * @param mat              La matriu sobre la qual es realitza l'assignació.
             * @param assignacioFiles  Matriu de booleans que indica l'assignació actual de zeros a files i columnes.
             * @return Un array de booleans que indica les files seleccionades en la millor assignació trobada.
             */
            private boolean[] assignacioCompletaDeFiles(int[][] mat, boolean[][] assignacioFiles) {
                boolean[] filesSeleccionades = new boolean[mat.length];
                boolean[][] assignacioFiles0 = new boolean[mat.length][mat[0].length];
                boolean[] maxSolucio = new boolean[mat.length];
                boolean[] colSeleccionades = new boolean[mat[0].length];
                int[] maxTrobat = new int[1]; // maxTrobat[0] = 0;
                trobarMaxFilesZero(mat, colSeleccionades, filesSeleccionades, maxSolucio, 0, 0, maxTrobat, assignacioFiles0, assignacioFiles);

                return maxSolucio;
            }


            /**
             * Troba la màxima assignació de files que cobreix tots els zeros de la matriu.
             *
             * @param mat               La matriu sobre la qual es realitza l'assignació.
             * @param colSeleccionades  Un array de booleans que indica les columnes seleccionades.
             * @param filesSeleccionades Un array de booleans que indica les files seleccionades.
             * @param maxSolucio        Un array de booleans que emmagatzema la millor solució trobada fins al moment.
             * @param fila              La fila actual on es troba l'algorisme.
             * @param nFilesSeleccionades El nombre de files seleccionades fins al moment.
             * @param maxTrobat         Un array d'enters que emmagatzema el nombre màxim de files trobades fins al moment.
             * @param assignacioFiles   Matriu de booleans que indica l'assignació actual de zeros a files i columnes.
             * @param assignacioFilesFinal Matriu de booleans que emmagatzema la millor assignació de zeros trobada.
             */
            private void trobarMaxFilesZero(int[][] mat, boolean[] colSeleccionades, boolean[] filesSeleccionades, boolean[] maxSolucio, int fila, int nFilesSeleccionades, int[] maxTrobat, boolean[][] assignacioFiles, boolean[][] assignacioFilesFinal) {
                int n = mat.length;
                if (fila == n) {
                    if (maxTrobat[0] < nFilesSeleccionades) {
                        maxTrobat[0] = nFilesSeleccionades;
                        for (int i = 0; i < assignacioFiles.length; i++) {
                            assignacioFilesFinal[i] = Arrays.copyOf(assignacioFiles[i], assignacioFiles[i].length);
                        }
                        System.arraycopy(filesSeleccionades, 0, maxSolucio, 0, filesSeleccionades.length);
                    }
                    return;
                }
                if (n - fila + nFilesSeleccionades < maxTrobat[0] || maxTrobat[0] == n) {
                    return;
                }

                for (int col = 0; col < mat[fila].length; col++) {
                    if (mat[fila][col] == 0 && !colSeleccionades[col]) {
                        colSeleccionades[col] = true;
                        filesSeleccionades[fila] = true;
                        assignacioFiles[fila][col] = true;
                        trobarMaxFilesZero(mat, colSeleccionades, filesSeleccionades, maxSolucio, fila + 1, nFilesSeleccionades + 1, maxTrobat, assignacioFiles, assignacioFilesFinal);
                        colSeleccionades[col] = false;
                        filesSeleccionades[fila] = false;
                        assignacioFiles[fila][col] = false;

                        if (maxTrobat[0] == n) break;
                    }
                }
                trobarMaxFilesZero(mat, colSeleccionades, filesSeleccionades, maxSolucio, fila + 1, nFilesSeleccionades, maxTrobat, assignacioFiles, assignacioFilesFinal);
            }
        }
    }
}
