package com.malsoryz.memoryz

import android.app.Dialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.malsoryz.memoryz.layout.EditorControl
import com.malsoryz.memoryz.layout.HueBar
import com.malsoryz.memoryz.ui.theme.MemoryzTheme
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

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

    Column(modifier = modifier.fillMaxSize()) {
        ColorPickerDialog()
        RichTextEditor(
            state = richEditorState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            placeholder = {
                Text(
                    text = "Write something...",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
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
            state = richEditorState
        )
    }
}

@Composable
fun ColorPickerDialog(
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = { showDialog = false }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp), // outside padding or margin
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                HueBar { hue ->
                    
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryzPreview() {
    MemoryzTheme {
        ColorPickerDialog()
    }
}