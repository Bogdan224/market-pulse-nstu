package academy.backend.market_pulse.filter;

import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Instrument;

public class CurencyFilter implements InstrumentFilter {
    private Currency currency;

    public CurencyFilter(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean matches(Instrument instrument) {
        return instrument.getCurrency().equals(currency);
    }
}
