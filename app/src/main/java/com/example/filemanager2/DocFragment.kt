package com.example.filemanager2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DocFragment : Fragment(), RecyclerFileAdapter.FileClickListener  {

    private lateinit var recyclerView: RecyclerView
    private val docFragmentViewModel:DocFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("back","onviewCreated in docFrag")
        (activity as MainActivity).activeFragmentTag = "DocFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("detailsFrag","oncreateView in DocFrag")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_doc, container, false)

        recyclerView = view.findViewById(R.id.docRecyclerviewFile)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = RecyclerFileAdapter(requireContext(), AllData.getAllDocFiles(), this)
        recyclerView.adapter = itemsAdapter

        if (docFragmentViewModel.file != null) {
            Log.i("detailsFrag","saved in docFragmentViewModel View mode")
            val detailsFragment = DetailsFragment()
            detailsFragment.sourceFragment = "DocFragment"
            val result = Bundle()
//            result.putString("sourceFragment","DocFragment")
            result.putString("fileName", docFragmentViewModel.file!!.fileName)
            result.putString(
                "lastModifiedTime",
                docFragmentViewModel.file!!.lastModifiedTime
            )
            result.putString("fileType", docFragmentViewModel.file!!.fileType.toString())
            result.putString("fileSize", docFragmentViewModel.file!!.fileSize)
            result.putString("filePath", docFragmentViewModel.file!!.filePath)
            detailsFragment.arguments = result
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager.replace(
                    R.id.docSelectedFileView,
                    detailsFragment,"DocFragment"
                )
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager.replace(
                    R.id.drawerSwitch, detailsFragment,"DocFragment"
                )
            }
            manager.addToBackStack("DocFragment").commit()
        }

        return  view
    }

    override fun clickedFile(file: File) {
        Log.i("selected", "clicked")
        val result = Bundle()
        result.putString("fileName", file.fileName)
        result.putString("lastModifiedTime", file.lastModifiedTime)
        result.putString("fileType", file.fileType.toString())
        result.putString("fileSize", file.fileSize)
        result.putString("filePath", file.filePath)
//        result.putString("sourceFragment","DocFragment")
        docFragmentViewModel.file = file
        val detailsFragment = DetailsFragment()
        detailsFragment.sourceFragment = "DocFragment"
        detailsFragment.arguments = result
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tag11", "Portrait")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.drawerSwitch, detailsFragment,"DocFragment")
                .addToBackStack("DocFragment")
                .commit()
        } else {
            Log.i("tag11", "Landscape")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().replace(
                R.id.docSelectedFileView,
                detailsFragment,"DocFragment"
            )
                .addToBackStack("DocFragment")
                .commit()
        }
    }

}