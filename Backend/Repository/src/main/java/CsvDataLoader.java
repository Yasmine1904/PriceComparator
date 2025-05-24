import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvDataLoader
{
    private static final String PRODUCT_DIR = "src/main/resources/data/products/";
    private static final String DISCOUNT_DIR = "src/main/resources/data/discounts/";
    private static final String ALERT_DIR = "src/main/resources/data/priceAlerts/";

    public List<Discount> loadDiscounts(String filePath) {
        String storeName = extractStoreNameOnly(filePath);

        try {
            List<Discount> discounts = new CsvToBeanBuilder<Discount>(new FileReader(filePath))
                    .withType(Discount.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            discounts.forEach(d -> d.setStoreName(storeName));

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<PriceAlert> loadPriceAlerts(String filePath) {
        try {
            return new CsvToBeanBuilder<PriceAlert>(new FileReader(filePath))
                    .withType(PriceAlert.class)
                    .withSeparator(',')
                    .build()
                    .parse();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<PriceHistoryEntry> loadPriceHistoryFromProducts(List<Product> products) {
        return products.stream()
                .map(p -> new PriceHistoryEntry(
                        p.getProductId(),
                        p.getProductName(),
                        p.getStoreName(),
                        p.getDate(),
                        p.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public List<Product> loadAllProducts() {
        File root = new File(PRODUCT_DIR);
        List<Product> result = new ArrayList<>();

        File[] entries = root.listFiles();
        if (entries == null) return result;

        for (File entry : entries) {
            if (entry.isDirectory()) {
                for (File file : entry.listFiles()) {
                    if (file.getName().endsWith(".csv")) {
                        result.addAll(loadProductsWithStoreName(file.getPath()));
                    }
                }
            } else if (entry.getName().endsWith(".csv")) {
                result.addAll(loadProductsWithStoreName(entry.getPath()));
            }
        }

        return result;
    }


    public static String extractStoreShortName(String pathOrStoreName) {
        String name = pathOrStoreName.replace("\\", "/");
        String fileName = name.substring(name.lastIndexOf('/') + 1);
        if (fileName.contains("_")) {
            return fileName.split("_")[0];
        }
        return fileName;
    }


    public List<Product> loadProductsWithStoreName(String filePath) {
        String[] nameParts = extractStoreAndDate(filePath);
        String storeName = nameParts[0];
        String date = nameParts[1];

        System.out.println("LOADED → " + storeName + " from file: " + filePath);

        try {
            List<Product> products = new CsvToBeanBuilder<Product>(new FileReader(filePath))
                    .withType(Product.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            products.forEach(p -> {
                p.setStoreName(storeName);
                p.setDate(date);
            });

            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void saveAlertsToCsv(List<PriceAlert> alerts, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8)) {
            writer.println("user_id,product_id,target_price,triggered"); // header

            for (PriceAlert alert : alerts) {
                writer.printf("%s,%s,%.2f,%b%n",
                        alert.getUserId(),
                        alert.getProductId(),
                        alert.getTargetPrice(),
                        alert.isTriggered()
                );
            }
            System.out.println("Alertele au fost salvate în: " + filePath);
        } catch (Exception e) {
            System.err.println("Eroare la salvarea alertelor: " + e.getMessage());
        }
    }

    public List<Discount> loadAllDiscounts() {
        File folder = new File(DISCOUNT_DIR);
        List<Discount> result = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".csv")) {
                result.addAll(loadDiscounts(file.getPath()));
            }
        }

        return result;
    }

    public List<PriceAlert> loadAllAlerts() {
        File folder = new File(ALERT_DIR);
        List<PriceAlert> result = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".csv")) {
                result.addAll(loadPriceAlerts(file.getPath()));
            }
        }

        return result;
    }


    private String[] extractStoreAndDate(String filePath) {
        // Exemplu: lidl_2025-05-08.csv
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1).replace(".csv", "");
        String[] parts = fileName.split("_");
        return new String[]{parts[0], parts[1]};
    }

    private String extractStoreNameOnly(String filePath) {
        // Exemplu: kaufland_discounts_2025-05-01.csv
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1).replace(".csv", "");
        return fileName.split("_")[0];
    }
}