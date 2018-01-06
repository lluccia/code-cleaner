import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class GettersAndSettersUselessDefaultCommentsCleanerVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        if (n.getJavadocComment().isPresent())
            if(isDefaultComment(n.getJavadocComment().get().getContent()))
                n.removeJavaDocComment();
    }

    boolean isDefaultComment(String comment) {
        return comment.matches("\\s*\\* @return the \\w+\\s*")
                || comment.matches("\\s*\\* @param \\w+ the \\w+ to set\\s*");
    }
}
