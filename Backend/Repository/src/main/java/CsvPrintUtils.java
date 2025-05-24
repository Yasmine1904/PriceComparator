import java.util.List;

public class CsvPrintUtils {

    public static void printProductsCsv(List<Product> products) {
        System.out.println("product_id;product_name;product_category;brand;package_quantity;package_unit;price;currency;store;date");
        for (Product p : products) {
            System.out.printf("%s;%s;%s;%s;%.2f;%s;%.2f;%s;%s;%s%n",
                    p.getProductId(),
                    p.getProductName(),
                    p.getProductCategory(),
                    p.getBrand(),
                    p.getPackageQuantity(),
                    p.getPackageUnit(),
                    p.getPrice(),
                    p.getCurrency(),
                    CsvDataLoader.extractStoreShortName(p.getStoreName()),
                    p.getDate());
        }
    }

    public static void printDiscountsCsv(List<Discount> discounts) {
        System.out.println("product_id;product_name;brand;package_quantity;package_unit;product_category;store;from_date;to_date;discount_percent");
        for (Discount d : discounts) {
            System.out.printf("%s;%s;%s;%.2f;%s;%s;%s;%s;%s;%d%n",
                    d.getProductId(),
                    d.getProductName(),
                    d.getBrand(),
                    d.getPackageQuantity(),
                    d.getPackageUnit(),
                    d.getProductCategory(),
                    CsvDataLoader.extractStoreShortName(d.getStoreName()),
                    d.getFromDate(),
                    d.getToDate(),
                    d.getPercentageOfDiscount());
        }
    }

    public static void printPriceAlertsCsv(List<PriceAlert> alerts) {
        System.out.println("user_id;product_id;target_price;triggered");
        for (PriceAlert alert : alerts) {
            System.out.printf("%s;%s;%.2f;%s%n",
                    alert.getUserId(),
                    alert.getProductId(),
                    alert.getTargetPrice(),
                    alert.isTriggered());
        }
    }

    public static void printPriceHistoryCsv(List<PriceHistoryEntry> history) {
        System.out.println("product_id;product_name;store;date;price");
        for (PriceHistoryEntry entry : history) {
            System.out.printf("%s;%s;%s;%s;%.2f%n",
                    entry.getProductId(),
                    entry.getProductName(),
                    CsvDataLoader.extractStoreShortName(entry.getStoreName()),
                    entry.getDate(),
                    entry.getPrice());
        }
    }
}