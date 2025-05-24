import com.opencsv.bean.CsvBindByName;

public class Discount {

    @CsvBindByName(column = "product_id")
    private String productId;

    @CsvBindByName(column = "product_name")
    private String productName;

    @CsvBindByName(column = "brand")
    private String brand;

    @CsvBindByName(column = "package_quantity")
    private double packageQuantity;

    @CsvBindByName(column = "package_unit")
    private String packageUnit;

    @CsvBindByName(column = "product_category")
    private String productCategory;

    private String storeName;

    @CsvBindByName(column = "from_date")
    private String fromDate;

    @CsvBindByName(column = "to_date")
    private String toDate;

    @CsvBindByName(column = "percentage_of_discount")
    private int percentageOfDiscount;

    public Discount() {
    }

    public Discount(String productId, String productName, String brand, double packageQuantity,
                    String packageUnit, String productCategory, String storeName,
                    String fromDate, String toDate, int percentageOfDiscount) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.productCategory = productCategory;
        this.storeName = storeName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentageOfDiscount = percentageOfDiscount;
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

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public double getPackageQuantity()
    {
        return packageQuantity;
    }

    public void setPackageQuantity(double packageQuantity)
    {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageUnit()
    {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit)
    {
        this.packageUnit = packageUnit;
    }

    public String getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(String productCategory)
    {
        this.productCategory = productCategory;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }

    public int getPercentageOfDiscount()
    {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(int percentageOfDiscount)
    {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    @Override
    public String toString()
    {
        return "Discount{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", packageQuantity=" + packageQuantity +
                ", packageUnit='" + packageUnit + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", storeName='" + storeName + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", percentageOfDiscount=" + percentageOfDiscount +
                '}';
    }
}