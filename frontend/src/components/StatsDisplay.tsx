interface TeamStats {
  members: number
  activeProjects: number
  completedThisMonth: number
  efficiency: number
}

interface StatsDisplayProps {
  stats: TeamStats | null
  teamName: string
}

function StatsDisplay({ stats, teamName }: StatsDisplayProps) {
  if (!stats) {
    return <div className="stats-empty">No statistics available</div>
  }

  return (
    <div className="stats-container">
      <h3 className="stats-title">
        {teamName.charAt(0).toUpperCase() + teamName.slice(1)} Team Statistics
      </h3>
      
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-label">Team Members</div>
          <div className="stat-value">{stats.members}</div>
        </div>
        
        <div className="stat-card">
          <div className="stat-label">Active Projects</div>
          <div className="stat-value">{stats.activeProjects}</div>
        </div>
        
        <div className="stat-card">
          <div className="stat-label">Completed This Month</div>
          <div className="stat-value">{stats.completedThisMonth}</div>
        </div>
        
        <div className="stat-card">
          <div className="stat-label">Team Efficiency</div>
          <div className="stat-value">{stats.efficiency}%</div>
        </div>
      </div>
    </div>
  )
}

export default StatsDisplay