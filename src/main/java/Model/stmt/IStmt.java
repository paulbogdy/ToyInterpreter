package Model.stmt;

import Model.ADT.IDict;
import Model.expression.*;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.util.List;

public interface IStmt {
    public List<PrgState> execute(PrgState state);
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv);
    //region Examples
    IStmt ex11 = new CompStmt(new VarDecl("a", IntType.INTEGER), new VarDecl("a", IntType.INTEGER));

    IStmt ex1 = new CompStmt(new VarDecl("v", BoolType.BOOLEAN),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    IStmt ex2 = new CompStmt(new VarDecl("a", IntType.INTEGER),
            new CompStmt(new VarDecl("b", IntType.INTEGER),
                    new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                            new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),
                                    ArithExp.Op.MULTIPLICATION), ArithExp.Op.PLUS)),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), ArithExp.Op.PLUS)),
                                    new PrintStmt(new VarExp("b"))))));

    IStmt ex3 = new CompStmt(new VarDecl("a", BoolType.BOOLEAN),
            new CompStmt(new VarDecl("v", IntType.INTEGER),
                    new CompStmt(new AssignStmt("a", new ValueExp(BoolValue.TRUE)),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v"))))));

    IStmt ex4 = new CompStmt(new VarDecl("varf", StringType.STRING),
            new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                    new CompStmt(new OpenRFile(new VarExp("varf")),
                            new CompStmt(new VarDecl("varc", IntType.INTEGER),
                                    new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                            new CompStmt(new PrintStmt(new VarExp("varc")),
                                                    new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                            new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                    new CloseRFile(new VarExp("varf"))))))))));
    IStmt ex5 = new CompStmt(new VarDecl("v", new RefType(IntType.INTEGER)),
            new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDecl("a", new RefType(new RefType(IntType.INTEGER))),
                            new CompStmt(new HeapAlloc("a", new VarExp("v")),
                                    new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("v")))))));
    IStmt ex6 = new CompStmt(new VarDecl("v", new RefType(IntType.INTEGER)),
            new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDecl("a", new RefType(new RefType(IntType.INTEGER))),
                            new CompStmt(new HeapAlloc("a", new VarExp("v")),
                                    new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                                            new PrintStmt(new ArithExp(new ReadHeap(new ReadHeap(new VarExp("a"))),
                                                    new ValueExp(new IntValue(5)), ArithExp.Op.PLUS)))))));
    IStmt ex7 = new CompStmt(new VarDecl("v", new RefType(IntType.INTEGER)),
            new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                            new CompStmt(new HeapWriting("v", new ValueExp(new IntValue(30))),
                                    new PrintStmt(new ArithExp(new ReadHeap(new VarExp("v")),
                                            new ValueExp(new IntValue(5)),ArithExp.Op.PLUS))))));
    IStmt ex8 = new CompStmt(new VarDecl("v", new RefType(IntType.INTEGER)),
            new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDecl("a", new RefType(new RefType(IntType.INTEGER))),
                            new CompStmt(new HeapAlloc("a", new VarExp("v")),
                                    new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a")))))))));
    IStmt ex9 = new CompStmt(new VarDecl("v", IntType.INTEGER),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                    new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)),
                            RelationalExp.Op.GREATER), new CompStmt(new PrintStmt(new VarExp("v")),
                            new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)),
                                    ArithExp.Op.MINUS)))),
                            new PrintStmt(new VarExp("v")))));
    IStmt ex10 = new CompStmt(new VarDecl("v", IntType.INTEGER),
            new CompStmt(new VarDecl("a", new RefType(IntType.INTEGER)),
                    new CompStmt(new HeapAlloc("a", new ValueExp(new IntValue(22))),
                            new ForkStmt(new CompStmt(new HeapWriting("a", new ValueExp(new IntValue(30))),
                                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                    new PrintStmt(new ReadHeap(new VarExp("a"))))))))));
    //endregion
}
