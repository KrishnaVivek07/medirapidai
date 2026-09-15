package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*

@Composable
fun SecurityModal(
    onDismiss: () -> Unit
) {
    var healthConsent by remember { mutableStateOf(true) }
    var locationConsent by remember { mutableStateOf(true) }
    var researchConsent by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MediBluePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Security & Compliance",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Encryption & Architecture Banner
                    item {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF0FDF4),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = SuccessEmerald,
                                    modifier = Modifier.size(28.dp)
                                )
                                Column {
                                    Text(
                                        text = "HIPAA-Ready & End-to-End Encrypted",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF14532D)
                                    )
                                    Text(
                                        text = "AES-256 GCM encryption at rest. Zero telemetry monetization. Audit logs recorded for every clinician access.",
                                        fontSize = 11.sp,
                                        color = Color(0xFF166534)
                                    )
                                }
                            }
                        }
                    }

                    // Role-based Access Control
                    item {
                        Text(
                            text = "ROLE-BASED ACCESS CONTROL (RBAC)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        RoleBadgeItem(role = "Patient (You)", access = "Full access to own records, vitals, prescriptions & emergency ring", color = MediBluePrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        RoleBadgeItem(role = "Licensed Clinician", access = "Time-limited access during active consultation to review AI summaries and issue signed prescriptions", color = SuccessEmerald)
                        Spacer(modifier = Modifier.height(6.dp))
                        RoleBadgeItem(role = "Participating Pharmacist", access = "Access restricted to validated prescription items & delivery fulfillment", color = WarningAmber)
                        Spacer(modifier = Modifier.height(6.dp))
                        RoleBadgeItem(role = "EMS First Responder", access = "Emergency-triggered GPS location & vital sign status during active 911 dispatch only", color = EmergencyRed)
                    }

                    // Consent Management Toggles
                    item {
                        Text(
                            text = "CONSENT & PERMISSIONS MANAGEMENT",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        ConsentToggleRow(
                            title = "Continuous Vitals Telemetry",
                            subtitle = "Stream Apple Watch / Wear OS metrics for emergency fall detection",
                            checked = healthConsent,
                            onCheckedChange = { healthConsent = it }
                        )

                        ConsentToggleRow(
                            title = "Precise Geolocation for EMS",
                            subtitle = "Share GPS coordinates during active SOS alerts",
                            checked = locationConsent,
                            onCheckedChange = { locationConsent = it }
                        )

                        ConsentToggleRow(
                            title = "Anonymized Startup AI Research",
                            subtitle = "Allow anonymized X-rays for model validation (strictly opt-in)",
                            checked = researchConsent,
                            onCheckedChange = { researchConsent = it }
                        )
                    }

                    // Audit Logs Table
                    item {
                        Text(
                            text = "ACCESS AUDIT LOGS (IMMUTABLE)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        AuditLogRow("10:14 AM", "Prescription RX-88402 created", "Dr. Elena Rostova, MD")
                        AuditLogRow("08:31 AM", "X-Ray SCAN-01 uploaded & analyzed", "MediRapid Computer Vision API")
                        AuditLogRow("Yesterday", "Emergency contacts ring verified", "Patient Self-Check")
                    }

                    // Policies
                    item {
                        Text(
                            text = "LEGAL & COMPLIANCE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Privacy Policy • Terms of Service • Medical Device Disclaimer\nMediRapid AI is a demonstration MVP prototype developed for university entrepreneurship showcase. It does not replace 911 dispatch or hospital trauma care.",
                            fontSize = 11.sp,
                            color = Slate500,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Slate900)
                ) {
                    Text("Save & Close")
                }
            }
        }
    }
}

@Composable
private fun RoleBadgeItem(role: String, access: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Slate50,
        border = androidx.compose.foundation.BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Text(
                    text = role,
                    color = color,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Text(
                text = access,
                fontSize = 11.sp,
                color = Slate700,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ConsentToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Slate900)
            Text(text = subtitle, fontSize = 11.sp, color = Slate500)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MediBluePrimary)
        )
    }
}

@Composable
private fun AuditLogRow(time: String, action: String, actor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = action, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Slate900)
            Text(text = "Actor: $actor", fontSize = 10.sp, color = Slate500)
        }
        Text(text = time, fontSize = 11.sp, color = Slate500)
    }
}
