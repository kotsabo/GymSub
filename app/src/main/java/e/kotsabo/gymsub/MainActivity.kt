package e.kotsabo.gymsub

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var customersDBHelper : CustomersDBHelper

    private var totalCustomers: Int = 0

    private lateinit var results: TextView
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var subscription: EditText
    private lateinit var go: Button
    private lateinit var erase: Button

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)

        mDrawerLayout.addDrawerListener(
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
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }

        this.results = findViewById(R.id.results)
        this.name = findViewById(R.id.name)
        this.surname = findViewById(R.id.surname)
        this.subscription = findViewById(R.id.subscription)
        this.go = findViewById(R.id.go)
        this.erase = findViewById(R.id.erase)

        this.go.setOnClickListener { this.goAction() }

        this.customersDBHelper = CustomersDBHelper(this)

        this.totalCustomers = this.customersDBHelper.readAllCustomers().size
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun goAction() {
        val currentName: String = this.name.text.toString()
        val currentSurname: String = this.surname.text.toString()
        val currentSubscription: Int = this.subscription.text.toString().toInt()
        val format = SimpleDateFormat("dd/MM/yyy")
        val currentDate = Date()
        val stringDate: String = format.format(currentDate)

        this.totalCustomers += 1

        val result = this.customersDBHelper.insertCustomer(Customer(this.totalCustomers, currentName, currentSurname, stringDate, currentSubscription))

        //this.results.text = "$currentName\n$currentSurname\n$currentSubscription\n$stringDate\n$totalCustomers"
        this.results.text = "Added user : $result"

        this.resetEditTexts()

    }

    private fun resetEditTexts() {
        this.name.setText("")
        this.surname.setText("")
        this.subscription.setText("")
    }



}

