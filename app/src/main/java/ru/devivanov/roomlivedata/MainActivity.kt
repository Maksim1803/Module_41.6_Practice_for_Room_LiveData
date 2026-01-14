package ru.devivanov.roomlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.devivanov.roomlivedata.data.StringEntity
import ru.devivanov.roomlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Подписываемся на последнее значение
        mainActivityViewModel.stringLiveData.observe(this) {
            if (it == null) return@observe
            val string = "Last entry: ID: ${it.id} | Text: ${it.string}"
            binding.textLastDbValue.text = string
        }

        // Подписываемся на весь список данных
        mainActivityViewModel.allStringsLiveData.observe(this) { list ->
            if (list == null || list.isEmpty()) {
                binding.textAllDbValues.text = "No history yet"
                return@observe
            }
            
            // Собираем все строки в одну большую строку для вывода
            val allDataString = list.joinToString(separator = "\n") { entity ->
                "ID: ${entity.id} -> ${entity.string}"
            }
            binding.textAllDbValues.text = allDataString
        }

        // По нажатию на кнопку сохраняем данные
        binding.button.setOnClickListener {
            val data = binding.editText.text.toString()
            if (data.isNotBlank()) {
                mainActivityViewModel.putDataToDB(StringEntity(string = data))
                binding.editText.text.clear() // Очищаем поле после ввода
            }
        }
    }
}