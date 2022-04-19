package com.elorenzog.finalprojecttvshows.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.data.User
import com.elorenzog.finalprojecttvshows.databinding.FragmentProfileBinding
import com.elorenzog.finalprojecttvshows.databinding.FragmentUpdateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UpdateProfileFragment : Fragment() {

    // ViewBinding
    private lateinit var binding: FragmentUpdateProfileBinding

    // FirebaseAuthorization
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        database = Firebase.database.reference

        binding.nameEt.setText(requireArguments().getString("name").toString())
        binding.lastNameEt.setText(requireArguments().getString("lastName").toString())
        binding.phoneNumberEt.setText(requireArguments().getString("phoneNumber").toString())
        if (requireArguments().getString("gender").toString() == "Male") {
            binding.sexEt.check(R.id.sexMale)
        } else if (requireArguments().getString("gender").toString() == "Female"){
            binding.sexEt.check(R.id.sexFemale)
        }
//        binding.sexEt.setText(requireArguments().getString("name").toString())
//        binding.emailEt.setText(requireArguments().getString("email").toString())
        binding.countryEt.setText(requireArguments().getString("country").toString())
        binding.provinceEt.setText(requireArguments().getString("province").toString())
        binding.addressEt.setText(requireArguments().getString("address").toString())
        binding.birthEt.setText(requireArguments().getString("birthDate").toString())

        binding.signUpBtn.setOnClickListener {

            var user: User = User()
            user.name = binding.nameEt.text.toString()
            user.lastName = binding.lastNameEt.text.toString()
            user.phoneNumber = binding.phoneNumberEt.text.toString()
            user.userid = firebaseUser!!.email.toString()
            var sex: String = if(binding.sexMale.isChecked) {
                "Male"
            } else if (binding.sexFemale.isChecked) {
                "Female"
            } else {
                "Undetermined"
            }
//            var sex = when (sexId) {
//                1 -> {
//                    "Male"
//                }
//                2 -> {
//                    "Female"
//                }
//                else -> {
//                    binding.sexEt.checkedRadioButtonId.toString()
//                }
//            }
            user.sex = sex
            user.dateOfBirth = binding.birthEt.text.toString()
            user.country = binding.countryEt.text.toString()
            user.province = binding.provinceEt.text.toString()
            user.address = binding.addressEt.text.toString()

            try {
                database.child("users").child(firebaseUser!!.uid).setValue(user)
                database.push()
                Toast.makeText(requireContext(),"Profile updated", Toast.LENGTH_SHORT).show()
                val fragment = ProfileFragment()
                val transaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            catch (e: Exception) {
                Toast.makeText(requireContext(),"An error has occurred due to ${e.message}", Toast.LENGTH_SHORT).show()
            }


        }

//        bundle.putString("phoneNumber", userFound.phoneNumber)
//        bundle.putString("gender", userFound.sex)
//        bundle.putString("email", userFound.userid)
//        bundle.putString("country", userFound.country)
//        bundle.putString("province", userFound.province)
//        bundle.putString("address", userFound.address)
//        bundle.putString("birthDate", userFound.dateOfBirth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
//        inflater.inflate(R.layout.fragment_update_profile, container, false)
    }

}