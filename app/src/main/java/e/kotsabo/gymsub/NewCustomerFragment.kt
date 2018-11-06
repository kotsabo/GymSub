package e.kotsabo.gymsub

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewCustomerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NewCustomerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class NewCustomerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var results: TextView
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var subscription: EditText
    private lateinit var go: Button
    private lateinit var erase: Button

    private lateinit var customersDBHelper : CustomersDBHelper

    private var totalCustomers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val thisView: View = inflater.inflate(R.layout.fragment_new_customer, container, false)

        this.results = thisView.findViewById(R.id.results)
        this.name = thisView.findViewById(R.id.name)
        this.surname = thisView.findViewById(R.id.surname)
        this.subscription = thisView.findViewById(R.id.subscription)
        this.go = thisView.findViewById(R.id.go)
        this.erase = thisView.findViewById(R.id.erase)

        this.go.setOnClickListener { this.goAction() }

        return thisView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.customersDBHelper = CustomersDBHelper(context)

        this.totalCustomers = this.customersDBHelper.readAllCustomers().size

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewCustomerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewCustomerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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
