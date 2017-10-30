package boxing.com.store.interfaces;


import boxing.com.store.base.BaseRecyclerAdapter;

/**
 * RecyclerView item 的点击回调事件
 * Created by liuyuli on 2017/6/1 0001.
 */

public interface ItemClickListener<T> {
    void onItemClickListener(BaseRecyclerAdapter<T> adapter, int position);
    void onItemLongClickListener(BaseRecyclerAdapter<T> adapter, int position);
}
