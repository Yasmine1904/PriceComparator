import java.text.Normalizer;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;


public class Service
{

    public Map<String, Product> optimizeBasket(List<String> basket, List<Product> allProducts) {
        Map<String, Product> result = new LinkedHashMap<>();

        for (String itemName : basket) {
            String normItem = normalize(itemName);

            List<Product> matches = allProducts.stream()
                    .filter(p -> normalize(p.getProductName()).contains(normItem))
                    .collect(Collectors.toList());

            if (!matches.isEmpty()) {
                Product cheapest = matches.stream()
                        .min(Comparator.comparingDouble(Product::getPrice))
                        .orElse(null);

                result.put(itemName, cheapest);
            } else {
                result.put(itemName, null);
            }
        }

        return result;
    }



    public List<Discount> getTopActiveDiscounts(List<Discount> allDiscounts, int limit) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allDiscounts.stream()
                .filter(d -> {
                    try {
                        String rawFrom = d.getFromDate().trim();
                        String rawTo = d.getToDate().trim();

                        LocalDate from = LocalDate.parse(rawFrom, formatter);
                        LocalDate to = LocalDate.parse(rawTo, formatter);

                        return !today.isBefore(from) && !today.isAfter(to);
                    } catch (Exception e) {
                        System.err.println("Eroare la parsare date pentru discount " + d.getProductId());
                        return false;
                    }
                })
                .sorted(Comparator.comparingInt(Discount::getPercentageOfDiscount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Discount> getNewDiscounts(List<Discount> allDiscounts) {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allDiscounts.stream()
                .filter(d -> {
                    LocalDate from = LocalDate.parse(d.getFromDate(), formatter);
                    return !from.isBefore(yesterday);
                })
                .sorted(Comparator.comparing(Discount::getFromDate).reversed())
                .collect(Collectors.toList());
    }

    public List<PriceHistoryEntry> getFilteredPriceHistory(List<Product> allProducts,
                                                           String storeFilter,
                                                           String brandFilter,
                                                           String categoryFilter) {
        return allProducts.stream()
                .filter(p -> storeFilter == null ||
                        normalize(p.getStoreName()).contains(normalize(CsvDataLoader.extractStoreShortName(storeFilter))))
                .filter(p -> brandFilter == null ||
                        normalize(p.getBrand()).contains(normalize(brandFilter)))
                .filter(p -> categoryFilter == null ||
                        normalize(p.getProductCategory()).contains(normalize(categoryFilter)))
                .map(p -> new PriceHistoryEntry(
                        p.getProductId(),
                        p.getProductName(),
                        p.getStoreName(),
                        p.getDate(),
                        p.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public List<Product> getBestValueProducts(List<Product> products) {
        return products.stream()
                .filter(p -> p.getPrice() > 0 && p.getPackageQuantity() > 0)
                .collect(Collectors.groupingBy(
                        p -> normalize(p.getProductName()),
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparingDouble(p -> p.getPrice() / p.getPackageQuantity())),
                                Optional::get
                        )
                ))
                .values().stream()
                .sorted(Comparator.comparing(p -> normalize(p.getProductName())))
                .collect(Collectors.toList());
    }

    public List<PriceAlert> getTriggeredAlerts(List<PriceAlert> alerts, List<Product> products) {
        List<PriceAlert> triggered = new ArrayList<>();

        for (PriceAlert alert : alerts) {
            String targetId = alert.getProductId();
            double targetPrice = alert.getTargetPrice();

            products.stream()
                    .filter(p -> p.getProductId().equalsIgnoreCase(targetId))
                    .filter(p -> p.getPrice() <= targetPrice)
                    .findFirst()
                    .ifPresent(p -> {
                        alert.setTriggered(true);
                        triggered.add(alert);
                    });
        }

        return triggered;
    }

    public static String normalize(String text) {
        if (text == null) return "";
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .toLowerCase()
                .trim();
    }
}