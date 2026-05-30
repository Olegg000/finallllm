package com.example.main3

import android.R.attr.phoneNumber
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

//var isOpenModal by remember { mutableStateOf(false) }
//var uriImage by remember { mutableStateOf<Uri?>(null) }
//var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
//
//val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//    uri.let {
//        uriImage = uri
//        bitmapImage=null
//        isOpenModal=false
//    }
//}
//
//val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bit ->
//    bit.let {
//        bitmapImage  = bit
//        uriImage=null
//        isOpenModal=false
//    }
//}

//val clipboard = LocalClipboardManager.current
//
//Button(onClick = {
//    clipboard.setText(AnnotatedString(phoneNumber))
//})

//AsyncImage(
//    model = "https://example.com/image.jpg",
//    contentDescription = null
//)
//

// composable("vacancy/create") { CreateVacancyScreen() }
//composable("vacancy/edit/{id}") { back ->
//    val id = back.arguments?.getString("id")
//    CreateVacancyScreen(editId = id)
//}

//Box(Modifier.size(80.dp).clip(CircleShape).clickable{
//    isOpenModal=true
//},
//contentAlignment = Alignment.Center) {
//    when {
//        bitmapImage != null -> {

//galleryLauncher.launch("image/*")

//floatingActionButton = {
//    FloatingActionButton(onClick = { viewModel.onCreate() }) {
//        Icon(Icons.Default.Add, contentDescription = null)
//    }
//}