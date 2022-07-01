package com.rodion2236.loftcoin.ui.util

import timber.log.Timber
import java.util.*

class DebugTree : Timber.DebugTree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?) {
        val stackTrace = Throwable().fillInStackTrace().stackTrace
        val lineOfOurCall = 5
        val stackTraceView = stackTrace[lineOfOurCall]
        var className = stackTraceView.className
        className = className.substring(className.lastIndexOf(".") + 1)
        super.log(
            priority, tag, String.format(Locale.US,
                "[%s] %s.%s(%s:%d): %s",
                Thread.currentThread().name,
                className,
                stackTraceView.methodName,
                stackTraceView.fileName,
                stackTraceView.lineNumber,
                message
            ), t
        )
    }
}