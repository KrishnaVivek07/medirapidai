package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    vitals: VitalMetrics,
    emergencyState: EmergencyState,
    prescriptions: List<Prescription>,
    scans: List<MedicalImageScan>,
    pharmacyOrder: PharmacyOrder?,
    contacts: List<EmergencyContact>,
    themeCustomization: ThemeCustomization = ThemeCustomization(),
    onCustomizeTheme: () -> Unit = {},
    onNavigate: (ScreenTab) -> Unit,
    onEmergencyClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // User Profile Header
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(MediBluePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("KM", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("Alex Rivera (Patient)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("ID: MEDI-99214 • Age: 28 • Blood: O+", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF064E3B)
                    ) {
                        Text(
                            text = "SECURE / ACTIVE",
                            color = Color(0xFF6EE7B7),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Section 1: Health Overview Card
        item {
            DashboardSectionCard(
                title = "1. Health Overview",
                subtitle = "Real-time vitals and baseline activity",
                icon = Icons.Default.Favorite,
                accentColor = Color(0xFFEF4444),
                onClick = { onNavigate(ScreenTab.DEVICES) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DashboardMetricBadge("Heart Rate", "${vitals.heartRate} BPM", MediBluePrimary)
                    DashboardMetricBadge("SpO2", "${vitals.bloodOxygen}%", Color(0xFF0284C7))
                    DashboardMetricBadge("Daily Steps", "${vitals.steps}", SuccessEmerald)
                    DashboardMetricBadge("Sleep", "${vitals.sleepHours}h", Color(0xFF8B5CF6))
                }
            }
        }

        // Section 2: Emergency Status Card
        item {
            DashboardSectionCard(
                title = "2. Emergency Status",
                subtitle = if (emergencyState.isActive) "🚨 ACTIVE EMERGENCY IN PROGRESS" else "Standby • Geofence Ready",
                icon = Icons.Default.Emergency,
                accentColor = EmergencyRed,
                onClick = onEmergencyClick
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (emergencyState.isActive) "Ambulance Unit: ${emergencyState.ambulanceUnit}" else "One-tap SOS protocol configured with location sharing",
                        fontSize = 12.sp,
                        color = if (emergencyState.isActive) EmergencyRed else Slate700,
                        fontWeight = if (emergencyState.isActive) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = onEmergencyClick,
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(if (emergencyState.isActive) "View SOS" else "Test SOS", fontSize = 11.sp)
                    }
                }
            }
        }

        // Section 3: Consultations
        item {
            DashboardSectionCard(
                title = "3. Consultations",
                subtitle = "Past and upcoming physician sessions",
                icon = Icons.Default.VideoCameraFront,
                accentColor = SuccessEmerald,
                onClick = { onNavigate(ScreenTab.CONSULT) }
            ) {
                Text(
                    text = "Upcoming: Dr. Elena Rostova, MD (Emergency Medicine) • Available for instant video follow-up",
                    fontSize = 12.sp,
                    color = Slate700
                )
            }
        }

        // Section 4: Medical Images
        item {
            DashboardSectionCard(
                title = "4. Medical Images",
                subtitle = "${scans.size} studies analyzed by Computer Vision",
                icon = Icons.Default.ImageSearch,
                accentColor = Color(0xFF8B5CF6),
                onClick = { onNavigate(ScreenTab.MEDICAL_IMAGES) }
            ) {
                scans.firstOrNull()?.let { scan ->
                    Text(
                        text = "Latest: ${scan.title} (${scan.scanType}) • Preliminary finding: ${scan.abnormalRegionText}",
                        fontSize = 12.sp,
                        color = Slate700
                    )
                }
            }
        }

        // Section 5: Prescriptions
        item {
            DashboardSectionCard(
                title = "5. Prescriptions",
                subtitle = "${prescriptions.size} active clinician prescriptions",
                icon = Icons.Default.Medication,
                accentColor = WarningAmber,
                onClick = { onNavigate(ScreenTab.PHARMACY) }
            ) {
                prescriptions.firstOrNull()?.let { pres ->
                    Text(
                        text = "Active: ${pres.medicines.firstOrNull()?.name} (${pres.durationDays} day course) • Prescribed by ${pres.doctorName}",
                        fontSize = 12.sp,
                        color = Slate700
                    )
                }
            }
        }

        // Section 6: Pharmacy Orders
        item {
            DashboardSectionCard(
                title = "6. Pharmacy Orders",
                subtitle = if (pharmacyOrder != null) "Order #${pharmacyOrder.id}" else "No active deliveries",
                icon = Icons.Default.LocalPharmacy,
                accentColor = Color(0xFF0284C7),
                onClick = { onNavigate(ScreenTab.PHARMACY) }
            ) {
                if (pharmacyOrder != null) {
                    Text(
                        text = "Status: ${pharmacyOrder.status.name} • Delivering from ${pharmacyOrder.pharmacyName} via MediRapid Courier",
                        fontSize = 12.sp,
                        color = Slate700
                    )
                } else {
                    Text("No pending pharmacy orders. Select a prescription to order.", fontSize = 12.sp, color = Slate500)
                }
            }
        }

        // Section 7: Health Devices
        item {
            DashboardSectionCard(
                title = "7. Health Devices",
                subtitle = "3 devices paired • Continuous background sync",
                icon = Icons.Default.Watch,
                accentColor = MediCyanAccent,
                onClick = { onNavigate(ScreenTab.DEVICES) }
            ) {
                Text(
                    text = "Primary Sensor: Apple Watch Ultra 2 (78% battery) • Fall sensor and pulse active",
                    fontSize = 12.sp,
                    color = Slate700
                )
            }
        }

        // Section 8: Emergency Contacts
        item {
            DashboardSectionCard(
                title = "8. Emergency Contacts",
                subtitle = "${contacts.size} contacts in trusted family safety ring",
                icon = Icons.Default.People,
                accentColor = EmergencyRed,
                onClick = onEmergencyClick
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    contacts.forEach { c ->
                        Text(
                            text = "• ${c.name} (${c.relationship}) - ${c.phone} ${if (c.isPrimary) "[Primary]" else ""}",
                            fontSize = 12.sp,
                            color = Slate700
                        )
                    }
                }
            }
        }

        // Section 9: Theme & Visual Customization
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    1.5.dp,
                    if (themeCustomization.isHighContrast) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(themeCustomization.palette.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = themeCustomization.palette.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "9. Theme & Visual Appearance",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Active: ${themeCustomization.mode.displayName} • ${themeCustomization.palette.title}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = onCustomizeTheme,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = themeCustomization.palette.primary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Customize", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Live Swatches Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(themeCustomization.palette.primary)
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(themeCustomization.palette.secondary)
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(themeCustomization.palette.accent)
                            )
                            Text(
                                text = "${themeCustomization.palette.title} Palette",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (themeCustomization.isHighContrast) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.Black
                            ) {
                                Text(
                                    text = "HIGH CONTRAST",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardSectionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(accentColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                    }
                    Column {
                        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Slate900)
                        Text(text = subtitle, fontSize = 11.sp, color = Slate500)
                    }
                }

                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Slate500, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun DashboardMetricBadge(label: String, value: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 9.sp, color = Slate500)
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
