package goodyapp.example.com.goodyapp.presentation.models;

public class Good {

    private long id;
    private String currency;
    private int quantity;
    private String name;
    private double price;
    private String priceDescription;
    // I have added this field to map with the hardcoded resources.
    // This should be a url instead of a hardcoded resource.
    private String resourceName;

    public Good(long id, String name, double price,
                String priceDescription, String resourceName) {

        this.currency = "EUR";
        this.quantity = 0;
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceDescription = priceDescription;
        this.resourceName = resourceName;
    }

    public Good(long id, String name, double price,
                String priceDescription, String resourceName, int quantity,
                String currency) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.priceDescription = priceDescription;
        this.resourceName = resourceName;
        this.quantity = quantity;
        this.currency = currency;
    }

    public Good(Good goodToCopy) {

        this.id = goodToCopy.getId();
        this.name = goodToCopy.getName();
        this.price = goodToCopy.getPrice();
        this.priceDescription = goodToCopy.getPriceDescription();
        this.resourceName = goodToCopy.getResourceName();
        this.quantity = goodToCopy.getQuantity();
    }

    public void addGoodQuantity(){
        this.quantity++;
    }

    public void removeGoodQuantity(){
        if (this.quantity > 0)
            this.quantity--;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getQuantityStr() {
        return String.valueOf(this.quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
