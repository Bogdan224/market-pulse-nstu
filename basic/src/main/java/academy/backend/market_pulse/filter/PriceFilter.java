package academy.backend.market_pulse.filter;

import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Stock;

import java.math.BigDecimal;

public class PriceFilter implements InstrumentFilter {
    private final BigDecimal price;
    private final EqualsOperator price_op;

    public enum EqualsOperator {
        GE, EQ, LE, LS, GR
    }

    public PriceFilter(BigDecimal price, EqualsOperator price_op) {
        this.price = price;
        this.price_op = price_op;
    }

    private boolean compareByParam(BigDecimal price, BigDecimal instrumentPrice, EqualsOperator param) {
        return switch (param) {
            case GE -> instrumentPrice.compareTo(price) >= 0;
            case EQ -> instrumentPrice.compareTo(price) == 0;
            case LE -> instrumentPrice.compareTo(price) <= 0;
            case LS -> instrumentPrice.compareTo(price) < 0;
            case GR -> instrumentPrice.compareTo(price) > 0;
        };
    }

    @Override
    public boolean matches(Instrument instrument) {
        return switch (instrument) {
            case Stock stock -> compareByParam(price, stock.getDividendYield(), price_op);
            default -> false;
        };
    }
}