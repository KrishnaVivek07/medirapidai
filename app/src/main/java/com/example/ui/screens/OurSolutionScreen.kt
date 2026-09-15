package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import com.example.ui.theme.*

@Composable
fun OurSolutionScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("our_solution_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Startup Pitch Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Slate900, Color(0xFF0F2C46))
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MediCyanAccent.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "COLLEGE ENTREPRENEURSHIP SHOWCASE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7DD3FC),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "MediRapid AI: Connected Emergency Healthcare",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Connecting users, doctors, pharmacies, and wearable telemetry into one unified emergency coordination pipeline.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Section 15: Problem -> Solution -> Technology -> Impact
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "THE STARTUP THESIS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MediBluePrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    PitchPillar(
                        step = "PROBLEM",
                        title = "Fragmented Emergency Delays",
                        description = "Emergency healthcare involves severe delays in triage consultation, diagnosis support, pharmacy medicine access, and patient-family communication. Patients often face hours of waiting or delayed care when minutes matter.",
                        icon = Icons.Default.ReportProblem,
                        accent = EmergencyRed
                    )

                    VerticalConnector()

                    PitchPillar(
                        step = "SOLUTION",
                        title = "MediRapid AI Unified Pipeline",
                        description = "MediRapid AI bridges these disconnected silos into one seamless protocol: Smart Sensor Fall Detection → Immediate Clinician Tele-Triage → Validated Rx Dispatch → 30-Min Doorstep Pharmacy Delivery.",
                        icon = Icons.Default.Lightbulb,
                        accent = SuccessEmerald
                    )

                    VerticalConnector()

                    PitchPillar(
                        step = "TECHNOLOGY",
                        title = "Multi-Agent AI + CV + Cloud Telemetry",
                        description = "State-of-the-art Computer Vision for preliminary radiograph screening, multi-agent LLM triage assistants, Wearable Bio-Sensor APIs (Wear OS / HealthKit), and geofenced pharmacy dispatch routers.",
                        icon = Icons.Default.Memory,
                        accent = MediBluePrimary
                    )

                    VerticalConnector()

                    PitchPillar(
                        step = "IMPACT",
                        title = "Quantifiable Clinical Outcomes",
                        description = "• 4.2x faster emergency coordination response\n• 65% reduction in non-urgent emergency room overcrowding\n• 25-minute average prescription delivery turnaround\n• Zero data selling / 100% HIPAA privacy integrity",
                        icon = Icons.Default.TrendingUp,
                        accent = Color(0xFF8B5CF6)
                    )
                }
            }
        }

        // Section 13: Internal AI Agent Architecture
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AI AGENT ARCHITECTURE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MediBluePrimary,
                            letterSpacing = 1.sp
                        )
                        Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFEFF6FF)) {
                            Text(
                                text = "Orchestrated Multi-Agent",
                                fontSize = 9.sp,
                                color = MediBluePrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp, 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "MediRapid AI Core Orchestrator",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Each agent has strictly bounded responsibilities. Critical medical decisions rely on validated clinical protocols rather than unconstrained LLM outputs.",
                        fontSize = 11.sp,
                        color = Slate500
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    AgentSpecItem(
                        name = "Health Agent",
                        role = "Symptom collection, patient health literacy, explain medical terms & medication instructions.",
                        badge = "Intake",
                        color = MediBluePrimary
                    )

                    AgentSpecItem(
                        name = "Triage Agent",
                        role = "Clinical acuity scoring, red-flag emergency detection, and clinician briefing generation.",
                        badge = "Triage",
                        color = Color(0xFF0284C7)
                    )

                    AgentSpecItem(
                        name = "Medical Image Analysis",
                        role = "Convolutional neural network for bounding box localization & radiograph anomaly density detection.",
                        badge = "Vision",
                        color = Color(0xFF8B5CF6)
                    )

                    AgentSpecItem(
                        name = "Doctor Assistant",
                        role = "Structures intake summaries, correlates wearable telemetry, and formats digital prescription orders for licensed MD review.",
                        badge = "EHR Sync",
                        color = SuccessEmerald
                    )

                    AgentSpecItem(
                        name = "Pharmacy Agent",
                        role = "Checks local pharmacy stock availability, calculates transit routes, and coordinates courier pickup.",
                        badge = "Logistics",
                        color = WarningAmber
                    )

                    AgentSpecItem(
                        name = "Emergency Agent",
                        role = "Predefined safety rules & location geofencing for 911 EMS alerts. Never relies on unverified LLM decisions for life safety.",
                        badge = "Life Safety",
                        color = EmergencyRed
                    )
                }
            }
        }

        // Section 14: Business Model Page
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "STARTUP BUSINESS MODEL & REVENUE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MediBluePrimary,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RevenueStreamRow(
                        title = "1. Tele-Consultation Transaction Fees",
                        description = "15% platform fee on patient-doctor virtual consultations and specialist referrals.",
                        tag = "B2C Fee"
                    )

                    RevenueStreamRow(
                        title = "2. Pharmacy Fulfillment Platform Fees",
                        description = "Per-delivery platform facilitation fee with participating local pharmacy partners (where legally permitted).",
                        tag = "Marketplace"
                    )

                    RevenueStreamRow(
                        title = "3. Premium Health Monitoring Subscription",
                        description = "$9.99/month for continuous high-frequency wearable analytics, fall detection guardrails, and family safety ring sync.",
                        tag = "SaaS"
                    )

                    RevenueStreamRow(
                        title = "4. Pharmacy Software Subscriptions",
                        description = "SaaS inventory routing terminal for local independent pharmacies to receive digital on-demand prescriptions.",
                        tag = "B2B SaaS"
                    )

                    RevenueStreamRow(
                        title = "5. Clinic Enterprise EHR Connector",
                        description = "Enterprise integration software for urgent care clinics to ingest AI triage summaries directly into Epic/Cerner EHR.",
                        tag = "Enterprise"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ethical Charter Notice
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF0FDF4),
                        border = BorderStroke(1.dp, Color(0xFF86EFAC))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = SuccessEmerald)
                            Column {
                                Text(
                                    text = "Ethical Healthcare Charter",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF14532D)
                                )
                                Text(
                                    text = "MediRapid AI will NEVER sell, monetize, or broker personal health data or patient telemetry to third parties.",
                                    fontSize = 10.sp,
                                    color = Color(0xFF166534)
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
private fun PitchPillar(
    step: String,
    title: String,
    description: String,
    icon: ImageVector,
    accent: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accent, modifier = Modifier.size(18.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Surface(shape = RoundedCornerShape(4.dp), color = accent.copy(alpha = 0.1f)) {
                Text(
                    text = step,
                    color = accent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate900)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = description, fontSize = 11.sp, color = Slate700, lineHeight = 16.sp)
        }
    }
}

@Composable
private fun VerticalConnector() {
    Box(
        modifier = Modifier
            .padding(start = 17.dp, top = 4.dp, bottom = 4.dp)
            .width(2.dp)
            .height(14.dp)
            .background(Slate200)
    )
}

@Composable
private fun AgentSpecItem(
    name: String,
    role: String,
    badge: String,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = color.copy(alpha = 0.12f)
            ) {
                Text(
                    text = badge,
                    color = color,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "→ $name", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate900)
                Text(text = role, fontSize = 10.sp, color = Slate700, lineHeight = 14.sp)
            }
        }
    }
}

@Composable
private fun RevenueStreamRow(
    title: String,
    description: String,
    tag: String
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Slate900)
            Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFEFF6FF)) {
                Text(text = tag, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MediBluePrimary, modifier = Modifier.padding(4.dp, 2.dp))
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = description, fontSize = 11.sp, color = Slate500, lineHeight = 15.sp)
    }
}
