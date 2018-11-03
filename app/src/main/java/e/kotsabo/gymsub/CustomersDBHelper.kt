package e.kotsabo.gymsub

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class CustomersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCustomer(customer: Customer): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID, customer.customerID)
        values.put(DBSubscription.CustomerEntry.COLUMN_NAME, customer.name)
        values.put(DBSubscription.CustomerEntry.COLUMN_SURNAME, customer.surname)
        values.put(DBSubscription.CustomerEntry.COLUMN_SUBSCRIPTION_DATE, customer.subscriptionDate)
        values.put(DBSubscription.CustomerEntry.COLUMN_MONTHS_SUBSCRIPTION, customer.subscription)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBSubscription.CustomerEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCustomer(customerID: Int): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(customerID.toString())
        // Issue SQL statement.
        db.delete(DBSubscription.CustomerEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    @SuppressLint("Recycle")
    fun readCustomer(customerID: Int): ArrayList<Customer> {
        val customers = ArrayList<Customer>()
        val db = writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(
                "select * from " + DBSubscription.CustomerEntry.TABLE_NAME + " WHERE " + DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID + "='" + customerID + "'",
                null
            )
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var customerid: Int
        var name: String
        var surname: String
        var subscriptionDate: String
        var subscription: Int

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                customerid = cursor.getInt(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_NAME))
                surname = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_SURNAME))
                subscriptionDate = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_SUBSCRIPTION_DATE))
                subscription = cursor.getInt(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_MONTHS_SUBSCRIPTION))

                customers.add(Customer(customerid, name, surname, subscriptionDate, subscription))
                cursor.moveToNext()
            }
        }
        return customers
    }

    @SuppressLint("Recycle")
    fun readAllCustomers(): ArrayList<Customer> {
        val customers = ArrayList<Customer>()
        val db = writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBSubscription.CustomerEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var customerid: Int
        var name: String
        var surname: String
        var subscriptionDate: String
        var subscription: Int

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                customerid = cursor.getInt(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_NAME))
                surname = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_SURNAME))
                subscriptionDate = cursor.getString(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_SUBSCRIPTION_DATE))
                subscription = cursor.getInt(cursor.getColumnIndex(DBSubscription.CustomerEntry.COLUMN_MONTHS_SUBSCRIPTION))

                customers.add(Customer(customerid, name, surname, subscriptionDate, subscription))
                cursor.moveToNext()
            }
        }
        return customers
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Subscriptions.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSubscription.CustomerEntry.TABLE_NAME + " (" +
                    DBSubscription.CustomerEntry.COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY," +
                    DBSubscription.CustomerEntry.COLUMN_NAME + " TEXT," +
                    DBSubscription.CustomerEntry.COLUMN_SURNAME + " TEXT," +
                    DBSubscription.CustomerEntry.COLUMN_SUBSCRIPTION_DATE + " TEXT," +
                    DBSubscription.CustomerEntry.COLUMN_MONTHS_SUBSCRIPTION + " INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSubscription.CustomerEntry.TABLE_NAME
    }

}
