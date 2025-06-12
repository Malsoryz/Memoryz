package com.malsoryz.memoryz

import android.content.ClipDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malsoryz.memoryz.ui.theme.MemoryzTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import kotlin.contracts.contract

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryzTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        EditorLayout(
                            modifier = Modifier.padding(
                                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                                top = innerPadding.calculateTopPadding(),
                                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                                bottom = 0.dp
                            )
                        )
                        Spacer(Modifier
                            .height(innerPadding.calculateBottomPadding())
                            .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorLayout(modifier: Modifier = Modifier) {
    val richEditorState = rememberRichTextState()

//    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        RichTextEditor(
            state = richEditorState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
//                color = textColor
            ),
            placeholder = {
                Text(
                    text = "Write something...",
                    color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f)
                )
            },
            colors = RichTextEditorDefaults.richTextEditorColors(
                containerColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
//            cursorBrush = SolidColor(textColor),
//            decorationBox = { innerTextField ->
//                Box {
//                    if (richEditorState.toText().toString().isEmpty()) {
//                        Text(
//                            text = "Write Something...",
//                            color = textColor.copy(alpha = 0.5f)
//                        )
//                    }
//                    innerTextField()
//                }
//            }
        )
        EditorControl(
            state = richEditorState,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.ime)
                .clip(RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp
                ))
                .background(MaterialTheme.colorScheme.primary.copy())
        )
    }
}

@Composable
fun EditorControl(
    state: RichTextState,
    modifier: Modifier = Modifier,
) {
    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize
    val textColor = Color.Red

    val currentTextAlign = state.currentParagraphStyle.textAlign
    val currentFontWeight = state.currentSpanStyle.fontWeight
    val currentFontStyle = state.currentSpanStyle.fontStyle
    val currentTextDecoration = state.currentSpanStyle.textDecoration
    val currentFontSize = state.currentSpanStyle.fontSize
    val currentTextColor = state.currentSpanStyle.color

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ControlItem( // Align Left
            selected = currentTextAlign == TextAlign.Start,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start)) },
            imageVector = Icons.AutoMirrored.Filled.FormatAlignLeft,
            contentDescription = "Start Align Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem( // Align Center
            selected = currentTextAlign == TextAlign.Center,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center)) },
            imageVector = Icons.Default.FormatAlignCenter,
            contentDescription = "Center Align Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem( // Align Right
            selected = currentTextAlign == TextAlign.End,
            onClick = { state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End)) },
            imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
            contentDescription = "Right Align Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem( // Bold
            selected = currentFontWeight == FontWeight.Bold,
            onClick = { state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) },
            imageVector = Icons.Default.FormatBold,
            contentDescription = "Bold Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem( // Italic
            selected = currentFontStyle == FontStyle.Italic,
            onClick = { state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic)) },
            imageVector = Icons.Default.FormatItalic,
            contentDescription = "Italic Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem(
            selected = currentTextDecoration == TextDecoration.Underline,
            onClick = { state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline)) },
            imageVector = Icons.Default.FormatUnderlined,
            contentDescription = "Underline Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem(
            selected = currentFontSize == titleSize,
            onClick = { state.toggleSpanStyle(SpanStyle(fontSize = titleSize)) },
            imageVector = Icons.Default.Title,
            contentDescription = "Title Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem(
            selected = currentFontSize == subtitleSize,
            onClick = { state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize)) },
            imageVector = Icons.Default.FormatSize,
            contentDescription = "Subtitle Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        ControlItem(
            selected = currentTextColor == textColor,
            onClick = { state.toggleSpanStyle(SpanStyle(color = textColor)) },
            imageVector = Icons.Default.Colorize,
            contentDescription = "Text Color Control",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ControlItem(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String = "RichEditor Controller Item Description",
    tint: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable { onClick() }
            .background(
                if (selected) selectedColor
                else unSelectedColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryzPreview() {
    MemoryzTheme {
        EditorLayout()
    }
}