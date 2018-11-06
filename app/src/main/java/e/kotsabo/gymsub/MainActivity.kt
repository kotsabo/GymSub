package e.kotsabo.gymsub

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity(), NewCustomerFragment.OnFragmentInteractionListener, CustomersListFragment.OnFragmentInteractionListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.manager = this.supportFragmentManager

        // Begin the transaction
        var transaction: FragmentTransaction = this.manager.beginTransaction()
        transaction.add(R.id.my_placeholder, NewCustomerFragment())
        // Complete the changes added above
        transaction.commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = this.supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        this.mDrawerLayout = findViewById(R.id.drawer_layout)

        this.mDrawerLayout.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // Respond when the drawer motion state changes
                }
            }
        )

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->

            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            this.mDrawerLayout.closeDrawers ()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId) {
                R.id.nav_insert -> {
                    Log.e("first", "yeah")
                    //val transaction: FragmentTransaction = manager.beginTransaction()
                    transaction = this.manager.beginTransaction()
                    // Replace the contents of the container with the new fragment
                    transaction.replace(R.id.my_placeholder, NewCustomerFragment())
                    // Complete the changes added above
                    transaction.commit()
                }
                R.id.nav_subscriptions_list -> {
                    Log.e("second", "yeah")
                    //val transaction: FragmentTransaction = manager.beginTransaction()
                    transaction = this.manager.beginTransaction()
                    // Replace the contents of the container with the new fragment
                    transaction.replace(R.id.my_placeholder, CustomersListFragment())
                    // Complete the changes added above
                    transaction.commit()
                }
                R.id.nav_settings -> {
                    Log.e("third", "yeah")

                }
            }

            true
        }

    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

