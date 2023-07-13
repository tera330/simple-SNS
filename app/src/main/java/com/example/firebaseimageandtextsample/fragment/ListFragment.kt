package com.example.firebaseimageandtextsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseimageandtextsample.ItemListAdapter
import com.example.firebaseimageandtextsample.databinding.FragmentListBinding
import com.example.firebaseimageandtextsample.firebase.FireStore
import com.google.firebase.auth.FirebaseAuth

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val fireStore = FireStore()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemListAdapter = ItemListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = itemListAdapter

        binding.postButton.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToCreatePostFragment()
            this.findNavController().navigate(action)
        }

        binding.signoutButton.setOnClickListener {
            auth.signOut()
        }

        fireStore.getData(itemListAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

