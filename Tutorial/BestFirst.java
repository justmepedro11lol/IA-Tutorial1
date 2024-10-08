
import java.util.*;

class BestFirst {

    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {

        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null) {
                g = father.g + l.getK();
            } else {
                g = 0.0;
            }
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        fechados = new HashMap<>();
        abertos.add(new State(s, null));

        while (!abertos.isEmpty()) {
            // Remove o estado com menor custo g da fila
            actual = abertos.poll();

            // Verifica se atingiu o objetivo
            if (actual.layout.isGoal(objective)) {
                // Reconstruir o caminho da solução
                List<State> solution = new ArrayList<>();
                State temp = actual;
                while (temp != null) {
                    solution.add(0, temp); // Adiciona no início da lista para formar o caminho
                    temp = temp.father;
                }
                return solution.iterator(); // Retorna o iterador sobre a solução
            }

            // Se o estado atual não for o objetivo, adiciona aos fechados
            fechados.put(actual.layout, actual);

            // Gera sucessores
            List<State> sucs = sucessores(actual);
            for (State suc : sucs) {
                if (!fechados.containsKey(suc.layout)) {
                    abertos.add(suc); // Adiciona sucessores não explorados à fila
                }
            }
        }

        return null; // Se a fila ficar vazia e o objetivo não foi encontrado, retorna null
    }

}
