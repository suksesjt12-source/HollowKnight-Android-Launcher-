package dev.lemon.lemon_emu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.lemon.lemon_emu.model.Game
import dev.lemon.lemon_emu.ui.modern.LibraryActions
import dev.lemon.lemon_emu.utils.MediaTekProfiles

private val NightBackground = Color(0xFF0B1018)
private val NightPanel = Color(0xFF151E2A)
private val NightPanelRaised = Color(0xFF202D3B)
private val NightBorder = Color(0xFF2B3C4D)
private val NightText = Color(0xFFF2F6FA)
private val NightMuted = Color(0xFFA9B9C8)
private val NightAccent = Color(0xFF69E0D0)
private val NightAccentText = Color(0xFF071A1A)
private val NightWarning = Color(0xFFFFC66D)

/**
 * Original launcher UI for this fork. Game discovery and emulation remain connected to the
 * existing Android/JNI engine through LibraryActions; no game files are bundled in the APK.
 */
@Composable
fun HollowHomeScreen(
    games: List<Game>,
    loading: Boolean,
    actions: LibraryActions
) {
    var showProfiles by rememberSaveable { mutableStateOf(false) }
    var appliedProfile by rememberSaveable { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 4.dp)
                ) {
                    BasicText(
                        "NIGHTFALL",
                        style = androidx.compose.ui.text.TextStyle(
                            color = NightAccent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 3.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    BasicText(
                        "Switch Runtime",
                        style = androidx.compose.ui.text.TextStyle(
                            color = NightText,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Spacer(Modifier.height(5.dp))
                    BasicText(
                        "Custom Android launcher · ARM64",
                        style = androidx.compose.ui.text.TextStyle(
                            color = NightMuted,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(NightPanel)
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            BasicText(
                                "MEDIATEK DEVICE PROFILE",
                                style = androidx.compose.ui.text.TextStyle(
                                    color = NightAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Spacer(Modifier.height(6.dp))
                            BasicText(
                                "Helio G100 / G99 · Mali-G57",
                                style = androidx.compose.ui.text.TextStyle(
                                    color = NightText,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(Modifier.height(4.dp))
                            BasicText(
                                "Preset eksperimental; hasil perlu diuji di perangkat.",
                                style = androidx.compose.ui.text.TextStyle(
                                    color = NightMuted,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(NightPanelRaised),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicText(
                                "MTK",
                                style = androidx.compose.ui.text.TextStyle(
                                    color = NightAccent,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    ActionButton(
                        label = "Buka preset MediaTek",
                        detail = "Resolusi, shader, akurasi GPU dan worker pipeline",
                        onClick = { showProfiles = true },
                        primary = true
                    )
                    appliedProfile?.let {
                        Spacer(Modifier.height(8.dp))
                        BasicText(
                            "Preset terakhir dipilih: $it",
                            style = androidx.compose.ui.text.TextStyle(
                                color = NightAccent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        BasicText(
                            "Game Library",
                            style = androidx.compose.ui.text.TextStyle(
                                color = NightText,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicText(
                            if (loading) "Memindai folder game…" else "${games.size} judul ditemukan",
                            style = androidx.compose.ui.text.TextStyle(
                                color = NightMuted,
                                fontSize = 12.sp
                            )
                        )
                    }
                    SmallButton("Tambah folder", onClick = actions.onAddGames)
                }
            }

            if (games.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(NightPanel)
                            .padding(20.dp)
                    ) {
                        BasicText(
                            if (loading) "Sedang memeriksa koleksi" else "Belum ada game",
                            style = androidx.compose.ui.text.TextStyle(
                                color = NightText,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(Modifier.height(6.dp))
                        BasicText(
                            "Pilih folder yang berisi file NSP/XCI milikmu. File game, prod.keys dan firmware tidak disertakan di aplikasi.",
                            style = androidx.compose.ui.text.TextStyle(
                                color = NightMuted,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        )
                        Spacer(Modifier.height(14.dp))
                        ActionButton(
                            label = "Pilih folder game",
                            detail = "Gunakan pemilih folder Android",
                            onClick = actions.onAddGames,
                            primary = true
                        )
                    }
                }
            } else {
                items(games, key = { it.path }) { game ->
                    GameCard(game = game, actions = actions)
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(NightPanel)
                        .padding(16.dp)
                ) {
                    BasicText(
                        "Runtime Tools",
                        style = androidx.compose.ui.text.TextStyle(
                            color = NightText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    ActionButton(
                        label = "Pengaturan emulator",
                        detail = "Kontrol grafis, CPU, audio dan input",
                        onClick = actions.onSettings
                    )
                    Spacer(Modifier.height(8.dp))
                    ActionButton(
                        label = "Kelola folder game",
                        detail = "Atur lokasi koleksi NSP/XCI",
                        onClick = actions.onManageFolders
                    )
                    Spacer(Modifier.height(8.dp))
                    ActionButton(
                        label = "Pasang update / DLC",
                        detail = "Gunakan file konten milikmu",
                        onClick = actions.onInstallContent
                    )
                    Spacer(Modifier.height(8.dp))
                    ActionButton(
                        label = "Statistik sesi",
                        detail = "Lihat aktivitas emulasi",
                        onClick = actions.onStatistics
                    )
                }
            }

            item {
                BasicText(
                    "NIGHTFALL is an independent community build. It is not affiliated with Nintendo or Team Cherry.",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        color = NightMuted,
                        fontSize = 10.sp,
                        lineHeight = 15.sp
                    )
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (showProfiles) {
        Dialog(onDismissRequest = { showProfiles = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(NightPanel)
                    .padding(18.dp)
            ) {
                BasicText(
                    "MediaTek Tuning",
                    style = androidx.compose.ui.text.TextStyle(
                        color = NightText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(5.dp))
                BasicText(
                    "Profil ini mengubah opsi emulator yang tersedia. Aplikasi tidak mengatur clock CPU/GPU atau governor sistem.",
                    style = androidx.compose.ui.text.TextStyle(
                        color = NightMuted,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                )
                Spacer(Modifier.height(16.dp))
                ActionButton(
                    label = "G100 / G99 · Performance",
                    detail = "0.75x · GPU Fast · async shader · 2 worker",
                    onClick = {
                        MediaTekProfiles.applyPerformance()
                        appliedProfile = "G100/G99 Performance"
                        showProfiles = false
                    },
                    primary = true
                )
                Spacer(Modifier.height(9.dp))
                ActionButton(
                    label = "G100 / G99 · Compatibility",
                    detail = "0.5x · GPU Accurate · async shader · 2 worker",
                    onClick = {
                        MediaTekProfiles.applyCompatibility()
                        appliedProfile = "G100/G99 Compatibility"
                        showProfiles = false
                    }
                )
                Spacer(Modifier.height(9.dp))
                ActionButton(
                    label = "G100 / G99 · Cooler",
                    detail = "0.5x · GPU Fast · AA off · 2 worker",
                    onClick = {
                        MediaTekProfiles.applyCooler()
                        appliedProfile = "G100/G99 Cooler"
                        showProfiles = false
                    }
                )
                Spacer(Modifier.height(14.dp))
                BasicText(
                    "Catatan: preset belum diuji pada semua firmware Helio G100/G99. Mode Fast dapat menimbulkan masalah visual pada game tertentu; pilih Compatibility bila ada glitch.",
                    style = androidx.compose.ui.text.TextStyle(
                        color = NightWarning,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                )
                Spacer(Modifier.height(12.dp))
                SmallButton("Tutup", onClick = { showProfiles = false })
            }
        }
    }
}

@Composable
private fun GameCard(game: Game, actions: LibraryActions) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(NightPanel)
            .padding(16.dp)
    ) {
        BasicText(
            game.title.ifBlank { "Nintendo Switch Game" },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = androidx.compose.ui.text.TextStyle(
                color = NightText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        )
        val metadata = listOf(game.developer, game.version).filter { it.isNotBlank() }.joinToString(" · ")
        if (metadata.isNotBlank()) {
            Spacer(Modifier.height(5.dp))
            BasicText(
                metadata,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = androidx.compose.ui.text.TextStyle(
                    color = NightMuted,
                    fontSize = 11.sp
                )
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SmallButton("Mainkan", onClick = { actions.onLaunch(game) }, primary = true)
            SmallButton("Detail", onClick = { actions.onDetails(game) })
            SmallButton("Preset", onClick = { actions.onPerformance(game) })
        }
    }
}

@Composable
private fun ActionButton(
    label: String,
    detail: String,
    onClick: () -> Unit,
    primary: Boolean = false
) {
    val background = if (primary) NightAccent else NightPanelRaised
    val foreground = if (primary) NightAccentText else NightText
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        BasicText(
            label,
            style = androidx.compose.ui.text.TextStyle(
                color = foreground,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(3.dp))
        BasicText(
            detail,
            style = androidx.compose.ui.text.TextStyle(
                color = if (primary) Color(0xFF23403D) else NightMuted,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun SmallButton(
    label: String,
    onClick: () -> Unit,
    primary: Boolean = false
) {
    val background = if (primary) NightAccent else NightPanelRaised
    val foreground = if (primary) NightAccentText else NightText
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            label,
            style = androidx.compose.ui.text.TextStyle(
                color = foreground,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
