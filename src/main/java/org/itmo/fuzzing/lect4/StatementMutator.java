package org.itmo.fuzzing.lect4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.Visitable;

public class StatementMutator extends ReturnMutator {
    public StatementMutator(int mutationLocation) {
        super(mutationLocation);
    }

    @Override
    public Node mutationVisit(Node node, int ind) {
        if (node instanceof ReturnStmt) {
            return super.mutationVisit(node, ind);
        } else {
            return new BlockStmt();
        }
    }

    @Override
    public Visitable visit(ExpressionStmt n, Void arg) {
        return mutableVisit(n);
    }
}
