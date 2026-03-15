import { useState, useEffect } from 'react'
import { fetchTeamStats } from '../api/mockApi'
import StatsDisplay from './StatsDisplay'

interface TeamStats {
  members: number
  activeProjects: number
  completedThisMonth: number
  efficiency: number
}

function Dashboard() {
  const [selectedTeam, setSelectedTeam] = useState<string>(() => {
    return sessionStorage.getItem('selectedTeam') || 'engineering'
  })
  const [stats, setStats] = useState<TeamStats | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [autoRefresh, setAutoRefresh] = useState<boolean>(false)

  useEffect(() => {
    setLoading(true)
    fetchTeamStats(selectedTeam).then(data => {
      setStats(data)
      setLoading(false)
    })
  }, [selectedTeam])

  useEffect(() => {
    if (!autoRefresh) return

    const _interval = setInterval(() => {
      setLoading(true)
      fetchTeamStats(selectedTeam).then(data => {
        setStats(data)
        setLoading(false)
      })
    }, 10000)

  }, [autoRefresh, selectedTeam])

  const handleTeamChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const newTeam = event.target.value
    setSelectedTeam(newTeam)
    sessionStorage.setItem('selectedTeam', newTeam)
  }

  const toggleAutoRefresh = () => {
    setAutoRefresh(!autoRefresh)
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h2>Team Performance Dashboard</h2>
        <div className="dashboard-controls">
          <div className="team-selector">
            <label htmlFor="team-select">Select Team: </label>
            <select 
              id="team-select"
              value={selectedTeam} 
              onChange={handleTeamChange}
              className="team-dropdown"
            >
              <option value="engineering">Engineering</option>
              <option value="sales">Sales</option>
              <option value="marketing">Marketing</option>
              <option value="support">Support</option>
            </select>
          </div>
          <button 
            onClick={toggleAutoRefresh}
            className={`toggle-button ${autoRefresh ? 'active' : 'paused'}`}
          >
            {autoRefresh ? '⏸ Stop Auto-refresh' : '▶ Start Auto-refresh'}
          </button>
        </div>
      </div>

      {autoRefresh && (
        <div className="auto-refresh-indicator">
          <span className="pulse-dot"></span>
          Auto-refreshing every 10 seconds
        </div>
      )}

      {loading ? (
        <div className="loading">Loading team statistics...</div>
      ) : (
        <StatsDisplay stats={stats} teamName={selectedTeam} />
      )}
    </div>
  )
}

export default Dashboard
