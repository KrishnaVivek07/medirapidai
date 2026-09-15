package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VitalMetrics
import com.example.ui.theme.*

@Composable
fun HealthDevicesScreen(
    vitals: VitalMetrics,
    isStreaming: Boolean,
    onToggleStreaming: () -> Unit,
    onSimulateFall: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("health_devices_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Smartwatch & Device Monitoring",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Continuous biometric telemetry from paired wearable devices",
                    fontSize = 13.sp,
                    color = Slate500
                )
            }
        }

        // Compliance notice
        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFEF3C7),
                border = BorderStroke(1.dp, Color(0xFFFDE68A))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Wearable measurements and consumer sensor telemetry cannot independently diagnose medical conditions. They provide supportive monitoring assistance.",
                        fontSize = 11.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Demo Device Mode Bar & Fall Simulator
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "College Pitch: Demo Device Mode",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Slate900
                            )
                            Text(
                                text = if (isStreaming) "Simulating live biometric pulse & sensor stream" else "Simulation paused",
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }
                        Switch(
                            checked = isStreaming,
                            onCheckedChange = { onToggleStreaming() },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SuccessEmerald)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Simulated Fall trigger button
                    Button(
                        onClick = onSimulateFall,
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("simulate_fall_button")
                    ) {
                        Icon(Icons.Default.Emergency, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Simulate High-G Fall Event (Trigger SOS Alert)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Real-Time ECG Heart Rate Waveform Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEF4444).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text("HEART RATE (BPM)", color = Color(0xFF94A3B8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("${vitals.heartRate} BPM", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF064E3B)
                        ) {
                            Text(
                                text = "NORMAL RESTING",
                                color = Color(0xFF6EE7B7),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ECG Canvas Waveform
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                    ) {
                        EcgCanvasWaveform()
                    }
                }
            }
        }

        // 4 Key Vitals Grid (SpO2, Steps, Sleep, Active Mins)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                VitalCard(
                    title = "Blood Oxygen",
                    value = "${vitals.bloodOxygen}%",
                    subtitle = "SpO2 (Pulse Oximetry)",
                    status = "Optimal",
                    icon = Icons.Default.Air,
                    accent = Color(0xFF0284C7),
                    modifier = Modifier.weight(1f)
                )
                VitalCard(
                    title = "Daily Steps",
                    value = "${vitals.steps}",
                    subtitle = "Target: ${vitals.targetSteps}",
                    status = "68% of Goal",
                    icon = Icons.Default.DirectionsWalk,
                    accent = SuccessEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                VitalCard(
                    title = "Restorative Sleep",
                    value = "${vitals.sleepHours} hrs",
                    subtitle = "REM: 2.1h • Deep: 1.8h",
                    status = "Healthy Cycled",
                    icon = Icons.Default.Bedtime,
                    accent = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f)
                )
                VitalCard(
                    title = "Active Exercise",
                    value = "${vitals.activeMinutes} mins",
                    subtitle = "${vitals.caloriesBurned} kcal burned",
                    status = "Cardio Peak",
                    icon = Icons.Default.FitnessCenter,
                    accent = WarningAmber,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Connected Device Manager List
        item {
            Text(
                text = "Paired Health Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Slate900
            )
        }

        item {
            DeviceStatusCard(
                name = "Apple Watch Ultra 2 (Cellular)",
                battery = 78,
                connected = true,
                syncText = "Continuous Bio-Sensor Sync • Active"
            )
        }

        item {
            DeviceStatusCard(
                name = "Pixel 9 Pro Bio-Sensor Core",
                battery = 94,
                connected = true,
                syncText = "Pedometer & Barometric Fall Sensor • Active"
            )
        }

        item {
            DeviceStatusCard(
                name = "Galaxy Watch 6 Classic",
                battery = 62,
                connected = false,
                syncText = "Bluetooth Standby • Last sync yesterday"
            )
        }
    }
}

@Composable
private fun EcgCanvasWaveform() {
    val infiniteTransition = rememberInfiniteTransition(label = "ecg")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ecgPhase"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val midY = h * 0.5f

        // Draw background grid lines
        for (x in 0..10) {
            drawLine(
                color = Color(0xFF1E293B),
                start = Offset(x * w / 10, 0f),
                end = Offset(x * w / 10, h),
                strokeWidth = 1f
            )
        }
        for (y in 0..4) {
            drawLine(
                color = Color(0xFF1E293B),
                start = Offset(0f, y * h / 4),
                end = Offset(w, y * h / 4),
                strokeWidth = 1f
            )
        }

        // Draw rhythmic ECG pulse line
        val path = Path()
        path.moveTo(0f, midY)

        val cycleWidth = w * 0.33f
        var currentX = 0f

        while (currentX < w) {
            val start = currentX
            path.lineTo(start + cycleWidth * 0.2f, midY)
            // P wave
            path.lineTo(start + cycleWidth * 0.28f, midY - 8f)
            path.lineTo(start + cycleWidth * 0.36f, midY)
            // PR segment
            path.lineTo(start + cycleWidth * 0.45f, midY)
            // QRS complex
            path.lineTo(start + cycleWidth * 0.48f, midY + 12f)
            path.lineTo(start + cycleWidth * 0.54f, midY - 48f) // R spike
            path.lineTo(start + cycleWidth * 0.60f, midY + 18f) // S
            path.lineTo(start + cycleWidth * 0.65f, midY)
            // T wave
            path.lineTo(start + cycleWidth * 0.78f, midY - 14f)
            path.lineTo(start + cycleWidth * 0.90f, midY)
            path.lineTo(start + cycleWidth, midY)

            currentX += cycleWidth
        }

        drawPath(
            path = path,
            color = Color(0xFF38BDF8),
            style = Stroke(width = 3.5f)
        )
    }
}

@Composable
private fun VitalCard(
    title: String,
    value: String,
    subtitle: String,
    status: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 12.sp, color = Slate500, fontWeight = FontWeight.SemiBold)
                Icon(imageVector = icon, contentDescription = null, tint = accent, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Slate900)
            Text(subtitle, fontSize = 10.sp, color = Slate500)
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = accent.copy(alpha = 0.1f)
            ) {
                Text(
                    text = status,
                    color = accent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun DeviceStatusCard(
    name: String,
    battery: Int,
    connected: Boolean,
    syncText: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (connected) MediBlueLight else Slate100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Watch,
                        contentDescription = null,
                        tint = if (connected) MediBluePrimary else Slate500,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Slate900)
                    Text(text = syncText, fontSize = 10.sp, color = Slate500)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (connected) Color(0xFFF0FDF4) else Slate100
                ) {
                    Text(
                        text = if (connected) "CONNECTED" else "STANDBY",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (connected) SuccessEmerald else Slate500,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(text = "⚡ $battery%", fontSize = 11.sp, color = Slate700, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
