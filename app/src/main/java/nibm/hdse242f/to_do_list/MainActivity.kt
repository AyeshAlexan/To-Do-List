package nibm.hdse242f.to_do_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nibm.hdse242f.to_do_list.model.TodoItem
import nibm.hdse242f.to_do_list.ui.theme.ToDoListTheme
import nibm.hdse242f.to_do_list.view.TodoViewModel
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    private val todoViewModel :TodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    TodoListScreen(viewModel = todoViewModel )
                }

                }
            }
        }
    }
@Composable
fun TodoListScreen(viewModel: TodoViewModel)
{
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(30.dp)) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("New Task") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addItem(inputText)
                inputText = ""
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = viewModel.todoItem,
                key = { it.id } // Use a unique key for better performance
            ) { item ->
                TodoItemRow(
                    item = item,
                    onCheckedChange = { viewModel.toggleCompleted(item) },
                    onDelete = { viewModel.removeItem(item) }
                )
                Divider()
            }
        }
    }
}
@Composable
fun TodoItemRow(
    item: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.task,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoListTheme {
        // Create a fake ViewModel for the preview
        val previewViewModel = TodoViewModel()
        previewViewModel.addItem("Buy milk")
        previewViewModel.addItem("Walk the dog")
        previewViewModel.toggleCompleted(previewViewModel.todoItem[0])

        TodoListScreen(viewModel = previewViewModel)
    }
}
