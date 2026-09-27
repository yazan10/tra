package yaz.phone.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import yaz.phone.model.AppUpdateConfig
import yaz.phone.ui.theme.PrimaryBlue
import yaz.phone.ui.theme.StatusWarning

@Composable
fun MandatoryUpdateDialog(
    config: AppUpdateConfig,
    onDismissOptional: () -> Unit
) {
    val context = LocalContext.current
    val isMandatory = config.isMandatory

    Dialog(
        onDismissRequest = {
            if (!isMandatory) onDismissOptional()
        },
        properties = DialogProperties(
            dismissOnBackPress = !isMandatory,
            dismissOnClickOutside = !isMandatory
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(if (isMandatory) Color(0xFFFEE2E2) else Color(0xFFE0F2FE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isMandatory) Icons.Default.Warning else Icons.Default.SystemUpdate,
                        contentDescription = null,
                        tint = if (isMandatory) Color(0xFFDC2626) else PrimaryBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isMandatory) "تحديث إجباري متوفر" else config.updateTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "الإصدار المتوفر: v${config.latestVersionName}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PrimaryBlue,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = config.updateMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(config.downloadUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // ignore
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isMandatory) Color(0xFFDC2626) else PrimaryBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "تحميل وتثبيت التحديث الآن",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                if (!isMandatory) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = onDismissOptional,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "تذكيري لاحقاً",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
                    }
                }
            }
        }
    }
}
