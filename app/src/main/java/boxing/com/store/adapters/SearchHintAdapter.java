package boxing.com.store.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import boxing.com.store.R;
import boxing.com.store.base.BaseRecyclerAdapter;
import boxing.com.store.sql.Body;
import boxing.com.store.utils.UIUtils;

/**
 * 搜索提示框 列表的adapter
 */

public class SearchHintAdapter extends BaseRecyclerAdapter<Body> {

    public SearchHintAdapter(List<Body> total) {
        super(total);
    }

    @Override
    public BaseViewHolder<Body> getViewHolder(ViewGroup parent) {
        View itemView = UIUtils.inflate(R.layout.item_seatch_hint, parent, false);
        return new BaseViewHolder<Body>(itemView) {
            private TextView name;
            private TextView price;

            @Override
            protected void initView(View itemView) {
                name = (TextView) itemView.findViewById(R.id.body_name);
                price = (TextView) itemView.findViewById(R.id.body_price);
            }

            @Override
            protected void setData(Body data) {
                name.setText(data.getName());
                price.setText(data.getOne_price() );
            }
        };
    }
}
