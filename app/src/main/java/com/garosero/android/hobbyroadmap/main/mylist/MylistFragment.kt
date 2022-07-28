package com.garosero.android.hobbyroadmap.main.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.databinding.FragmentMylistBinding
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel

class MylistFragment : Fragment() {
    var model : MylistViewModel = MylistViewModel()
    var binding : FragmentMylistBinding? = null
    var adapter : MylistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMylistBinding.inflate(inflater, container, false)
        model.subscribeUserData(requireContext())
        adapter = MylistAdapter(SQLiteSearchHelper(requireContext()))

        initView()
        registerObserver()

        return binding!!.root
    }

    private fun registerObserver(){
        // data observer for adapter ver2
        val observerVer2 = Observer<ArrayList<MyClass>>{
            // oder by LClassID, MClassID, SClassID
            var data = it
            data.sortBy { "${it.LClassId} ${it.MClassId} ${it.SClassId}" }
            adapter?.submitData(it)
            initView()
        }
        model.classList.observe(viewLifecycleOwner, observerVer2)
    }

    private fun initView(){
        binding!!.recycler.adapter = adapter

        if (binding == null){
            return
        } // end if

        if (adapter == null){
            return
        } // end if

        if (adapter!!.dataset.isEmpty()){
            binding!!.llEmpty.visibility = View.VISIBLE
        } else {
            binding!!.llEmpty.visibility = View.GONE
        } // end if

        binding!!.btnFindRoadmap.setOnClickListener {
            //Log.e("EMPTY", "click")
            findNavController().popBackStack()
            findNavController().navigate(R.id.item_home)
        }
    }
}