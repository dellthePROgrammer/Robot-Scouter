package com.supercilex.robotscouter.ui.scouting.templatelist.viewholder

import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.supercilex.robotscouter.R
import com.supercilex.robotscouter.data.model.Metric
import com.supercilex.robotscouter.ui.scouting.scoutlist.viewholder.CounterViewHolder
import com.supercilex.robotscouter.util.data.nullOrFull
import com.supercilex.robotscouter.util.unsafeLazy
import kotterknife.bindView
import java.util.Locale

class CounterTemplateViewHolder(itemView: View) : CounterViewHolder(itemView),
        MetricTemplateViewHolder<Metric.Number, Long> {
    override val reorder: View by bindView(R.id.reorder)
    override val nameEditor: EditText by unsafeLazy { name as EditText }
    private val unit: EditText by bindView(R.id.unit)

    init {
        init()

        itemView as LinearLayout
        itemView.removeView(unit)
        itemView.addView(unit, itemView.childCount - 1)
        (count.layoutParams as LinearLayout.LayoutParams).rightMargin = 0

        unit.onFocusChangeListener = this
    }

    override fun bind() {
        super.bind()
        unit.setText(metric.unit)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        if (name.hasFocus()) metric.name = name.text.toString()
    }

    override fun setValue() {
        count.text = String.format(Locale.getDefault(), "%d", metric.value)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        super.onFocusChange(v, hasFocus)
        if (!hasFocus && v === unit) {
            metric.unit = unit.text.nullOrFull()?.toString()
        }
    }
}
