import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    // 하드코딩 리스트
    val years: List<Int> = (1980..2024).toList()
    val months = (1..12).toList()
    val days = (1..31).toList()

    // UI 상태
    val selectedYear = mutableStateOf<Int?>(null)
    val selectedMonth = mutableStateOf<Int?>(null)
    val selectedDay = mutableStateOf<Int?>(null)

    val yearExpanded = mutableStateOf(false)
    val monthExpanded = mutableStateOf(false)
    val dayExpanded = mutableStateOf(false)


    fun toggleYear() {
        yearExpanded.value = !yearExpanded.value
        monthExpanded.value = false
        dayExpanded.value = false
    }
    fun toggleMonth() {
        yearExpanded.value = false
        monthExpanded.value =!monthExpanded.value
        dayExpanded.value = false
    }
    fun toggleDay() {
        yearExpanded.value = false
        monthExpanded.value = false
        dayExpanded.value =!dayExpanded.value
    }

}
