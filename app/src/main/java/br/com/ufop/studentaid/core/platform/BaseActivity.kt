package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import br.com.ufop.studentaid.R
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.main_activity.*

abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0) :
    AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar_main?.let {

            setSupportActionBar(it)
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> handleDrawer()

        }
        return super.onOptionsItemSelected(item)

    }

    var doubleBackToExit: Boolean = false

    private fun handleDrawer() {
        drawer_layout?.let {
            if (it.isDrawerOpen(GravityCompat.START))
                drawer_layout?.closeDrawer(GravityCompat.START)
            else {
                drawer_layout?.openDrawer(GravityCompat.START)

            }

        }

    }

    private fun handleBackPressed() {

        showMessage("Pressione novamente para sair")
        this.doubleBackToExit = true
    }

    /**
     * - Display a Toasty message
     * @param message String msg
     * @param isErrorMessage set to true if is an error
     */
    fun showMessage(message: String, isErrorMessage: Boolean = false) {

//        if (isErrorMessage) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//            Toasty.error(this, message).show()
//        } else {

//            Toasty.success(this, message).show()
    }

    override fun onBackPressed() {
        drawer_layout?.let {
            if (it.isDrawerOpen(GravityCompat.START))
                drawer_layout?.closeDrawer(GravityCompat.START)
            else {

                when {
                    doubleBackToExit -> moveTaskToBack(true)
                    else -> handleBackPressed()
                }
                Handler().postDelayed({ doubleBackToExit = false }, 2000)
            }

        }
    }
}