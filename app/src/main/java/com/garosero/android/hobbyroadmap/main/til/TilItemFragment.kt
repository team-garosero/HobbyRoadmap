package com.garosero.android.hobbyroadmap.main.til

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel

@RequiresApi(Build.VERSION_CODES.O)
class TilItemFragment : Fragment() {
    var binding : FragmentTilItemBinding? = null
    var model : TilViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilItemBinding.inflate(layoutInflater)
        model = TilParentFragment.model

        initView()

        return binding?.root
    }

    private fun initView(){
        if (binding == null){
            return
        }

        with(binding!!) {
            val tilItem = model?.getTilItemBeingEdited()
            if (tilItem != null){
                tvTitle.text = tilItem.title
                tvDate.text = tilItem.date
                tvContent.text = tilItem.content
            } // end if

            btnDelete.setOnClickListener {
                model?.updateTilItemBeingEdited(null)
                view?.findNavController()?.navigate(R.id.action_tilItemFragment_to_tilListFragment)
            }
            btnSave.setOnClickListener {
                model?.updateTilItemBeingEdited(null)
                view?.findNavController()?.navigate(R.id.action_tilItemFragment_to_tilListFragment)
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("경고").setMessage("수정된 내용이 있습니다. 삭제하거나 저장해주세요.")
        builder.setPositiveButton("yes", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })

        builder.create().show()
    }

    private fun isEdited() : Boolean{
        val originContent = model?.getTilItemBeingEdited()?.content
        val newContent = binding?.tvContent?.text

        return if (originContent == null || newContent == null){ false }
        else !originContent.equals(newContent)
    }
}