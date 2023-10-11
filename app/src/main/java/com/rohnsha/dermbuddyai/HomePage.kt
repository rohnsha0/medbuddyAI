package com.rohnsha.dermbuddyai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohnsha.dermbuddyai.ui.theme.BGMain
import com.rohnsha.dermbuddyai.ui.theme.ViewDash
import com.rohnsha.dermbuddyai.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    padding: PaddingValues
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "MedBuddy AI",
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
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .padding(padding)
                .padding(top = 20.dp)
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            ScanCard()
            Text(
                text = "Explore",
                fontFamily = fontFamily,
                fontWeight = FontWeight(600),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(top = 18.dp, start = 24.dp)
            )
            explore_home()
            Text(
                text = "Read about Diseases",
                fontFamily = fontFamily,
                fontWeight = FontWeight(600),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(top = 30.dp, start = 24.dp)
            )
            explore_diseases()
            Text(
                text = "Current Medical Affairs",
                fontFamily = fontFamily,
                fontWeight = FontWeight(600),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(top = 30.dp, start = 24.dp)
            )
        }
    }
}

@Composable
fun explore_diseases() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            explore_tabs(title = "Neural", icon = Icons.Filled.Psychology, weight = .49f)
            Spacer(modifier = Modifier.width(12.dp))
            explore_tabs(title = "Derma", icon = Icons.Filled.EmojiPeople, weight = 1f)
        }
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            explore_tabs(title = "Respiratory", icon = Icons.Filled.SelfImprovement, weight = .49f)
            Spacer(modifier = Modifier.width(12.dp))
            explore_tabs(title = "More", icon = Icons.Filled.ReadMore, weight = 1f)
        }
    }
}

@Composable
fun explore_home() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            explore_tabs(title = "Scan", icon = Icons.Filled.CameraAlt, weight = .4f)
            Spacer(modifier = Modifier.width(12.dp))
            explore_tabs(title = "Import Reports", icon = Icons.Filled.Biotech, weight = 1f)
        }
    }
}

@Composable
fun explore_tabs(
    title: String,
    icon: ImageVector,
    weight: Float,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(weight)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
            .background(color = ViewDash, shape = RoundedCornerShape(16.dp))
            .clickable {
                       onClick
            },
        contentAlignment = Alignment.CenterStart
    ){
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 13.dp)
                    .height(34.dp)
                    .width(34.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                        .padding(2.dp),
                    imageVector = icon,
                    contentDescription = "cam_icon"
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 13.dp, end = 18.dp),
                text = title,
                fontWeight = FontWeight(600)
            )
        }
    }
}

@Composable
fun ScanCard() {
    Box(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 30.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                .background(ViewDash, shape = RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 13.dp, start = 13.dp)
                        .height(34.dp)
                        .width(34.dp)
                        .background(Color.Black, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "scan icon",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .padding(2.dp),
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 13.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        fontWeight = FontWeight(600),
                        fontSize = 15.sp,
                        fontFamily = fontFamily,
                        text = "Rohan Shaw",
                    )
                    Text(
                        fontSize = 13.sp,
                        text = "20 | Male",
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        fontWeight = FontWeight(600),
                        fontSize = 15.sp,
                        text = "PROFILE",
                        color = MaterialTheme.typography.bodyMedium.color.copy(alpha = .6f),
                        modifier = Modifier
                            .padding(top = 10.dp, end = 13.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 18.dp, start = 13.dp)
            ) {
                Text(
                    fontWeight = FontWeight(600),
                    fontFamily = fontFamily,
                    text = "Medical Condition: "
                )
                Text(
                    text = "Fit",
                    fontFamily = fontFamily
                    )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, bottom = 12.dp, top = 3.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontWeight = FontWeight(600),
                    fontFamily = fontFamily,
                    text = "Last Scan: "
                )
                Text(
                    fontFamily = fontFamily,
                    text = "10 Aug, 2023 at 21:36"
                )
            }
            /*
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 13.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Camera,
                        contentDescription = "camera_icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Capture")
                }
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 13.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Collections,
                        contentDescription = "collections_icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Gallery")
                }
            }*/

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    HomeScreen(padding = PaddingValues(all = 0.dp))
}