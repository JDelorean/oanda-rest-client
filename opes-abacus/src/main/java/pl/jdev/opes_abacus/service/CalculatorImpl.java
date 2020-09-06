package pl.jdev.opes_abacus.service;

import org.springframework.stereotype.Service;
import pl.jdev.opes_abacus.controller.dto.CalculationType;
import pl.jdev.opes_abacus.controller.dto.InputArgs;
import pl.jdev.opes_abacus.controller.dto.MAInputArgs;
import pl.jdev.opes_abacus.domain.Result;

import java.util.Arrays;

@Service
public class CalculatorImpl implements Calculator {

    private ResultManager resultManager;

    public CalculatorImpl(final ResultManager resultManager) {
        this.resultManager = resultManager;
    }


    private CalculationType resolveType(final InputArgs inputArgs) throws RuntimeException {
        if (inputArgs instanceof MAInputArgs) {
            return ((MAInputArgs) inputArgs).getType();
        } else {
            throw new RuntimeException("Unable to resolve input type");
        }
    }

    public Result calculate(final InputArgs inputArgs) throws RuntimeException {
        CalculationType type = resolveType(inputArgs);


        Object[] res = null;
        if (CalculationType.SMA == type) {
            MAInputArgs args = (MAInputArgs) inputArgs;
            res = SimpleCalculator.mulitpleSma().apply(args.getPeriodLength(), args.getCandles());
            System.out.println(Arrays.asList(res));
        } else if (CalculationType.EMA == type) {
            MAInputArgs args = (MAInputArgs) inputArgs;
            res = SimpleCalculator.multipleEma().apply(args.getPeriodLength(), args.getCandles());
            System.out.println(Arrays.asList(res));
        }
        return resultManager.storeResult(type, res);
    }
}
