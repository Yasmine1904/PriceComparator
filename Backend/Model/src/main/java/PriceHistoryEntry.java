import com.opencsv.bean.CsvBindByName;

public class PriceHistoryEntry {

    @CsvBindByName(column = "product_id")
    private String productId;

    @CsvBindByName(column = "product_name")
    private String productName;

    private String storeName;
    private String date;

    @CsvBindByName(column = "price")
    private double price;

    public PriceHistoryEntry() {
    }

    public PriceHistoryEntry(String productId, String productName, String storeName, String date, double price) {
        this.productId = productId;
        this.productName = productName;
        this.storeName = storeName;
        this.date = date;
        this.price = price;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "PriceHistoryEntry{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}