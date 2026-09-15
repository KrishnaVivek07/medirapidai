package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun PharmacyScreen(
    pharmacies: List<Pharmacy>,
    prescriptions: List<Prescription>,
    activeOrder: PharmacyOrder?,
    onOrderPrescription: (Prescription, Pharmacy) -> Unit,
    onExplainWithAi: (Prescription) -> Unit
) {
    var selectedPrescription by remember { mutableStateOf(prescriptions.firstOrNull()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("pharmacy_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Rapid Pharmacy Network & Delivery",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Connects verified clinician prescriptions with local fulfillment hubs",
                    fontSize = 13.sp,
                    color = Slate500
                )
            }
        }

        // Stepper: Doctor Prescription -> Pharmacy Selection -> Order -> Preparation -> Delivery
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "FULFILLMENT PIPELINE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MediBluePrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PipelinePill("1. Doctor Rx", active = true)
                        Text("→", color = Slate500, fontSize = 11.sp)
                        PipelinePill("2. Pharmacy", active = true)
                        Text("→", color = Slate500, fontSize = 11.sp)
                        PipelinePill("3. Order", active = activeOrder != null)
                        Text("→", color = Slate500, fontSize = 11.sp)
                        PipelinePill("4. Prep", active = activeOrder?.status == OrderStatus.PREPARING || activeOrder?.status == OrderStatus.OUT_FOR_DELIVERY)
                        Text("→", color = Slate500, fontSize = 11.sp)
                        PipelinePill("5. Delivery", active = activeOrder?.status == OrderStatus.OUT_FOR_DELIVERY)
                    }
                }
            }
        }

        // Active Delivery Tracking Card (if order is placed)
        activeOrder?.let { order ->
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF38BDF8))
                                )
                                Text(
                                    text = "LIVE MEDICINE COURIER TRACKING",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = order.id,
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Prescription from: ${order.pharmacyName}",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Items: ${order.items.joinToString(", ")}",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Status Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when (order.status) {
                                OrderStatus.CONFIRMED -> Color(0xFF1E293B)
                                OrderStatus.PREPARING -> Color(0xFF854D0E)
                                OrderStatus.OUT_FOR_DELIVERY -> Color(0xFF065F46)
                                OrderStatus.DELIVERED -> SuccessEmerald
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = when (order.status) {
                                        OrderStatus.CONFIRMED -> Icons.Default.ReceiptLong
                                        OrderStatus.PREPARING -> Icons.Default.Inventory
                                        OrderStatus.OUT_FOR_DELIVERY -> Icons.Default.TwoWheeler
                                        OrderStatus.DELIVERED -> Icons.Default.DoneAll
                                    },
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = when (order.status) {
                                        OrderStatus.CONFIRMED -> "Order Confirmed • Dispatching to Pharmacist"
                                        OrderStatus.PREPARING -> "Pharmacist Packaging Medication (~10 mins)"
                                        OrderStatus.OUT_FOR_DELIVERY -> "Courier En Route: ${order.courierName} (ETA ~18 min)"
                                        OrderStatus.DELIVERED -> "Delivered to Doorstep"
                                    },
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: Active Doctor Prescriptions
        item {
            Text(
                text = "Verified Clinician Prescriptions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
        }

        items(prescriptions) { pres ->
            PrescriptionCard(
                prescription = pres,
                isSelected = pres.id == selectedPrescription?.id,
                onSelect = { selectedPrescription = pres },
                onExplain = { onExplainWithAi(pres) }
            )
        }

        // Section: Nearby Participating Pharmacies
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nearby Participating Pharmacies",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Sorted by proximity",
                    fontSize = 11.sp,
                    color = Slate500
                )
            }
        }

        items(pharmacies) { pharmacy ->
            PharmacyItemCard(
                pharmacy = pharmacy,
                onOrderNow = {
                    selectedPrescription?.let { pres ->
                        onOrderPrescription(pres, pharmacy)
                    }
                }
            )
        }
    }
}

@Composable
private fun PipelinePill(text: String, active: Boolean) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = if (active) MediBlueLight else Slate100
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (active) MediBlueDark else Slate500,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun PrescriptionCard(
    prescription: Prescription,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onExplain: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) MediBluePrimary else Slate200),
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Medication, contentDescription = null, tint = MediBluePrimary)
                    Column {
                        Text(
                            text = prescription.id,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Slate900
                        )
                        Text(
                            text = "${prescription.date} • ${prescription.doctorName}",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (prescription.canRefill) Color(0xFFF0FDF4) else Slate100
                ) {
                    Text(
                        text = if (prescription.canRefill) "REFILL ACTIVE" else "FINAL DOSE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (prescription.canRefill) SuccessEmerald else Slate500,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Diagnosis: ${prescription.diagnosisSummary}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate700
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Medicines list
            prescription.medicines.forEach { med ->
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Slate50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = med.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Slate900)
                            Text(text = "${med.dosage} • ${med.frequency}", fontSize = 10.sp, color = Slate500)
                            Text(text = med.instructions, fontSize = 10.sp, color = Slate700)
                        }
                        if (med.inStock) {
                            Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFF0FDF4)) {
                                Text("In Stock", fontSize = 9.sp, color = SuccessEmerald, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp, 2.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { expanded = !expanded },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp), tint = MediBluePrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Explain with AI", fontSize = 11.sp, color = MediBlueDark)
                }

                Button(
                    onClick = onSelect,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) SuccessEmerald else MediBluePrimary),
                    modifier = Modifier.weight(1.2f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(if (isSelected) "Selected for Order ✓" else "Select to Order", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            // AI Explanation Dropdown
            AnimatedVisibility(visible = expanded) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFEFF6FF),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "AI Clinical Guidance (Preliminary Explanation):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MediBlueDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = prescription.aiExplanation,
                            fontSize = 11.sp,
                            color = Color(0xFF1E3A8A),
                            lineHeight = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Note: AI explains instructions for literacy only. AI cannot modify or create prescriptions.",
                            fontSize = 9.sp,
                            color = Slate500
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PharmacyItemCard(
    pharmacy: Pharmacy,
    onOrderNow: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = pharmacy.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Slate900
                    )
                    Text(
                        text = "${pharmacy.address} • ${pharmacy.distanceMiles} mi away",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFF0FDF4)
                ) {
                    Text(
                        text = "OPEN 24/7",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = SuccessEmerald,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text("Prep Time", fontSize = 10.sp, color = Slate500)
                    Text("~${pharmacy.prepTimeMinutes} mins", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                }
                Column {
                    Text("Delivery ETA", fontSize = 10.sp, color = Slate500)
                    Text("~${pharmacy.deliveryTimeMinutes} mins", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900)
                }
                Column {
                    Text("Rating", fontSize = 10.sp, color = Slate500)
                    Text("★ ${pharmacy.rating}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onOrderNow,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .testTag("order_pharmacy_${pharmacy.id}"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MediBluePrimary)
            ) {
                Icon(Icons.Default.DeliveryDining, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Order Prescriptions for Doorstep Delivery", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
