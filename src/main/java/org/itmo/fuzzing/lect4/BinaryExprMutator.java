package org.itmo.fuzzing.lect4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryExprMutator extends Mutator {

    List<BinaryExpr.Operator> operators =
            Arrays.asList(
                    BinaryExpr.Operator.PLUS,
                    BinaryExpr.Operator.MINUS,
                    BinaryExpr.Operator.MULTIPLY,
                    BinaryExpr.Operator.DIVIDE
            );


    public BinaryExprMutator(int mutationLocation) {
        super(mutationLocation);
    }

    @Override
    public Node mutationVisit(Node node, int ind) {
        if (node instanceof BinaryExpr && operators.contains(((BinaryExpr) node).getOperator())) {
            var operatorsWithoutOriginal =
                    operators.stream().filter(operator -> !operator.equals(((BinaryExpr) node).getOperator())).toList();
            var newOperator = operatorsWithoutOriginal.get(ind);
            return new BinaryExpr(((BinaryExpr) node).getLeft(), ((BinaryExpr) node).getRight(), newOperator);
        }
        return node;
    }

    @Override
    public int getMutationsPerStatement() {
        return 3;
    }

    @Override
    public Visitable visit(BinaryExpr n, Void arg) {
        return mutableVisit(n);
    }
}
