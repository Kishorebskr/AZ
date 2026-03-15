interface UserData {
  name: string
  role: string
  email: string
  lastActive: string
}

interface ProfileCardProps {
  user: UserData
}

function ProfileCard({ user }: ProfileCardProps) {
  return (
    <div className="profile-card">
      <div className="profile-avatar">
        {user.name.split(' ').map(n => n[0]).join('')}
      </div>
      <div className="profile-details">
        <h3 className="profile-name">{user.name}</h3>
        <p className="profile-role">{user.role}</p>
        <p className="profile-email">{user.email}</p>
        <p className="profile-last-active">
          Last active: {user.lastActive}
        </p>
      </div>
    </div>
  )
}

export default ProfileCard