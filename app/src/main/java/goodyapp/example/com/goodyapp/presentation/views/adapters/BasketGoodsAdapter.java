package goodyapp.example.com.goodyapp.presentation.views.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goodyapp.example.com.goodyapp.R;
import goodyapp.example.com.goodyapp.presentation.models.Good;

import static goodyapp.example.com.goodyapp.Utils.getGoodPriceSummary;

public class BasketGoodsAdapter extends RecyclerView.Adapter<BasketGoodsAdapter.GoodViewHolder> {

    private final DecimalFormat priceFormatter;
    private RemoveGoodFromBasketCallback removeGoodFromBasketCallback;
    private Context context;
    private AppCompatActivity activity;

    private List<Good> goods;

    public BasketGoodsAdapter(Context context, List<Good> goods, RemoveGoodFromBasketCallback callback) {
        this.context = context;
        this.activity = (AppCompatActivity) context;
        this.goods = goods;
        this.removeGoodFromBasketCallback = callback;
        this.priceFormatter = new DecimalFormat("#0.00");
    }

    public void updateBasketGoodsAdapter(List<Good> goods){
        this.goods = goods;
        notifyDataSetChanged();
    }

    @Override
    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_good_row, parent, false);
        GoodViewHolder viewHolder = new GoodViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GoodViewHolder holder, final int position) {

        Good good = goods.get(position);

        int goodImageID = context.getResources().getIdentifier(good.getResourceName() ,
                            "drawable", context.getPackageName());
        holder.basketGoodImage.setBackgroundResource(goodImageID);
        holder.basketGoodName.setText(good.getName());
        holder.basketPriceSummary.setText(getGoodPriceSummary(activity, good));

        double basketTotalPrice  = good.getPrice() * good.getQuantity();
        holder.basketGoodTotalPrice.setText(priceFormatter.format(basketTotalPrice));
    }

    @Override
    public int getItemCount() {
        if (goods != null)
            return goods.size();
        return 0;
    }


    public class GoodViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.basket_good_image) ImageView basketGoodImage;
        @BindView(R.id.basket_good_name) TextView basketGoodName;
        @BindView(R.id.basket_price_summary) TextView basketPriceSummary;
        @BindView(R.id.basket_good_total_price) TextView basketGoodTotalPrice;
        @BindView(R.id.basket_remove_good) ImageView basketRemoveGood;

        public GoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.basket_remove_good)
        public void removeGoodOnClick() {
            int pos = getAdapterPosition();
            Good goodToRemove = goods.get(pos);
            removeGoodFromBasketCallback.removeGoodFromBasket(goodToRemove);
            goods.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public interface RemoveGoodFromBasketCallback {
        void removeGoodFromBasket(Good goodToRemove);
    }
}
