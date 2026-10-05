package academy.backend.market_pulse.filter;

import academy.backend.market_pulse.model.Instrument;

public class TypeFilter implements InstrumentFilter {
    private String type;

    public TypeFilter(String type){
        this.type = type;
    }

    @Override
    public boolean matches(Instrument instrument) {
        return instrument.getType().equals(type);
    }
}
