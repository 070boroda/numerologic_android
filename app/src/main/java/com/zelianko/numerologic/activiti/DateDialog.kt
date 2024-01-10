package com.zelianko.numerologic.activiti

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.zelianko.numerologic.viewmodel.SelectedDateTextViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DateDialog(
    width: Dp = 350.dp,
    height: Dp = 150.dp,
    viewModel: SelectedDateTextViewModel,
) {
    WheelDatePicker(
        startDate = LocalDate.of(LocalDate.now().year.minus(3), 1, 1),
        minDate = LocalDate.of(1900, 1, 1),
        maxDate = LocalDate.now(),
        yearsRange = IntRange(1900, 2122),
        size = DpSize(width, height),
        rowCount = 3,
        textStyle = MaterialTheme.typography.titleLarge,
        textColor = Color.White,
        selectorProperties = WheelPickerDefaults.selectorProperties(
            enabled = true,
            shape = RoundedCornerShape(0.dp),
            color = Color(0xFFf1faee).copy(alpha = 0.2f),
            border = BorderStroke(2.dp, Color(0xFF8FBBF1)),

            )
    ) { snappedDateTime ->
        viewModel.setSelectedDateText(
            "${snappedDateTime.dayOfMonth}/${snappedDateTime.monthValue}/${snappedDateTime.year}"
        )
    }
}