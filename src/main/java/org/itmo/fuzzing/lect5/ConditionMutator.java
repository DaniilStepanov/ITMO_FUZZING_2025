package org.itmo.fuzzing.lect5;

import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConditionMutator extends VoidVisitorAdapter<Void> {
    //    evaluate_condition(2, 'Eq', c, '+')
    private static int counter = 0;
    private static String methodName = "BranchDistance.evaluateCondition";

    @Override
    public void visit(IfStmt n, Void arg) {
        super.visit(n, arg);
        if (n.getCondition() instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr) n.getCondition();
            if (binaryExpr.getOperator() == BinaryExpr.Operator.AND) {
                var mapName = binaryExpr.getLeft().asMethodCallExpr().getScope().get().toString();
                var key = binaryExpr.getLeft().asMethodCallExpr().getArgument(0).toString();
                var mapName2 = binaryExpr.getRight().asMethodCallExpr().getScope().get().toString();
                var key2 = binaryExpr.getRight().asMethodCallExpr().getArgument(0).toString();
                n.setCondition(
                        new BinaryExpr(
                                new MethodCallExpr(
                                        methodName,
                                        new IntegerLiteralExpr(String.valueOf(counter++)),
                                        new StringLiteralExpr("IN"),
                                        new StringLiteralExpr(key),
                                        new StringLiteralExpr(mapName)
                                ),
                                new MethodCallExpr(
                                        methodName,
                                        new IntegerLiteralExpr(String.valueOf(counter++)),
                                        new StringLiteralExpr("IN"),
                                        new StringLiteralExpr(key2),
                                        new StringLiteralExpr(mapName2)
                                ),
                                BinaryExpr.Operator.AND
                        )
                );
            } else {
                var left = binaryExpr.getLeft();
                var right = binaryExpr.getRight();
                var op = binaryExpr.getOperator();
                n.setCondition(
                        new MethodCallExpr(
                                methodName,
                                new IntegerLiteralExpr(String.valueOf(counter++)),
                                new StringLiteralExpr(op.toString()),
                                new StringLiteralExpr(left.toString()),
                                new StringLiteralExpr(right.toString())
                        )
                );
            }
        }
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        super.visit(n, arg);
        if (n.getCondition() instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr) n.getCondition();
            var left = binaryExpr.getLeft();
            var right = binaryExpr.getRight();
            var op = binaryExpr.getOperator();
            n.setCondition(
                    new MethodCallExpr(
                            methodName,
                            new IntegerLiteralExpr(String.valueOf(counter++)),
                            new StringLiteralExpr(op.toString()),
                            new StringLiteralExpr(left.toString()),
                            new StringLiteralExpr(right.toString())
                    )
            );
        }
    }
}