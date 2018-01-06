import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class GettersAndSettersUselessDefaultCommentsCleanerTest {
    
    @Test
    public void cleanupUselessAccessorComments() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GettersAndSetters.java");

        CompilationUnit cu = JavaParser.parse(inputStream);
        cu.accept(new GettersAndSettersUselessDefaultCommentsCleanerVisitor(), null);

        assertThat(cu.toString()).isEqualTo(
                "public class GettersAndSetters {\n" +
                "\n" +
                "    private String field1;\n" +
                "\n" +
                "    private String field2;\n" +
                "\n" +
                "    public String getField1() {\n" +
                "        return field1;\n" +
                "    }\n" +
                "\n" +
                "    public void setField1(String field1) {\n" +
                "        this.field1 = field1;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Some non default comment\n" +
                "     * @return the field2\n" +
                "     */\n" +
                "    public String getField2() {\n" +
                "        return field2;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Some non default comment\n" +
                "     * @param field2 the field2 to set\n" +
                "     */\n" +
                "    public void setField2(String field2) {\n" +
                "        this.field2 = field2;\n" +
                "    }\n" +
                "}\n");
    }
    
    @Test
    public void defaultCommentMatcherTest() {
        GettersAndSettersUselessDefaultCommentsCleanerVisitor visitor =
                new GettersAndSettersUselessDefaultCommentsCleanerVisitor();

        assertThat(visitor.isDefaultComment("     * @return the field1")).isTrue();
        assertThat(visitor.isDefaultComment("     * @param field1 the field1 to set")).isTrue();

        assertThat(visitor.isDefaultComment("     * Some non default comment\n" +
                                            "     * @param field2 the field2 to set")).isFalse();
    }


}
