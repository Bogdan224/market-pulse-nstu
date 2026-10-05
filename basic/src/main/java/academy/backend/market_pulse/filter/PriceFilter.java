package academy.backend.market_pulse.filter;

import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Stock;

import java.math.BigDecimal;

public class PriceFilter implements InstrumentFilter {
    private final BigDecimal yieldDividend;

    public PriceFilter(BigDecimal yieldDividend) {
        this.yieldDividend = yieldDividend;
    }

    @Override
    public boolean matches(Instrument instrument) {
        if(!(instrument instanceof Stock stock)) {
            return false;
        }

        return stock.getDividendYield().compareTo(yieldDividend) >= 0;
    }
}
