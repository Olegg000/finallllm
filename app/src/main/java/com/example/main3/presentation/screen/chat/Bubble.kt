package com.example.main3.presentation.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Black
import com.example.ui.theme.GlobalFontFamily
import com.example.ui.theme.Placeholder
import com.example.ui.theme.Primary
import com.example.ui.theme.TextB
import com.example.ui.theme.WhiteDef

@Composable
fun Bubble(msg: Message) {
    val isFromUser = !msg.isFromLm
    val isFromLLm = msg.isFromLm

    val baseDp = 20.dp
    val smallDp = 4.dp

    val shape = AbsoluteRoundedCornerShape(
        topLeft = baseDp,
        topRight = baseDp,
        bottomLeft = if (isFromLLm) smallDp else baseDp,
        bottomRight = if (isFromUser) smallDp else baseDp
    )

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            Modifier.fillMaxWidth(0.7f),
            horizontalAlignment = if (isFromUser) Alignment.End else Alignment.Start
        ) {
            Box(
                Modifier
                    .clip(shape)
                    .background(if (isFromUser) Primary else WhiteDef, shape)
                    .border(if (isFromLLm) 1.dp else 0.dp, Placeholder, shape),
                contentAlignment = Alignment.Center
            ) {
                TextB(
                    msg.message,
                    style = GlobalFontFamily.FieldLabel,
                    color = if (isFromLLm) Black else WhiteDef,
                    modifier = Modifier.padding(16.dp),
                    maxLines = 999
                )
            }
            Spacer(Modifier.height(4.dp))
            TextB(
                msg.time,
                style = GlobalFontFamily.BodySmall,
                color = Placeholder
            )
        }
    }
}