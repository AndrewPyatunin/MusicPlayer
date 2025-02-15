package com.andreich.musicplayer

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.andreich.musicplayer.ui.MusicPlayerFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (!checkPermission()) {
            requestPermission()
        } else {
            // Permission is already granted; proceed with your logic
        }
        setContentView(R.layout.activity_main)
        val fragment = MusicPlayerFragment.newInstance()
        supportFragmentManager.addOnBackStackChangedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is MusicPlayerFragment) {
            showActionBar(false)
            Log.d("Fragment", "backstack")
        } else {
            showActionBar(true)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getLocalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    hideKeyboard(v)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(editText: EditText) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showActionBar(isShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        supportActionBar?.setDisplayShowHomeEnabled(isShow)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is MusicPlayerFragment) {
            finish()
            Log.d("Fragment", "backstack")
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                Log.d("Fragment", "backstack")
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private val REQUEST_PERMISSION_CODE = 100

    fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This permission is needed to access external storage for the app.")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION_CODE)
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
                // Proceed with the action that requires the permission
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
                // Handle the case where permission is denied
            }
        }
    }
}