package com.andreich.musicplayer.ui

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.andreich.musicplayer.MyApp
import com.andreich.musicplayer.R
import com.andreich.navigation.EventBus
import com.andreich.navigation.NavigationEvent
import com.andreich.navigation.Navigator
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), Navigator {

    private var navController: NavController? = null
    private val topLevelDestinations = setOf(getTabsFragmentDestination())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!checkPermission()) {
            requestPermission()
        }
        (application as MyApp).component.inject(this)
        setContentView(R.layout.activity_main)
        val navController = getRootNavController()
        prepareRootNavController(navController)
        onNavControllerActivated(navController)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
        EventBus.subscribe(NavigationEvent::class.java, this::onEvent)
    }

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
            supportActionBar?.title = prepareTitle(destination.label, arguments)
        }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {

        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments[argName].toString())
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }

    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        return navHost.navController
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        EventBus.unsubscribe(NavigationEvent::class.java, this::onEvent)
        super.onDestroy()
    }

    private fun prepareRootNavController(navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(
            getTabsFragmentDestination()
        )
        navController.graph = graph
    }

    private fun getTabsFragmentDestination() = R.id.tabsFragment
    private fun getMainNavigationGraphId() = R.navigation.main_navigation

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
        return (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (isStartDestination(navController?.currentDestination)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    private val REQUEST_PERMISSION_CODE = 100

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This permission is needed to access external storage for the app.")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION_CODE
                    )
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun navigate(event: NavigationEvent) {
        onEvent(event)
    }

    private fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.NavigateToFeatureMusicPlayer -> {

            }
        }
    }
}