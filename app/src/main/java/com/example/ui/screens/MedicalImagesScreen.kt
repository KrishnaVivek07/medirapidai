package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MedicalImageScan
import com.example.model.ReviewStatus
import com.example.model.ScreenTab
import com.example.ui.theme.*

@Composable
fun MedicalImagesScreen(
    scans: List<MedicalImageScan>,
    selectedScan: MedicalImageScan?,
    isAnalyzing: Boolean,
    onSelectScan: (MedicalImageScan) -> Unit,
    onUploadScan: (String) -> Unit,
    onForwardToDoctor: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("medical_images_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header info
        item {
            Column {
                Text(
                    text = "AI Medical Image Assistance",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Computer Vision preliminary screening for radiograph anomalies",
                    fontSize = 13.sp,
                    color = Slate500
                )
            }
        }

        // Compliance Warning
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
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Potential abnormality detected. This result is NOT a diagnosis. Clinician review is strictly required before clinical decisions or treatment. Medication is never prescribed automatically based on images.",
                        fontSize = 11.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Stepper: Upload X-Ray -> AI Analysis -> Preliminary Finding -> Doctor Review
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepPill("1. Upload", active = true)
                    Text("→", color = Slate500)
                    StepPill("2. AI Scan", active = true)
                    Text("→", color = Slate500)
                    StepPill("3. Finding", active = true)
                    Text("→", color = Slate500)
                    StepPill("4. MD Review", active = selectedScan?.status == ReviewStatus.CLINICIAN_VERIFIED)
                }
            }
        }

        // Upload Action Buttons
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Upload Medical Radiograph (Demo CV Model)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Slate900
                    )
                    Text(
                        text = "Select an anonymized demo study to run preliminary edge/density detection:",
                        fontSize = 12.sp,
                        color = Slate500,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onUploadScan("chest") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("upload_chest_xray_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MediBluePrimary),
                            enabled = !isAnalyzing
                        ) {
                            Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Chest X-Ray", fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onUploadScan("wrist") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("upload_wrist_xray_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E)),
                            enabled = !isAnalyzing
                        ) {
                            Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Wrist X-Ray", fontSize = 12.sp)
                        }
                    }

                    if (isAnalyzing) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            Text(
                                text = "Running Convolutional Feature Extractor & Heatmap Localization...",
                                fontSize = 11.sp,
                                color = MediBluePrimary
                            )
                        }
                    }
                }
            }
        }

        // Scan Viewer with Canvas Bounding Box Overlay
        selectedScan?.let { scan ->
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = scan.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${scan.scanType} • ${scan.date}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (scan.confidence > 85) Color(0xFF881337) else Color(0xFF14532D)
                            ) {
                                Text(
                                    text = "AI Confidence: ${scan.confidence}%",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Radiograph Canvas Simulation with Heatmap Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF030712))
                        ) {
                            RadiographCanvas(scan = scan)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Preliminary finding report card
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1E293B),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Analytics,
                                        contentDescription = null,
                                        tint = Color(0xFF38BDF8),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "AI PRELIMINARY SCREENING REPORT",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = scan.preliminaryFinding,
                                    fontSize = 13.sp,
                                    color = Color(0xFFF1F5F9),
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Detected Region: ${scan.abnormalRegionText}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Action: Forward to Doctor
                        Button(
                            onClick = onForwardToDoctor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("forward_scan_to_doctor_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = MediBluePrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Forward Scan to Doctor for Clinician Review", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Scans History
        item {
            Text(
                text = "Recent Imaging Studies",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Slate900
            )
        }

        items(scans) { scan ->
            StudyHistoryItem(
                scan = scan,
                isSelected = scan.id == selectedScan?.id,
                onClick = { onSelectScan(scan) }
            )
        }
    }
}

@Composable
private fun StepPill(text: String, active: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (active) MediBlueLight else Slate100
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (active) MediBlueDark else Slate500,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun RadiographCanvas(scan: MedicalImageScan) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Draw simulated radiograph bones & lungs
        if (scan.sampleGraphicType == "chest") {
            // Draw rib cage arcs
            for (i in 1..5) {
                drawArc(
                    color = Color.White.copy(alpha = 0.25f),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(w * 0.15f, h * (0.15f + i * 0.12f)),
                    size = Size(w * 0.7f, h * 0.18f),
                    style = Stroke(width = 6f)
                )
            }
            // Spine center
            drawLine(
                color = Color.White.copy(alpha = 0.4f),
                start = Offset(w * 0.5f, h * 0.1f),
                end = Offset(w * 0.5f, h * 0.9f),
                strokeWidth = 12f
            )

            // Suspected Consolidation Bounding Box in Lower Right Lobe
            drawRect(
                color = Color(0xFFEF4444).copy(alpha = 0.22f),
                topLeft = Offset(w * 0.58f, h * 0.52f),
                size = Size(w * 0.28f, h * 0.32f)
            )
            drawRect(
                color = Color(0xFFEF4444),
                topLeft = Offset(w * 0.58f, h * 0.52f),
                size = Size(w * 0.28f, h * 0.32f),
                style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f)))
            )
        } else {
            // Wrist bones
            drawRoundRect(
                color = Color.White.copy(alpha = 0.35f),
                topLeft = Offset(w * 0.35f, h * 0.2f),
                size = Size(w * 0.14f, h * 0.65f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
            )
            drawRoundRect(
                color = Color.White.copy(alpha = 0.35f),
                topLeft = Offset(w * 0.52f, h * 0.25f),
                size = Size(w * 0.12f, h * 0.6f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
            )
            // Green "Normal alignment" box
            drawRect(
                color = Color(0xFF10B981).copy(alpha = 0.15f),
                topLeft = Offset(w * 0.30f, h * 0.18f),
                size = Size(w * 0.40f, h * 0.35f)
            )
            drawRect(
                color = Color(0xFF10B981),
                topLeft = Offset(w * 0.30f, h * 0.18f),
                size = Size(w * 0.40f, h * 0.35f),
                style = Stroke(width = 2f)
            )
        }
    }
}

@Composable
private fun StudyHistoryItem(
    scan: MedicalImageScan,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) MediBluePrimary else Slate200),
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
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F172A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column {
                    Text(
                        text = scan.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Slate900
                    )
                    Text(
                        text = "${scan.scanType} • ${scan.date}",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (scan.status == ReviewStatus.CLINICIAN_VERIFIED) Color(0xFFF0FDF4) else Color(0xFFFEF3C7)
            ) {
                Text(
                    text = if (scan.status == ReviewStatus.CLINICIAN_VERIFIED) "VERIFIED" else "PENDING MD",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (scan.status == ReviewStatus.CLINICIAN_VERIFIED) SuccessEmerald else WarningAmber,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}
