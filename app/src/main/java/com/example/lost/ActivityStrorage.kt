package com.example.lost

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import kotlinx.android.synthetic.main.activity_strorage.*
import com.example.lost.databinding.ActivityStrorageBinding
import com.google.android.gms.location.ActivityTransition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class ActivityStrorage : AppCompatActivity() {

    private lateinit var binding: ActivityStrorageBinding
    private val CAMERA_REQUEST_CODE=1
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var imageuri:Uri
    lateinit var urgency:RadioGroup
    lateinit var describe:EditText
    lateinit var requiredPeople:EditText
    lateinit var nearestLandmark:EditText
    lateinit var buttonPost:Button
    lateinit var issue:Spinner
    lateinit var animaltype:Spinner
    lateinit var buttoncam:FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStrorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val uid=auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Feed")

        describe=findViewById(R.id.describe)
        requiredPeople=findViewById(R.id.RequiredPeople)
        nearestLandmark=findViewById(R.id.NearestLandmark)
        urgency=findViewById(R.id.RadioGroup) as RadioGroup
        issue=findViewById(R.id.Issue) as Spinner
        buttoncam=findViewById(R.id.buttonCam)
        animaltype=findViewById(R.id.AnimalType) as Spinner
        buttonPost=findViewById(R.id.buttonPost)

        val issues= arrayOf("Injured","Diseased","Weak","Abandoned","In danger","Not in list")
        val animaltypes=arrayOf("Dog","Cat","Bird","Cattle")

        issue.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,issues)
        animaltype.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,animaltypes)


        binding.buttonPost.setOnClickListener{
            val description=binding.describe.text.toString().trim()
            val people=binding.RequiredPeople.text.toString().trim()
            val landmark=binding.NearestLandmark.text.toString().trim()



            val feed=Feed(description, people, landmark)

            if(uid!=null)
            {
                databaseReference.child(uid).setValue(feed).addOnCompleteListener{
                    if(it.isSuccessful){
                        uploadProfilePic()
                    }
                }

            }
        }
        /**urgency.setOnCheckedChangeListener{group,checkedId ->
            if(checkedId==R.id.VeryUrgent)
            {

            }
            if(checkedId==R.id.Urgent)
            {

            }
            if(checkedId==R.id.NotUrgent)
            {

            }

        }**/


        issue.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.buttonCam.setOnClickListener{
            cameraCheckPermission()
        }
    }

    private fun cameraCheckPermission() {
        Dexter.withContext(this).withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA).withListener(
            object :MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if(report.areAllPermissionsGranted())(
                                camera()
                        )
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showDialogForPermission()
                }

            }
        ).onSameThread().check()

    }

    private fun showDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("Allow Lost to access your Camera, Media, Files and Photos")
            .setPositiveButton("GO TO SETTINGS"){_,_->
                try{
                    val intent= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri=Uri.fromParts("package",packageName,null)
                    intent.data=uri
                    startActivity(intent)


                }catch (e:ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("DENY"){
                dialog,_->dialog.dismiss()
            }.show()
    }

    private fun camera() {
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==Activity.RESULT_OK){

            when(requestCode){
                CAMERA_REQUEST_CODE->{

                    val bitmap=data?.extras?.get("data") as Bitmap
                    binding.ImageStorage.load(bitmap){
                        crossfade(true)
                        crossfade(1000)
                    }
                }
            }
        }
    }

    private fun uploadProfilePic() {
        imageuri= Uri.parse("android.resource://$packageName/${R.id.ImageStorage}")
        storageReference=FirebaseStorage.getInstance().getReference("Feed"+auth.currentUser?.uid)
        storageReference.putFile(imageuri).addOnSuccessListener {

            Toast.makeText(this,"Profile successfully uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Could not upload Image",Toast.LENGTH_SHORT).show()
        }
    }
}