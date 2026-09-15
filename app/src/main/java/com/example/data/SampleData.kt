package com.example.data

import com.example.model.*

object SampleData {
    val doctors = listOf(
        Doctor(
            id = "doc-1",
            name = "Dr. Elena Rostova, MD",
            specialty = "Emergency Medicine & Critical Care",
            hospital = "Bay Area Trauma & Medical Center",
            experienceYears = 14,
            rating = 4.9f,
            reviewCount = 312,
            isVerified = true,
            isAvailableNow = true,
            consultationFee = "$45",
            avatarInitial = "ER"
        ),
        Doctor(
            id = "doc-2",
            name = "Dr. Marcus Vance, DO",
            specialty = "Internal Medicine & Tele-Triage",
            hospital = "University Health Pavilion",
            experienceYears = 9,
            rating = 4.8f,
            reviewCount = 194,
            isVerified = true,
            isAvailableNow = true,
            consultationFee = "$35",
            avatarInitial = "MV"
        ),
        Doctor(
            id = "doc-3",
            name = "Dr. Sarah Jenkins, MD",
            specialty = "Cardiology & Wearables Specialist",
            hospital = "Pacific Heart Institute",
            experienceYears = 16,
            rating = 5.0f,
            reviewCount = 428,
            isVerified = true,
            isAvailableNow = false,
            consultationFee = "$60",
            avatarInitial = "SJ"
        ),
        Doctor(
            id = "doc-4",
            name = "Dr. Tariq Al-Mansoor, MD",
            specialty = "Family Medicine & Urgent Care",
            hospital = "Community Health Network",
            experienceYears = 11,
            rating = 4.7f,
            reviewCount = 158,
            isVerified = true,
            isAvailableNow = true,
            consultationFee = "$30",
            avatarInitial = "TA"
        )
    )

    val emergencyContacts = listOf(
        EmergencyContact(
            id = "c-1",
            name = "Katherine Miller",
            relationship = "Parent / Guardian",
            phone = "+1 (555) 389-2041",
            isPrimary = true,
            isNotified = false
        ),
        EmergencyContact(
            id = "c-2",
            name = "David Miller",
            relationship = "Sibling / Alternate",
            phone = "+1 (555) 712-4490",
            isPrimary = false,
            isNotified = false
        )
    )

    val pharmacies = listOf(
        Pharmacy(
            id = "ph-1",
            name = "MediRapid Express Central",
            address = "742 Market St, San Francisco, CA",
            distanceMiles = 0.4,
            prepTimeMinutes = 12,
            deliveryTimeMinutes = 25,
            rating = 4.9f,
            isOpen = true,
            acceptsInsurance = true
        ),
        Pharmacy(
            id = "ph-2",
            name = "CVS MinuteClinic Pharmacy #408",
            address = "1282 Folsom St, San Francisco, CA",
            distanceMiles = 0.8,
            prepTimeMinutes = 15,
            deliveryTimeMinutes = 35,
            rating = 4.6f,
            isOpen = true,
            acceptsInsurance = true
        ),
        Pharmacy(
            id = "ph-3",
            name = "Walgreens 24/7 Community Hub",
            address = "339 4th St, San Francisco, CA",
            distanceMiles = 1.1,
            prepTimeMinutes = 10,
            deliveryTimeMinutes = 30,
            rating = 4.7f,
            isOpen = true,
            acceptsInsurance = true
        )
    )

    val prescriptions = listOf(
        Prescription(
            id = "RX-88402",
            date = "Oct 12, 2026",
            doctorName = "Dr. Elena Rostova, MD",
            doctorSpecialty = "Emergency Medicine",
            diagnosisSummary = "Acute Bronchial Spasm with Persistent Cough",
            medicines = listOf(
                MedicineItem("Albuterol Sulfate Inhaler", "90 mcg", "2 puffs every 4-6 hours as needed", "Shake well before inhalation. Rinse mouth with water after use."),
                MedicineItem("Benzonatate Capsules", "100 mg", "1 capsule 3 times daily", "Swallow whole with a full glass of water. Do not chew.")
            ),
            durationDays = 7,
            canRefill = true,
            aiExplanation = "Albuterol relaxes airway muscles rapidly to open bronchial passages. Benzonatate anesthetizes the stretch sensors in your lungs to soothe cough reflexes. Remember: Take with water and avoid caffeine if heart rate elevates."
        ),
        Prescription(
            id = "RX-77194",
            date = "Sep 28, 2026",
            doctorName = "Dr. Marcus Vance, DO",
            doctorSpecialty = "Internal Medicine",
            diagnosisSummary = "Streptococcal Pharyngitis (Bacterial)",
            medicines = listOf(
                MedicineItem("Amoxicillin Oral Suspension", "500 mg", "1 tablet every 8 hours", "Finish entire 10-day course even if feeling better.")
            ),
            durationDays = 10,
            canRefill = false,
            aiExplanation = "Amoxicillin is an antibiotic targeting bacterial cell walls. It must be taken for the complete duration to avoid bacterial antibiotic resistance."
        )
    )

    val medicalScans = listOf(
        MedicalImageScan(
            id = "SCAN-01",
            title = "Chest PA Radiograph (X-Ray)",
            scanType = "Chest X-Ray (Anteroposterior)",
            date = "Today, 08:30 AM",
            preliminaryFinding = "Suspected focal consolidation in right lower lobe (RLL) suggestive of early lobar pneumonia or atelectasis.",
            confidence = 88,
            abnormalRegionText = "Right Lower Thoracic Zone (Coordinates: [X: 62%, Y: 58%, Radius: 42px])",
            status = ReviewStatus.PENDING_CLINICIAN_REVIEW,
            sampleGraphicType = "chest"
        ),
        MedicalImageScan(
            id = "SCAN-02",
            title = "Left Wrist AP / Lateral X-Ray",
            scanType = "Orthopedic Radiograph",
            date = "Yesterday",
            preliminaryFinding = "No cortical disruption or acute fracture detected in distal radius or ulna. Mild soft tissue edema.",
            confidence = 94,
            abnormalRegionText = "Dorsal radiocarpal soft tissue cushion",
            status = ReviewStatus.CLINICIAN_VERIFIED,
            sampleGraphicType = "wrist"
        )
    )

    val healthDevices = listOf(
        HealthDevice("dev-1", "Apple Watch Ultra 2 (Cellular)", "Smartwatch", 78, true, "2 mins ago"),
        HealthDevice("dev-2", "Galaxy Watch 6 Classic", "Smartwatch", 62, false, "Yesterday"),
        HealthDevice("dev-3", "Pixel 9 Pro Bio-Sensor Core", "Smartphone Sensor Hub", 94, true, "Just now")
    )

    val initialChatMessages = listOf(
        ChatMessage(
            id = "msg-1",
            sender = MessageSender.AGENT,
            message = "Hello, I am the MediRapid Health Agent. I'm here to help gather your symptoms, provide preliminary guidance, and organize a summary for a qualified doctor.\n\n⚠️ Disclaimer: AI provides assistance and preliminary information only. I cannot independently diagnose diseases or prescribe medication. In life-threatening situations, use the Emergency Help button immediately.",
            timestamp = "Just now",
            suggestedActions = listOf(
                "I feel chest tightness & fatigue",
                "Explain my Albuterol prescription",
                "Review my latest vitals",
                "Set medication reminder"
            )
        )
    )
}
