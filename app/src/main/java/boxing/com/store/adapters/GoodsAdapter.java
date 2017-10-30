package boxing.com.store.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import boxing.com.store.R;
import boxing.com.store.base.BaseRecyclerAdapter;
import boxing.com.store.sql.Goods;
import boxing.com.store.utils.TimeUtil;
import boxing.com.store.utils.UIUtils;

/**
 * Created by 首页货物列表 on 2017/9/15.
 */

public class GoodsAdapter extends BaseRecyclerAdapter<Goods>{
    public GoodsAdapter(List<Goods> total) {
        super(total);
    }

    @Override
    public BaseViewHolder<Goods> getViewHolder(ViewGroup parent) {
        View view = UIUtils.inflate(R.layout.item_goods, parent, false);
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends BaseViewHolder<Goods> {
        private TextView name;
        private TextView price;
        private TextView num;
        private TextView time;

        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        protected void initView(View itemView) {
            name = (TextView)itemView.findViewById(R.id.item_name);
            price = (TextView)itemView.findViewById(R.id.item_price);
            num = (TextView)itemView.findViewById(R.id.item_num);
            time = (TextView)itemView.findViewById(R.id.item_time);
        }

        @Override
        protected void setData(Goods data) {
            name.setText(data.getName());
            price.setText(data.getPrice()+"");
            num.setText(data.getInto_num()+"");
            long time = data.getTime();
            this.time.setText(TimeUtil.getChinaDateTime(time));
        }
    }
}
