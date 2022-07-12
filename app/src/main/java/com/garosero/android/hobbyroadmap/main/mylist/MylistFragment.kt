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
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel

class MylistFragment : Fragment() {
    var model : MylistViewModel = MylistViewModel()
    var binding : FragmentMylistBinding? = null
    var adapter = MylistParentAdapter(model)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMylistBinding.inflate(inflater, container, false)
        model.subscribeUserData(requireContext())
        initView()
        registerObserver()

        return binding!!.root
    }

    private fun registerObserver(){
        val observer = Observer<MutableMap<String, ArrayList<MyClass>>>{
            val keys = ArrayList<String>()
            it.keys.forEach {
                keys.add(it)
            }
            adapter.submitData(keys)
        }
        model.myClass.observe(viewLifecycleOwner, observer)
    }

    private fun initView(){
        binding!!.recycler.adapter = adapter

        if (binding == null){
            return
        } // end if

        if (adapter.dataset.isEmpty()){
            binding!!.emptyView.visibility = View.GONE
        } else {
            binding!!.emptyView.visibility = View.VISIBLE
        } // end if

        // fixme click event 가  인식되지 않아 추후 처리
        /*binding!!.emptyView.setOnClickCallback(
            object : EmptyView.OnClickCallback {
                override fun onClick(v: View) {
                    Log.e("EMPTY", "click")
                    findNavController().navigate(R.id.item_home)
                }
            }
        )*/
        binding!!.emptyView.setOnClickListener {
            //Log.e("EMPTY", "click")
            findNavController().navigate(R.id.item_home)
        }
    }
}