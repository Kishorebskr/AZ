interface Activity {
  id: number
  action: string
  timestamp: string
}

interface ActivityCardProps {
  activity: Activity
}

function ActivityCard({ activity }: ActivityCardProps) {
  return (
    <div className="activity-card">
      <div className="activity-indicator" />
      <div className="activity-content">
        <p className="activity-action">{activity.action}</p>
        <span className="activity-timestamp">{activity.timestamp}</span>
      </div>
    </div>
  )
}

export default ActivityCard
