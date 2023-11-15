package com.rohnsha.medbuddyai

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.MotionPhotosAuto
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PsychologyAlt
import androidx.compose.material.icons.outlined.CenterFocusWeak
import androidx.compose.material.icons.outlined.MotionPhotosAuto
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PsychologyAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rohnsha.medbuddyai.bottom_navbar.bottomNavItems
import com.rohnsha.medbuddyai.domain.analyzer
import com.rohnsha.medbuddyai.domain.classification
import com.rohnsha.medbuddyai.domain.photoCaptureViewModel
import com.rohnsha.medbuddyai.ui.theme.BGMain
import com.rohnsha.medbuddyai.ui.theme.fontFamily
import kotlinx.coroutines.launch

private lateinit var viewModelPhotoSave: photoCaptureViewModel
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    padding: PaddingValues,
    navController: NavHostController,
    photoCaptureVM: photoCaptureViewModel
) {
    viewModelPhotoSave= photoCaptureVM
    var cameraPermissionState: PermissionState= rememberPermissionState(permission = Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted){
        Log.d("permission", "permissionGranted")
    } else {
        Log.d("permission", "permissionNOtGranted")
        ActivityCompat.requestPermissions(
            LocalContext.current as Activity,
            arrayOf(Manifest.permission.CAMERA),
            0
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Scan",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight(600),
                        fontSize = 26.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BGMain
                )
            )
        },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BGMain
    ) { value ->
        Column(
            modifier = Modifier
                .padding(value)
                .padding(padding)
                .padding(top = 20.dp)
                .fillMaxSize()
        ) {
            ScanOptions()
            ScanMainScreen(
                navController
            )
        }
    }
}

@Composable
fun ScanOptions() {
    var autoBool= remember {
        mutableStateOf(true)
    }
    var xRayBool= remember {
        mutableStateOf(false)
    }
    var mriBool= remember {
        mutableStateOf(false)
    }
    var skinBool = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        ScanOptionsItem(unselectedIcon = Icons.Outlined.MotionPhotosAuto, selectedIcon = Icons.Filled.MotionPhotosAuto, description_icon = "auto", clickAction = {
            autoBool.value= true
            xRayBool.value=false
            mriBool.value= false
            skinBool.value= false
            Log.d(
                "clicked",
                "xray $xRayBool, autobool $autoBool"
            )
        }, enabledState = autoBool, text = "Auto")
        Spacer(modifier = Modifier.width(13.dp))
        ScanOptionsItem(unselectedIcon = Icons.Outlined.CenterFocusWeak, description_icon = "data_array", clickAction = {
            autoBool.value= false
            xRayBool.value=true
            mriBool.value= false
            skinBool.value= false
            Log.d(
                "clicked",
                "xray $xRayBool, autobool $autoBool"
            )
        }, enabledState = xRayBool, text = "X-Ray", selectedIcon = Icons.Filled.CenterFocusStrong)
        Spacer(modifier = Modifier.width(13.dp))
        ScanOptionsItem(unselectedIcon = Icons.Outlined.PsychologyAlt, description_icon = "data_array", clickAction = {
            autoBool.value= false
            xRayBool.value=false
            mriBool.value=true
            skinBool.value=false
            Log.d(
                "clicked",
                "xray $xRayBool, autobool $autoBool"
            )
        }, enabledState = mriBool, text = "MRI", selectedIcon = Icons.Filled.PsychologyAlt)
        Spacer(modifier = Modifier.width(13.dp))
        ScanOptionsItem(unselectedIcon = Icons.Outlined.Person, description_icon = "data_array", clickAction = {
            autoBool.value= false
            xRayBool.value=false
            mriBool.value=false
            skinBool.value=true
            Log.d(
                "clicked",
                "xray $xRayBool, autobool $autoBool"
            )
        }, enabledState = skinBool, text = "Skin Manifestation", selectedIcon = Icons.Filled.Person)
    }
}

@Composable
fun ScanOptionsItem(
    unselectedIcon: ImageVector,
    selectedIcon: ImageVector,
    description_icon: String,
    clickAction: () -> Unit,
    enabledState: MutableState<Boolean>,
    text: String
) {
    Surface(
        modifier = Modifier
            .clickable(onClick = clickAction),
        color = MaterialTheme.colorScheme.primary.copy(0f)
    ) {
        Row(
            modifier = Modifier
                .height(34.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .then(
                    if (enabledState.value) Modifier.background(
                        color = Color.White,
                        shape = RoundedCornerShape(6.dp)
                    ) else Modifier
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier
                        .padding(start = 9.dp, end = 3.dp)
                        .height(24.dp)
                        .width(24.dp),
                    imageVector = if (enabledState.value) selectedIcon else unselectedIcon,
                    contentDescription = description_icon
                )
            }
            if (enabledState.value){
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, end = 9.dp),
                    text = text,
                    fontWeight = FontWeight(600)
                )
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier= Modifier,
    imgBitmap: Bitmap? = null,
) {
    val lifecycleOwner= LocalLifecycleOwner.current
    if (imgBitmap!=null){
        Image(
            bitmap = imgBitmap.asImageBitmap(),
            contentDescription = "null",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller= controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            },
            modifier = modifier
        )
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Bitmap) -> Unit,
    toCcamFeed: (classification) -> Unit,
){
    Log.d("successIndexModelTF", "entered")

    var classificationResult: classification= classification(indexNumber = 404, confident = 404f)
    controller.takePicture(
        ContextCompat.getMainExecutor(ContextUtill.ContextUtils.getApplicationContext()),
        object : OnImageCapturedCallback(){
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                Log.d("successIndexModelTF", "entered1")

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                onPhotoTaken(rotatedBitmap)
/*
                classificationResult= classifier.classifyIndex(context, rotatedBitmap, scanOption = "lung")
                val chestDiseases= listOf(
                    "normal", "pneumonia", "tuberculosis"
                )

                Log.d("successIndexModelTF", "Found: ${chestDiseases[classificationResult.indexNumber]} with ${
                    String.format(
                        "%.2f",
                        classificationResult.confident
                    )
                }% confidence!\"")*/
                toCcamFeed(classificationResult)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)

                Log.d("successIndexModelTF", "jii $exception", )
            }
        }
    )
}

@Composable
fun ScanMainScreen(
    navController: NavHostController
) {
    val conttext= LocalContext.current
    var itt= classification(0, 6f)
    val detecteddClassification= remember {
        mutableStateOf(itt.indexNumber)
    }
    val scope= rememberCoroutineScope()
    val analyzer= remember {
        analyzer(
            context = conttext,
            onResults = {
                detecteddClassification.value= it.indexNumber
            }
        )
    }
    val controller= remember {
        LifecycleCameraController(ContextUtill.ContextUtils.getApplicationContext()).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                CameraController.IMAGE_ANALYSIS
            )
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(ContextUtill.ContextUtils.getApplicationContext()),
                analyzer
            )
        }
    }
    val listTest= listOf(
        "MRI",
        "XRAY",
        "Skin Manifestion"
    )
    val isPredictingBool= remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
        ){
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f),
                imgBitmap = viewModelPhotoSave.bitmaps.collectAsState().value,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                CameraPreviewSegmentOp(
                    title = "Detected: ",
                    weight = 0.5f,
                    dataItem = listTest[detecteddClassification.value]
                )
                CameraPreviewSegmentOp(
                    title = "Detected: ",
                    weight = 0.5f,
                    dataItem = listTest[detecteddClassification.value]
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(BGMain),
        ) {
            CameraControlsItem(
                title = "FLIP",
                widthPercentage = if (!isPredictingBool.value) 0.3f else 0.25f,
                paddingVal = PaddingValues(start = 13.dp, top=9.dp, bottom = 9.dp, end = 6.5.dp),
                onClickAction = {

                }
            )
            CameraControlsItem(
                title = "CAPTURE",
                widthPercentage = if(!isPredictingBool.value) 0.5f else 0.6f,
                paddingVal = PaddingValues(start = 6.5.dp, top=9.dp, bottom = 9.dp, end = 6.5.dp),
                onClickAction = {
                    isPredictingBool.value=true
                    scope.launch {
                        takePhoto(
                            controller = controller,
                            context = conttext,
                            onPhotoTaken = viewModelPhotoSave::onTakePhoto,
                            toCcamFeed = {
                                navController.navigate(bottomNavItems.ScanCatogoricals.route)
                            }
                        )
                    }
                },
                isPredicting = isPredictingBool.value
            )
            CameraControlsItem(
                title = "GALLERY",
                widthPercentage = 1f,
                paddingVal = PaddingValues(start = 6.5.dp, top=9.dp, bottom = 9.dp, end = 13.dp),
                onClickAction = {

                }
            )
        }
    }
}

@Composable
fun CameraPreviewSegmentOp(
    title: String,
    weight: Float,
    dataItem: String
) {
    Row(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .height(30.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontFamily = fontFamily
        )
        Text(
            text = dataItem,//listTest[detecteddClassification.value],
            fontFamily = fontFamily,
            fontWeight = FontWeight(600),
            fontSize = 14.sp
        )
    }
}

@Composable
fun CameraControlsItem(
    title: String,
    paddingVal: PaddingValues,
    widthPercentage: Float,
    onClickAction: () -> Unit,
    isPredicting: Boolean= false
) {
    Row(
        modifier = Modifier
            .padding(paddingVal)
            .fillMaxHeight()
            .fillMaxWidth(widthPercentage)
            .background(Color.White, RoundedCornerShape(8.dp))
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            )
            .clickable(
                onClick = onClickAction
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight(600),
            fontFamily = fontFamily
        )
        if (isPredicting){
            Spacer(modifier = Modifier.width(9.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp),
                strokeWidth = 2.dp,
                color = Color.Black.copy(0.5f)
            )
        }
    }
}

private fun importFromGallery(){

}