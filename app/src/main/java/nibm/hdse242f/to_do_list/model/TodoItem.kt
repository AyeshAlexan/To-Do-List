package nibm.hdse242f.to_do_list.model

data class TodoItem ( val id: Int, val task: String, var isCompleted: Boolean= false)
