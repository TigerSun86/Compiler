package asgn1;

public class Example {
    // Stm :: a:=5+3; b := (print (a, a-1), 10*a); print(b)
    // Out ::
    //        8 7
    //        80
    static Stm a_program = 
       new CompoundStm(new AssignStm("a",new OpExp(new NumExp(5), OpExp.Plus, new NumExp(3))),
         new CompoundStm(new AssignStm("b",
            new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
               new LastExpList(new OpExp(new IdExp("a"), OpExp.Minus, new NumExp(1))))),
                  new OpExp(new NumExp(10), OpExp.Times, new IdExp("a")))),
                     new PrintStm(new LastExpList(new IdExp("b")))));

    // Stm :: a:=5+3; b := (print ((print (a, a-1, a-2), 10*a), a-1), 10*a);
    // Out ::
    // 3
    // 8 7
    // 80 7 6
    static Stm another_program = new CompoundStm(new AssignStm("a", new OpExp(
            new NumExp(5), OpExp.Plus, new NumExp(3))), new AssignStm("b",
            new EseqExp(new PrintStm(new PairExpList(new EseqExp(new PrintStm(
                    new PairExpList(new IdExp("a"), new LastExpList(new OpExp(
                            new IdExp("a"), OpExp.Minus, new NumExp(1))))),
                    new OpExp(new NumExp(10), OpExp.Times, new IdExp("a"))),
                    new PairExpList(new OpExp(new IdExp("a"), OpExp.Minus,
                            new NumExp(1)), new LastExpList(new OpExp(
                            new IdExp("a"), OpExp.Minus, new NumExp(2)))))),
                    new OpExp(new NumExp(10), OpExp.Times, new IdExp("a")))));
}
