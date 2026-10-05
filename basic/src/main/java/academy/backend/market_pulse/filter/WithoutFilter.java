package academy.backend.market_pulse.filter;

import academy.backend.market_pulse.model.Instrument;

public class WithoutFilter implements InstrumentFilter {
    public WithoutFilter() { }

    @Override
    public boolean matches(Instrument instrument) {
        return true;
    }
}
