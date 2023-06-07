package com.antwhale.sample.samplepdf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    private lateinit var pdfBtn: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pdfBtn = findViewById(R.id.pdfBtn)
        imageView = findViewById(R.id.imageView)

        pdfBtn.setOnClickListener {
            val fileName = filesDir.absolutePath.plus("/").plus(System.currentTimeMillis().toString()).plus(".pdf")
            Log.d(TAG, "fileName: $fileName")

            convertImageToPDF(fileName)
        }
    }

    private fun convertImageToPDF(pdfFilePath: String) {
// Load the image from the file
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ex_doc_img001)
//        val resizedBitmap = Bitmap.createScaledBitmap(
//            bitmap, bitmap.width / 3, bitmap.height / 3, false)

        // Create a new PdfDocument
        val pdfDocument = PdfDocument()

        // Create a page from the image file
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create() // A4 size in points (72 points = 1 inch)
        val page = pdfDocument.startPage(pageInfo)



        // Draw the bitmap on the page
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // Finish the page
        pdfDocument.finishPage(page)

        // Save the document to the file
        try {
            val file = File(pdfFilePath)
            val fos = FileOutputStream(file)
            pdfDocument.writeTo(fos)
            pdfDocument.close()
            fos.close()
            // PDF creation successful
        } catch (e: IOException) {
            e.printStackTrace()
            // Error occurred while creating the PDF
        }
    }
}