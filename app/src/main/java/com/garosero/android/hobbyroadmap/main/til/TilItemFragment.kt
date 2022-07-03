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
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding
import com.garosero.android.hobbyroadmap.main.helper.ActionConfig
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.CreateTilRequest
import com.garosero.android.hobbyroadmap.network.request.DeleteTilRequest
import com.garosero.android.hobbyroadmap.network.request.UpdateTilRequest

@RequiresApi(Build.VERSION_CODES.O)
class TilItemFragment : Fragment() {
    private var binding : FragmentTilItemBinding? = null
    private var model : TilViewModel? = null
    private var courseItem : CourseItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilItemBinding.inflate(layoutInflater)
        model = TilParentFragment.model

        initIntentData()
        initView()

        return binding?.root
    }

    private fun initView(){
        if (binding == null){
            return
        }

        with(binding!!) {
            val tilItem = model?.getTilItemBeingEdited()

            // syllabus 에서 write 를 요청 받음.
            if (courseItem != null){
                tvTitle.text = courseItem!!.title
                tvDate.text = DateHelper.changeDateToYYMMDD()

                llBtnCreate.visibility = View.VISIBLE
                llBtnUpdate.visibility = View.GONE
            } else {
                if (tilItem != null){
                    tvTitle.text = tilItem.title
                    tvDate.text = tilItem.date
                    tvContent.setText(tilItem.content)
                } // end if

                llBtnCreate.visibility = View.GONE
                llBtnUpdate.visibility = View.VISIBLE

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

            btnCancel.setOnClickListener {
                requireActivity().finish()
            }

            btnSave.setOnClickListener {
                // get Til item
                val tilItem = TilItem(
                    date = getDateString(),
                    uid = AppApplication().getUid(),
                    content = getContent(),
                    /*courseID = getCourseId(),
                    roadmapID = getRoadmapId(),
                    categoryID = getCategoryId()*/
                )

                // request
                //NetworkFactory.request(CreateTilRequest(tilItem))
                //requireActivity().finish()

                requireActivity().finish()
            }

            btnUpdate.setOnClickListener {
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

    private fun getDateString() : String{
        val dateString = binding?.tvDate?.text as String
        if (dateString == ""){
            return DateHelper.changeDateToYYMMDD()
        } // end if

        return dateString
    }

    private fun getContent() : String {
        return binding?.tvContent?.text.toString()
    }

    private fun initIntentData() {
        val actionConfig = ActionConfig()
        courseItem = actionConfig.getCourseItem(arguments)
    }

    /**
     * 새로 만드는 중인지, 수정 중인지 체크
     */
    private fun isUpdateMode() : Boolean {
        return requireActivity().intent.action == ActionConfig.ACTION_ROADMAP_TO_TIL_WRITE
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