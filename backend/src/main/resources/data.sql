-- Teams
INSERT INTO teams (id, name, members, active_projects, completed_this_month, efficiency) VALUES
(1, 'Engineering', 12, 8, 23, 87),
(2, 'Sales', 8, 15, 42, 92),
(3, 'Marketing', 6, 11, 18, 78),
(4, 'Support', 10, 5, 156, 94);

-- Users
INSERT INTO users (id, name, email, team_id) VALUES
(1, 'Alex Johnson', 'alex.johnson@company.com', 1),
(2, 'Sarah Chen', 'sarah.chen@company.com', 1),
(3, 'Mike Peters', 'mike.peters@company.com', 1),
(4, 'Emily Davis', 'emily.davis@company.com', 2),
(5, 'James Wilson', 'james.wilson@company.com', 2),
(6, 'Lisa Brown', 'lisa.brown@company.com', 3),
(7, 'David Kim', 'david.kim@company.com', 3),
(8, 'Anna Martinez', 'anna.martinez@company.com', 4),
(9, 'Tom Anderson', 'tom.anderson@company.com', 4),
(10, 'Rachel Lee', 'rachel.lee@company.com', 1);

-- Activities for Live Feed
INSERT INTO activities (id, action, timestamp, user_id) VALUES
(1, 'Reviewed pull request #234', CURRENT_TIMESTAMP - INTERVAL '5' MINUTE, 1),
(2, 'Deployed new release v2.3.1', CURRENT_TIMESTAMP - INTERVAL '12' MINUTE, 2),
(3, 'Fixed bug in authentication module', CURRENT_TIMESTAMP - INTERVAL '25' MINUTE, 3),
(4, 'Updated project documentation', CURRENT_TIMESTAMP - INTERVAL '45' MINUTE, 1),
(5, 'Merged feature branch to main', CURRENT_TIMESTAMP - INTERVAL '1' HOUR, 4),
(6, 'Completed code review for PR #567', CURRENT_TIMESTAMP - INTERVAL '2' HOUR, 5),
(7, 'Attended team standup meeting', CURRENT_TIMESTAMP - INTERVAL '3' HOUR, 6),
(8, 'Created new Jira ticket for bug', CURRENT_TIMESTAMP - INTERVAL '4' HOUR, 7),
(9, 'Responded to customer inquiry', CURRENT_TIMESTAMP - INTERVAL '5' HOUR, 8),
(10, 'Updated sprint backlog', CURRENT_TIMESTAMP - INTERVAL '6' HOUR, 9),
(11, 'Commented on issue #156', CURRENT_TIMESTAMP - INTERVAL '7' HOUR, 10),
(12, 'Pushed hotfix to production', CURRENT_TIMESTAMP - INTERVAL '8' HOUR, 1),
(13, 'Reviewed security audit report', CURRENT_TIMESTAMP - INTERVAL '9' HOUR, 2),
(14, 'Optimized database queries', CURRENT_TIMESTAMP - INTERVAL '10' HOUR, 3),
(15, 'Set up CI/CD pipeline', CURRENT_TIMESTAMP - INTERVAL '11' HOUR, 4);

-- More activities for user 1 (Alex Johnson) for profile page
INSERT INTO activities (id, action, timestamp, user_id) VALUES
(16, 'Started new feature development', CURRENT_TIMESTAMP - INTERVAL '1' DAY, 1),
(17, 'Participated in architecture review', CURRENT_TIMESTAMP - INTERVAL '2' DAY, 1),
(18, 'Mentored junior developer', CURRENT_TIMESTAMP - INTERVAL '3' DAY, 1),
(19, 'Wrote unit tests for API module', CURRENT_TIMESTAMP - INTERVAL '4' DAY, 1),
(20, 'Presented at tech talk', CURRENT_TIMESTAMP - INTERVAL '5' DAY, 1);
