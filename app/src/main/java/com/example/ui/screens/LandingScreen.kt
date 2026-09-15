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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenTab
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    onNavigate: (ScreenTab) -> Unit,
    onEmergencyClick: () -> Unit,
    onCustomizeTheme: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("landing_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Slate900,
                                Color(0xFF0F2537),
                                Color(0xFF093554)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF1E293B),
                        border = BorderStroke(1.dp, MediCyanAccent.copy(alpha = 0.5f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(MediCyanAccent)
                            )
                            Text(
                                text = "MEDIRAPID AI HEALTHCARE NETWORK",
                                color = Color(0xFF7DD3FC),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Healthcare help when every minute matters.",
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "AI-assisted consultation, health monitoring and rapid access to participating local pharmacies.",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4 Action Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // 🚨 Emergency Help Button
                        Button(
                            onClick = onEmergencyClick,
                            colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("hero_emergency_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "🚨 Emergency Help",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Secondary Buttons Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HeroActionButton(
                                title = "👨‍⚕️ Consult Doctor",
                                onClick = { onNavigate(ScreenTab.CONSULT) },
                                modifier = Modifier.weight(1f)
                            )
                            HeroActionButton(
                                title = "💊 Medicines",
                                onClick = { onNavigate(ScreenTab.PHARMACY) },
                                modifier = Modifier.weight(1f)
                            )
                            HeroActionButton(
                                title = "⌚ Connect Device",
                                onClick = { onNavigate(ScreenTab.DEVICES) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Quick Theme Customizer Pill
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onCustomizeTheme)
                                .testTag("landing_theme_pill")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "🎨 Customize App Theme & Colors",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Edit",
                                        color = Color(0xFF7DD3FC),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = Color(0xFF7DD3FC),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Visual Pipeline: User -> AI Assistance -> Doctor -> Pharmacy -> Delivery
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "RAPID COORDINATION FLOW",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MediBluePrimary,
                            letterSpacing = 1.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFEFF6FF)
                        ) {
                            Text(
                                text = "End-to-End Care",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MediBluePrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Flow steps
                    FlowStepRow(step = "1", title = "User", desc = "Triggers SOS or symptom check", icon = Icons.Default.Person, active = true)
                    FlowArrow()
                    FlowStepRow(step = "2", title = "AI Assistance", desc = "Organizes preliminary triage & terms", icon = Icons.Default.SmartToy, active = true)
                    FlowArrow()
                    FlowStepRow(step = "3", title = "Doctor", desc = "Qualified clinician evaluates & prescribes", icon = Icons.Default.MedicalServices, active = true)
                    FlowArrow()
                    FlowStepRow(step = "4", title = "Pharmacy", desc = "Participating local hub preps meds", icon = Icons.Default.LocalPharmacy, active = true)
                    FlowArrow()
                    FlowStepRow(step = "5", title = "Delivery", desc = "Doorstep dispatch with live ETA", icon = Icons.Default.DeliveryDining, active = true)
                }
            }
        }

        // Section Title: Key Features
        item {
            PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Platform Capabilities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Engineered for rapid response when seconds count",
                    fontSize = 13.sp,
                    color = Slate500
                )
            }
        }

        // 6 Feature Cards
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    title = "AI Health Assistant",
                    description = "Helps organize symptoms and medical information and recommends appropriate next steps.",
                    icon = Icons.Default.SmartToy,
                    accentColor = MediBluePrimary,
                    tag = "Triage Agent",
                    onClick = { onNavigate(ScreenTab.AI_HEALTH) }
                )

                FeatureCard(
                    title = "Online Consultation",
                    description = "Connect users with qualified healthcare professionals via encrypted video, audio, or instant chat.",
                    icon = Icons.Default.VideoCameraFront,
                    accentColor = SuccessEmerald,
                    tag = "Licensed MDs",
                    onClick = { onNavigate(ScreenTab.CONSULT) }
                )

                FeatureCard(
                    title = "AI Medical Image Assistance",
                    description = "Allows supported medical images to be analyzed for validated preliminary findings that require clinician review.",
                    icon = Icons.Default.ImageSearch,
                    accentColor = Color(0xFF8B5CF6),
                    tag = "Computer Vision",
                    onClick = { onNavigate(ScreenTab.MEDICAL_IMAGES) }
                )

                FeatureCard(
                    title = "Rapid Pharmacy Network",
                    description = "Connects doctor-approved prescriptions with participating nearby local pharmacies for rapid preparation.",
                    icon = Icons.Default.LocalPharmacy,
                    accentColor = WarningAmber,
                    tag = "15-Min Prep",
                    onClick = { onNavigate(ScreenTab.PHARMACY) }
                )

                FeatureCard(
                    title = "Smart Health Monitoring",
                    description = "Connect supported smartphones and wearable devices where APIs are available for vitals tracking.",
                    icon = Icons.Default.Watch,
                    accentColor = MediCyanAccent,
                    tag = "Live Telemetry",
                    onClick = { onNavigate(ScreenTab.DEVICES) }
                )

                FeatureCard(
                    title = "Emergency Response",
                    description = "Provides an emergency workflow using authorized location and device telemetry with simulated EMS dispatch.",
                    icon = Icons.Default.Emergency,
                    accentColor = EmergencyRed,
                    tag = "SOS Escalation",
                    onClick = onEmergencyClick
                )
            }
        }

        // Startup Presentation Banner
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigate(ScreenTab.STARTUP) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate900)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MediBluePrimary.copy(alpha = 0.3f)
                        ) {
                            Text(
                                text = "COLLEGE STARTUP PITCH",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7DD3FC),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explore Our Solution & Architecture",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Problem → Solution → Business Model → Multi-Agent System",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroActionButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF1E293B),
        border = BorderStroke(1.dp, Color(0xFF334155)),
        modifier = modifier.height(44.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FlowStepRow(
    step: String,
    title: String,
    desc: String,
    icon: ImageVector,
    active: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(if (active) MediBlueLight else Slate100),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (active) MediBluePrimary else Slate500,
                modifier = Modifier.size(18.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "$step. $title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Slate900
                )
            }
            Text(
                text = desc,
                fontSize = 11.sp,
                color = Slate500
            )
        }
    }
}

@Composable
private fun FlowArrow() {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp)
            .width(2.dp)
            .height(14.dp)
            .background(Color(0xFFBAE6FD))
    )
}

@Composable
private fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    accentColor: Color,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = accentColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = Slate700
                )
            }
        }
    }
}
