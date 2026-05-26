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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Black
import com.example.ui.theme.GlobalFontFamily
import com.example.ui.theme.Placeholder
import com.example.ui.theme.Primary
import com.example.ui.theme.Spaces.s4
import com.example.ui.theme.TextB
import com.example.ui.theme.WhiteDef

@Composable
fun Bubble(msg: Message) {
    val fromUser = !msg.fromLLm
    val fromLlm = msg.fromLLm

    val baseDp = 20.dp
    val smallDp = 4.dp
    val shape = AbsoluteRoundedCornerShape(
        topLeft = baseDp,
        topRight = baseDp,
        bottomLeft = if (fromLlm) smallDp else baseDp,
        bottomRight = if (fromUser) smallDp else baseDp
    )

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (fromUser) Arrangement.End else Arrangement.Start
    ) {
        Column(horizontalAlignment = if (fromUser) Alignment.End else Alignment.Start) {
            Box(
                Modifier
                    .clip(shape)
                    .border(if (fromUser) 0.dp else 1.dp, Placeholder,shape)
                    .background(if (fromUser) Primary else WhiteDef),
                contentAlignment = Alignment.Center
            ) {
                TextB(
                    msg.message,
                    color = if (fromLlm) Black else WhiteDef,
                    style = GlobalFontFamily.FieldLabel,
                    maxLines = 999,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(Modifier.height(s4))
            TextB(
                msg.time,
                style = GlobalFontFamily.BodySmall,
                color = Placeholder
            )
        }
    }

}