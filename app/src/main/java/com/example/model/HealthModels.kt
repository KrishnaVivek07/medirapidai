package com.example.model

enum class ScreenTab(val title: String, val iconName: String) {
    HOME("Home", "home"),
    EMERGENCY("Emergency", "emergency"),
    CONSULT("Consult", "doctor"),
    AI_HEALTH("AI Health", "ai"),
    MEDICAL_IMAGES("Medical Images", "xray"),
    PHARMACY("Pharmacy", "pharmacy"),
    DEVICES("Devices", "watch"),
    DASHBOARD("Dashboard", "dashboard"),
    STARTUP("Our Solution", "solution")
}

data class VitalMetrics(
    val heartRate: Int = 76,
    val steps: Int = 6840,
    val targetSteps: Int = 10000,
    val bloodOxygen: Int = 98,
    val systolicBp: Int = 118,
    val diastolicBp: Int = 78,
    val sleepHours: Float = 7.4f,
    val caloriesBurned: Int = 460,
    val activeMinutes: Int = 42,
    val fallDetected: Boolean = false,
    val fallTimestamp: String? = null
)

data class EmergencyState(
    val isActive: Boolean = false,
    val step: EmergencyStep = EmergencyStep.IDLE,
    val locationShared: Boolean = false,
    val locationAddress: String = "37.7749° N, 122.4194° W (4th & King St, San Francisco, CA)",
    val contactsNotified: Boolean = false,
    val emsDispatched: Boolean = false,
    val ambulanceUnit: String = "Unit EMS-402 (Rapid Rescue)",
    val estimatedArrivalMinutes: Int = 6,
    val requestId: String = "EMG-2026-9481",
    val notes: String = "Automated sensor-triggered rapid emergency event"
)

enum class EmergencyStep {
    IDLE,
    CONFIRMATION,
    LOCATION_REQUEST,
    CONTACTS_ALERTED,
    SERVICES_DISPATCHED,
    IN_TRANSIT
}

data class EmergencyContact(
    val id: String,
    val name: String,
    val relationship: String,
    val phone: String,
    val isPrimary: Boolean = false,
    val isNotified: Boolean = false
)

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val hospital: String,
    val experienceYears: Int,
    val rating: Float,
    val reviewCount: Int,
    val isVerified: Boolean = true,
    val isAvailableNow: Boolean = true,
    val consultationFee: String = "$35",
    val avatarInitial: String = "Dr"
)

data class MedicineItem(
    val name: String,
    val dosage: String,
    val frequency: String,
    val instructions: String,
    val inStock: Boolean = true
)

data class Prescription(
    val id: String,
    val date: String,
    val doctorName: String,
    val doctorSpecialty: String,
    val diagnosisSummary: String,
    val medicines: List<MedicineItem>,
    val durationDays: Int,
    val canRefill: Boolean,
    val aiExplanation: String
)

data class Pharmacy(
    val id: String,
    val name: String,
    val address: String,
    val distanceMiles: Double,
    val prepTimeMinutes: Int,
    val deliveryTimeMinutes: Int,
    val rating: Float,
    val isOpen: Boolean = true,
    val acceptsInsurance: Boolean = true
)

data class PharmacyOrder(
    val id: String,
    val pharmacyName: String,
    val prescriptionId: String,
    val items: List<String>,
    val totalAmount: String,
    val status: OrderStatus,
    val orderTime: String,
    val courierName: String = "Carlos M. (MediRapid Express)"
)

enum class OrderStatus {
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED
}

data class MedicalImageScan(
    val id: String,
    val title: String,
    val scanType: String,
    val date: String,
    val preliminaryFinding: String,
    val confidence: Int,
    val abnormalRegionText: String,
    val status: ReviewStatus,
    val sampleGraphicType: String
)

enum class ReviewStatus {
    PRELIMINARY_AI_ANALYSIS,
    PENDING_CLINICIAN_REVIEW,
    CLINICIAN_VERIFIED
}

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val message: String,
    val timestamp: String,
    val isUrgent: Boolean = false,
    val suggestedActions: List<String> = emptyList(),
    val doctorSummary: String? = null
)

enum class MessageSender {
    USER,
    AGENT,
    SYSTEM
}

data class HealthDevice(
    val id: String,
    val name: String,
    val deviceType: String,
    val batteryPercent: Int,
    val isConnected: Boolean,
    val lastSync: String
)
