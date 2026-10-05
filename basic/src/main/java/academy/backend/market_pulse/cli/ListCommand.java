package academy.backend.market_pulse.cli;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import academy.backend.market_pulse.filter.*;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.repository.InstrumentRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", description = "Список инструментов")
public class ListCommand implements Callable<Integer> {

    @Option(names = "--type", description = "Фильтр по типу инструмента")
    private String type;

    @Option(names = "--ticker", description = "Фильтр по тикеру")
    private String ticker;

    @Option(names = "--currency", description = "Фильтр по валюте")
    private Currency currency;

    @Option(names = "--price", description = "Фильтр по цене акции")
    private BigDecimal price;

    private final InstrumentRepository repository;

    public ListCommand(InstrumentRepository repository) {
        this.repository = repository;
    }

    private InstrumentFilter choseInstrumentFilter() {
        List<InstrumentFilter> filters = new ArrayList<>();

        if(type != null) {
            filters.add(new TypeFilter(type));
        }
        if(ticker != null) {
            filters.add(new TickerFilter(ticker));
        }
        if(currency != null) {
            filters.add(new CurencyFilter(currency));
        }
        if(price != null) {
            filters.add(new PriceFilter(price));
        }

        if(filters.isEmpty()) {
            return new WithoutFilter();
        }
        if(filters.size() > 1) {
            throw new IllegalArgumentException("У команды должен быть 1 параметр");
        }

        return filters.getFirst();
    }

    @Override
    public Integer call() {
        try {
            InstrumentFilter filter = choseInstrumentFilter();

            int ind = 1;
            for (var instrument : repository) {
                if(filter.matches(instrument)) {
                    if(ind == 1) {
                        System.out.println("Список отфильтрованных инструментов:");
                    }
                    System.out.println(ind + ". " + instrument.toString());
                    ind += 1;
                }
            }

            if(ind == 1) {
                System.out.println("Инструменты по заданному фильтру отсутствуют!");
            }

            return 0;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
}
