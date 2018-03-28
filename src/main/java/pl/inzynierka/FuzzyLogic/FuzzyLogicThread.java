package pl.inzynierka.FuzzyLogic;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.activation.General;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.defuzzifier.WeightedAverage;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.AlgebraicProduct;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import java.time.LocalDate;

public class FuzzyLogicThread {

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.setName("ObstacleAvoidance");
        engine.setDescription("");

        String firstInputName = "INPUT";
        String firstOutputName = "OUTPUT";

        InputVariable INPUT = new InputVariable();
        INPUT.setName("INPUT");
        INPUT.setDescription("");
        INPUT.setEnabled(true);
        INPUT.setRange(0.00, 50.00);
        INPUT.setLockValueInRange(false);
        INPUT.addTerm(new Trapezoid("A", 0.0, 0.0, 20.0, 30.0));
        INPUT.addTerm(new Triangle("B", 20.0, 30.0, 40.0));
        INPUT.addTerm(new Trapezoid("C", 30.0, 40.0, 50.0, 50.0));
        engine.addInputVariable(INPUT);

        OutputVariable OUTPUT = new OutputVariable();
        OUTPUT.setName("OUTPUT");
        OUTPUT.setDescription("");
        OUTPUT.setEnabled(true);
        OUTPUT.setRange(0.00, 100.00);
        OUTPUT.setLockValueInRange(false);
        OUTPUT.setAggregation(new Maximum());
        OUTPUT.setDefuzzifier(new Centroid(100));
        OUTPUT.setDefaultValue(Double.NaN);
        OUTPUT.setLockPreviousValue(false);
        OUTPUT.addTerm(new Triangle("D", 0.00, 0.00, 50.00));
        OUTPUT.addTerm(new Triangle("E", 20.00, 60.00,80.00));
        OUTPUT.addTerm(new Triangle("F", 60.00, 100.00,100.00));
        engine.addOutputVariable(OUTPUT);

        RuleBlock RULE = new RuleBlock();
        RULE.setName("RULE");
        RULE.setDescription("");
        RULE.setEnabled(true);
        RULE.setConjunction(new Minimum());
        RULE.setDisjunction(new Maximum());
        RULE.setImplication(new Minimum());
        RULE.setActivation(new General());
        RULE.addRule(Rule.parse(String.format("if %s is A then %s is D", firstInputName, firstOutputName), engine));
        RULE.addRule(Rule.parse("if INPUT is B then OUTPUT is E", engine));
        RULE.addRule(Rule.parse("if INPUT is C then OUTPUT is F", engine));
        engine.addRuleBlock(RULE);

        StringBuilder status = new StringBuilder();
        if (! engine.isReady(status))
            throw new RuntimeException("[engine error] engine is not ready:n" + status);

        InputVariable obstacler = engine.getInputVariable("INPUT");
        OutputVariable steer = engine.getOutputVariable("OUTPUT");
        RuleBlock ruleBlock = engine.getRuleBlock("RULE");

        obstacler.setValue(25);
        engine.process();
        FuzzyLite.logger().info(String.format(
                "obstacle.input = %s -> steer.output = %s",
                Op.str(33), Op.str(steer.getValue())));
    }

}
