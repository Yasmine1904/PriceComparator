import com.opencsv.bean.CsvBindByName;

public class PriceAlert {

    @CsvBindByName(column = "user_id")
    private String userId;

    @CsvBindByName(column = "product_id")
    private String productId;

    @CsvBindByName(column = "target_price")
    private double targetPrice;

    @CsvBindByName(column = "triggered")
    private boolean triggered;

    public PriceAlert() {
    }

    public PriceAlert(String userId, String productId, double targetPrice, boolean triggered) {
        this.userId = userId;
        this.productId = productId;
        this.targetPrice = targetPrice;
        this.triggered = triggered;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public double getTargetPrice()
    {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice)
    {
        this.targetPrice = targetPrice;
    }

    public boolean isTriggered()
    {
        return triggered;
    }

    public void setTriggered(boolean triggered)
    {
        this.triggered = triggered;
    }

    @Override
    public String toString()
    {
        return "PriceAlert{" +
                "userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", targetPrice=" + targetPrice +
                ", triggered=" + triggered +
                '}';
    }
}