package com.example.filemanager2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerFileAdapter(private val context: Context, private val listItems: MutableList<File>, val fileClickListener: FileClickListener) :
    RecyclerView.Adapter<RecyclerFileAdapter.FileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
//            Log.i("orientation","came recycler file view for dimensions")
        return FileViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_file_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = listItems[position]
        holder.fileName.text = item.fileName
//        holder.fileType.text = "." + item.fileType.toStrixng()
        holder.fileSize.text = item.fileSize
        holder.filePath.text = item.filePath
        holder.fileType.text = item.fileType.toString()

        if (item.fileType == FileType.docx){
            holder.fileImage.setImageResource(R.drawable.docx_image)
        }
        else if (item.fileType == FileType.doc){
            holder.fileImage.setImageResource(R.drawable.doc_image)
        }else if (item.fileType == FileType.txt){
            holder.fileImage.setImageResource(R.drawable.text_image)
        }

        holder.itemView.setOnClickListener{
            fileClickListener.clickedFile(item)
        }
    }

    class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName = view.findViewById<TextView>(R.id.fileName_TextView)
//        val fileType: TextView = view.findViewById(R.id.fileType)
        val filePath = view.findViewById<TextView>(R.id.filePath_TextView)
        val fileSize = view.findViewById<TextView>(R.id.fileSize_TextView)
        val fileImage = view.findViewById<ImageView>(R.id.fileImage)
        val fileType = view.findViewById<TextView>(R.id.fileType_TextView)
    }

    interface FileClickListener{
        fun clickedFile(file: File)
    }
}