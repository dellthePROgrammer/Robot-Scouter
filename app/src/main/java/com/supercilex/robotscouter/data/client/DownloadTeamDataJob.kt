package com.supercilex.robotscouter.data.client

import android.os.Build
import android.support.annotation.RequiresApi
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.supercilex.robotscouter.data.model.Team
import com.supercilex.robotscouter.data.remote.TbaDownloader
import com.supercilex.robotscouter.util.data.model.isStale
import com.supercilex.robotscouter.util.data.model.update
import com.supercilex.robotscouter.util.data.startInternetJob14
import com.supercilex.robotscouter.util.data.startInternetJob21

fun Team.startDownloadDataJob() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        startInternetJob21(number.toInt(), DownloadTeamDataJob21::class.java)
    } else {
        startInternetJob14(number.toInt(), DownloadTeamDataJob14::class.java)
    }
}

interface DownloadTeamDataJob : TeamJob {
    override val updateTeam: (team: Team, newTeam: Team) -> Unit
        get() = { team, newTeam -> team.update(newTeam) }

    override fun startTask(
            originalTeam: Team,
            existingFetchedTeam: Team
    ): Task<Team?> = if (existingFetchedTeam.isStale) {
        TbaDownloader.load(existingFetchedTeam)
    } else {
        Tasks.forResult(null)
    }
}

class DownloadTeamDataJob14 : TbaJobBase14(), DownloadTeamDataJob

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DownloadTeamDataJob21 : TbaJobBase21(), DownloadTeamDataJob
