package com.supercilex.robotscouter.ui.scouting.scoutlist

import android.support.v4.app.FragmentManager
import com.supercilex.robotscouter.R
import com.supercilex.robotscouter.util.ui.TemplateSelectionListener
import com.supercilex.robotscouter.util.ui.TemplateSelectorDialog

class ScoutTemplateSelectorDialog : TemplateSelectorDialog() {
    override val title = R.string.title_add_scout_template_selector

    override fun onItemSelected(key: String) =
            (parentFragment as TemplateSelectionListener).onTemplateSelected(key)

    companion object {
        private const val TAG = "ScoutTemplateSelector"

        fun show(manager: FragmentManager) =
                ScoutTemplateSelectorDialog().show(manager, TAG)
    }
}