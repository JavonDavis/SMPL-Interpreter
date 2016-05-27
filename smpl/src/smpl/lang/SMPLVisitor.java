package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public interface SMPLVisitor<S, T> {

    //Program
    T visitSMPLProgram(SMPLProgram smplProgram, S arg) throws SMPLException;

    //Top-level stuff
    T visitSMPLStatement(SMPLStatement smplStatement, S arg) throws SMPLException;
    T visitSMPLStmtDefinition(SMPLStmtDefinition smplStmtDefinition, S arg) throws SMPLException;
    T visitSMPLStmtSequence(SMPLStmtSequence smplStmtSequence, S arg) throws SMPLException;

    //Built-in functions
    T visitVectorExp(VectorExp vectorExp, S arg) throws SMPLException;
    T visitReadExp(ReadExp rExp, S arg) throws SMPLException;
    T visitReadIntExp(ReadIntExp rIntExp, S arg) throws SMPLException;
    T visitLetStmt(LetStmt letStmt, S arg) throws SMPLException;
    T visitSMPLFunctionCallExp(SMPLFunctionCallExp smplFunctionCallExp, S arg) throws SMPLException;

    T visitProcExp(ProcExp procExp, S arg) throws SMPLException;
    T visitIfExp(IfExp ifExp, S arg) throws SMPLException;
    T visitLazyExp(LazyExp lazyExp, S arg) throws SMPLException;
    T visitCompoundExp(CompoundExp compoundExp, S arg) throws SMPLException;
    T visitCallExp(CallExp callExp, S arg) throws SMPLException;
    T visitBooleanExp(BooleanExp booleanExp, S arg) throws SMPLException;
    T visitPrintStmt(PrintStmt printStmt, S arg) throws SMPLException;
    T visitPrintLnStmt(PrintLnStmt printLnStmt, S arg) throws SMPLException;
    T visitCaseExp(CaseExp caseExp, S arg) throws SMPLException;
    T visitGeneratorExp(GeneratorExp generatorExp, S arg) throws SMPLException;

    T visitExpSub(ExpSub expSub, S arg) throws SMPLException;
    T visitExpAdd(ExpAdd expAdd, S arg) throws SMPLException;
    T visitExpDiv(ExpDiv expDiv, S arg) throws SMPLException;
    T visitExpLit(ExpLit expLit, S arg) throws SMPLException;
    T visitExpMod(ExpMod expMod, S arg) throws SMPLException;
    T visitExpMul(ExpMul expMul, S arg) throws SMPLException;
    T visitExpVar(ExpVar expVar, S arg) throws SMPLException;

    T visitStringLiteral(StringLit strLit, S arg) throws SMPLException;
    T visitUnnamedFunctionCall(UnnamedFunctionCall ufc, S arg) throws SMPLException;
    T visitListLiteral(ListLiteral ll, S arg) throws SMPLException;

    T visitExpBitAnd(ExpBitAnd expBitAnd, S arg) throws SMPLException;
    T visitExpBitNot(ExpBitNot expBitNot, S arg) throws SMPLException;
    T visitExpBitOr(ExpBitOr expBitOr, S arg) throws SMPLException;
    T visitExpCmp(ExpCmp expCmp, S arg) throws SMPLException;
    T visitExpNot(ExpNot expNot, S arg) throws SMPLException;
    T visitExpAnd(ExpAnd expAnd, S arg) throws SMPLException;
    T visitExpOr(ExpOr expOr, S arg) throws SMPLException;

    T visitBinOrHexExp(BinHexExp binHexExp, S arg);
}
