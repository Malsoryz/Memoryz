package com.malsoryz.memoryz.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.StrikethroughS
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun EditorControl(
    state: RichTextState,
    modifier: Modifier = Modifier,
    titleSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize,
    subtitleSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    textColor: Color = Color.Yellow,
    textBackground: Color = Color.Yellow,
    alignControls: Boolean = true,
    boldControl: Boolean = true,
    italicControl: Boolean = true,
    underlineControl: Boolean = true,
    lineThroughControl: Boolean = true,
    titleControl: Boolean = true,
    subtitleControl: Boolean = true,
    textColorControl: Boolean = true,
    textBackgroundControl: Boolean = true
) {
    val currentTextAlign = state.currentParagraphStyle.textAlign
    val currentFontWeight = state.currentSpanStyle.fontWeight
    val currentFontStyle = state.currentSpanStyle.fontStyle
    val currentTextDecoration = state.currentSpanStyle.textDecoration
    val currentFontSize = state.currentSpanStyle.fontSize
    val currentTextColor = state.currentSpanStyle.color
    val currentTextBackground = state.currentSpanStyle.background

    return Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f))
            .padding(all = 10.dp)
            .horizontalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.ime)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (alignControls) ControlItem( // Align Left
            selected = currentTextAlign == TextAlign.Start,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start)) },
            imageVector = Icons.AutoMirrored.Filled.FormatAlignLeft,
            contentDescription = "Start Align Control"
        )
        if (alignControls) ControlItem( // Align Center
            selected = currentTextAlign == TextAlign.Center,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center)) },
            imageVector = Icons.Default.FormatAlignCenter,
            contentDescription = "Center Align Control"
        )
        if (alignControls) ControlItem( // Align Right
            selected = currentTextAlign == TextAlign.End,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End)) },
            imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
            contentDescription = "Right Align Control"
        )
        if (boldControl) ControlItem( // Bold
            selected = currentFontWeight == FontWeight.Bold,
            onClick = { state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) },
            imageVector = Icons.Default.FormatBold,
            contentDescription = "Bold Control"
        )
        if (italicControl) ControlItem( // Italic
            selected = currentFontStyle == FontStyle.Italic,
            onClick = { state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic)) },
            imageVector = Icons.Default.FormatItalic,
            contentDescription = "Italic Control"
        )
        if (underlineControl) ControlItem( // Underline
            selected = currentTextDecoration == TextDecoration.Underline,
            onClick = { state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline)) },
            imageVector = Icons.Default.FormatUnderlined,
            contentDescription = "Underline Control"
        )
        if (lineThroughControl) ControlItem( // Line Through
            selected = currentTextDecoration == TextDecoration.LineThrough,
            onClick = { state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) },
            imageVector = Icons.Default.StrikethroughS,
            contentDescription = "Line Through Control"
        )
        if (titleControl) ControlItem( // Title or Header
            selected = currentFontSize == titleSize,
            onClick = { state.toggleSpanStyle(SpanStyle(fontSize = titleSize)) },
            imageVector = Icons.Default.Title,
            contentDescription = "Title Control"
        )
        if (subtitleControl) ControlItem( // Subtitle
            selected = currentFontSize == subtitleSize,
            onClick = { state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize)) },
            imageVector = Icons.Default.FormatSize,
            contentDescription = "Subtitle Control"
        )
        if (textColorControl) ControlItem( // Text Color
            selected = currentTextColor == textColor,
            onClick = { state.toggleSpanStyle(SpanStyle(color = textColor)) },
            imageVector = Icons.Default.FormatColorText,
            contentDescription = "Text Color Control"
        )
        if (textBackgroundControl) ControlItem( // Text Background Color
            selected = currentTextBackground == textBackground,
            onClick = { state.toggleSpanStyle(SpanStyle(background = textBackground)) },
            imageVector = Icons.Default.FormatColorFill,
            contentDescription = "Text Background Control"
        )
    }
}

@Composable
fun ControlItem(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String = "RichEditor Controller Item Description",
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable { onClick() }
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = if (selected) selectedColor else unSelectedColor
        )
    }
}