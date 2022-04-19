package com.elorenzog.finalprojecttvshows.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.data.User
import com.elorenzog.finalprojecttvshows.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    // ViewBinding
    private lateinit var binding: FragmentProfileBinding

    // FirebaseAuthorization
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var userFound: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
//        inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        chekUser()

        binding.logOutBtn.setOnClickListener{
            firebaseAuth.signOut()
            chekUser()
        }

        binding.floatingActionButtonEdit.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("name", userFound.name)
            bundle.putString("lastName", userFound.lastName)
            bundle.putString("phoneNumber", userFound.phoneNumber)
            bundle.putString("gender", userFound.sex)
            bundle.putString("email", userFound.userid)
            bundle.putString("country", userFound.country)
            bundle.putString("province", userFound.province)
            bundle.putString("address", userFound.address)
            bundle.putString("birthDate", userFound.dateOfBirth)
            val fragment = UpdateProfileFragment()
            fragment.arguments = bundle
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun chekUser() {
        // check user is logged
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {

            try {
                // texts
                val data = database.child("users").child(firebaseUser.uid).get().addOnSuccessListener {

                    userFound = it.getValue<User>()!!
                    if (userFound != null) {
                        binding.nameProfileEt.text = getString(R.string.name_profile) + userFound.name
                        binding.lastNameProfileEt.text = getString(R.string.lastname_profile) + userFound.lastName
                        binding.phoneNumberProfileEt.text = getString(R.string.phone_number_profile) + userFound.phoneNumber
                        binding.sexProfileEt.text = getString(R.string.sex_profile) + userFound.sex
                        binding.emailProfileEt.text =  getString(R.string.email_profile) + userFound.userid
                        binding.countryProfileEt.text = getString(R.string.country_profile) + userFound.country
                        binding.provinceProfileEt.text = getString(R.string.province_profile) + userFound.province
                        binding.addressProfileEt.text = getString(R.string.address_profile) + userFound.address
                        binding.birthProfileEt.text = getString(R.string.birth_date_profile) + userFound.dateOfBirth
                    }
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),"Error getting data from firebase", Toast.LENGTH_SHORT).show()
                    Log.e("firebase", "Error getting data", it)
                }
            }
            catch (e: Exception){
                Toast.makeText(requireContext(),"Error getting data ${e.message}", Toast.LENGTH_SHORT).show()
            }




        }
        else {
            val fragment = LoginFragment()
//            fragment.arguments = bundle
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
//            finish()
        }
    }
}