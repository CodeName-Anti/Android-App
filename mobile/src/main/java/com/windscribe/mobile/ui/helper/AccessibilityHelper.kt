package com.windscribe.mobile.ui.helper

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.accessibility.AccessibilityManager

/**
 * Detects whether a screen reader (TalkBack, BrailleBack, ...) is driving the UI.
 *
 * The puzzle CAPTCHA can only be solved by dragging a piece to a position that is judged visually,
 * and the API scores the drag trail that gesture produces. Neither is available to a screen reader
 * user, so when one is active we ask the API for the text based challenge instead.
 */
object AccessibilityHelper {
    fun isScreenReaderEnabled(context: Context): Boolean {
        val manager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
                ?: return false
        if (!manager.isEnabled) {
            return false
        }
        if (manager.isTouchExplorationEnabled) {
            return true
        }
        // Braille displays and external keyboard users may not have touch exploration on.
        val feedbackTypes =
            AccessibilityServiceInfo.FEEDBACK_SPOKEN or AccessibilityServiceInfo.FEEDBACK_BRAILLE
        return manager.getEnabledAccessibilityServiceList(feedbackTypes).isNotEmpty()
    }
}
