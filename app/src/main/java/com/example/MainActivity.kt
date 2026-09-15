package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ScreenTab
import com.example.model.ThemeCustomization
import com.example.model.ThemeMode
import com.example.state.MediRapidViewModel
import com.example.ui.components.AppHeader
import com.example.ui.components.FallDetectionDialog
import com.example.ui.components.SecurityModal
import com.example.ui.components.ThemeCustomizerDialog
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MediRapidViewModel = viewModel()
            MyApplicationTheme(customization = viewModel.themeCustomization) {
                MediRapidApp(viewModel = viewModel)
            }
        }
    }
}

fun getTabIcon(tab: ScreenTab): ImageVector = when (tab) {
    ScreenTab.HOME -> Icons.Default.Home
    ScreenTab.EMERGENCY -> Icons.Default.Emergency
    ScreenTab.CONSULT -> Icons.Default.MedicalServices
    ScreenTab.AI_HEALTH -> Icons.Default.SmartToy
    ScreenTab.MEDICAL_IMAGES -> Icons.Default.ImageSearch
    ScreenTab.PHARMACY -> Icons.Default.LocalPharmacy
    ScreenTab.DEVICES -> Icons.Default.Watch
    ScreenTab.DASHBOARD -> Icons.Default.Dashboard
    ScreenTab.STARTUP -> Icons.Default.Business
}

@Composable
fun MediRapidApp(
    viewModel: MediRapidViewModel = viewModel()
) {
    val activeTab = viewModel.activeTab
    val emergencyState = viewModel.emergencyState
    val vitals = viewModel.vitals
    val isStreaming = viewModel.isDemoTelemetryStreaming
    val doctors = viewModel.doctors
    val activeDoctor = viewModel.activeConsultDoctor
    val isConsultRoomOpen = viewModel.showDoctorConsultRoom
    val consultCallType = viewModel.consultCallType
    val chatMessages = viewModel.chatMessages
    val doctorSummary = viewModel.currentAiTriageSummary
    val scans = viewModel.medicalScans
    val selectedScan = viewModel.selectedScan
    val isAnalyzingScan = viewModel.isAnalyzingImage
    val pharmacies = viewModel.pharmacies
    val prescriptions = viewModel.prescriptions
    val pharmacyOrder = viewModel.activePharmacyOrder
    val contacts = viewModel.emergencyContacts
    val isDemoModeActive = viewModel.isDemoModeActive
    val showFallAlert = viewModel.isFallModalVisible
    val fallCountdown = viewModel.fallCountdown
    val showSecurityModal = viewModel.showSecurityModal
    val showThemeModal = viewModel.showThemeModal
    val themeConfig = viewModel.themeCustomization

    val isDark = when (themeConfig.mode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    // Fall Detection Escalation Dialog
    if (showFallAlert) {
        FallDetectionDialog(
            countdownSeconds = fallCountdown,
            onDismissOk = { viewModel.dismissFallOk() },
            onConfirmNeedHelp = { viewModel.confirmFallNeedHelp() }
        )
    }

    // Security & Compliance Dialog
    if (showSecurityModal) {
        SecurityModal(
            onDismiss = { viewModel.showSecurityModal = false }
        )
    }

    // Theme Customizer Modal Dialog
    if (showThemeModal) {
        ThemeCustomizerDialog(
            currentConfig = themeConfig,
            onApplyConfig = { newConfig ->
                viewModel.themeCustomization = newConfig
            },
            onDismiss = { viewModel.showThemeModal = false }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("medirapid_main_scaffold"),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                AppHeader(
                    isDemoMode = isDemoModeActive,
                    onToggleDemoMode = { viewModel.isDemoModeActive = !viewModel.isDemoModeActive },
                    onEmergencyClick = { viewModel.triggerEmergencyHelp() },
                    onSecurityClick = { viewModel.showSecurityModal = true },
                    onThemeClick = { viewModel.showThemeModal = true },
                    onOpenDemo1 = { viewModel.runDemo1() },
                    onOpenDemo2 = { viewModel.runDemo2() },
                    onOpenDemo3 = { viewModel.runDemo3() },
                    onOpenDemo4 = { viewModel.runDemo4() }
                )

                // Scrollable Navigation Tab Strip
                val tabRowContainerColor = if (isDark) {
                    if (themeConfig.isHighContrast) Color.Black else Color(0xFF0F172A)
                } else {
                    Slate900
                }

                ScrollableTabRow(
                    selectedTabIndex = activeTab.ordinal,
                    edgePadding = 12.dp,
                    containerColor = tabRowContainerColor,
                    contentColor = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScreenTab.values().forEach { tab ->
                        val isSelected = activeTab == tab
                        Tab(
                            selected = isSelected,
                            onClick = { viewModel.activeTab = tab },
                            text = {
                                Text(
                                    text = tab.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) themeConfig.palette.accent else Color(0xFF94A3B8)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = getTabIcon(tab),
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (isSelected) themeConfig.palette.accent else Color(0xFF94A3B8)
                                )
                            },
                            modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                val primaryTabs = listOf(
                    ScreenTab.HOME,
                    ScreenTab.EMERGENCY,
                    ScreenTab.AI_HEALTH,
                    ScreenTab.CONSULT,
                    ScreenTab.PHARMACY
                )

                primaryTabs.forEach { tab ->
                    val isSelected = activeTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.activeTab = tab },
                        icon = {
                            if (tab == ScreenTab.EMERGENCY && emergencyState.isActive) {
                                BadgedBox(badge = { Badge { Text("!") } }) {
                                    Icon(getTabIcon(tab), contentDescription = tab.title, tint = EmergencyRed)
                                }
                            } else {
                                Icon(
                                    imageVector = getTabIcon(tab),
                                    contentDescription = tab.title,
                                    tint = if (isSelected) themeConfig.palette.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) themeConfig.palette.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (tab == ScreenTab.EMERGENCY) EmergencyRed else themeConfig.palette.primary,
                            selectedTextColor = if (tab == ScreenTab.EMERGENCY) EmergencyRed else themeConfig.palette.primary,
                            indicatorColor = if (tab == ScreenTab.EMERGENCY) Color(0xFFFEE2E2) else themeConfig.palette.primaryLight.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.testTag("bottom_nav_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (activeTab) {
                ScreenTab.HOME -> LandingScreen(
                    onNavigate = { viewModel.activeTab = it },
                    onEmergencyClick = { viewModel.triggerEmergencyHelp() },
                    onCustomizeTheme = { viewModel.showThemeModal = true }
                )
                ScreenTab.EMERGENCY -> EmergencyScreen(
                    emergencyState = emergencyState,
                    contacts = contacts,
                    onTriggerHelp = { viewModel.triggerEmergencyHelp() },
                    onConfirmEmergency = { viewModel.confirmEmergency() },
                    onCancelEmergency = { viewModel.cancelEmergency() }
                )
                ScreenTab.CONSULT -> DoctorConsultScreen(
                    doctors = doctors,
                    activeDoctor = activeDoctor,
                    isConsultRoomOpen = isConsultRoomOpen,
                    consultCallType = consultCallType,
                    aiTriageSummary = doctorSummary,
                    onStartConsult = { doctor, callType -> viewModel.startDoctorConsultation(doctor, callType) },
                    onCloseConsult = { viewModel.closeDoctorConsultation() },
                    onNavigate = { viewModel.activeTab = it }
                )
                ScreenTab.AI_HEALTH -> AiHealthAgentScreen(
                    chatMessages = chatMessages,
                    doctorSummary = doctorSummary,
                    onSendMessage = { viewModel.sendUserMessage(it) },
                    onNavigate = { viewModel.activeTab = it },
                    onEmergencyClick = { viewModel.triggerEmergencyHelp() }
                )
                ScreenTab.MEDICAL_IMAGES -> MedicalImagesScreen(
                    scans = scans,
                    selectedScan = selectedScan,
                    isAnalyzing = isAnalyzingScan,
                    onSelectScan = { viewModel.selectedScan = it },
                    onUploadScan = { viewModel.uploadAndAnalyzeScan(it) },
                    onForwardToDoctor = {
                        viewModel.activeTab = ScreenTab.CONSULT
                    }
                )
                ScreenTab.PHARMACY -> PharmacyScreen(
                    pharmacies = pharmacies,
                    prescriptions = prescriptions,
                    activeOrder = pharmacyOrder,
                    onOrderPrescription = { pres, pharmacy -> viewModel.orderPrescriptionViaPharmacy(pres, pharmacy) },
                    onExplainWithAi = { pres ->
                        viewModel.sendUserMessage("Please explain the clinical instructions for prescription ${pres.id}: ${pres.medicines.firstOrNull()?.name}")
                        viewModel.activeTab = ScreenTab.AI_HEALTH
                    }
                )
                ScreenTab.DEVICES -> HealthDevicesScreen(
                    vitals = vitals,
                    isStreaming = isStreaming,
                    onToggleStreaming = { viewModel.isDemoTelemetryStreaming = !viewModel.isDemoTelemetryStreaming },
                    onSimulateFall = { viewModel.triggerSimulatedFall() }
                )
                ScreenTab.DASHBOARD -> DashboardScreen(
                    vitals = vitals,
                    emergencyState = emergencyState,
                    prescriptions = prescriptions,
                    scans = scans,
                    pharmacyOrder = pharmacyOrder,
                    contacts = contacts,
                    themeCustomization = themeConfig,
                    onCustomizeTheme = { viewModel.showThemeModal = true },
                    onNavigate = { viewModel.activeTab = it },
                    onEmergencyClick = { viewModel.triggerEmergencyHelp() }
                )
                ScreenTab.STARTUP -> OurSolutionScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
