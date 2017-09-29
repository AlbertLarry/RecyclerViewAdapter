package recycleview.larry.com.library

import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * 说明：自定义Holder实现类
 * 作者：mainTel
 * 时间：2016/6/29 17:57
 * 备注：
 */
class AlbertViewHolder(itemView: View) {

    internal var viewSparseArray: SparseArray<View>
    var convertView: View
        internal set

    init {
        viewSparseArray = SparseArray()
        convertView = itemView
        convertView.tag = viewSparseArray
    }

    operator fun <T : View> get(id: Int): T {
        var childView: View? = viewSparseArray.get(id)
        if (null == childView) {
            childView = convertView.findViewById(id)
            viewSparseArray.put(id, childView)
        }
        return (childView as T)!!
    }

    fun getTextView(id: Int): TextView {
        return get(id)
    }

    fun getButton(id: Int): Button {
        return get(id)
    }

    fun getImageView(id: Int): ImageView {
        return get(id)
    }

    fun setTextView(id: Int, charSequence: CharSequence) {
        getTextView(id).text = charSequence
    }

    companion object {

        fun getHolder(view: View): AlbertViewHolder {
            var viewBaseHolder: AlbertViewHolder? = view.tag as AlbertViewHolder
            if (viewBaseHolder == null) {
                viewBaseHolder = AlbertViewHolder(view)
                view.tag = viewBaseHolder
            }
            return viewBaseHolder
        }
    }
}
