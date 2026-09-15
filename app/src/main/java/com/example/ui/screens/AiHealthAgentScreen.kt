package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.model.ChatMessage
import com.example.model.MessageSender
import com.example.model.ScreenTab
import com.example.ui.theme.*

@Composable
fun AiHealthAgentScreen(
    chatMessages: List<ChatMessage>,
    doctorSummary: String?,
    onSendMessage: (String) -> Unit,
    onNavigate: (ScreenTab) -> Unit,
    onEmergencyClick: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("ai_health_agent_screen")
    ) {
        // Agent Sub-Header with Safety Status
        Surface(
            color = Color.White,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F2FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = MediBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "MediRapid Health Agent",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Slate900
                        )
                        Text(
                            text = "Triage & Literacy Assistant • Preliminary only",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF0FDF4)
                ) {
                    Text(
                        text = "Safety Mode: ACTIVE",
                        color = SuccessEmerald,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Doctor Summary Drawer / Banner if generated
        if (doctorSummary != null) {
            Surface(
                color = Color(0xFFEFF6FF),
                border = BorderStroke(1.dp, Color(0xFFBFDBFE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = null,
                                tint = MediBluePrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "CLINICAL DOCTOR SUMMARY PREPARED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MediBlueDark
                            )
                        }

                        TextButton(
                            onClick = { onNavigate(ScreenTab.CONSULT) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Forward to Doctor →", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = doctorSummary,
                        fontSize = 11.sp,
                        color = Color(0xFF1E3A8A),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chatMessages) { message ->
                ChatMessageItem(
                    message = message,
                    onActionClick = { actionText ->
                        if (actionText.contains("emergency", ignoreCase = true)) {
                            onEmergencyClick()
                        } else if (actionText.contains("doctor", ignoreCase = true)) {
                            onNavigate(ScreenTab.CONSULT)
                        } else {
                            onSendMessage(actionText)
                        }
                    }
                )
            }
        }

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                QuickPromptChip("Chest tightness & fatigue") { onSendMessage("I feel sudden chest tightness and fatigue") }
            }
            item {
                QuickPromptChip("Explain my prescription") { onSendMessage("Explain my Albuterol and Benzonatate prescription instructions") }
            }
            item {
                QuickPromptChip("What does SpO2 mean?") { onSendMessage("Explain medical term: What does SpO2 blood oxygen saturation mean?") }
            }
            item {
                QuickPromptChip("Set med reminder") { onSendMessage("Set a daily medication reminder for 8:00 AM and 8:00 PM") }
            }
        }

        // Message Input Field
        Surface(
            color = Color.White,
            border = BorderStroke(1.dp, Slate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .imePadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Describe your symptoms or ask a question...", fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MediBluePrimary,
                        unfocusedBorderColor = Slate200
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MediBluePrimary)
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Message",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    message: ChatMessage,
    onActionClick: (String) -> Unit
) {
    val isUser = message.sender == MessageSender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.widthIn(max = 340.dp)
        ) {
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (message.isUrgent) Color(0xFFFEE2E2) else Color(0xFFE0F2FE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (message.isUrgent) Icons.Default.Warning else Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = if (message.isUrgent) EmergencyRed else MediBluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                color = when {
                    isUser -> MediBluePrimary
                    message.isUrgent -> Color(0xFFFFF1F2)
                    else -> Color.White
                },
                border = if (!isUser) BorderStroke(1.dp, if (message.isUrgent) Color(0xFFFDA4AF) else Slate200) else null,
                shadowElevation = if (isUser) 0.dp else 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = message.message,
                        color = if (isUser) Color.White else if (message.isUrgent) Color(0xFF991B1B) else Slate900,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    // Suggested Action Buttons
                    if (message.suggestedActions.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            message.suggestedActions.forEach { action ->
                                Surface(
                                    onClick = { onActionClick(action) },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (action.contains("Emergency")) EmergencyRed else Color(0xFFF1F5F9),
                                    border = BorderStroke(1.dp, if (action.contains("Emergency")) EmergencyRed else Slate200)
                                ) {
                                    Text(
                                        text = action,
                                        color = if (action.contains("Emergency")) Color.White else MediBlueDark,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = message.timestamp,
            fontSize = 10.sp,
            color = Slate500,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun QuickPromptChip(text: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFBAE6FD))
    ) {
        Text(
            text = text,
            color = MediBlueDark,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
