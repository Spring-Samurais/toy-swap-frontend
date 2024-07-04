
package com.springsamurais.toyswap.ui.addlisting

//import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.springsamurais.toyswap.databinding.ActivityAddListingBinding
import com.springsamurais.toyswap.model.Listing
import com.springsamurais.toyswap.service.APIService
import com.springsamurais.toyswap.service.RetrofitInstance
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddListingActivity : AppCompatActivity() {

    var imageView: ImageView? = null;
    var selectedBitmap: Bitmap? = null;
    var takeImageButton: Button? = null;
    var galleryImageButton: Button? = null;
    var itemTitleInput: EditText? = null;
    var itemDescriptionInput: EditText? = null;
    var conditionSpinner: Spinner? = null;
    var categorySpinner: Spinner? = null;
    var addListingButton: Button? = null;
    var cancelButton: Button? = null;
    var apiService: APIService? = null;


    private val REQUEST_CAMERA_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.springsamurais.toyswap.R.layout.activity_add_listing)


        imageView = findViewById(com.springsamurais.toyswap.R.id.user_image)
        takeImageButton = findViewById(com.springsamurais.toyswap.R.id.take_image_button)
        galleryImageButton = findViewById(com.springsamurais.toyswap.R.id.gallery_image_button)
        itemTitleInput = findViewById(com.springsamurais.toyswap.R.id.item_title_input)
        itemDescriptionInput = findViewById(com.springsamurais.toyswap.R.id.item_description_input)


        addListingButton = findViewById(com.springsamurais.toyswap.R.id.add_listing_button)
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

        cancelButton?.setOnClickListener(View.OnClickListener {
            finish()
        })

        takeImageButton?.setOnClickListener(View.OnClickListener {
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
        })

        galleryImageButton?.setOnClickListener(View.OnClickListener {
            openGallery()
        })

        addListingButton?.setOnClickListener(View.OnClickListener {
            uploadListingToServer()
        })

    } // end of onCreate



    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    private fun uploadListingToServer() {
        selectedBitmap?.let { bitmap ->
            // convert bitmap to file
            val file = File(cacheDir, "photo.jpeg")
            try {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                Toast.makeText(this, "Error Uploading Image", Toast.LENGTH_SHORT).show()
                return
            }

            // create request body for image file
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)

            val photo = MultipartBody.Part.createFormData("photo", file.name, requestFile)

            // prepare text parts for request body
            val title = RequestBody.create(
                MediaType.parse("text/plain"),
                itemTitleInput?.text.toString()
            )

            val description = RequestBody.create(
                MediaType.parse("text/plain"),
                itemDescriptionInput?.text.toString()
            )

            val condition = RequestBody.create(
                MediaType.parse("text/plain"),
                conditionSpinner?.selectedItem.toString()
            )
            val category = RequestBody.create(
                MediaType.parse("text/plain"),
                categorySpinner?.selectedItem.toString()
            )

            val statusListing = RequestBody.create(MediaType.parse("text/plain"), "AVAILABLE")
            val userid = RequestBody.create(MediaType.parse("text/plain"), "1")

            // call API to upload listing
            val call = apiService?.postListing(
                title,
                photo,
                category,
                description,
                condition,
                statusListing,
                userid
            )?.enqueue(object : Callback<Listing> {
                override fun onResponse(
                    call: Call<Listing>,
                    response: retrofit2.Response<Listing>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddListingActivity,
                            "Listing Uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(
                            this@AddListingActivity,
                            "Error Uploading Listing: $errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("UploadError:", "Error response $errorBody")
                    }
                }

                override fun onFailure(call: Call<Listing>, t: Throwable) {
                    val errorMessage = t.message

                    Toast.makeText(
                        this@AddListingActivity,
                        "Something went wrong $errorMessage",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("UploadFailure:", "Error: ${t.message}",t)
                }
            })

        } ?: run {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    } // end of uploadListingToServer


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