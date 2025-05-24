import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            String consoleCharset = System.getProperty("os.name").toLowerCase().contains("win") ? "CP850" : "UTF-8";
            PrintStream ps = new PrintStream(System.out, true, consoleCharset);
            System.setOut(ps);
            System.setErr(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in, System.getProperty("os.name").toLowerCase().contains("win") ? "CP850" : "UTF-8");
        CsvDataLoader loader = new CsvDataLoader();
        Service service = new Service();

        List<Product> products = loader.loadAllProducts();
        List<Discount> discounts = loader.loadAllDiscounts();
        List<PriceAlert> alerts = loader.loadAllAlerts();

        while (true) {
            System.out.println("\n=== MENIU PRINCIPAL ===");
            System.out.println("1. Listează toate produsele disponibile");
            System.out.println("2. Sugestie coș optim");
            System.out.println("3. Afișează reducerile curente");
            System.out.println("4. Nou: reduceri din ultimele 24h");
            System.out.println("5. Vizualizează istoricul prețurilor (filtre)");
            System.out.println("6. Top produse valoare per unitate");
            System.out.println("7. Alerte active de preț");
            System.out.println("8. Filtrează reducerile active");
            System.out.println("0. Închide aplicația");
            System.out.print("Selectează opțiunea dorită: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.println("\n=== PRODUSE DISPONIBILE ===");
                    CsvPrintUtils.printProductsCsv(products);
                }
                case "2" -> {
                    System.out.print("Introduceți lista de produse (ex: lapte, făină): ");
                    List<String> basket = Arrays.stream(scanner.nextLine().split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toList();
                    System.out.println("\n=== PROPUNERE COȘ OPTIM ===");
                    service.optimizeBasket(basket, products)
                            .forEach((name, p) -> System.out.printf(
                                    "- %s → %s (%.2f RON) în %s [%s]%n",
                                    name,
                                    p != null ? p.getProductName() : "-",
                                    p != null ? p.getPrice() : 0,
                                    p != null ? CsvDataLoader.extractStoreShortName(p.getStoreName()) : "-",
                                    p != null ? p.getDate() : "-"));
                }
                case "3" -> {
                    System.out.println("\n=== REDUCERI ÎN VIGOARE ===");
                    CsvPrintUtils.printDiscountsCsv(discounts);
                }
                case "4" -> {
                    System.out.println("\n=== REZULTATE ÎN ULTIMELE 24H ===");
                    CsvPrintUtils.printDiscountsCsv(service.getNewDiscounts(discounts));
                }
                case "5" -> {
                    System.out.print("Filtru magazin (sau Enter pt. toate): ");
                    String store = scanner.nextLine().trim();
                    System.out.print("Filtru brand (sau Enter): ");
                    String brand = scanner.nextLine().trim();
                    System.out.print("Filtru categorie (sau Enter): ");
                    String category = scanner.nextLine().trim();
                    System.out.println("\n=== ISTORIC PREȚURI FILTRAT ===");
                    CsvPrintUtils.printPriceHistoryCsv(
                            service.getFilteredPriceHistory(products,
                                    store.isEmpty() ? null : store,
                                    brand.isEmpty() ? null : brand,
                                    category.isEmpty() ? null : category)
                    );
                }
                case "6" -> {
                    System.out.println("\n=== TOP VALOARE/UNITATE ===");
                    service.getBestValueProducts(products)
                            .forEach(p -> System.out.printf(
                                    "- %s (%s): %.2f RON/%s în %s [%s]%n",
                                    p.getProductName(), p.getBrand(),
                                    p.getPrice() / p.getPackageQuantity(),
                                    p.getPackageUnit(),
                                    CsvDataLoader.extractStoreShortName(p.getStoreName()),
                                    p.getDate()));
                }
                case "7" -> {
                    System.out.println("\n=== ALERTE DE PREȚ ACTIVATE ===");
                    List<PriceAlert> triggered = service.getTriggeredAlerts(alerts, products);
                    if (triggered.isEmpty()) {
                        System.out.println("Nicio alertă activată.");
                    } else {
                        triggered.forEach(alert -> {
                            Product p = products.stream()
                                    .filter(prod -> prod.getProductId().equalsIgnoreCase(alert.getProductId()))
                                    .min(Comparator.comparing(Product::getPrice))
                                    .orElse(null);
                            if (p != null) {
                                System.out.printf(
                                        "- %s: %.2f RON (< %.2f) la %s [%s]%n",
                                        p.getProductName(), p.getPrice(), alert.getTargetPrice(),
                                        CsvDataLoader.extractStoreShortName(p.getStoreName()), p.getDate());
                            }
                        });
                    }
                    loader.saveAlertsToCsv(alerts, "src/main/resources/priceAlerts/price_alerts.csv");
                }
                case "8" -> {
                    System.out.print("Număr de reduceri de afișat: ");
                    int n = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("\n=== FILTRU REDUCERI ACTIVE ===");
                    CsvPrintUtils.printDiscountsCsv(service.getTopActiveDiscounts(discounts, n));
                }
                case "0" -> {
                    System.out.println("Închidere aplicație. La revedere!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opțiune necunoscută. Încercați din nou.");
            }
        }
    }
}
