package com.example.firebaseimageandtextsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseimageandtextsample.databinding.FragmentInitialBinding
import com.example.firebaseimageandtextsample.firebase.MyFirebaseAuth
import com.example.firebaseimageandtextsample.firebase.uid
import com.google.firebase.auth.FirebaseAuth

class InitialFragment : Fragment() {
    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!
    private val myAuth = MyFirebaseAuth()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)

        if (auth.currentUser != null) {
            // クラッシュ
            //val action = InitialFragmentDirections.actionInitialFragmentToListFragment()
            //this.findNavController().navigate(action)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButton.setOnClickListener {
            val userEmail = binding.userEmailInput.text.toString()
            val userPassword = binding.userPasswordInput.text.toString()
            if (!userEmail.isNullOrEmpty() && !userPassword.isNullOrEmpty()) {
                myAuth.signUp(userEmail, userPassword)
                    if (uid != null) {
                        val action = InitialFragmentDirections.actionInitialFragmentToCreateProfileFragment()
                        this.findNavController().navigate(action)
                    }
            }
        }

        binding.loginButton.setOnClickListener {
            val userEmail = binding.userEmailInput.text.toString()
            val userPassword = binding.userPasswordInput.text.toString()
            if (!userEmail.isNullOrEmpty() && !userPassword.isNullOrEmpty()) {
                myAuth.logIn(userEmail, userPassword)
                if (uid != null) {
                    val action = InitialFragmentDirections.actionInitialFragmentToListFragment()
                    this.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}