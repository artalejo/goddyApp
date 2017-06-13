package goodyapp.example.com.goodyapp.presentation.views.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goodyapp.example.com.goodyapp.R;
import goodyapp.example.com.goodyapp.presentation.models.Good;

import static goodyapp.example.com.goodyapp.Utils.getPriceDescription;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodViewHolder> {

    private final AppCompatActivity activity;
    private AddGoodToBasketCallback addGoodToBasketCallback;
    private Context context;

    private List<Good> goods;

    public GoodsAdapter(Context context, List<Good> goods, AddGoodToBasketCallback callback) {
        this.context = context;
        this.activity = (AppCompatActivity) context;
        this.goods = goods;
        this.addGoodToBasketCallback = callback;
    }

    public void updateGoodsAdapter(List<Good> goods){
        this.goods = goods;
        notifyDataSetChanged();
    }

    @Override
    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_row, parent, false);
        GoodViewHolder viewHolder = new GoodViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GoodViewHolder holder, final int position) {

        Good good = goods.get(position);

        int goodImageID = context.getResources().getIdentifier(good.getResourceName() ,
                            "drawable", context.getPackageName());
        holder.goodImage.setBackgroundResource(goodImageID);
        holder.goodName.setText(good.getName());
        holder.priceDescription.setText(getPriceDescription(activity, good));
        holder.goodQuantity.setText(good.getQuantityStr());
    }

    @Override
    public int getItemCount() {
        if (goods != null)
            return goods.size();
        return 0;
    }


    public class GoodViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.good_image) ImageView goodImage;
        @BindView(R.id.good_name) TextView goodName;
        @BindView(R.id.price_description) TextView priceDescription;
        @BindView(R.id.good_quantity) TextView goodQuantity;
        @BindView(R.id.add_good) ImageView addGood;
        @BindView(R.id.remove_good) ImageView removeGood;
        @BindView(R.id.add_to_basket) ImageView addToBasket;

        public GoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.add_good)
        public void addGoodOnClick() {
            int pos = getAdapterPosition();
            Good good = goods.get(pos);
            good.addGoodQuantity();
            goodQuantity.setText(good.getQuantityStr());
        }

        @OnClick(R.id.remove_good)
        public void removeGoodOnClick() {
            int pos = getAdapterPosition();
            Good good = goods.get(pos);
            good.removeGoodQuantity();
            goodQuantity.setText(good.getQuantityStr());
        }

        @OnClick(R.id.add_to_basket)
        public void addToBasketGoodOnClick() {
            int pos = getAdapterPosition();
            Good goodToAdd = goods.get(pos);
            if (goodToAdd.getQuantity() > 0) {
                addGoodToBasketCallback.addGoodToBasket(new Good(goodToAdd));
                // Initializing the quantity when adding to the basket
                goodToAdd.setQuantity(0);
                goodQuantity.setText(goodToAdd.getQuantityStr());
            }
        }
    }

    public interface AddGoodToBasketCallback {
        void addGoodToBasket(Good goodToAdd);
    }
}
