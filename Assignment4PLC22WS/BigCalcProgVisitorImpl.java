/**
 * @author <>
 * @id matrnr
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class BigCalcProgVisitorImpl extends BigCalcProgBaseVisitor<BigDecimal> {
    private final Map<String, BigDecimal> variableValueMap = new HashMap<>();

    @Override
    public BigDecimal visitExpressionStatement(BigCalcProgParser.ExpressionStatementContext ctx) {
        BigDecimal result = BigDecimal.ZERO;
        for(BigCalcProgParser.StatementContext statement : ctx.statement()){
            result = visit(statement);
        }
        return result;
    }

    @Override
    public BigDecimal visitStatementExpr(BigCalcProgParser.StatementExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitStatementAssign(BigCalcProgParser.StatementAssignContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public BigDecimal visitVariable(BigCalcProgParser.VariableContext ctx){
        BigDecimal value = variableValueMap.get(ctx.Variable().getText());
        if(value == null){
            return BigDecimal.ZERO;
        }
        return value;
    }

    @Override
    public BigDecimal visitParentheses(BigCalcProgParser.ParenthesesContext ctx){
        return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitAssignment(BigCalcProgParser.AssignmentContext ctx){
        BigDecimal value = visit(ctx.expression());
        variableValueMap.put(ctx.Variable().getText(), value);
        return value;
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcProgParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("*")) {
            return left.multiply(right);
        } else {
            return left.divide(right, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public BigDecimal visitAddSub(BigCalcProgParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("+")) {
            return left.add(right);
        } else {
            return left.subtract(right);
        }
    }

    @Override
    public BigDecimal visitNum(BigCalcProgParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }
}
