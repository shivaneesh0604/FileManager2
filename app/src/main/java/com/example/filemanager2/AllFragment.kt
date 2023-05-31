package com.example.filemanager2

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllFragment : Fragment(), RecyclerFileAdapter.FileClickListener {

    private lateinit var recyclerView: RecyclerView
    internal val allFragmentViewModel: AllFragmentViewModel by activityViewModels()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("back", "onviewCreated in allfrag")
        (activity as MainActivity).activeFragmentTag = "AllFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("detailsFrag","oncreateView in AllFrag")
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        val newFileButton: FloatingActionButton = view.findViewById(R.id.add_file)
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.allRecyclerViewContainer, AllFragmentRecyclerViewContainer()
//            )
//            .addToBackStack(null).commit()

        recyclerView = view.findViewById(R.id.allRecyclerviewFile)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = RecyclerFileAdapter(requireContext(), AllData.getAllFiles(), this)
        recyclerView.adapter = itemsAdapter

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent = result.data!!
                    val fileName = data.getStringExtra("fileName")
                    val fileTypeReceived = data.getStringExtra("fileType")
                    val lastModifiedTime = data.getStringExtra("lastModifiedTime")
                    val fileSize = data.getStringExtra("fileSize")
                    val filePath = data.getStringExtra("filePath")
                    lateinit var fileType: FileType
                    if (fileTypeReceived == "txt") {
                        fileType = FileType.txt
                    } else if (fileTypeReceived == "doc") {
                        fileType = FileType.doc
                    } else if (fileTypeReceived == "docx") {
                        fileType = FileType.docx
                    }
                    val file = File(fileName, fileType, lastModifiedTime, fileSize, filePath)
                    AllData.addFile(file)
                }
            }

        if (allFragmentViewModel.file != null) {
            Log.i("detailsFrag","saved in allFragment View mode")
            val detailsFragment = DetailsFragment()
            detailsFragment.sourceFragment = "AllFragment"
            val result = Bundle()
//            result.putString("sourceFragment","AllFragment")
            result.putString("fileName", allFragmentViewModel.file!!.fileName)
            result.putString(
                "lastModifiedTime",
                allFragmentViewModel.file!!.lastModifiedTime
            )
            result.putString("fileType", allFragmentViewModel.file!!.fileType.toString())
            result.putString("fileSize", allFragmentViewModel.file!!.fileSize)
            result.putString("filePath", allFragmentViewModel.file!!.filePath)
            detailsFragment.arguments = result
            val manager = requireActivity().supportFragmentManager.beginTransaction()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager.replace(
                    R.id.allSelectedFileView,
                    detailsFragment,"AllFragment"
                )
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                manager.replace(
                    R.id.drawerSwitch, detailsFragment,"AllFragment"
                )
            }
            manager.addToBackStack("AllFragment").commit()
        }
        newFileButton.setOnClickListener {
            val intent: Intent =
                Intent(requireContext(), NewFileCreationActivity::class.java)
            activityResultLauncher.launch(intent)
        }
        return view
    }

    override fun clickedFile(file: File) {
        Log.i("selected", "clicked")
        val result = Bundle()
//        result.putString("sourceFragment","AllFragment")
        result.putString("fileName", file.fileName)
        result.putString("lastModifiedTime", file.lastModifiedTime)
        result.putString("fileType", file.fileType.toString())
        result.putString("fileSize", file.fileSize)
        result.putString("filePath", file.filePath)

        allFragmentViewModel.file = file
        Log.i(
            "orientation",
            "check in recyclerViewContainerFragment whether fileis saved " + allFragmentViewModel.file
        )
        val detailsFragment = DetailsFragment()
        detailsFragment.sourceFragment = "AllFragment"
        detailsFragment.arguments = result
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tag11", "Portrait")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.drawerSwitch, detailsFragment,"AllFragment")
                .addToBackStack("AllFragment")
                .commit()
        } else {
            Log.i("tag11", "Landscape")
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().replace(
                R.id.allSelectedFileView,
                detailsFragment,"AllFragment"
            )
                .addToBackStack("AllFragment")
                .commit()
        }
    }


}
