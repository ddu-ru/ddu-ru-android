import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.component.GreetingMessageCard

@Composable
fun YearDropdownField(
    modifier: Modifier = Modifier,
    label: String,
    selectedValue: Int?,
    expanded: Boolean,
    items: List<Int>,
    onFieldClick: () -> Unit,
    onItemSelected: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = label,
//            style = DduruTheme.typography.bodySmall,
//            color = DduruTheme.colors.MainColor
//        )
//


        Box {
            Column(modifier = modifier) {

                Text(
                    text = label,
                    style = DduruTheme.typography.bodySmall,
                    color = DduruTheme.colors.MainColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFieldClick() }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedValue?.toString() ?: "Text",
                        modifier = Modifier.weight(1f),
                        style = DduruTheme.typography.bodyMedium,
                        color = DduruTheme.colors.MainColor
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }


                HorizontalDivider(
                    thickness = 1.dp,
                    color = DduruTheme.colors.GradientEnd
                )
            }



            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onFieldClick,
                modifier = Modifier.background(color = DduruTheme.colors.Background, shape = RoundedCornerShape(8.dp))
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.toString()) },
                        onClick = { onItemSelected(item) }
                    )
                }
            }
//                expanded = expanded,
//                onDismissRequest = onFieldClick
//            ) {
//                Surface(
//                    shape = RoundedCornerShape(8.dp),
//                    color = DduruTheme.colors.Background,
//                    shadowElevation = 30.dp
//                ) {
//                    Column {
//                        items.forEach { item ->
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        text = item.toString(),
//                                        color = DduruTheme.colors.MainColor,
//                                        style = DduruTheme.typography.bodyMedium
//                                    )
//                                },
//                                onClick = { onItemSelected(item) }
//                            )
//                        }
//                    }
//                }
//            }

        }
    }
}
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "년",
            selectedValue = viewModel.selectedYear.value,
            expanded = viewModel.yearExpanded.value,
            items = viewModel.years,
            onFieldClick = { viewModel.toggleYear() },
            onItemSelected = {
                viewModel.selectedYear.value = it
                viewModel.yearExpanded.value = false
            }
        )

        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "월",
            selectedValue = viewModel.selectedMonth.value,
            expanded = viewModel.monthExpanded.value,
            items = viewModel.months,
            onFieldClick = { viewModel.toggleMonth() },
            onItemSelected = {
                viewModel.selectedMonth.value = it
                viewModel.monthExpanded.value = false
            }
        )

        YearDropdownField(
            modifier = Modifier.weight(1f),
            label = "일",
            selectedValue = viewModel.selectedDay.value,
            expanded = viewModel.dayExpanded.value,
            items = viewModel.days,
            onFieldClick = { viewModel.toggleDay() },
            onItemSelected = {
                viewModel.selectedDay.value = it
                viewModel.dayExpanded.value = false
            }
        )
    }
}

@Preview
@Composable
private  fun Screen(){
    RegisterScreen()
}
