package com.garosero.android.hobbyroadmap.main.til

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.DeleteTilRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.request.UpdateTilRequest

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
                tvContent.setText(tilItem.content)
            } // end if

            btnDelete.setOnClickListener {
                showAlertDialog(object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tilItem = model?.getTilItemBeingEdited()
                        val tilId = tilItem?.tilId ?: return

                        val deleteRequest = DeleteTilRequest(tilId)
                        NetworkFactory.request(deleteRequest)

                        model?.updateTilItemBeingEdited(null)
                        view?.findNavController()?.navigate(R.id.action_tilItemFragment_to_tilListFragment)
                    }
                })
            }
            btnSave.setOnClickListener {
                if (isEdited()){
                    val tilItem = model?.getTilItemBeingEdited()
                    val tilId = tilItem?.tilId ?: return@setOnClickListener
                    val content = binding?.tvContent?.text ?: return@setOnClickListener

                    val updateRequest = UpdateTilRequest(tilId, content.toString())
                    NetworkFactory.request(updateRequest)

                } // end if

                model?.updateTilItemBeingEdited(null)
                view?.findNavController()?.navigate(R.id.action_tilItemFragment_to_tilListFragment)
            }
        }
    }

    private fun showAlertDialog(listener : DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(this.requireContext())
        builder
            .setTitle("경고")
            .setMessage("기록이 삭제됩니다.")
            .setPositiveButton("yes", listener)
            .setNegativeButton("no", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
            .create()
            .show()
    }

    private fun isEdited() : Boolean{
        val originContent = model?.getTilItemBeingEdited()?.content
        val newContent = binding?.tvContent?.text

        return if (originContent == null || newContent == null){ false }
        else !originContent.equals(newContent)
    }
}