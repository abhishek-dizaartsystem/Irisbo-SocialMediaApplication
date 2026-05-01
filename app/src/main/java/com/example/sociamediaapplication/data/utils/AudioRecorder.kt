package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var filePath: String? = null
    private var startTimeMs: Long = 0L

    fun start(context: Context): String {
        val file = File.createTempFile(
            "audio_${System.currentTimeMillis()}",
            ".m4a",
            context.cacheDir
        )

        filePath = file.absolutePath

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            prepare()
            start()
        }

        startTimeMs = System.currentTimeMillis()

        return filePath!!
    }

    fun stop(): String? {
        val elapsed = System.currentTimeMillis() - startTimeMs

        // MediaRecorder needs at least ~300ms of data before stop() is safe
        if (elapsed < 300L) {
            cancel()
            return null // treat as cancelled — too short
        }

        return try {
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
            filePath
        } catch (e: RuntimeException) {
            // stop() failed — recording was too short or not started properly
            cancel()
            null
        }
    }

    fun cancel() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            // Ignore — recorder may not have started
        } finally {
            recorder = null
            filePath = null
        }
    }

    fun getAmplitude(): Int {
        return try {
            recorder?.maxAmplitude ?: 0
        } catch (e: Exception) {
            0
        }
    }
}