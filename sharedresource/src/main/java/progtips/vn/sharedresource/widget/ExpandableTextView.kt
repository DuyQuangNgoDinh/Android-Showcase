package progtips.vn.sharedresource.widget

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import progtips.vn.sharedresource.R

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), View.OnClickListener {

    private var expandText: String =
        DEFAULT_EXPAND_TEXT
    private var collapseText: String =
        DEFAULT_COLLAPSE_TEXT
    private var originalText: String? = null
    private var trimLines: Int = 0

    private var eventListener: EventListener? = null

    private var buttonColor: Int = 0
    private var state = State.Uninit

    private fun onClickToggleButton() {
        if (eventListener?.onClickToggleButton() != true) {
            when (state) {
                State.Collapsed -> expand()
                State.Expanded -> collapse()
            }
        }
    }

    init {
        readAttributesSet(attrs)
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onClickToggleButton()
    }

    private fun readAttributesSet(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        trimLines = typedArray.getInt(R.styleable.ExpandableTextView_trimLines, Integer.MAX_VALUE)
        buttonColor = typedArray.getColor(R.styleable.ExpandableTextView_buttonColor, 0)
        val originalText = typedArray.getString(R.styleable.ExpandableTextView_originalText)
        expandText = typedArray.getString(R.styleable.ExpandableTextView_expandText) ?: DEFAULT_EXPAND_TEXT
        collapseText = typedArray.getString(R.styleable.ExpandableTextView_collapseText) ?: DEFAULT_COLLAPSE_TEXT
        typedArray.recycle()

        setOriginalText(originalText)
    }

    fun setOriginalText(originalText: String?) {
        this.originalText = originalText
        text = originalText
        restoreState()
    }

    private fun restoreState() {
        when (state) {
            State.Expanded -> expand()
            State.Uninit, State.Collapsed -> collapse()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        restoreState()
    }

    private fun collapse() {
        if (layout == null || layout.lineCount <= trimLines)
            return

        val capturedOriginalText = originalText
            ?: return

        val trimEndIndex = layout.getLineEnd(trimLines - 1) - expandText.length
        val text: String
        if (trimEndIndex < capturedOriginalText.length) {
            text = capturedOriginalText.substring(0, trimEndIndex) + "... "
        } else {
            text = capturedOriginalText
        }
        val displayTextBuilder = SpannableStringBuilder(text)
        displayTextBuilder.append(createToggleText(expandText))

        state = State.Collapsed

        setText(displayTextBuilder)
    }

    private fun expand() {
        if (state == State.Uninit)
            return

        if (state == State.Expanded && (layout == null || layout.lineCount <= trimLines))
            return

        val displayTextBuilder = SpannableStringBuilder(originalText)
        displayTextBuilder.append(createToggleText(collapseText))

        state = State.Expanded

        text = displayTextBuilder
    }

    private fun createToggleText(content: String): CharSequence {
        val expandSpannable = SpannableString(content)
        expandSpannable.setSpan(ForegroundColorSpan(buttonColor), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return expandSpannable
    }

    fun setEventListener(eventListener: EventListener) {
        this.eventListener = eventListener
    }

    internal enum class State {
        Uninit, Collapsed, Expanded
    }

    interface EventListener {
        fun onClickToggleButton(): Boolean
    }

    companion object {
        private const val DEFAULT_EXPAND_TEXT = "[ + ]"
        private const val DEFAULT_COLLAPSE_TEXT = ""
    }

}
