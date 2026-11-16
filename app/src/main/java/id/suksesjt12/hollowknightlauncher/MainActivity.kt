package id.suksesjt12.hollowknightlauncher

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface

class MainActivity : Activity() {
    private lateinit var selectedFile: TextView
    private var gameUri: Uri? = null
    private val pickGame = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val bg = Color.rgb(12, 15, 22)
        val white = Color.rgb(235, 239, 245)
        val muted = Color.rgb(155, 166, 181)
        val accent = Color.rgb(91, 167, 190)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(36, 24, 36, 24)
            setBackgroundColor(bg)
        }
        val title = TextView(this).apply {
            text = "HOLLOW KNIGHT"
            textSize = 30f
            setTextColor(white)
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "ANDROID LAUNCHER  •  EARLY BUILD"
            textSize = 12f
            setTextColor(muted)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 26)
        }
        selectedFile = TextView(this).apply {
            text = "Belum ada file game yang dipilih"
            textSize = 14f
            setTextColor(muted)
            gravity = Gravity.CENTER
            setPadding(12, 12, 12, 20)
        }
        val choose = Button(this).apply {
            text = "PILIH FILE GAME"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "*/*"
                }
                startActivityForResult(intent, pickGame)
            }
        }
        val play = Button(this).apply {
            text = "PLAY"
            setOnClickListener {
                val message = if (gameUri == null) {
                    "Pilih file terlebih dahulu. Backend emulator belum terpasang."
                } else {
                    "File terpilih, tetapi backend emulator belum terpasang. Launcher ini belum dapat menjalankan NSP."
                }
                android.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("Status Emulator")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
        val status = TextView(this).apply {
            text = "STATUS BACKEND: BELUM TERPASANG"
            textSize = 12f
            setTextColor(accent)
            gravity = Gravity.CENTER
            setPadding(0, 22, 0, 0)
        }
        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(selectedFile)
        layout.addView(choose)
        layout.addView(play)
        layout.addView(status)
        setContentView(layout)
    }

    @Deprecated("Deprecated in Android API, retained for broad compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickGame && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                gameUri = uri
                try {
                    contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: SecurityException) { }
                selectedFile.text = "File dipilih: ${uri.lastPathSegment ?: "Dokumen"}"
            }
        }
    }
}
