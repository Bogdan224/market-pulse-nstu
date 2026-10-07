package academy.backend.market_pulse.cli;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;

import academy.backend.market_pulse.converter.InstrumentFilterConverter;
import academy.backend.market_pulse.filter.*;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.repository.InstrumentRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", description = "Список инструментов")
public class ListCommand implements Callable<Integer> {
    public enum ListCommandFilter {
        WITHOUT, BY_TYPE, BY_TICKER, BY_CURRENCY, BY_PRICE
    }

    @Option(names = "--type", description = "Фильтр по типу инструмента (STOCK, BOND, ETF)")
    private String type;

    @Option(names = "--ticker", description = "Фильтр по тикеру")
    private String ticker;

    @Option(names = "--currency", description = "Фильтр по валюте (RUB, USD, EUR)")
    private Currency currency;

    @Option(names = "--price", description = "Фильтр по цене акции")
    private BigDecimal price;

    @Option(names = "--price-op", description = "Оператор, определяющий логику сравнения (GE - >=, EQ - ==, LE - <=, LS - <, GR - >)")
    private PriceFilter.EqualsOperator price_op;

    private final InstrumentRepository repository;

    public ListCommand(InstrumentRepository repository) {
        this.repository = repository;
    }

    private Map.Entry<ListCommandFilter, List<Object>> getRule() {
        List<ListCommandFilter> rules = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (type != null) {
            rules.add(ListCommandFilter.BY_TYPE);
            params.add(type);
        }
        if (ticker != null) {
            rules.add(ListCommandFilter.BY_TICKER);
            params.add(ticker);
        }
        if (currency != null) {
            rules.add(ListCommandFilter.BY_CURRENCY);
            params.add(currency);
        }
        if (price != null || price_op != null) {
            if (price != null && price_op != null) {
                rules.add(ListCommandFilter.BY_PRICE);
                params.addAll(List.of(price, price_op));
            } else {
                throw new IllegalArgumentException("Для фильтра по цене нужно заполнить 2 параметра: price и price-op!");
            }
        }

        if (rules.isEmpty()) {
            rules.add(ListCommandFilter.WITHOUT);
        }
        if (rules.size() > 1) {
            throw new IllegalArgumentException("У команды должен быть 1 параметр!");
        }

        return new AbstractMap.SimpleEntry<>(rules.getFirst(), params);
    }

    @Override
    public Integer call() {
        try {
            var pair = getRule();
            var filter = InstrumentFilterConverter.convert(pair.getKey(), pair.getValue());

            int ind = 1;
            for (var instrument : repository) {
                if (filter.matches(instrument)) {
                    if (ind == 1) {
                        System.out.println("Список отфильтрованных инструментов:");
                    }
                    System.out.println(ind + ". " + instrument.toString());
                    ind += 1;
                }
            }

            if (ind == 1) {
                System.out.println("Инструменты по заданному фильтру отсутствуют!");
            }

            return 0;
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
            return 1;
        }
    }
}
