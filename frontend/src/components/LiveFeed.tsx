import { useState, useEffect } from 'react'
import { fetchFeedActivity } from '../api/mockApi'
import ActivityCard from './ActivityCard'

interface Activity {
  id: number
  action: string
  timestamp: string
}

function LiveFeed() {
  const [activities, setActivities] = useState<Activity[]>([])
  const [isActive, setIsActive] = useState<boolean>(true)

  useEffect(() => {
    const loadInitialActivities = async () => {
      const initial = await fetchFeedActivity()
      setActivities(initial)
    }

    loadInitialActivities()
  }, [])

  useEffect(() => {
    if (!isActive) return

    const interval = setInterval(async () => {
      const newActivities = await fetchFeedActivity()
      setActivities(prev => [...newActivities, ...prev].slice(0, 100))
    }, 3000)

    return () => clearInterval(interval)
  }, [isActive])

  const toggleFeed = () => {
    setIsActive(!isActive)
  }

  return (
    <div className="live-feed">
      <div className="feed-header">
        <h2>Live Activity Feed</h2>
        <div className="feed-controls">
          <span className="message-counter">
            Showing latest {activities.length} of max 100 activities
          </span>
          <button
            onClick={toggleFeed}
            className={`toggle-button ${isActive ? 'active' : 'paused'}`}
          >
            {isActive ? '⏸ Pause Feed' : '▶ Resume Feed'}
          </button>
        </div>
      </div>

      <div className="feed-status">
        <span className={`status-indicator ${isActive ? 'live' : 'paused'}`}></span>
        <span>{isActive ? 'Live updates active' : 'Feed paused'}</span>
      </div>

      <div className="messages-container">
        {activities.length === 0 ? (
          <div className="no-messages">Waiting for activities...</div>
        ) : (
          activities.map((activity) => (
            <ActivityCard key={activity.id} activity={activity} />
          ))
        )}
      </div>
    </div>
  )
}

export default LiveFeed
