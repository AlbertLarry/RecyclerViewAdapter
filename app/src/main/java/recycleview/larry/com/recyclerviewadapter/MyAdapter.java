package recycleview.larry.com.recyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import recycleview.larry.com.library.BaseRecyclerViewAdapter;


/**
 * Created by PC on 2016/12/8.
 */
public class MyAdapter extends BaseRecyclerViewAdapter<String> {

    @Override
    public RecyclerView.ViewHolder onBaseCreateviewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.item;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, null, false);
        return new BaseHolder(view);
    }

    @Override
    public void onBaseBindViewHolder(RecyclerView.ViewHolder baseHolder, int position, String data) {
        BaseRecyclerViewAdapter.BaseHolder holder = null;
        if (baseHolder instanceof BaseRecyclerViewAdapter.BaseHolder) {
            holder = (BaseHolder) baseHolder;
            holder.getMyViewHolder().setTextView(R.id.textView, data);
        }
    }
}
