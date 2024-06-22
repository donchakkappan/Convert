package com.allutils.convert.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.allutils.convert.NavHostActivity
import com.allutils.convert.R

class ShortcutsHandler(private val context: Context) {

    companion object {
        const val CURRENCY_SHORTCUT_ID = "CURRENCY_SHORTCUT_ID"
    }

    fun createShortcuts() {
        val listOfShortcuts = createShortcutsList()
        ShortcutManagerCompat.setDynamicShortcuts(context, listOfShortcuts)
    }

    private fun createShortcutsList(): List<ShortcutInfoCompat> {
        val shortcutOne = buildShortcut(
            id = CURRENCY_SHORTCUT_ID,
            shortLabel = context.getString(R.string.currency),
            longLabel = context.getString(R.string.currency),
            intent = Intent(context, NavHostActivity::class.java).apply {
                action = Intent.ACTION_VIEW
            },
            shortcutIcon = R.drawable.ic_currency
        )

        return listOf(shortcutOne)
    }

    private fun buildShortcut(
        id: String,
        shortLabel: String,
        longLabel: String,
        intent: Intent,
        shortcutIcon: Int
    ): ShortcutInfoCompat {
        return ShortcutInfoCompat.Builder(context, id)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIntent(intent)
            .setIcon(IconCompat.createWithResource(context, shortcutIcon))
            .build()
    }
}
