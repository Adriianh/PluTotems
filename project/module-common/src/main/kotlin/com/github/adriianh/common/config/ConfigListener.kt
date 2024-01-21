package com.github.adriianh.common.config

import taboolib.common5.FileWatcher
import java.io.File

object ConfigListener {
    private val files = mutableSetOf<File>()
    private val watcher = FileWatcher()

    fun isListeningFile(file: File): Boolean {
        return watcher.hasListener(file)
    }

    fun addFileListener(file: File, runnable: () -> Unit) {
        watcher.addSimpleListener(file, runnable)
        files.add(file)
    }

    fun removeFileListener(file: File) {
        watcher.removeListener(file)
    }
}