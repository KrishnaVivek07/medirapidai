package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EmergencyContact
import com.example.model.EmergencyState
import com.example.model.EmergencyStep
import com.example.ui.theme.*

@Composable
fun EmergencyScreen(
    emergencyState: EmergencyState,
    contacts: List<EmergencyContact>,
    onTriggerHelp: () -> Unit,
    onConfirmEmergency: () -> Unit,
    onCancelEmergency: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sosPulse"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("emergency_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Prototype Disclaimer Banner
        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFEF2F2),
                border = BorderStroke(1.dp, Color(0xFFFCA5A5))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = EmergencyRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Simulated Emergency Network • Prototype/Demo — Not for real medical use. If you are experiencing a life-threatening emergency in real life, dial 911 immediately.",
                        fontSize = 11.sp,
                        color = Color(0xFF991B1B),
                        lineHeight = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Active Emergency Tracking Card (if active)
        if (emergencyState.isActive) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, EmergencyRed),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(EmergencyRed)
                                )
                                Text(
                                    text = "LIVE EMERGENCY WORKFLOW",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmergencyRed
                                )
                            }
                            Text(
                                text = emergencyState.requestId,
                                fontSize = 11.sp,
                                color = Slate500,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Stepper status
                        EmergencyStatusItem(
                            stepNumber = "1",
                            title = "Emergency Request Created",
                            subtitle = "Triggered via MediRapid Instant SOS Protocol",
                            completed = true
                        )

                        EmergencyStatusItem(
                            stepNumber = "2",
                            title = "Location: Shared",
                            subtitle = emergencyState.locationAddress,
                            completed = emergencyState.locationShared
                        )

                        EmergencyStatusItem(
                            stepNumber = "3",
                            title = "Emergency Contacts: Notified",
                            subtitle = if (emergencyState.contactsNotified) "SMS & In-App siren push delivered to 2 trusted contacts" else "Awaiting dispatch approval...",
                            completed = emergencyState.contactsNotified
                        )

                        EmergencyStatusItem(
                            stepNumber = "4",
                            title = "Emergency Service: Demo",
                            subtitle = if (emergencyState.emsDispatched) "${emergencyState.ambulanceUnit} en route. (Note: Estimated ~${emergencyState.estimatedArrivalMinutes} min, traffic dependent. Fixed arrival time cannot be guaranteed.)" else "Connecting with regional 911 dispatch terminal...",
                            completed = emergencyState.emsDispatched,
                            isLast = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cancellation option
                        OutlinedButton(
                            onClick = onCancelEmergency,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Slate700)
                        ) {
                            Text("Cancel Emergency (False Alarm)")
                        }
                    }
                }
            }
        } else {
            // Main SOS Activation Button
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Slate200),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Rapid Emergency Assistance",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "Press and confirm to notify contacts, share GPS coordinates, and alert emergency response.",
                            fontSize = 12.sp,
                            color = Slate500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Large SOS Button with Pulse
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(EmergencyRed)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = onTriggerHelp,
                                colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .testTag("sos_large_button")
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Emergency,
                                        contentDescription = "SOS",
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "SOS HELP",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Location: Authorized • 4th & King St, San Francisco, CA",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                }
            }
        }

        // Confirmation Dialog / Banner when triggered
        if (emergencyState.step == EmergencyStep.CONFIRMATION) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
                    border = BorderStroke(1.dp, Color(0xFFFDA4AF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Confirm Emergency Activation",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = EmergencyRed
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "This will immediately notify your primary contacts and simulate first-responder ambulance dispatch.",
                            fontSize = 12.sp,
                            color = Color(0xFF881337)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = onCancelEmergency,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = onConfirmEmergency,
                                colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                                modifier = Modifier
                                    .weight(1.4f)
                                    .testTag("confirm_emergency_action"),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Yes, Send Help", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Emergency Contacts Section
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Emergency Safety Ring",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFF0FDF4)
                        ) {
                            Text(
                                text = "2 Active Contacts",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SuccessEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    contacts.forEach { contact ->
                        EmergencyContactCard(contact = contact)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Emergency Services Directory
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Emergency Response Services",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    ServiceOptionRow(
                        title = "911 Central Police & EMS",
                        subtitle = "Direct emergency line for life-threatening events",
                        actionLabel = "Call 911",
                        badge = "Primary",
                        badgeColor = EmergencyRed
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ServiceOptionRow(
                        title = "Bay Area Trauma Level 1 Center",
                        subtitle = "1.2 miles away • Rapid ER check-in queue",
                        actionLabel = "Directions",
                        badge = "Nearby",
                        badgeColor = MediBluePrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ServiceOptionRow(
                        title = "Poison Help Hotline",
                        subtitle = "1-800-222-1222 • 24/7 toxic exposure guidance",
                        actionLabel = "Call Line",
                        badge = "Toll-Free",
                        badgeColor = WarningAmber
                    )
                }
            }
        }
    }
}

@Composable
private fun EmergencyStatusItem(
    stepNumber: String,
    title: String,
    subtitle: String,
    completed: Boolean,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (completed) SuccessEmerald else Slate200),
                contentAlignment = Alignment.Center
            ) {
                if (completed) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(
                        text = stepNumber,
                        color = Slate500,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(26.dp)
                        .background(if (completed) SuccessEmerald.copy(alpha = 0.5f) else Slate200)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (completed) Slate900 else Slate500
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Slate500,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun EmergencyContactCard(contact: EmergencyContact) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = contact.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    if (contact.isPrimary) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MediBluePrimary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = "PRIMARY",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = MediBluePrimary,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (contact.isNotified) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFFEE2E2)
                        ) {
                            Text(
                                text = "NOTIFIED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmergencyRed,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "${contact.relationship} • ${contact.phone}",
                    fontSize = 11.sp,
                    color = Slate500
                )
            }

            IconButton(onClick = { /* simulated call */ }) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call Contact",
                    tint = SuccessEmerald
                )
            }
        }
    }
}

@Composable
private fun ServiceOptionRow(
    title: String,
    subtitle: String,
    actionLabel: String,
    badge: String,
    badgeColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Slate50,
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = badgeColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = badge,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Slate500
                )
            }

            Button(
                onClick = { /* simulated action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Slate900),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.height(34.dp)
            ) {
                Text(actionLabel, fontSize = 11.sp)
            }
        }
    }
}
