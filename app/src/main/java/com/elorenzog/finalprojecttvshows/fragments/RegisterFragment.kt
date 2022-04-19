package com.elorenzog.finalprojecttvshows.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.core.view.get
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.data.User
import com.elorenzog.finalprojecttvshows.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    // ViewBinding
    private lateinit var binding: FragmentRegisterBinding

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var name = ""
    private var lastName = ""
    private var phoneNumber = ""
    private var email = ""
    private var password = ""
    private var sex = ""
    private var dateOfBirth = ""
    private var country = ""
    private var province = ""
    private var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Progress Dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        // handle click
        binding.signUpBtn.setOnClickListener{
            validateData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
//        inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun validateData() {
        name = binding.nameEt.text.toString().trim()
        lastName = binding.lastNameEt.text.toString().trim()
        phoneNumber = binding.phoneNumberEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val radioButtonId = binding.sexEt.checkedRadioButtonId;
        sex = when (radioButtonId) {
            1 -> {
                "Male"
            }
            2 -> {
                "Female"
            }
            else -> {
                "Undetermined"
            }
        }
//        sex = radioSexButton.text.toString()
        dateOfBirth = binding.birthEt.text.toString()
        country = binding.countryEt.text.toString().trim()
        province = binding.provinceEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()

        // validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Please enter password"
        }
        else if(password.length < 6){
            binding.passwordEt.error = "Password must at least characters long"
        }
        else{ // all data is validated
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressDialog.show()

        // create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(requireContext(),"Account created with email $email", Toast.LENGTH_SHORT).show()
                // data
                var user = User()
                user.userid = email.toString()
                user.name = name
                user.lastName = lastName
                user.phoneNumber = phoneNumber
                user.sex = sex
                user.dateOfBirth = dateOfBirth
                user.country = country
                user.province = province
                user.address = address
//
                try {
                    database.child("users").child(firebaseUser.uid).setValue(user)
                    database.push()
                    val fragment = TvShowListFragment()
//                    fragment.arguments = bundle
                    val transaction = requireFragmentManager().beginTransaction()
                    transaction.replace(R.id.fragmentContainerView, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
//                    findNavController().navigate(R.id.action_loginFragment_to_tvShowListFragment)
                }
                catch (e: Exception){
                    Toast.makeText(requireContext(),"An error has occurred", Toast.LENGTH_SHORT).show()
                }


            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}