/*
 * Copyright (C) 2021 adoujean1996@gmail.com
 *
 */
package ci.jjk.circleprogresstwotext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator

/**
 * CircleProgressTwoText is a subclass of {@link android.view.View} class
 * */
class CircleProgressTwoText(context: Context, attrs: AttributeSet) : View(context, attrs) {
    /**
     * ProgressBar type
     * CIRCULAR : Default is circular
     */


    private var strokeWidth = 4f
    private var progress = 0f
    private var progressText = ""
    private var progressTextUnity = ""
    private var min = 0
    private var max = 100

    /** Start the progress at 6 o'clock */
    private val startAngle = 90

    /** Cercle trace background color **/
    private var color = Color.DKGRAY

    private var rectF: RectF? = null
    private var backgroundPaint: Paint? = null
    private var foregroundPaint: Paint? = null


    /** Setters and Getters of preview variable **/
    fun getStrokeWidth(): Float {
        return strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        backgroundPaint!!.strokeWidth = strokeWidth
        foregroundPaint!!.strokeWidth = strokeWidth
        invalidate()
        requestLayout() //Because it should recalculate its bounds
    }

    fun getProgressTextUnity(): String {
        return progressTextUnity
    }

    fun setProgressTextUnity(progressTextUnity: String) {
        this.progressTextUnity = progressTextUnity
        invalidate()
    }

    fun getProgressText(): String {
        return progressText
    }

    fun setProgressText(progressText: String) {
        this.progressText = progressText
        invalidate()
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun getMin(): Int {
        return min
    }

    fun setMin(min: Int) {
        this.min = min
        invalidate()
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        this.max = max
        invalidate()
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
        backgroundPaint!!.color = adjustAlpha(color, 0.3f)
        foregroundPaint!!.color = color
        invalidate()
        requestLayout()
    }

    private fun init(context: Context, attrs: AttributeSet) {

        rectF = RectF()
        /** Obtain values from the XML layout **/

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgressBar,
            0, 0
        )
        /** Reading values from the XML layout **/
        try {
            strokeWidth = typedArray.getDimension(
                R.styleable.CircleProgressBar_progressBarThickness,
                strokeWidth
            )
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress)
            color = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor, color)
            min = typedArray.getInt(R.styleable.CircleProgressBar_min, min)
            max = typedArray.getInt(R.styleable.CircleProgressBar_max, max)

            if (typedArray.getString(R.styleable.CircleProgressBar_progressText)!=null) {
                progressText =
                    typedArray.getString(R.styleable.CircleProgressBar_progressText).toString()
            }
            if (typedArray.getString(R.styleable.CircleProgressBar_progressTextUnity)!=null) {
                progressTextUnity =
                    typedArray.getString(R.styleable.CircleProgressBar_progressTextUnity).toString()
            }

        } finally {
            typedArray.recycle()
        }

        /** Initialise cercle paint **/
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint!!.color = adjustAlpha(color, 0.3f)
        backgroundPaint!!.style = Paint.Style.STROKE
        backgroundPaint!!.strokeWidth = strokeWidth
        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint!!.color = color
        foregroundPaint!!.style = Paint.Style.STROKE
        foregroundPaint!!.strokeWidth = strokeWidth

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /** Draw cercle and set paint **/

        canvas.drawOval(rectF!!, backgroundPaint!!)
        val angle = 360 * progress / max
        canvas.drawArc(rectF!!, startAngle.toFloat(), angle, false, foregroundPaint!!)

        /** Define Text height and width **/
        val h= (canvas.height/2).toFloat()
        val w= (canvas.width/2).toFloat()

        /** Set the size on the two Texts according to cercle height **/
        val textSize=h/3
        val textUnitySize=textSize*2

        /** Set cercle Text paint **/

        val textPaint= TextPaint()
        textPaint.setTextSize(textSize)
        textPaint.color=Color.BLACK
        textPaint.setTextAlign(Paint.Align.CENTER)


        val textPaintUnity= TextPaint()
        textPaintUnity.setTextSize(textSize)
        textPaintUnity.color=Color.GRAY
        textPaintUnity.setTextAlign(Paint.Align.CENTER)

        /** Draw cercle text **/

        canvas.drawText(progressText , w, h-((textPaint.descent()-textPaint.ascent())/2), textPaint)
        canvas.drawText( progressTextUnity, w, h+textPaintUnity.descent()-textPaintUnity.ascent(), textPaintUnity!!)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
            rectF!![0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2] =
                min - strokeWidth / 2
    }


    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Set the progress with an animation.
     * Note that the [ObjectAnimator] Class automatically set the progress
     * so don't call the [com.mrn.customprogressbar.CircleProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {
        animation(progress)
    }

    fun setProgressWithAnimationAndMax(progress: Float, animMaxProgress: Float) {

        val handler = Handler()
        animation(animMaxProgress)
        handler.postDelayed(
            Runnable {animation(progress)
                     setProgressText("${progress.toInt()}")},
            1500
        )
    }

    private fun animation(progress: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = 1000
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    init {
        init(context, attrs)
    }
}