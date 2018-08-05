package com.cao.choosemorepicture

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.cao.choosemorepicture.ImageLoader
import com.cao.choosemorepicture.Instance
import com.cao.choosemorepicture.R

/**
 * Created by gaoyufei on 2017/6/22.
 * It not only can display many image
 * in single view, but also support edit.
 * you can custom the max image number,
 * span per line, dynamic or static mode
 */

class MultiPictureView : FrameLayout {
    interface ItemClickCallback {

        fun onItemClicked(view: View, index: Int, uris: ArrayList<Uri>)

    }

    interface AddClickCallback {

        fun onAddClick(view: View)

    }

    interface DeleteClickCallback {

        fun onDeleted(view: View, index: Int)

    }

    /**
     * 共两种布局方式
     */
    class ImageLayoutMode {
        companion object {
            @JvmStatic
            val DYNAMIC = 1
            @JvmStatic
            val STATIC = 2

        }
    }


    companion object {
        @JvmStatic
        fun setImageLoader(imageLoader: ImageLoader) {
            Instance.imageLoader = imageLoader
        }
    }

    private var space = 8.toPx()

    // 每行最多显示多少张
    private var span = 3

    // 布局方式，动态和固定
    private var imageLayoutMode = ImageLayoutMode.STATIC

    // 最多显示图片个数
    private var max = 9

    // 横纵比
    private var ratio: Float = 1F


    // 删除图标
    private var deleteDrawableId: Int = R.drawable.ic_multiple_image_view_delete

    // 添加图标
    private var addDrawableId: Int = R.drawable.ic_multiple_image_view_add


    var itemClickCallback: ItemClickCallback? = null

    var deleteClickCallback: DeleteClickCallback? = object : DeleteClickCallback {
        override fun onDeleted(view: View, index: Int) {
            if (editable) {
                removeItem(index)
            }
        }

    }

    var addClickCallback: AddClickCallback? = null


    private val imageList = arrayListOf<Uri>()
    // 测量后实际要显示的行
    private var columnMeasure = 0
    private var rowMeasure = 0
    private var imageWidthMeasure = 0F
    private var imageHeightMeasure = 0F
    // 图片的Padding
    private var imagePaddingMeasure = 0

    // 是否可编辑
    private var editable: Boolean = false;


    private var deleteBitmap: Bitmap? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.MultiPictureView, defStyleAttr, 0)
        try {
            span = typeArray.getInteger(R.styleable.MultiPictureView_span, span)
            ratio = typeArray.getFloat(R.styleable.MultiPictureView_ratio, ratio)
            space = typeArray.getDimension(R.styleable.MultiPictureView_space, space.toFloat()).toInt()
            imageLayoutMode = typeArray.getInteger(R.styleable.MultiPictureView_imageLayoutMode, imageLayoutMode)
            max = typeArray.getInteger(R.styleable.MultiPictureView_max, max)
            editable = typeArray.getBoolean(R.styleable.MultiPictureView_editable, editable)
            deleteDrawableId = typeArray.getResourceId(R.styleable.MultiPictureView_deleteDrawable, deleteDrawableId)
            addDrawableId = typeArray.getResourceId(R.styleable.MultiPictureView_addDrawable, addDrawableId)
        } finally {
            typeArray.recycle();
        }

        setDeleteResource(deleteDrawableId)
        refresh()
    }

    /**
     * 将所有的图片添加到FrameLayout中
     */
    fun setupView() {
        removeAllViews()

        for (i in 0 until getNeedViewCount()) {
            val image = generateImage(i)
            addView(image)
            image.setOnClickListener {
                val arrayList = arrayListOf<Uri>()
                arrayList.addAll(imageList)
                itemClickCallback?.onItemClicked(it, i, arrayList)
            }
        }
        if (shouldDrawAddView()) {
            val image = generateImage(-1)
            image.setImageResource(addDrawableId)
            addView(image)
            image.setOnClickListener { addClickCallback?.onAddClick(it) }
        }
    }

    fun generateImage(index: Int): ImageView {
        val bitmap = if (editable) deleteBitmap else null
        val image = CustomImageView(context, index, bitmap, deleteClickCallback)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        if (editable) {
            image.setPadding(imagePaddingMeasure, imagePaddingMeasure, imagePaddingMeasure, imagePaddingMeasure)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                image.cropToPadding = true
            }
        }
        return image
    }

    fun measureImageSize(width: Int) {
        var imageSize = 0
        when (imageLayoutMode) {
            ImageLayoutMode.STATIC -> {
                columnMeasure = span
            }
            else -> {
                // 取一个合理的column
                columnMeasure = Math.min(Math.ceil(Math.sqrt(childCount.toDouble())).toInt(), span)
            }
        }
        rowMeasure = childCount / columnMeasure + if (childCount % columnMeasure == 0) 0 else 1
        imageSize = (width - space * (columnMeasure - 1)) / columnMeasure
        imageWidthMeasure = imageSize.toFloat()
        imageHeightMeasure = imageWidthMeasure / ratio
    }

    private fun getNeedViewCount() = Math.min(getCount(), max)

    private fun shouldDrawAddView() = editable && getCount() < max


    fun setDeleteResource(id: Int) {
        this.deleteDrawableId = id
        try {
            this.deleteBitmap = drawableToBitmap(resources.getDrawable(id))
            if (deleteBitmap != null) {
                imagePaddingMeasure = deleteBitmap!!.width / 2
            }
        }catch (e:Resources.NotFoundException){
            e.printStackTrace()
            this.deleteBitmap = null;
        }
    }

    fun setAddResource(id:Int){
        this.addDrawableId = id
    }

    fun setEditable(editable: Boolean) {
        this.editable = editable
        setupView()
        requestLayout()
    }

    fun isEditable(): Boolean = editable

    fun getList(): ArrayList<Uri> {
        return imageList
    }

    fun refresh() {
        setupView()
        requestLayout()
    }

    fun setList(list: List<Uri>) {
        imageList.clear()
        imageList.addAll(list)
        refresh()
    }

    fun getCount(): Int {
        return imageList.size
    }

    fun addItem(uri: Uri) {
        addItem(uri, true)
    }

    fun addItem(uri: Uri, refresh: Boolean) {
        imageList.add(uri)
        if (refresh)
            refresh()
    }

    fun addItem(uri: List<Uri>) {
        imageList.addAll(uri)
        refresh()
    }

    fun clearItem() {
        imageList.clear()
        refresh()
    }

    fun removeItem(index: Int, refresh: Boolean) {
        imageList.removeAt(index)
        if (refresh)
            refresh()
    }

    fun removeItem(index: Int) {
        removeItem(index, true)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isInEditMode) {
            setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 60.toPx())
            return
        }
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
            return
        }
        measureImageSize(MeasureSpec.getSize(widthMeasureSpec))
        val width = imageWidthMeasure * columnMeasure + (columnMeasure - 1) * space
        val height = imageHeightMeasure * rowMeasure + (rowMeasure - 1) * space
        setMeasuredDimension(width.toInt(), height.toInt())

        for (i in 0 until childCount) {
            val child = getChildAt(i) as ImageView
            if (child.visibility == GONE)
                return
            val measureWidth = MeasureSpec.makeMeasureSpec(imageWidthMeasure.toInt(), MeasureSpec.EXACTLY)
            val measureHeight = MeasureSpec.makeMeasureSpec(imageHeightMeasure.toInt(), MeasureSpec.EXACTLY)
            child.measure(measureWidth, measureHeight)
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE)
                return

            val horizontalIndex = i % columnMeasure
            val verticalIndex = i / columnMeasure
            // 左边距
            val l = horizontalIndex * imageWidthMeasure + horizontalIndex * space
            // 上边距
            val t = verticalIndex * imageHeightMeasure + verticalIndex * space

            // 右边
            val r = l + imageWidthMeasure
            // 下边
            val b = t + imageHeightMeasure

            child.layout(l.toInt(), t.toInt(), r.toInt(), b.toInt())

            if (i < imageList.size) {
                bindImage(child as ImageView, imageList[i])
            }
        }
    }


    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if(drawable == null)
            return null;
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    /**
     * 将DP转换为PX
     */
    fun Int.toPx(): Int {
        return this.toFloat().toPx()
    }

    fun Float.toPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    private fun bindImage(imageView: ImageView, uri: Uri) {
        if (Instance.imageLoader == null)
            throw NullPointerException("Please call MultipleImageView.setImageLoader(...) at least one times ")
        Instance.imageLoader?.loadImage(imageView, uri)
    }

}
