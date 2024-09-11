
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PuzzleUnitTests {

    @Test
    public void testConstructor() {
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" 23");
        pw.println("145");
        pw.println("678");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testChildren() {
        // Cria um tabuleiro inicial onde o espaço vazio (0) está no meio
        Board b = new Board("123405678");

        // Obtém a lista de filhos (movimentos possíveis)
        List<Ilayout> children = b.children();

        // Verifica se a quantidade de filhos é 4 (cima, baixo, esquerda, direita)
        assertEquals(4, children.size());

        // Verifica os filhos gerados
        Board expectedChild1 = new Board("103425678"); // Mover para cima
        Board expectedChild2 = new Board("123475608"); // Mover para baixo
        Board expectedChild3 = new Board("123045678"); // Mover para a esquerda
        Board expectedChild4 = new Board("123450678"); // Mover para a direita

        // Compara os filhos gerados com os esperados
        assertEquals(expectedChild1.toString(), children.get(0).toString());
        assertEquals(expectedChild2.toString(), children.get(1).toString());
        assertEquals(expectedChild3.toString(), children.get(2).toString());
        assertEquals(expectedChild4.toString(), children.get(3).toString());
    }
}
