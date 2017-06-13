package goodyapp.example.com.goodyapp.presentation.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goodyapp.example.com.goodyapp.R;
import goodyapp.example.com.goodyapp.domain.executor.impl.ThreadExecutor;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.presentation.models.Rates;
import goodyapp.example.com.goodyapp.presentation.presenters.GoodsPresenter;
import goodyapp.example.com.goodyapp.presentation.presenters.impl.GoodsPresenterImpl;
import goodyapp.example.com.goodyapp.presentation.views.adapters.GoodsAdapter;
import goodyapp.example.com.goodyapp.presentation.views.listeners.BasketMenuItemStuffListener;
import goodyapp.example.com.goodyapp.storage.GoodsRepositoryImpl;
import goodyapp.example.com.goodyapp.threading.MainThreadImpl;

import static goodyapp.example.com.goodyapp.Constants.CURRENCY_SELECTED;

public class GoodsActivity extends AppCompatActivity implements GoodsPresenter.View, GoodsAdapter.AddGoodToBasketCallback {

    @BindView(R.id.goods_toolbar) Toolbar goodsToolbar;
    @BindView(R.id.goods_recycler) RecyclerView goodsRecycler;
    @BindView(R.id.goods_progress_linear) LinearLayout goodsProgressBar;
    // Text view that will display the number of items that the basket has.
    private TextView basketMenuCounter;
    public static int basketItems = 0;

    private GoodsAdapter goodsAdapter;
    private GoodsPresenterImpl goodsPresenter;
    private List<Good> goods;
    private String currencySelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        currencySelected = "EUR"; // By default eur is selected

        if (goodsToolbar != null) {
            goodsToolbar.setTitle(R.string.goods_activity_toolbar_title);
            setSupportActionBar(goodsToolbar);
        }

        goodsRecycler.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        goods = new ArrayList<>();
        goodsAdapter = new GoodsAdapter(this, goods, this);
        goodsRecycler.setAdapter(goodsAdapter);

        goodsPresenter = new GoodsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this, new GoodsRepositoryImpl(this), currencySelected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        goodsPresenter.showGoods();
        // Getting the basket size to initialize the counter of items in the basket
        goodsPresenter.getBasketSize();
        goodsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_menu, menu);
        final View basketMenu = menu.findItem(R.id.basket_item).getActionView();
        basketMenuCounter = (TextView) basketMenu.findViewById(R.id.basket_counter);
        updateBasketCount(basketItems);
        new BasketMenuItemStuffListener(basketMenu, "Show hot message") {
            @Override
            public void onClick(View v) {
                startBasketActivity();
            }
        };
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.currency_item:
                showCurrencyDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateBasketCount(final int newBasketCount) {

        basketItems = newBasketCount;

        if (basketMenuCounter == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (newBasketCount == 0)
                    basketMenuCounter.setVisibility(View.INVISIBLE);
                else {
                    basketMenuCounter.setVisibility(View.VISIBLE);
                    basketMenuCounter.setText(Integer.toString(newBasketCount));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Removing all activities when moving to the back
        ActivityCompat.finishAffinity(this);
        moveTaskToBack(true);
    }

    private void startBasketActivity() {
        Intent basketActivityIntent = new Intent(GoodsActivity.this, BasketActivity.class);
        basketActivityIntent.putExtra(CURRENCY_SELECTED, currencySelected);
        startActivity(basketActivityIntent);
    }

    private void showCurrencyDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select Currency:");

        final ArrayAdapter<Rates> arrayAdapter =
                new ArrayAdapter<>(this, R.layout.currency_row, Rates.values());


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currencySelected = arrayAdapter.getItem(which).name();
                updateGoodPricesWithNewCurrency();
                dialog.dismiss();
            }
        });
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.show();
    }

    private void updateGoodPricesWithNewCurrency() {
        goodsPresenter.setCurrency(currencySelected);
        goodsPresenter.showGoods();
        goodsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGoodsRetrieved(List<Good> goods) {
        this.goods = goods;
        if (goodsAdapter != null)
            goodsAdapter.updateGoodsAdapter(goods);
        goodsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBasketGoodsSizeRetrieved(int basketSize) {
        // Initializing the basket size
        GoodsActivity.basketItems = basketSize;
        updateBasketCount(basketItems);
    }

    @Override
    public void onGoodAdded(boolean added) {
        Toast.makeText(this, R.string.good_added_to_basket_msg, Toast.LENGTH_SHORT).show();
        GoodsActivity.basketItems++;
        updateBasketCount(basketItems);
    }

    @Override
    public void onGoodRemovedFromBasket(boolean removed) {
        Toast.makeText(this, R.string.good_removed_from_basket_msg, Toast.LENGTH_SHORT).show();
        GoodsActivity.basketItems--;
        updateBasketCount(basketItems);
    }

    @Override
    public void addGoodToBasket(Good goodToAdd) {
        // Callback from the adapter that will add a good to the basket
        goodsPresenter.addGoodToBasket(goodToAdd);
    }
}
