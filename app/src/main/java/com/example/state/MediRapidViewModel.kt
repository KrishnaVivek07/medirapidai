package com.example.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediRapidViewModel : ViewModel() {
    var activeTab by mutableStateOf(ScreenTab.HOME)
    var isDemoModeActive by mutableStateOf(true)
    var isFallModalVisible by mutableStateOf(false)
    var fallCountdown by mutableIntStateOf(30)
    var showSecurityModal by mutableStateOf(false)
    var showThemeModal by mutableStateOf(false)
    var themeCustomization by mutableStateOf(ThemeCustomization())
    var showDoctorConsultRoom by mutableStateOf(false)
    var activeConsultDoctor by mutableStateOf<Doctor?>(null)
    var consultCallType by mutableStateOf("Video Call")

    // Emergency Workflow State
    var emergencyState by mutableStateOf(EmergencyState())
    val emergencyContacts = mutableStateListOf<EmergencyContact>().apply {
        addAll(SampleData.emergencyContacts)
    }

    // Vitals Telemetry
    var vitals by mutableStateOf(VitalMetrics())
    var isDemoTelemetryStreaming by mutableStateOf(true)

    // Chat / AI Health Agent
    val chatMessages = mutableStateListOf<ChatMessage>().apply {
        addAll(SampleData.initialChatMessages)
    }
    var currentAiTriageSummary by mutableStateOf<String?>(null)

    // Medical Images
    val medicalScans = mutableStateListOf<MedicalImageScan>().apply {
        addAll(SampleData.medicalScans)
    }
    var selectedScan by mutableStateOf<MedicalImageScan?>(SampleData.medicalScans.firstOrNull())
    var isAnalyzingImage by mutableStateOf(false)

    // Doctors & Marketplace
    val doctors = mutableStateListOf<Doctor>().apply {
        addAll(SampleData.doctors)
    }

    // Prescriptions & Pharmacy
    val prescriptions = mutableStateListOf<Prescription>().apply {
        addAll(SampleData.prescriptions)
    }
    val pharmacies = mutableStateListOf<Pharmacy>().apply {
        addAll(SampleData.pharmacies)
    }
    var activePharmacyOrder by mutableStateOf<PharmacyOrder?>(
        PharmacyOrder(
            id = "ORD-99182",
            pharmacyName = "MediRapid Express Central",
            prescriptionId = "RX-88402",
            items = listOf("Albuterol Sulfate Inhaler 90mcg", "Benzonatate Capsules 100mg"),
            totalAmount = "$28.50",
            status = OrderStatus.OUT_FOR_DELIVERY,
            orderTime = "Today, 10:14 AM"
        )
    )

    init {
        startTelemetryLoop()
    }

    private fun startTelemetryLoop() {
        viewModelScope.launch {
            while (true) {
                delay(3000)
                if (isDemoTelemetryStreaming) {
                    val hrDelta = (-2..3).random()
                    val newHr = (vitals.heartRate + hrDelta).coerceIn(68, 92)
                    vitals = vitals.copy(
                        heartRate = newHr,
                        steps = vitals.steps + (0..6).random(),
                        activeMinutes = vitals.activeMinutes + if ((0..10).random() > 8) 1 else 0
                    )
                }
            }
        }
    }

    // Emergency Actions
    fun triggerEmergencyHelp() {
        emergencyState = emergencyState.copy(
            isActive = true,
            step = EmergencyStep.CONFIRMATION
        )
        activeTab = ScreenTab.EMERGENCY
    }

    fun confirmEmergency() {
        viewModelScope.launch {
            emergencyState = emergencyState.copy(step = EmergencyStep.LOCATION_REQUEST, locationShared = true)
            delay(1200)
            emergencyState = emergencyState.copy(step = EmergencyStep.CONTACTS_ALERTED, contactsNotified = true)
            // Mark contacts as notified
            for (i in emergencyContacts.indices) {
                emergencyContacts[i] = emergencyContacts[i].copy(isNotified = true)
            }
            delay(1500)
            emergencyState = emergencyState.copy(
                step = EmergencyStep.SERVICES_DISPATCHED,
                emsDispatched = true,
                ambulanceUnit = "Unit EMS-402 (Rapid Rescue)",
                estimatedArrivalMinutes = 6
            )
        }
    }

    fun cancelEmergency() {
        emergencyState = EmergencyState(isActive = false, step = EmergencyStep.IDLE)
        for (i in emergencyContacts.indices) {
            emergencyContacts[i] = emergencyContacts[i].copy(isNotified = false)
        }
    }

    // Fall Detection
    fun triggerSimulatedFall() {
        isFallModalVisible = true
        fallCountdown = 30
        viewModelScope.launch {
            while (isFallModalVisible && fallCountdown > 0) {
                delay(1000)
                if (isFallModalVisible) {
                    fallCountdown--
                    if (fallCountdown == 0) {
                        // Auto escalate
                        isFallModalVisible = false
                        triggerEmergencyHelp()
                        confirmEmergency()
                    }
                }
            }
        }
    }

    fun dismissFallOk() {
        isFallModalVisible = false
    }

    fun confirmFallNeedHelp() {
        isFallModalVisible = false
        triggerEmergencyHelp()
        confirmEmergency()
    }

    // Chat interactions
    fun sendUserMessage(text: String) {
        val userMsg = ChatMessage(
            id = "user-${System.currentTimeMillis()}",
            sender = MessageSender.USER,
            message = text,
            timestamp = "Just now"
        )
        chatMessages.add(userMsg)

        viewModelScope.launch {
            delay(1000)
            val lower = text.lowercase()
            when {
                lower.contains("chest") || lower.contains("heart") || lower.contains("breath") || lower.contains("shortness") -> {
                    val response = ChatMessage(
                        id = "ai-${System.currentTimeMillis()}",
                        sender = MessageSender.AGENT,
                        message = "⚠️ WARNING: Chest discomfort and shortness of breath can indicate an urgent clinical condition. \n\n" +
                                "Preliminary Advice:\n" +
                                "• Stop all physical exertion immediately and sit in an upright position.\n" +
                                "• Do not attempt to drive yourself.\n" +
                                "• If discomfort radiates to jaw, arm, or neck, activate 🚨 Emergency Help immediately.\n\n" +
                                "Would you like me to connect you with Dr. Elena Rostova (Emergency Medicine) right now or initiate EMS alert?",
                        timestamp = "Just now",
                        isUrgent = true,
                        suggestedActions = listOf("🚨 Activate Emergency Help", "Connect to Dr. Rostova Now", "Explain medical terms"),
                        doctorSummary = "PATIENT TRIAGE BRIEF: Chief Complaint: Acute chest tightness with shortness of breath. Duration: Recent onset. Acuity: High. Recommendation: Emergency Physician Evaluation."
                    )
                    chatMessages.add(response)
                    currentAiTriageSummary = response.doctorSummary
                }
                lower.contains("prescription") || lower.contains("albuterol") || lower.contains("amoxicillin") -> {
                    val response = ChatMessage(
                        id = "ai-${System.currentTimeMillis()}",
                        sender = MessageSender.AGENT,
                        message = "Your active prescription for Albuterol Sulfate (90mcg) is a fast-acting bronchodilator. \n\n" +
                                "Clinician Instructions: 2 puffs every 4 to 6 hours as needed. Shake inhaler well, exhale completely, press canister while inhaling deeply, and hold breath for 10 seconds. \n\n" +
                                "Note: As an AI, I cannot modify your dosage. Always consult your prescriber before making changes.",
                        timestamp = "Just now",
                        suggestedActions = listOf("Refill via Pharmacy", "Ask about side effects", "Set medication alarm")
                    )
                    chatMessages.add(response)
                }
                lower.contains("vitals") || lower.contains("heart rate") -> {
                    val response = ChatMessage(
                        id = "ai-${System.currentTimeMillis()}",
                        sender = MessageSender.AGENT,
                        message = "Your connected Smartwatch reports:\n" +
                                "• Heart Rate: ${vitals.heartRate} BPM (Normal sinus resting range)\n" +
                                "• SpO2: ${vitals.bloodOxygen}% (Healthy oxygenation)\n" +
                                "• Activity: ${vitals.activeMinutes} mins active, ${vitals.steps} steps\n\n" +
                                "All metrics appear stable at this moment. Wearable telemetry provides monitoring assistance only and does not diagnose disease.",
                        timestamp = "Just now",
                        suggestedActions = listOf("Show Full Vitals Graph", "Schedule Routine Checkup")
                    )
                    chatMessages.add(response)
                }
                else -> {
                    val response = ChatMessage(
                        id = "ai-${System.currentTimeMillis()}",
                        sender = MessageSender.AGENT,
                        message = "I have noted your symptoms: \"$text\". \n\n" +
                                "Follow-up questions:\n" +
                                "1. How long have you been experiencing these symptoms?\n" +
                                "2. On a scale of 1 to 10, what is the discomfort severity?\n" +
                                "3. Do you have any known allergies or pre-existing conditions?\n\n" +
                                "I am compiling this information into a preliminary clinical brief ready for clinician review.",
                        timestamp = "Just now",
                        suggestedActions = listOf("Started 2 hours ago", "Severity is mild (3/10)", "Consult a Doctor Now")
                    )
                    chatMessages.add(response)
                }
            }
        }
    }

    // Image upload & analysis
    fun uploadAndAnalyzeScan(scanType: String) {
        isAnalyzingImage = true
        viewModelScope.launch {
            delay(1800)
            val newScan = if (scanType.contains("chest", ignoreCase = true)) {
                MedicalImageScan(
                    id = "SCAN-${(100..999).random()}",
                    title = "Computed Chest PA Radiograph",
                    scanType = "Chest X-Ray (AP)",
                    date = "Just now",
                    preliminaryFinding = "Computer Vision Model detected region of localized parenchymal density in lower lung field (Confidence: 86%). Requires radiologist evaluation.",
                    confidence = 86,
                    abnormalRegionText = "Right Lower Lobe (Consolidation Suspected)",
                    status = ReviewStatus.PRELIMINARY_AI_ANALYSIS,
                    sampleGraphicType = "chest"
                )
            } else {
                MedicalImageScan(
                    id = "SCAN-${(100..999).random()}",
                    title = "Orthopedic Wrist Radiograph",
                    scanType = "Wrist AP/Lateral",
                    date = "Just now",
                    preliminaryFinding = "Preliminary AI contour scan: Normal osseous alignment without acute cortical interruption detected (Confidence: 92%).",
                    confidence = 92,
                    abnormalRegionText = "Scaphoid-Lunate space within normal limits",
                    status = ReviewStatus.PRELIMINARY_AI_ANALYSIS,
                    sampleGraphicType = "wrist"
                )
            }
            medicalScans.add(0, newScan)
            selectedScan = newScan
            isAnalyzingImage = false
        }
    }

    fun startDoctorConsultation(doctor: Doctor, type: String = "Video Call") {
        activeConsultDoctor = doctor
        consultCallType = type
        showDoctorConsultRoom = true
    }

    fun closeDoctorConsultation() {
        showDoctorConsultRoom = false
    }

    fun orderPrescriptionViaPharmacy(prescription: Prescription, pharmacy: Pharmacy) {
        val newOrder = PharmacyOrder(
            id = "ORD-${(10000..99999).random()}",
            pharmacyName = pharmacy.name,
            prescriptionId = prescription.id,
            items = prescription.medicines.map { "${it.name} (${it.dosage})" },
            totalAmount = "$32.40",
            status = OrderStatus.CONFIRMED,
            orderTime = "Just now"
        )
        activePharmacyOrder = newOrder
        activeTab = ScreenTab.PHARMACY

        // Simulate order progression
        viewModelScope.launch {
            delay(3000)
            if (activePharmacyOrder?.id == newOrder.id) {
                activePharmacyOrder = activePharmacyOrder?.copy(status = OrderStatus.PREPARING)
            }
            delay(4000)
            if (activePharmacyOrder?.id == newOrder.id) {
                activePharmacyOrder = activePharmacyOrder?.copy(status = OrderStatus.OUT_FOR_DELIVERY)
            }
        }
    }

    // Demo Scenarios for College Entrepreneurship Pitch
    fun runDemo1() {
        // Demo 1: Upload X-ray -> AI preliminary analysis -> Doctor review
        activeTab = ScreenTab.MEDICAL_IMAGES
        uploadAndAnalyzeScan("chest")
    }

    fun runDemo2() {
        // Demo 2: Smartwatch detects simulated fall -> User confirmation -> Emergency workflow
        activeTab = ScreenTab.DEVICES
        triggerSimulatedFall()
    }

    fun runDemo3() {
        // Demo 3: Doctor creates simulated prescription -> Nearby pharmacy -> Medicine order -> Delivery tracking
        val doc = doctors.first()
        val pres = prescriptions.first()
        val pharm = pharmacies.first()
        orderPrescriptionViaPharmacy(pres, pharm)
    }

    fun runDemo4() {
        // Demo 4: Health data dashboard -> AI identifies a potentially concerning pattern -> Recommends professional consultation
        activeTab = ScreenTab.DEVICES
        vitals = vitals.copy(heartRate = 126, bloodOxygen = 93) // Tachycardia + SpO2 dip
        sendUserMessage("My smartwatch just notified me of a sustained heart rate spike of 126 BPM and oxygen dip to 93%.")
        activeTab = ScreenTab.AI_HEALTH
    }
}
