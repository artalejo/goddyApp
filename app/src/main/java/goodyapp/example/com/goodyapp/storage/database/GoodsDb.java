package goodyapp.example.com.goodyapp.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goodyapp.example.com.goodyapp.presentation.models.Good;

public class GoodsDb extends SQLiteOpenHelper {

    //Singleton
    private static final String DATABASE_NAME = "goodsDb";
    private static final int DATABASE_VERSION = 1;

    // GOODS TABLE
    private static final String GOODS_TABLE = "goods";
    private static final String GOOD_ID = "id";
    private static final String GOOD_NAME = "name";
    private static final String GOOD_EUR_PRICE = "price";
    private static final String GOOD_EUR_PRICE_DESCRIPTION = "price_description";
    private static final String GOOD_RESOURCE = "resource";

    // BASKET TABLE
    private static final String BASKET_TABLE = "basket";
    private static final String BASKET_GOOD_ID = "good_id";
    private static final String BASKET_GOOD_QUANTITY= "quantity";

    // CURRENCIES TABLE
    private static final String CURRENCIES_TABLE = "currencies";
    private static final String CURRENCY_NAME = "name";
    private static final String CURRENCY_VALUE = "value";

    private static GoodsDb dbInstance = null;

    public static synchronized GoodsDb getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new GoodsDb(context);
        }
        return dbInstance;
    }

    private GoodsDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String CREATE_GOODS_TABLE = "CREATE TABLE goods (" +
            " id INT PRIMARY KEY," +
            " name TEXT," +
            " price INT," +
            " price_description TEXT," +
            " resource TEXT)";

    private static String CREATE_BASKET_TABLE = "CREATE TABLE basket (" +
            " good_id TEXT," +
            " quantity INT," +
            " FOREIGN KEY(good_id) REFERENCES goods(id))";


    private static String CREATE_CURRENCY_VALUES_TABLE = "CREATE TABLE currencies (" +
            " name TEXT PRIMARY KEY," +
            " value INT)";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_GOODS_TABLE);
        db.execSQL(CREATE_BASKET_TABLE);
        db.execSQL(CREATE_CURRENCY_VALUES_TABLE);

        // Inserting the hardcoded goods when creating the db.
        this.insertGood(db, new Good.Builder().id(0).name("Beans").price(0.73).priceDescription("per can").resourceName("beans").build());
        this.insertGood(db, new Good.Builder().id(1).name("Eggs").price(2.1).priceDescription("per dozen").resourceName("eggs").build());
        this.insertGood(db, new Good.Builder().id(2).name("Milk").price(1.4).priceDescription("per bottle").resourceName("milk").build());
        this.insertGood(db, new Good.Builder().id(3).name("Peas").price(0.95).priceDescription("per bag").resourceName("peas").build());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // DB FUNCTIONS
    public boolean insertGood(SQLiteDatabase db, Good good) {
        if (good == null)
            return false;

        ContentValues goodValue = new ContentValues();
        goodValue.put(GOOD_ID, good.getId());
        goodValue.put(GOOD_NAME, good.getName());
        goodValue.put(GOOD_EUR_PRICE, good.getPrice());
        goodValue.put(GOOD_EUR_PRICE_DESCRIPTION, good.getPriceDescription());
        goodValue.put(GOOD_RESOURCE, good.getResourceName());
        db.insert(GOODS_TABLE, null, goodValue);
        return true;
    }

    public boolean addGoodToBasket(Good good) {

        if (good == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        int goodQuantityFromBasket = getGoodQuantityFromBasket(db, good.getId());

        if (goodQuantityFromBasket > 0)
            // In case the good is already in the basket, we add the new quantity and update it.
            return updateGoodQuantity(good.getId(), goodQuantityFromBasket + good.getQuantity());
        else
            return insertNewGoodToBasket(db, good);
    }

    private boolean insertNewGoodToBasket(SQLiteDatabase db, Good good) {
        ContentValues goodValue = new ContentValues();
        goodValue.put(BASKET_GOOD_ID, good.getId());
        goodValue.put(BASKET_GOOD_QUANTITY, good.getQuantity());
        db.insert(BASKET_TABLE, null, goodValue);
        return true;
    }

    private boolean updateGoodQuantity(long goodID, int quantity) {

        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE basket" +
                " SET quantity = '" + quantity + "'" +
                " WHERE good_id = '" + goodID + "'";

        try {
            db.execSQL(updateQuery);
        }
        catch (SQLException e){
            return false;
        }

        return true;
    }

    private int getGoodQuantityFromBasket(SQLiteDatabase db, long goodID) {

        String getQuantityQuery = "SELECT quantity FROM basket WHERE good_id = '" + goodID + "'";
        Cursor cursor = db.rawQuery(getQuantityQuery, null);

        int goodQuantity = 0;
        if (cursor.moveToFirst()) {
            goodQuantity = cursor.getInt(0);
        }
        cursor.close();

        return goodQuantity;
    }

    public boolean removeGoodFromBasket(Good good) {
        if (good == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(BASKET_TABLE, BASKET_GOOD_ID + " = ?",
                new String[]{String.valueOf(good.getId())});

        return delete == 1;
    }

    public ArrayList<Good> getGoods(String currencySelected){
        SQLiteDatabase db = this.getReadableDatabase();

        double currencyValueAgainstEuro = getCurrencyValue(db, currencySelected);
        int noQuantity = 0;

        ArrayList<Good> goods = new ArrayList<>();
        String getGoodsQuery = "SELECT id, name, price, price_description, resource FROM goods";
        Cursor cursor = db.rawQuery(getGoodsQuery, null);

        try {
            while (cursor.moveToNext()) {
                long goodID = cursor.getLong(0);
                String goodName = cursor.getString(1);
                // We calculate on the fly the value of the goods depending of the currency
                double goodPrice = cursor.getDouble(2) * currencyValueAgainstEuro;
                String priceDescription = cursor.getString(3);
                String resourceName = cursor.getString(4);
                goods.add(new Good.Builder().id(goodID).name(goodName).price(goodPrice).priceDescription(priceDescription).
                        resourceName(resourceName).quantity(noQuantity).currency(currencySelected).build());
            }
        } finally {
            cursor.close();
        }
        return goods;
    }

    public List<Good> getBasketGoods(String currencySelected) {
        SQLiteDatabase db = this.getReadableDatabase();

        double currencyValueAgainstEuro = getCurrencyValue(db, currencySelected);

        ArrayList<Good> basketGoods = new ArrayList<>();
        String getGoodsQuery =
                "SELECT g.id, g.name, g.price, g.price_description, g.resource, b.quantity" +
                    " FROM goods as g" +
                    " JOIN basket as b" +
                    " ON g.id = b.good_id" +
                    " ORDER BY b.quantity;";

        Cursor cursor = db.rawQuery(getGoodsQuery, null);

        try {
            while (cursor.moveToNext()) {
                long goodID = cursor.getLong(0);
                String goodName = cursor.getString(1);
                // We calculate on the fly the value of the goods depending of the currency
                double goodPrice = cursor.getDouble(2) * currencyValueAgainstEuro;
                String priceDescription = cursor.getString(3);
                String resourceName = cursor.getString(4);
                int goodQuantity = cursor.getInt(5);
                basketGoods.add(new Good.Builder().id(goodID).name(goodName).price(goodPrice).priceDescription(priceDescription).
                        resourceName(resourceName).quantity(goodQuantity).currency(currencySelected).build());
            }
        } finally {
            cursor.close();
        }
        return basketGoods;
    }

    public void insertCurrencies(HashMap<String, Double> currencies) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Map.Entry<String, Double> c : currencies.entrySet()) {
            insertCurrency(db, c.getKey(), c.getValue());
        }
    }

    private void insertCurrency(SQLiteDatabase db, String currencyName, double currencyValue) {
        ContentValues currencyContent = new ContentValues();
        currencyContent.put(CURRENCY_NAME, currencyName);
        currencyContent.put(CURRENCY_VALUE, currencyValue);
        db.insertWithOnConflict(CURRENCIES_TABLE, null, currencyContent, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private double getCurrencyValue(SQLiteDatabase db, String currencyName) {
        // By default the value of euro is 1
        if (currencyName.equals("EUR"))
            return 1;

        String getCurrencyValueQuery = "SELECT value FROM currencies WHERE name = '" + currencyName + "'";
        Cursor cursor = db.rawQuery(getCurrencyValueQuery, null);

        double currencyValue = 0;

        if (cursor.moveToFirst()) {
            currencyValue = cursor.getDouble(0);
        }
        cursor.close();

        return currencyValue;
    }
}
