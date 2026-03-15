import { BrowserRouter as Router, Routes, Route, Link, useLocation, Navigate } from 'react-router-dom'
import Dashboard from './components/Dashboard'
import LiveFeed from './components/LiveFeed'
import UserProfile from './components/UserProfile'
import './App.css'

function Navigation() {
  const location = useLocation()
  
  const isActive = (path: string) => {
    return location.pathname === path ? 'active' : ''
  }

  return (
    <nav className="navbar">
      <h1>Team Management Portal</h1>
      <div className="nav-links">
        <Link to="/dashboard" className={isActive('/dashboard')}>Dashboard</Link>
        <Link to="/feed" className={isActive('/feed')}>Live Feed</Link>
        <Link to="/profile" className={isActive('/profile')}>Profile</Link>
      </div>
    </nav>
  )
}

function App() {
  return (
    <Router>
      <div className="app">
        <Navigation />
        
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/feed" element={<LiveFeed />} />
            <Route path="/profile" element={<UserProfile />} />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App