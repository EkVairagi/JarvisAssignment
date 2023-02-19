package com.xynderous.vatole.jarvisassignment.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xynderous.vatole.jarvisassignment.R
import com.xynderous.vatole.jarvisassignment.Utils.ApplicationConstants
import com.xynderous.vatole.jarvisassignment.Utils.CommonUtils
import com.xynderous.vatole.jarvisassignment.database.UserProfile
import com.xynderous.vatole.jarvisassignment.databinding.ActivityUserProfileBinding
import com.xynderous.vatole.jarvisassignment.myinterface.FromStartDate
import com.xynderous.vatole.jarvisassignment.myinterface.ToSTartDate
import com.xynderous.vatole.jarvisassignment.viemodel.UserProfileViewModel
import java.lang.ref.WeakReference


class UserProfileActivity : AppCompatActivity(),View.OnClickListener,FromStartDate,ToSTartDate {

    private lateinit var binding: ActivityUserProfileBinding

    private lateinit var viewModel: UserProfileViewModel

    private var imageUri: String? = null

    private var startDate:String = ""
    private var endDate:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()

        viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        val userProfile = intent.getSerializableExtra(ApplicationConstants.USER) as UserProfile?

        userProfile?.let {

            setField(userProfile)

            imageUri = userProfile.imageUrl
            binding.btnSubmit.visibility = View.GONE
            binding.btnUpdate.visibility = View.VISIBLE

            binding.btnUpdate.setOnClickListener {
                updateUser(userProfile)
            }


        }

        binding.btnChoose.setOnClickListener { openDialog()  }

        binding.btnSubmit.setOnClickListener { submitProfile() }




    }

    private fun setClickListener() {
        binding.etDob.setOnClickListener(this)
        binding.etStartyear.setOnClickListener(this)
        binding.etEndyear.setOnClickListener(this)
        binding.etLevel.setOnClickListener(this)
        binding.etStream.setOnClickListener(this)
        binding.clAddMNo.setOnClickListener(this)
        binding.clAddAddress.setOnClickListener(this)
        binding.clAddEducation.setOnClickListener(this)
    }

    private fun submitProfile() {

        if (binding.etFname.text?.trim().toString().isNotEmpty() && !(imageUri.isNullOrEmpty())) {

            viewModel.submitUser(
                UserProfile(
                    0,
                    binding.etFname.text?.trim().toString(),
                    binding.etLname.text?.trim().toString(),
                    binding.etDob.text?.trim().toString(),
                    binding.etMno.text?.trim().toString(),
                    binding.etHno.text?.trim().toString(),
                    binding.etArea.text?.trim().toString(),
                    binding.etPincode.text?.trim().toString(),
                    binding.etCity.text?.trim().toString(),
                    binding.etState.text?.trim().toString(),
                    binding.etLevel.text?.trim().toString(),
                    binding.etStream.text?.trim().toString(),
                    binding.etStartyear.text?.trim().toString(),
                    binding.etEndyear.text?.trim().toString(),
                    binding.etCollege.text?.trim().toString(),
                    imageUri?:""
                )
            )
            finish()
        }
        else {
            Toast.makeText(this,getString(R.string.empty_fields), Toast.LENGTH_LONG).show()
        }

    }


    private fun updateUser(userProfile: UserProfile) {

        if (binding.etFname.text?.trim().toString().isNotEmpty() && !(imageUri.isNullOrEmpty())) {

            viewModel.updateUser(
                UserProfile(
                    userProfile.id,
                    binding.etFname.text?.trim().toString(),
                    binding.etLname.text?.trim().toString(),
                    binding.etDob.text?.trim().toString(),
                    binding.etMno.text?.trim().toString(),
                    binding.etHno.text?.trim().toString(),
                    binding.etArea.text?.trim().toString(),
                    binding.etPincode.text?.trim().toString(),
                    binding.etCity.text?.trim().toString(),
                    binding.etState.text?.trim().toString(),
                    binding.etLevel.text?.trim().toString(),
                    binding.etStream.text?.trim().toString(),
                    binding.etStartyear.text?.trim().toString(),
                    binding.etEndyear.text?.trim().toString(),
                    binding.etCollege.text?.trim().toString(),
                    imageUri?:""
                )
            )
            finish()
        }
        else {
            Toast.makeText(this,getString(R.string.empty_fields), Toast.LENGTH_LONG).show()
        }
    }

    private fun setField(userProfile: UserProfile) {

        binding.etFname.setText(userProfile.fname)
        binding.etLname.setText(userProfile.lname)
        binding.etDob.setText(userProfile.dob)
        binding.etMno.setText(userProfile.mobile)
        binding.etHno.setText(userProfile.houseno)
        binding.etArea.setText(userProfile.area)
        binding.etPincode.setText(userProfile.pincode)
        binding.etCity.setText(userProfile.city)
        binding.etState.setText(userProfile.state)
        binding.etLevel.setText(userProfile.level)
        binding.etStream.setText(userProfile.stream)
        binding.etStartyear.setText(userProfile.start_year)
        binding.etEndyear.setText(userProfile.end_year)
        binding.etCollege.setText(userProfile.college_name)
        if(userProfile.imageUrl.isNotEmpty()) {
            Glide.with(this).load(userProfile.imageUrl).into(binding.ivPhoto)
        }
    }

    private fun openDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(getString(R.string.select))
        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> openGallery()
                1 -> openCamera()
            }
        }
        pictureDialog.show()

    }

    private fun openCamera() {
        val openCamera = Intent(this, CameraActivity::class.java)
        getCameraUri.launch(openCamera)
    }

    private val getCameraUri = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data != null) {
                imageUri = it.data!!.extras?.get(ApplicationConstants.CAMERA).toString()
                Glide.with(this).load(imageUri).into(binding.ivPhoto)

            }

        }
    }

    private fun openGallery() {

        if (allPermissionsGranted()) {
            choosePhotoFromGallary()
        }
        else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 11
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }



    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        getResult.launch(galleryIntent)

    }
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data != null) {
                imageUri = it.data!!.dataString.toString()
                Glide.with(this).load(imageUri).into(binding.ivPhoto)

            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                choosePhotoFromGallary()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.et_dob ->{
                CommonUtils.showDatePickerWithButton(
                    WeakReference(binding.etDob), WeakReference(this)
                )
            }

            R.id.et_startyear ->{
                showFromDatePickerDialog()
            }

            R.id.et_endyear ->{
                if (startDate.isEmpty()){
                    Toast.makeText(this,"Select Start Date First",Toast.LENGTH_LONG).show()
                }else{
                    showToDatePickerDialog()
                }
            }

            R.id.et_level->{
                val popup = PopupMenu(this, v)
                popup.menuInflater.inflate(R.menu.level_selection, popup.menu)
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.item_ssc -> {
                            binding.etLevel.setText(item.toString())
                        }
                        R.id.item_hsc -> {
                            binding.etLevel.setText(item.toString())
                        }
                        R.id.item_graduation -> {
                            binding.etLevel.setText(item.toString())
                        }
                        R.id.item_pg -> {
                            binding.etLevel.setText(item.toString())
                        }
                    }
                    true
                }
                popup.show()
            }

            R.id.et_stream->{
                val popup = PopupMenu(this, v)
                popup.menuInflater.inflate(R.menu.stream_selection, popup.menu)
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.item_cse -> {
                            binding.etStream.setText(item.toString())
                        }
                        R.id.item_me -> {
                            binding.etStream.setText(item.toString())
                        }
                    }
                    true
                }
                popup.show()
            }

            R.id.clAddMNo ->{
                onAddField()
            }

            R.id.clAddAddress ->{
                onAddAddressField()
            }

            R.id.clAddEducation ->{
                onAddEducationField()
            }

        }
    }




    private fun showToDatePickerDialog() {

        Bundle().apply {
            putString(ApplicationConstants.START_DATE,startDate)
            val newFragment: DialogFragment = ToDatePickerFragment()
            newFragment.arguments = this
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun showFromDatePickerDialog() {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }
    override fun sendInput(input: String?) {
        startDate = input.toString()
        binding.etStartyear.setText(input?:"")
    }

    override fun sendToStartDate(input: String?) {
        endDate = input.toString()
        binding.etEndyear.setText(input?:"")
    }


    private fun onAddField() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.mobile_no_extra_filed, null)
        binding.parentMobileNo.addView(rowView, binding.parentMobileNo.childCount - 1)
        val ivDeleteMNoField = rowView.findViewById<ImageView>(R.id.ivDeleteMNoField)
        ivDeleteMNoField.setOnClickListener { v -> onDelete(v) }
    }


    private fun onDelete(v: View) {
        binding.parentMobileNo.removeView(v.parent as View)
    }

    private fun onAddAddressField() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.address_extra_field, null)
        binding.parentAddress.addView(rowView, binding.parentAddress.childCount - 1)
        val ivDeleteAddressField = rowView.findViewById<ImageView>(R.id.ivDeleteAddressField)
        ivDeleteAddressField.setOnClickListener { v -> onDeleteAddressField(v) }
    }


    private fun onDeleteAddressField(v: View) {
        binding.parentAddress.removeView(v.parent as View)
    }


    private fun onAddEducationField() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.education_extra_field, null)
        binding.parentEducation.addView(rowView, binding.parentEducation.childCount - 1)
        val ivDeleteEducationField = rowView.findViewById<ImageView>(R.id.ivDeleteEducationField)
        ivDeleteEducationField.setOnClickListener { v -> onDeleteEducationField(v) }
    }


    private fun onDeleteEducationField(v: View) {
        binding.parentEducation.removeView(v.parent as View)
    }


}