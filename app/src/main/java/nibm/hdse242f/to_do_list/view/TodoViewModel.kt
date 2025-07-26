package nibm.hdse242f.to_do_list.view
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nibm.hdse242f.to_do_list.model.TodoItem
import java.util.concurrent.atomic.AtomicInteger

class TodoViewModel : ViewModel() {
    private val  _todoItems = mutableStateListOf<TodoItem>()
    val todoItem : List<TodoItem> = _todoItems

    private  var lastId = AtomicInteger(0)

    fun addItem(task : String){
        if (task.isNotBlank()){
            _todoItems.add(
                TodoItem(
                    id = lastId.incrementAndGet(),
                    task=task
                )
            )
        }
    }
    fun removeItem(item: TodoItem)
    {
        _todoItems.remove(item)

    }
    fun toggleCompleted(item: TodoItem)
    {
        val itemIndex = _todoItems.indexOf(item)
        if (itemIndex != -1){
            val updatedItem= item.copy(isCompleted = !item.isCompleted)
            _todoItems[itemIndex]= updatedItem
        }
    }
}
