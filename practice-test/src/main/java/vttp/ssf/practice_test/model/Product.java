package vttp.ssf.practice_test.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Product {
    private int id;
    private String title;
    private String description;
    private int price;
    private float discountPercentage;
    private float rating;
    private int stock;
    private String brand;
    private String category;
    private long dated;
    private int buy;

    public Product() {
    }

    public Product(int id, String title, String description, int price, float discountPercentage, float rating,
            int stock, String brand, String category, long dated, int buy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.brand = brand;
        this.category = category;
        this.dated = dated;
        this.buy = buy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDated() {
        return dated;
    }

    public void setDated(long dated) {
        this.dated = dated;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
                + ", discountPercentage=" + discountPercentage + ", rating=" + rating + ", stock=" + stock + ", brand="
                + brand + ", category=" + category + ", dated=" + dated + ", buy=" + buy + "]";
    }

    public String toJson(String json) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject j = reader.readObject();
            JsonObject jObj = Json.createObjectBuilder()
                    .add("id", j.getInt("id"))
                    .add("title", j.getString("title"))
                    .add("description", j.getString("description"))
                    .add("price", j.getInt("price"))
                    .add("discountPercentage", j.getString("discountPercentage"))
                    .add("rating", j.getString("rating"))
                    .add("stock", j.getInt("stock"))
                    .add("brand", j.getString("brand"))
                    .add("category", j.getString("category"))
                    .add("dated", j.getString("dated"))
                    .add("buy", j.getInt("buy"))
                    .build();
            return jObj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Product jsonToProduct(String json) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject j = reader.readObject();
            Product p = new Product(j.getInt("id"), j.getString("title"), j.getString("description"), j.getInt("price"),
                    (float) j.getJsonNumber("discountPercentage").doubleValue(),
                    (float) j.getJsonNumber("rating").doubleValue(), j.getInt("stock"), j.getString("brand"),
                    j.getString("category"),
                    j.getJsonNumber("dated").longValue(), j.getInt("buy"));
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJsonString() {
        JsonObject jObj = Json.createObjectBuilder()
                .add("id", this.id)
                .add("title", this.title)
                .add("description", this.description)
                .add("price", this.price)
                .add("discountPercentage", this.discountPercentage)
                .add("rating", this.rating)
                .add("stock", this.stock)
                .add("brand", this.brand)
                .add("category", this.category)
                .add("dated", this.dated)
                .add("buy", this.buy)
                .build();
        return jObj.toString();
    }
}
