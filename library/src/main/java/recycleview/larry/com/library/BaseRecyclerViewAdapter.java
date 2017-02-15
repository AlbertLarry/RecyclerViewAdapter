package recycleview.larry.com.library;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/8.
 * 添加头部
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEAD = 0x200;
    private static final int TYPE_NORMAL = 0x201;

    private ArrayList<T> mList;
    private View mHeadView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View mEmptyView;


    public BaseRecyclerViewAdapter() {
        mList = new ArrayList<>();
    }

    public BaseRecyclerViewAdapter(ArrayList<T> list) {
        mList = list;
    }

    public void addheadView(View headView) {
        mHeadView = headView;
    }

    public void setEmptyView(View pEmptyView) {
        mEmptyView = pEmptyView;
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addDatas(ArrayList<T> list) {
        mList.addAll(list);
        if (mList == null||mList.size()==0) {

        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeadView == null) return TYPE_NORMAL;
        else if (mHeadView != null && position == 0)
            return TYPE_HEAD;
        else return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeadView != null && viewType == TYPE_HEAD) return new BaseHolder(mHeadView);
        return onBaseCreateviewHolder(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder onBaseCreateviewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        final int pos = getRealPosition(holder);
        T data = mList.get(pos);
        onBaseBindViewHolder(holder, pos, data);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, pos, holder.getItemId());
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, pos, holder.getItemId());
                    return false;
                }
            });
        }
    }

    public abstract void onBaseBindViewHolder(RecyclerView.ViewHolder baseHolder, int position, T data);

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeadView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeadView == null ? mList.size() : mList.size() + 1;
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        MyViewHolder mMyViewHolder;

        public BaseHolder(View itemView) {
            super(itemView);
            mMyViewHolder = new MyViewHolder(itemView);
        }

        public MyViewHolder getMyViewHolder() {
            return mMyViewHolder;
        }
    }

    /**
     * 处理布局不是line的时候
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEAD
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    //长按
    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    //点击
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position, long id);
    }

}
