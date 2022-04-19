package com.elorenzog.finalprojecttvshows.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.elorenzog.finalprojecttvshows.R
import com.elorenzog.finalprojecttvshows.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    // ViewBinding
    private lateinit var binding: FragmentForgotPasswordBinding

    // FirebaseAuthorization
    private lateinit var firebaseAuth: FirebaseAuth

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Init firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure progress dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Sending mail...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.emailRecoverEt.setText(requireArguments().getString("email").toString())

        binding.loginBtn.setOnClickListener {
            val emailAddress = binding.emailRecoverEt.text.toString()

            progressDialog.show()

            try{
                firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(),"The recovery email has been sent ", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
//                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                            val fragment = LoginFragment()
//                            fragment.arguments = bundle
                            val transaction = requireFragmentManager().beginTransaction()
                            transaction.replace(R.id.fragmentContainerView, fragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        binding.emailRecoverEt.error = "An error has occurred, please check your email"
                        Toast.makeText(requireContext(),"The recovery email has not been sent ", Toast.LENGTH_SHORT).show()

                    }
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Please write your email address", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
//        inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

}