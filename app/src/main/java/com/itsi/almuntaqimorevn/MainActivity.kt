package com.itsi.almuntaqimorevn

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.itsi.almuntaqimorevn.databinding.ActivityMainBinding
import com.itsi.almuntaqimorevn.utils.MyContextWrapper
import com.itsi.almuntaqimorevn.utils.MyUtils
import com.itsi.almuntaqimorevn.viewmodels.SharedViewModel
import java.util.Locale


/*val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()*/

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionNBottomNavBarNNavController()

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        viewModel.selected.observe(this, Observer<String>  { item ->
            supportActionBar?.title = item
            // Update the UI using new item data
        })

    }

    private fun setupActionNBottomNavBarNNavController() {
        setSupportActionBar(binding.toolbar)
        val bottomNavView: BottomNavigationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        val navController = navHostFragment.navController //findNavController(R.id.nav_host_fragment_content_main)
        //val topLevelDestinations = setOf(R.id.secondFragment, R.id.bookmarksFragment)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.secondFragment, R.id.bookmarksFragment, R.id.settingsFragment
            ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_font_size -> {
                //val currentFragment = NavHostFragment.findNavController().currentDestination?.id

                /*val navHostFragment: NavHostFragment? =
                    supportFragmentManager.findFragmentById(R.id.nav_host)*/

                //val currentFragment = supportFragmentManager.currentNavigationFragment
                //val fragId = supportFragmentManager.findFragmentById(R.id.secondFragment)

                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]

                val navController = navHostFragment.navController

                val jc = currentFragment?.javaClass
                val sF = SecondFragment::class.java
                val bF = BookmarksFragment::class.java
                val stF = SettingsFragment::class.java
                if (jc?.name == sF.name) {
                    showBottomDialogFontSize()
                } else if (jc?.name == bF.name) {
                    // In Bookmarks Frag, move to SecondFrag
                    navController.navigateUp()
                    navController.navigate(R.id.secondFragment)
                    showBottomDialogFontSize()
                } else if (jc?.name == stF.name) {
                    // In Settings Frag, move to SecondFrag
                    navController.navigateUp()
                    navController.navigate(R.id.secondFragment)
                    showBottomDialogFontSize()
                } else {
                    // show dialog in case the login does not work, so it can still change font sizes
                    showBottomDialogFontSize()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showBottomDialogFontSize() {
        //val currentFragment = supportFragmentManager.currentNavigationFragment
        // Show bottom dialog
        val bt = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view: View = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_fontsize, null)

        var fontSize = MyUtils().getFontSize(this)

        val tvEditFontSize = view.findViewById<TextView>(R.id.tv_editable_font_size) as TextView
        tvEditFontSize.text = fontSize.toString()

        view.findViewById<ImageButton>(R.id.ibutton_increase_size).setOnClickListener {
            if(fontSize<60) {
                tvEditFontSize.text = (++fontSize).toString()
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]
                (currentFragment as SecondFragment).onFontSizeModified(fontSize)
            }
        }

        view.findViewById<ImageButton>(R.id.ibutton_reduce_size).setOnClickListener {
            if(fontSize>10) {
                tvEditFontSize.text = (--fontSize).toString()
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]
                (currentFragment as SecondFragment).onFontSizeModified(fontSize)
            }
        }

        bt.setContentView(view)
        bt.show()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    @Deprecated("Uses deprecated Api", ReplaceWith("Use setApplicationLocales(appLocale)"), DeprecationLevel.WARNING)
    private fun setAppLocaleOld() {
        val config = resources.configuration
        val lang = "ar" // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, "ar"))
    }
}