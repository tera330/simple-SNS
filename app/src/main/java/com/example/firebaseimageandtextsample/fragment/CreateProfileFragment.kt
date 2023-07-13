package com.example.firebaseimageandtextsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseimageandtextsample.databinding.FragmentCreateProfileBinding
import com.example.firebaseimageandtextsample.firebase.FireStore

class CreateProfileFragment : Fragment() {
    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!
    private val fireStore = FireStore()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            val address = binding.userAddressInput.text.toString()
            val name = binding.userNameInput.text.toString()
            val introduction = binding.userInfo.text.toString()

            if (!address.isNullOrEmpty() && !name.isNullOrEmpty() && !introduction.isNullOrEmpty()) {
                fireStore.createProfile(address, name, introduction)
                val action = CreateProfileFragmentDirections.actionCreateProfileFragmentToListFragment()
                this.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}