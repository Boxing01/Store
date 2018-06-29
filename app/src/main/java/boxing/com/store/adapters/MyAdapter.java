package boxing.com.store.adapters;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import boxing.com.store.base.BaseHolder;


public class MyAdapter<Data> extends BaseAdapter implements RecyclerListener, OnItemClickListener {

    private Filter filter;
    private List<BaseHolder> mDisplayedHolders;//用于记录所有显示的holder
    private List<Data> list = new ArrayList<>();//adapter的数据集
    private MyAdapterInterface<Data> mAdapterInterface;

    /**
     * @param mAdapterInterface(传this,不能为null)
     * @param listView
     * @param datas
     * @param isMore(一般传false,表示只有一种item模式)
     */
    public MyAdapter(MyAdapterInterface<Data> mAdapterInterface, AbsListView listView, List<Data>
            datas, boolean isMore) {
        mDisplayedHolders = new ArrayList<BaseHolder>();
        this.mAdapterInterface = mAdapterInterface;
        if (null != listView) {
            //设置
            listView.setRecyclerListener(this);
            listView.setOnItemClickListener(this);
        }
        setIsMore(isMore);
        this.list.clear();
        this.list.addAll(datas);
    }

    /**
     * 刷新适配器
     */
    public void reLoadAdapter(List<Data> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<Data> datas) {
        list = datas;
    }

    public List<Data> getData() {
        return list;
    }

    private int MORE_VIEW_TYPE = 0;
    private int ITEM_VIEW_TYPE = 1;
    private boolean isMore;
    private int mCount;

    @Override
    public int getCount() {
        if (isMore) {
            return list.size() + 1;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            mCount++;
        } else {
            mCount = 0;
        }

        if (mCount > 1) {
            if (convertView != null) {
                return convertView;
            }
        }
        BaseHolder holder;
        if (convertView != null && convertView.getTag() instanceof BaseHolder) {
            holder = (BaseHolder) convertView.getTag();
        } else {
            if (getItemViewType(position) == MORE_VIEW_TYPE) {
                holder = mAdapterInterface.getMoreHolder();
            } else {
                holder = mAdapterInterface.getHolder(position);
            }
            convertView = holder.getRootView();
            convertView.setTag(holder);
        }
        if (getItemViewType(position) == ITEM_VIEW_TYPE) {
            holder.setPosition(position);
            holder.setData(list.get(position));
        }
        mDisplayedHolders.add(holder);
        return convertView;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        if (null != view) {
            Object tag = view.getTag();
            if (tag instanceof BaseHolder) {
                BaseHolder<Data> holder = (BaseHolder<Data>) tag;
                synchronized (mDisplayedHolders) {
                    mDisplayedHolders.remove(holder);
                }
            }
        }
    }

    // 获取item有几种类型，默认是一种类型，这里在加一是为了做加载更多。
    @Override
    public int getViewTypeCount() {
        if (isMore) {
            return super.getViewTypeCount() + 1;// 加1是为了最后加载更多的布局
        } else {
            return super.getViewTypeCount();
        }
    }

    // 根据position位置返回哪种item展示类型
    @Override
    public int getItemViewType(int position) {
        if (isMore) {
            if (position == getCount() - 1) {
                return MORE_VIEW_TYPE;// 加载更多的布局
            } else {
                return ITEM_VIEW_TYPE;
            }
        } else {
            return ITEM_VIEW_TYPE;
        }
    }

    public void setIsMore(boolean b) {
        this.isMore = b;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        mAdapterInterface.onItemClick(arg0, arg1, arg2, arg3);
    }

    public interface MyAdapterInterface<Data> {
        /**
         * 面向holder编程,单独写个holder继承BaseHollder,写法如下:
         * return new XXXHolder();
         *
         * @param position
         * @return
         */
        public abstract BaseHolder<Data> getHolder(int position);

        /**
         * 一般用在有两种item模式,
         * 1,大部分是直接return null即可
         * 2,如listView最后一条为加载更多,就返回个MoreHolder
         *
         * @return
         */
        public abstract BaseHolder<?> getMoreHolder();

        /**
         * 条目点击事件
         *
         * @param listView
         * @param view
         * @param position
         * @param id
         */
        public abstract void onItemClick(AdapterView<?> listView, View view, int position, long id);
    }
}
