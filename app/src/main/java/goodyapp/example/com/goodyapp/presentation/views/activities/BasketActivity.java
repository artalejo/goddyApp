package goodyapp.example.com.goodyapp.presentation.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goodyapp.example.com.goodyapp.R;
import goodyapp.example.com.goodyapp.domain.executor.impl.ThreadExecutor;
import goodyapp.example.com.goodyapp.presentation.models.Good;
import goodyapp.example.com.goodyapp.presentation.presenters.BasketPresenter;
import goodyapp.example.com.goodyapp.presentation.presenters.impl.BasketPresenterImpl;
import goodyapp.example.com.goodyapp.presentation.views.adapters.BasketGoodsAdapter;
import goodyapp.example.com.goodyapp.storage.GoodsRepositoryImpl;
import goodyapp.example.com.goodyapp.threading.MainThreadImpl;

import static goodyapp.example.com.goodyapp.Constants.CURRENCY_SELECTED;
import static goodyapp.example.com.goodyapp.Utils.getCheckoutTotalMessage;

public class BasketActivity extends AppCompatActivity implements BasketGoodsAdapter.RemoveGoodFromBasketCallback,
        BasketPresenter.View {

    @BindView(R.id.basket_toolbar) Toolbar basketToolbar;
    @BindView(R.id.basket_goods_recycler) RecyclerView basketRecycler;
    @BindView(R.id.basket_goods_checkout) Button basketCheckout;
    private String currencySelected;
    private List<Good> basketGoods;
    private BasketGoodsAdapter basketGoodsAdapter;
    private BasketPresenterImpl basketPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            currencySelected = extras.getString(CURRENCY_SELECTED);

        if (basketToolbar != null) {
            basketToolbar.setTitle(R.string.basket_activity_toolbar_title);
            setSupportActionBar(basketToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        basketRecycler.setLayoutManager(new LinearLayoutManager(this));

        basketGoods = new ArrayList<>();
        basketGoodsAdapter = new BasketGoodsAdapter(this, basketGoods, this);
        basketRecycler.setAdapter(basketGoodsAdapter);

        basketPresenter = new BasketPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this, new GoodsRepositoryImpl(this), currencySelected);
    }

    @OnClick(R.id.basket_goods_checkout)
    public void checkoutOnClick() {
        showCheckoutDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        basketPresenter.showBasketGoods();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void removeGoodFromBasket(Good goodToRemove) {
        // Callback from adapter to delete the good from the basket
        basketPresenter.removeGoodFromBasket(goodToRemove);
    }

    @Override
    public void onBasketGoodsRetrieved(List<Good> goods) {
        this.basketGoods = goods;
        if (basketGoodsAdapter != null)
            basketGoodsAdapter.updateBasketGoodsAdapter(basketGoods);
    }

    @Override
    public void onGoodRemovedFromBasket(boolean removed) {
        Toast.makeText(this, R.string.good_removed_from_basket_msg, Toast.LENGTH_SHORT).show();
        GoodsActivity.basketItems--;
    }

    private void showCheckoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BasketActivity.this);
        builder.setTitle(getString(R.string.checkout_dialog_title));
        builder.setMessage(getCheckoutTotalMessage(this, basketGoods));

        String positiveText = getString(R.string.checkout_buy);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BasketActivity.this, R.string.checkout_completed,
                                       Toast.LENGTH_LONG).show();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
