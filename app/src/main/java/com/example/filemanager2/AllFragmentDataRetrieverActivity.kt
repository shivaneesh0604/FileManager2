//package com.example.filemanager2
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ListView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class AllFragmentDataRetrieverActivity : AppCompatActivity(),RecyclerFileAdapter.FileClickListener {
//    private lateinit var recyclerItems: RecyclerView
//    private lateinit var intent: Intent
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_all_fragement_data_retriever)
//
//        recyclerItems = findViewById<RecyclerView>(R.id.retreiver_recyclerView)
//        recyclerItems.layoutManager = LinearLayoutManager(this)
//
//        val itemsAdapter = RecyclerFileAdapter(this, AllData.getAllFiles(), this)
//        recyclerItems.adapter = itemsAdapter
//    }
//
//    override fun clickedFile(file: File) {
//        intent.putExtra("selected_file",file.id)
//        setResult(RESULT_OK, intent)
//        finish()
//    }
//}
