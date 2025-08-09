package com.middleman.composables.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.dot.Dot
import com.middleman.composables.dot.Options
import com.middleman.composables.profile.ProfileImage
import com.project.middleman.core.common.toTimeAgo
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.lightColorAccent
import com.project.middleman.designsystem.themes.surfaceBrandLight
import com.project.middleman.designsystem.themes.white


@Composable
fun ChallengeCard(
    challenge: Challenge,
    onChallengeClick: (Challenge) -> Unit
) {

    val creator =  challenge.participant.entries.find {
        it.value.status == "Creator"
    }?.value

    val participant =  challenge.participant.entries.find {
        it.value.status == "Participant"
    }?.value

    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (photo, name, row1, category, option) = createRefs()

            ProfileImage(
                modifier = Modifier
                    .size(35.dp)
                    .constrainAs(photo) {
                        top.linkTo(parent.top, margin = 4.dp)
                        start.linkTo(parent.start)
                    },
                imageUrl = creator?.photoUrl,
            )

            Text(
                text = "@" + (creator?.displayName ?: "Unknown"),
                style = Typography.labelMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(photo.end, margin = 8.dp)
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(row1) {
                    top.linkTo(name.bottom, margin = 4.dp)
                    start.linkTo(photo.end, margin = 8.dp)
                }
            ) {
                Text(
                    text = challenge.createdAt.toTimeAgo(),
                    style = Typography.labelSmall.copy(fontSize = 12.sp),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(6.dp))

                Dot(size = 6.dp, color = Color.Gray)

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = if (challenge.visibility) "Public" else "Invite-Only",
                    style = Typography.labelSmall.copy(fontSize = 12.sp),
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier.constrainAs(option) {
                    top.linkTo(category.bottom, margin = 4.dp)
                    end.linkTo(parent.end)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = colorAccent,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = challenge.category,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                        fontWeight = FontWeight.Bold,
                        color = white
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Options()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = challenge.title,
            style = Typography.labelSmall.copy(fontSize = 15.sp),
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = challenge.description,
            style = Typography.labelSmall.copy(fontSize = 14.sp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clickable { onChallengeClick(challenge) }
                .background(
                    color = surfaceBrandLight,
                    shape = RoundedCornerShape(6.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Stake",
                style = Typography.labelSmall.copy(fontSize = 14.sp),
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "$${challenge.payoutAmount}",
                style = Typography.titleMedium.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Bold,
                color = colorAccent
            )
        }
    }
}
