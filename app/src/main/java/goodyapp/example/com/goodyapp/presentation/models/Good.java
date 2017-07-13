package goodyapp.example.com.goodyapp.presentation.models;

public class Good {

    private final long id;
    private final String currency;
    private int quantity;
    private final String name;
    private final double price;
    private final String priceDescription;
    // I have added this field to map with the hardcoded resources.
    // This should be a url instead of a hardcoded resource.
    private final String resourceName;

    // Copy constructor
    public Good(Good goodToCopy) {

        this.id = goodToCopy.getId();
        this.name = goodToCopy.getName();
        this.price = goodToCopy.getPrice();
        this.priceDescription = goodToCopy.getPriceDescription();
        this.resourceName = goodToCopy.getResourceName();
        this.quantity = goodToCopy.getQuantity();
        this.currency = "EUR";
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

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public long getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public String getResourceName() {
        return resourceName;
    }

    public static class Builder {

        private long id;
        private String currency;
        private int quantity;
        private String name;
        private double price;
        private String priceDescription;
        // I have added this field to map with the hardcoded resources.
        // This should be a url instead of a hardcoded resource.
        private String resourceName;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder priceDescription(String priceDescription) {
            this.priceDescription = priceDescription;
            return this;
        }

        public Builder resourceName(String resourceName) {
            this.resourceName = resourceName;
            return this;
        }

        public Good build() {
            return new Good(this);
        }

    }

    private Good(Builder builder) {
        id = builder.id;
        currency = builder.currency;
        quantity = builder.quantity;
        name = builder.name;
        price = builder.price;
        priceDescription = builder.priceDescription;
        resourceName = builder.resourceName;
    }

}
