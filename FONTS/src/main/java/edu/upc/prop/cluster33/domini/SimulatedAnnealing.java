package edu.upc.prop.cluster33.domini;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe SimulatedAnnealing que implementa l'algorisme d'optimització Simulated Annealing per a generar layouts de teclats.
 * L'algorisme busca la millor disposició de tecles basant-se en les freqüències d'ús i la proximitat entre les tecles.
 */
public class SimulatedAnnealing extends Algorisme{
    /**
     * Matriu de distàncies utilitzada en l'algoritme per a determinar la distància entre tecles.
     */
    private int[][] matriuDistancies;
    /**
     * Matriu de proximitat utilitzada en l'algoritme per a determinar la proximitat entre tecles.
     */
    private int[][] matriuProximitat;
    /**
     * Temperatura inicial de l'algoritme de Simulated Annealing.
     */
    private double temperatura = 1000;
    /**
     * Taxa de refredament utilitzada en l'algoritme per a reduir la temperatura a cada iteració.
     */
    private final double coolingRate = 0.05;
    /**
     * Nombre d'iteracions que l'algoritme realitzarà.
     */
    private final int iteracions = 1000;

    /**
     * Retorna el nom de l'algorisme.
     * @return String representant el nom de l'algorisme.
     */
    @Override
    public String getNom() {
        return "SimulatedAnnealing";
    }

    /**
     * Inicialitza les matrius de distàncies i proximitat.
     */
    @Override
    public void init() {
        matriuDistancies = new int[0][0];
        matriuProximitat = new int[0][0];
    }

    /**
     * Genera un layout de teclat basant-se en les freqüències de les paraules.
     * @param frequencies Objecte Frequencies que conté les freqüències de les paraules.
     * @param columnes Nombre de columnes del teclat.
     * @param files Nombre de files del teclat.
     * @return Layout de teclat en forma de matriu de caràcters.
     */
    @Override
    public char[][] generarLayout(Frequencies frequencies, int columnes, int files) {
        inicialitzarMatriuProximitat(frequencies);
        inicialitzarMatriuDistancies(frequencies);
        int[] res = CalculSolucioParcial();
        sa(Arrays.stream(res).boxed().collect(Collectors.toList()));
        return creaLayoutAmbSolucio((ArrayList<Integer>)Arrays.stream(res).boxed().collect(Collectors.toList()), columnes, files, frequencies);
    }

    /**
     * Inicialitza la matriu de distàncies basant-se en les freqüències.
     * @param frequencies Objecte Frequencies que conté les freqüències de les paraules.
     */
    private void inicialitzarMatriuDistancies(Frequencies frequencies) {
        Alfabet alfabet = frequencies.getAlfabet();
        int alfabetSize = alfabet.getMida();
        int[][] matriuDist = new int[alfabetSize][alfabetSize];

        //int[] teclesLlunyanes = new int[matriuProximitat.length];

        for (int i = 0; i < alfabetSize; i++) {
            int xi = i / alfabetSize;
            int yi = i % alfabetSize;

            for (int j = 0; j < alfabetSize; j++) {
                int xj = j / alfabetSize;
                int yj = j % alfabetSize;

                int dx = xj - xi;
                int dy = yj - yi;
                int dist = (int) Math.sqrt(dx * dx + dy * dy)*100;
                matriuDist[i][j] = dist;
                if (i < matriuProximitat.length && j < matriuProximitat.length) {
                    //teclesLlunyanes[i] += dist;
                    //teclesLlunyanes[j] += dist;
                }
            }
        }

        matriuDistancies = matriuDist;
        //return indexsArrayOrdenat(teclesLlunyanes);
    }

    /**
     * Inicialitza la matriu de proximitat basant-se en les freqüències de les paraules.
     * @param frequencies Objecte Frequencies que conté les freqüències de les paraules.
     */
    private void inicialitzarMatriuProximitat(Frequencies frequencies){
        TreeMap<String,Integer> freq = frequencies.getLlistaFrequencies();
        Alfabet alfabet = frequencies.getAlfabet();
        int alfabetSize = alfabet.getMida();
        //int[] caractersPocFrequents = new int[alfabetSize];
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
                    //caractersPocFrequents[indexEsquerra] += paraulaFreq;
                    //caractersPocFrequents[indexDreta] += paraulaFreq;
                }
            }
        }
        matriuProximitat = matriuProx;
        //return indexsArrayOrdenat(caractersPocFrequents);
    }

    /**
     * Executa l'algorisme de Simulated Annealing per optimitzar la solució parcial.
     * @param SolucioParcial Llista de Integers representant la solució parcial.
     */
    private void sa(List<Integer> SolucioParcial) {
        //List<Integer> costs = new ArrayList<Integer>();

        while (temperatura > 0.05) {

            for(int i = 0; i < iteracions; i++) {

                List<Integer> novaSolucio = new ArrayList<>(SolucioParcial);
                Random rand = new Random();

                int pos1 = rand.nextInt(SolucioParcial.size());
                int pos2 = rand.nextInt(SolucioParcial.size());

                while (pos1 == pos2)
                    pos2 = rand.nextInt(SolucioParcial.size());

                int swap1 = novaSolucio.get(pos1);
                int swap2 = novaSolucio.get(pos2);

                novaSolucio.set(pos2, swap1);
                novaSolucio.set(pos1, swap2);

                int costActual = cost(SolucioParcial);
                int sostAlternatiu = cost(novaSolucio);

                double random = rand.nextDouble();

                if ((acceptanceProbability(costActual, sostAlternatiu, temperatura) > random)) {
                    SolucioParcial = novaSolucio;
                }
            }
            temperatura *= (1 - coolingRate);
        }
    }

    /**
     * Calcula la probabilitat d'acceptació d'un canvi basant-se en la temperatura actual.
     * @param costActual Cost actual de la solució.
     * @param nouCost Nou cost de la solució proposta.
     * @param temperature Temperatura actual de l'algorisme.
     * @return Probabilitat d'acceptació del canvi.
     */
    private double acceptanceProbability(int costActual, int nouCost, double temperature) {
        if (nouCost < costActual) return 1.0;

        return Math.exp((costActual - nouCost) / temperature);
    }

    /**
     * Calcula el cost d'una solució donada.
     * @param solucio Llista d'Integers representant la solució.
     * @return Cost de la solució.
     */
    private int cost(List<Integer> solucio) {
        int cost = 0;

        for(int i = 0 ; i < matriuProximitat.length ; i++){
            for(int j = 0 ; j < matriuProximitat[0].length ; j++){
                if (matriuProximitat[i][j] != 0) {
                    int dist = matriuDistancies[solucio.indexOf(i)][solucio.indexOf(j)];
                    cost = cost + (dist * matriuProximitat[i][j]);
                }
            }
        }
        return cost;
    }

    /**
     * Calcula una solució parcial inicial.
     * @return Array d'Integers representant la solució parcial inicial.
     */
    private int[] CalculSolucioParcial() {
        int[] sumaProx = new int[matriuProximitat.length];
        int[] sumaDist = new int[matriuDistancies.length];
        int[] solucio = new int[matriuProximitat.length];

        for (int i = 0; i < matriuProximitat.length; ++i) {
            int sumaTemp = 0;
            for (int j = 0; j < matriuProximitat[0].length; ++j) {
                sumaTemp = sumaTemp + matriuProximitat[i][j];
            }
            sumaProx[i] = sumaTemp;
        }

        for (int i = 0; i < matriuDistancies.length; ++i) {
            int sumaTemp = 0;
            for (int j = 0; j < matriuDistancies[0].length; ++j) {
                sumaTemp = sumaTemp + matriuDistancies[i][j];
            }
            sumaDist[i] = sumaTemp;
        }

        for (int i = 0; i < solucio.length; ++i) {
            int j = getMaxIndex(sumaProx);
            int k = getMinIndex(sumaDist);
            solucio[Math.abs(k)] = j;
            sumaDist[Math.abs(k)] = 10000000;
            sumaProx[Math.abs(j)] = 0;
        }
        return solucio;
    }

    /**
     * Retorna l'índex de l'element màxim en un array.
     * @param array Array d'Integers.
     * @return Índex de l'element màxim.
     */
    private static int getMaxIndex(int[] array) {
        if ( array == null || array.length == 0 ) return -1; // Aixo no hauria de pasar mai

        int max = 0;
        for (int i = 1; i < array.length; i++)
            if (array[i] > array[max]) max = i;

        return max;
    }

    /**
     * Retorna l'índex de l'element mínim en un array.
     * @param array Array d'Integers.
     * @return Índex de l'element mínim.
     */
    private static int getMinIndex(int[] array) {
        int index = 0;
        int min = array[index];

        for (int i = 1; i < array.length; i++) {
            if (array[i] <= min){
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Crea un layout de teclat amb la solució donada.
     * @param solucio Llista d'Integers representant la solució.
     * @param columnes Nombre de columnes del teclat.
     * @param files Nombre de files del teclat.
     * @param frequencies Objecte Frequencies amb les freqüències de les paraules.
     * @return Layout de teclat en forma de matriu de caràcters.
     */
    private char[][] creaLayoutAmbSolucio(ArrayList<Integer> solucio, int columnes, int files, Frequencies frequencies) {
        Alfabet alfabet = frequencies.getAlfabet();
        char[][] layout = new char[files][columnes];

        for (int tecla = 0; tecla < solucio.size(); tecla++){
            int caracterIndex = solucio.get(tecla);
            layout[tecla/columnes][tecla%columnes] = alfabet.getCharAtIndex(caracterIndex);
        }
        return layout;
    }

}
