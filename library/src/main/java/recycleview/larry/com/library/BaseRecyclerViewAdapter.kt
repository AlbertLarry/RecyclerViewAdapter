package recycleview.larry.com.library

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

/**
 * Created by PC on 2016/12/8.
 * 添加头部
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var mList: ArrayList<T>? = null
    private var mHeadView: View? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mEmptyView: View? = null


    constructor() {
        mList = ArrayList()
    }

    constructor(list: ArrayList<T>) {
        mList = list
    }

    fun addheadView(headView: View) {
        mHeadView = headView
    }

    fun setEmptyView(pEmptyView: View) {
        mEmptyView = pEmptyView
    }

    /**
     * 添加数据
     *
     * @param list
     */
    fun addDatas(list: ArrayList<T>) {
        mList!!.addAll(list)
        if (mList == null || mList!!.size == 0) {

        }
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return if (mHeadView == null)
            TYPE_NORMAL
        else if (mHeadView != null && position == 0)
            TYPE_HEAD
        else
            TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (mHeadView != null && viewType == TYPE_HEAD) BaseHolder(mHeadView!!) else onBaseCreateviewHolder(parent, viewType)
    }

    abstract fun onBaseCreateviewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEAD)
            return
        val pos = getRealPosition(holder)
        val data = mList!![pos]
        onBaseBindViewHolder(holder, pos, data)

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { mOnItemClickListener!!.onItemClick(holder.itemView, pos, holder.itemId) }
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener {
                mOnItemLongClickListener!!.onItemLongClick(holder.itemView, pos, holder.itemId)
                false
            }
        }
    }

    abstract fun onBaseBindViewHolder(baseHolder: RecyclerView.ViewHolder, position: Int, data: T)

    private fun getRealPosition(holder: RecyclerView.ViewHolder): Int {
        val position = holder.layoutPosition
        return if (mHeadView == null) position else position - 1
    }

    override fun getItemCount(): Int {
        return if (mHeadView == null) mList!!.size else mList!!.size + 1
    }

    inner class BaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mAlbertViewHolder: AlbertViewHolder
            internal set

        init {
            mAlbertViewHolder = AlbertViewHolder(itemView)
        }
    }

    /**
     * 处理布局不是line的时候
     *
     * @param recyclerView
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)

        val manager = recyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == TYPE_HEAD)
                        manager.spanCount
                    else
                        1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        super.onViewAttachedToWindow(holder)
        val lp = holder!!.itemView.layoutParams
        if (lp != null
                && lp is StaggeredGridLayoutManager.LayoutParams
                && holder.layoutPosition == 0) {
            lp.isFullSpan = true
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        mOnItemLongClickListener = listener
    }

    //长按
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, id: Long)
    }

    //点击
    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int, id: Long): Boolean
    }

    companion object {
        private val TYPE_HEAD = 0x200
        private val TYPE_NORMAL = 0x201
    }

}
