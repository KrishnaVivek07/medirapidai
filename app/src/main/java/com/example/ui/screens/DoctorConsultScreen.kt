package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Doctor
import com.example.model.ScreenTab
import com.example.ui.theme.*

@Composable
fun DoctorConsultScreen(
    doctors: List<Doctor>,
    activeDoctor: Doctor?,
    isConsultRoomOpen: Boolean,
    consultCallType: String,
    aiTriageSummary: String?,
    onStartConsult: (Doctor, String) -> Unit,
    onCloseConsult: () -> Unit,
    onNavigate: (ScreenTab) -> Unit
) {
    if (isConsultRoomOpen && activeDoctor != null) {
        // Active Tele-Consultation Room Simulator
        ActiveConsultationRoom(
            doctor = activeDoctor,
            callType = consultCallType,
            aiSummary = aiTriageSummary,
            onClose = onCloseConsult,
            onNavigateToPrescription = { onNavigate(ScreenTab.PHARMACY) }
        )
    } else {
        // Doctor Marketplace / Directory
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Slate50)
                .testTag("doctor_consult_screen"),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Connect with Qualified Clinicians",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Board-certified physicians available for rapid video, audio, or chat triage",
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }
            }

            // Legal & Role Notice
            item {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFEFF6FF),
                    border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = MediBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Licensed Clinician Network • Only verified medical doctors can evaluate clinical summaries and issue legally binding prescriptions. AI provides assistance and intake organization only.",
                            fontSize = 11.sp,
                            color = MediBlueDark,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // AI Summary Handoff Card if present
            if (aiTriageSummary != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF93C5FD))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MediBluePrimary, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Ready to share with your Doctor:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MediBlueDark
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = aiTriageSummary,
                                fontSize = 11.sp,
                                color = Slate700
                            )
                        }
                    }
                }
            }

            items(doctors) { doctor ->
                DoctorProfileCard(
                    doctor = doctor,
                    onSelectCall = { type -> onStartConsult(doctor, type) }
                )
            }
        }
    }
}

@Composable
private fun DoctorProfileCard(
    doctor: Doctor,
    onSelectCall: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MediBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = doctor.avatarInitial,
                            fontWeight = FontWeight.ExtraBold,
                            color = MediBluePrimary,
                            fontSize = 18.sp
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = doctor.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Slate900
                            )
                            if (doctor.isVerified) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified MD",
                                    tint = MediBluePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Text(
                            text = doctor.specialty,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MediBlueDark
                        )
                        Text(
                            text = "${doctor.hospital} • ${doctor.experienceYears} yrs exp",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (doctor.isAvailableNow) Color(0xFFF0FDF4) else Slate100
                ) {
                    Text(
                        text = if (doctor.isAvailableNow) "AVAILABLE NOW" else "NEXT: 20M",
                        color = if (doctor.isAvailableNow) SuccessEmerald else Slate500,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Slate100)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                    Text(text = "${doctor.rating}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate900)
                    Text(text = "(${doctor.reviewCount} reviews)", fontSize = 11.sp, color = Slate500)
                }

                Text(
                    text = "${doctor.consultationFee} / consult",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Slate900
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3 Consultation Launch Buttons: Video, Audio, Chat
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSelectCall("Video Call") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MediBluePrimary),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Video", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onSelectCall("Audio Call") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F766E)),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Audio", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onSelectCall("Chat") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Chat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ActiveConsultationRoom(
    doctor: Doctor,
    callType: String,
    aiSummary: String?,
    onClose: () -> Unit,
    onNavigateToPrescription: () -> Unit
) {
    var isMuted by remember { mutableStateOf(false) }
    var isVideoOff by remember { mutableStateOf(false) }
    var prescriptionCreated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090D16))
            .padding(16.dp)
            .testTag("active_consultation_room"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Room Header
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
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(SuccessEmerald)
                )
                Text(
                    text = "LIVE ENCRYPTED $callType",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFF1E293B)
            ) {
                Text(
                    text = "Time: 04:18",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Main Video / Audio Screen Simulation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1E293B)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MediBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = doctor.avatarInitial,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = doctor.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${doctor.specialty} • ${doctor.hospital}",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF064E3B)
                ) {
                    Text(
                        text = "Medical License: CA-MD-892401 (Verified)",
                        color = Color(0xFF6EE7B7),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            // Inset: Patient self-view
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0F172A),
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(width = 90.dp, height = 120.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("You (HD)", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        // Doctor's Verified Prescription Pad simulation
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Clinician Prescription Pad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Slate900
                    )
                    Text(
                        text = "Only Doctor Can Issue",
                        fontSize = 10.sp,
                        color = EmergencyRed,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (prescriptionCreated) "Signed: Albuterol Sulfate Inhaler 90mcg (1 Inhaler, 1 Refill)" else "Doctor has reviewed your AI symptoms & X-Ray. Ready to issue Rx?",
                    fontSize = 11.sp,
                    color = Slate700
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (!prescriptionCreated) {
                    Button(
                        onClick = { prescriptionCreated = true },
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessEmerald),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Doctor Issues Verified Prescription", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onNavigateToPrescription,
                        colors = ButtonDefaults.buttonColors(containerColor = MediBluePrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Order via Nearby Pharmacy Network →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Call Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { isMuted = !isMuted },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isMuted) Color(0xFFEF4444) else Color(0xFF334155))
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Mute",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { isVideoOff = !isVideoOff },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isVideoOff) Color(0xFFEF4444) else Color(0xFF334155))
            ) {
                Icon(
                    imageVector = if (isVideoOff) Icons.Default.VideocamOff else Icons.Default.Videocam,
                    contentDescription = "Video Toggle",
                    tint = Color.White
                )
            }

            // End Call
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = Color.White)
                Spacer(modifier = Modifier.width(6.dp))
                Text("End Call", fontWeight = FontWeight.Bold)
            }
        }
    }
}
