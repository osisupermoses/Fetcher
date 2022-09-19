package com.crop2cash.fetcher.util.dialog.awesome_custom_dalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.crop2cash.fetcher.presentation.theme.ErrorColor
import com.crop2cash.fetcher.presentation.theme.InfoColor
import com.crop2cash.fetcher.presentation.theme.Shapes
import com.crop2cash.fetcher.presentation.theme.SuccessColor
import com.osisupermoses.trustsoft_fintech_compose.util.ui_utils.dialogs.awesome_custom_dalog.AwesomeCustomDialogType

@Composable
fun SuccessDialog(
    title: String,
    desc: String,
    onOkayClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onOkayClick
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.wrapContentSize()
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(180.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = onOkayClick,
                            shape = Shapes.large,
                            colors = ButtonDefaults.buttonColors(contentColor = SuccessColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(5.dp))
                                .clickable { onOkayClick.invoke() }
                        ) {
                            Text(
                                text = "Ok",
                                color = Color.White
                            )
                        }
                    }
                }
            }
            SuccessHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun ErrorDialog(
    title: String,
    desc: String,
    twoOptionsNeeded: Boolean = false,
    onRetry: () -> Unit = {},
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties =
            if (twoOptionsNeeded) {
                DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            } else {
                DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            }
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight(0.8f)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = onDismiss,
                                shape = Shapes.large,
                                colors = ButtonDefaults.buttonColors(contentColor = ErrorColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = if (twoOptionsNeeded) "Exit" else "Dismiss",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (twoOptionsNeeded) {
                                Button(
                                    onClick = onRetry,
                                    shape = Shapes.large,
                                    colors = ButtonDefaults.buttonColors(contentColor = SuccessColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.5f)
                                        .clip(RoundedCornerShape(5.dp))
                                ) {
                                    Text(
                                        text = "Retry",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
            ErrorHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun InfoDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit,
    onOkayClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(200.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = onDismiss,
                            shape = Shapes.small,
                            colors = ButtonDefaults.buttonColors(contentColor = InfoColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Ok",
                                color = Color.White,
                                modifier = Modifier.clickable { onOkayClick.invoke() }
                            )
                        }
                    }
                }
            }
            InfoHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun AwesomeCustomDialog(
    type: AwesomeCustomDialogType,
    title: String,
    desc: String,
    twoOptionsNeeded: Boolean = false,
    onDismiss: () -> Unit = {},
    onRetry: () -> Unit = {},
    onOkayClick: () -> Unit = {},
) {
    MaterialTheme {
        when (type) {
            AwesomeCustomDialogType.SUCCESS -> {
                SuccessDialog(
                    title = title,
                    desc = desc,
                    onOkayClick = onOkayClick
                )
            }
            AwesomeCustomDialogType.ERROR -> {
                ErrorDialog(
                    title = title,
                    desc = desc,
                    twoOptionsNeeded = twoOptionsNeeded,
                    onRetry = onRetry,
                    onDismiss = onDismiss
                )
            }
            AwesomeCustomDialogType.INFO -> {
                InfoDialog(
                    title = title,
                    desc = desc,
                    onDismiss = onDismiss,
                    onOkayClick = onOkayClick
                )
            }
        }
    }
}