package com.springsamurais.toyswap.ui.updatelisting


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.springsamurais.toyswap.R
import com.springsamurais.toyswap.databinding.ActivityUpdateListingBinding
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.service.APIService
import com.springsamurais.toyswap.service.RetrofitInstance
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UpdateListingActivity : AppCompatActivity() {

    var imageView: ImageView? = null
    var selectedBitmap: Bitmap? = null
    var takeImageButton: Button? = null
    var galleryImageButton: Button? = null
    var itemTitleInput: EditText? = null
    var itemDescriptionInput: EditText? = null
    var conditionSpinner: Spinner? = null
    var categorySpinner: Spinner? = null
    var updateListingButton: Button? = null
    var cancelButton: Button? = null
    var apiService: APIService? = null

    private var binding: ActivityUpdateListingBinding? = null
    private var handler: UpdateListingClickHandlers? = null;
    private var listing: Listing? = null;


    private val REQUEST_CAMERA_PERMISSION = 100

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.springsamurais.toyswap.R.layout.activity_update_listing)

        listing = intent.getParcelableExtra("LISTING_ITEM", Listing::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_listing)
        handler = UpdateListingClickHandlers(this)
        binding?.clickHandler = handler
        binding?.listing = listing

        imageView = findViewById(com.springsamurais.toyswap.R.id.user_image)

        takeImageButton = findViewById(com.springsamurais.toyswap.R.id.take_image_button)
        galleryImageButton = findViewById(com.springsamurais.toyswap.R.id.gallery_image_button)
        itemTitleInput = findViewById(com.springsamurais.toyswap.R.id.item_title_input)
        itemDescriptionInput = findViewById(com.springsamurais.toyswap.R.id.item_description_input)


        updateListingButton = findViewById(R.id.updateListingButton)
        cancelButton = findViewById(com.springsamurais.toyswap.R.id.cancel_button)


        conditionSpinner = findViewById(com.springsamurais.toyswap.R.id.condition_spinner)
        ArrayAdapter.createFromResource(
            this,
            com.springsamurais.toyswap.R.array.conditions_array,
            android.R.layout.simple_spinner_item
        ).also{
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            conditionSpinner?.adapter = adapter
        }


        categorySpinner = findViewById(com.springsamurais.toyswap.R.id.category_spinner)
        ArrayAdapter.createFromResource(
            this,
            com.springsamurais.toyswap.R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also{
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner?.adapter = adapter
        }


        apiService = RetrofitInstance.instance

        cancelButton?.setOnClickListener {
            finish()
        }

        takeImageButton?.setOnClickListener {
            // check camera permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // if permission granted, open camera
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        galleryImageButton?.setOnClickListener{
            openGallery()
        }

        updateListingButton?.setOnClickListener(View.OnClickListener {
            if(itemTitleInput?.text.toString().isEmpty() || itemDescriptionInput?.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter title, description and image", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            uploadListingToServer()
        })


       setFormattedContent(listing!!)

    } // end of onCreate

    private fun setFormattedContent(listing: Listing) {
        // Select views from layout
        itemTitleInput?.setText(listing.title)
        itemDescriptionInput?.setText(listing.description)
        conditionSpinner?.setSelection(listing.condition!!.indexOf("_"))
        categorySpinner?.setSelection(listing.category!!.indexOf("_"))
    }


    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }


    private fun uploadListingToServer() {

        val title = RequestBody.create(MediaType.parse("text/plain"), itemTitleInput?.text.toString())
        val description = RequestBody.create(MediaType.parse("text/plain"), itemDescriptionInput?.text.toString())
        val condition = RequestBody.create(MediaType.parse("text/plain"), conditionSpinner?.selectedItem.toString())
        val category = RequestBody.create(MediaType.parse("text/plain"), categorySpinner?.selectedItem.toString())
        val statusListing = RequestBody.create(MediaType.parse("text/plain"), "AVAILABLE")
        // val listingID = RequestBody.create(MediaType.parse("text/plain"), listing?.id.toString())
        val listingID =  listing?.id.toString()


        apiService?.updateListing(
            listingID,
            title,
            category,
            description,
            condition,
            statusListing
        )?.enqueue(object : Callback<Listing> {
            override fun onResponse(call: Call<Listing>, response: Response<Listing>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateListingActivity, "Listing Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            override fun onFailure(call: Call<Listing>, t: Throwable) {
                Toast.makeText(this@UpdateListingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        uploadLauncher.launch(intent)
    }

    var uploadLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                val data = result.data
                val imageUri = data?.data
                try {
                    selectedBitmap =
                        MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    imageView!!.setImageBitmap(selectedBitmap)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }


    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    var cameraLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result?.resultCode == RESULT_OK) {
                val data = result.data
                val bitmap = data?.extras?.get("data") as Bitmap
                selectedBitmap = bitmap
                imageView!!.setImageBitmap(bitmap)
            }
        }


} // end of class