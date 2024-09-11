import org.testng.annotations.Test;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        Board b = new Board("123456780"); // Estado inicial para teste
        List<Ilayout> children = b.children();

        // Espera-se 2 filhos para o estado "123456780"
        assertEquals(2, children.size());

        // Testa se os filhos estão corretos
        boolean hasChild1 = false;
        boolean hasChild2 = false;

        for (Ilayout child : children) {
            String childStr = child.toString();
            if (childStr.equals("123456708")) { // Movimento para a direita
                hasChild1 = true;
            } else if (childStr.equals("123456807")) { // Movimento para baixo
                hasChild2 = true;
            }
        }

        assertTrue(hasChild1);
        assertTrue(hasChild2);
    }


}
